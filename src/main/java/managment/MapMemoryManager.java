package managment;

import datasources.maps.MapDataSource;
import datastructures.maps.InMemoryMap;
import datastructures.maps.MapPersistence;
import synchronizer.maps.DataMapSynchronizer;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MapMemoryManager<K,V> {

    private final MapPersistence<K, V> mapPersistence;
    private final InMemoryMap<K, V> inMemoryMap;
    private final int SYNCHRONIZER_MILLIS_DELAY = 500;
    private final Object lockObject = new Object();

    public MapMemoryManager(MapDataSource<K, V> mapDataSource, MapPersistence<K, V> mapPersistence,
                            InMemoryMap<K, V> inMemoryMap, DataMapSynchronizer<K, V> dataMapSynchronizer) {
        this.mapPersistence = mapPersistence;
        this.inMemoryMap = inMemoryMap;
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            synchronized (lockObject) {
                try {
                    /*
                      we synchronize the data eagerly and not lazy since we want the manager to be as close as possible
                      to the source of truth
                     */
                    dataMapSynchronizer.synchronize(mapDataSource, mapPersistence, inMemoryMap);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, SYNCHRONIZER_MILLIS_DELAY, TimeUnit.MILLISECONDS);
    }

    public V get(K key) {
        synchronized (lockObject) {
            Optional<V> fromMapPersistence = mapPersistence.get(key);
            return fromMapPersistence.orElseGet(() -> inMemoryMap.get(key));
        }
    }

    public Collection<V> values() {
        synchronized (lockObject) {
            Optional<Collection<V>> fromMapPersistence = mapPersistence.values();
            if (fromMapPersistence.isPresent() && !fromMapPersistence.get().isEmpty()) {
                return fromMapPersistence.get();
            }
        return inMemoryMap.values();
        }
    }

}
