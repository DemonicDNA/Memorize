package managment;

import datasources.maps.MapDataSource;
import datastructures.maps.InMemoryMap;
import datastructures.maps.MapPersistence;
import synchronizer.maps.DataMapSynchronizer;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MapMemoryManager<K,V>{

    private final MapDataSource<K,V> mapDataSource;
    private final MapPersistence<K,V> mapPersistence;
    private final InMemoryMap<K,V> inMemoryMap;
    private final int SYNCHRONIZER_MILLIS_DELAY = 500;
    private final Object lockObject = new Object();

    public MapMemoryManager(MapDataSource<K,V> mapDataSource, MapPersistence<K,V> mapPersistence,
                            InMemoryMap<K,V> inMemoryMap, DataMapSynchronizer<K,V> dataMapSynchronizer) {
        this.mapDataSource = mapDataSource;
        this.mapPersistence = mapPersistence;
        this.inMemoryMap = inMemoryMap;
        Executors.newSingleThreadScheduledExecutor().schedule(()->{
            synchronized (lockObject) {
                try {
                    dataMapSynchronizer.synchronize(mapDataSource, mapPersistence, inMemoryMap);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        },SYNCHRONIZER_MILLIS_DELAY, TimeUnit.MILLISECONDS);
    }

    public V get(K key){
        synchronized (lockObject) {
            Optional<V> fromDataSource = mapDataSource.get(key);
            return fromDataSource.orElseGet(() -> {
                Optional<V> fromMapPersistence = mapPersistence.get(key);
                return fromMapPersistence.orElseGet(() -> inMemoryMap.get(key));
            });
        }
    }

    public Collection<V> values(){
        synchronized (lockObject) {
            Optional<Collection<V>> fromDataSource = mapDataSource.values();
            if(fromDataSource.isPresent() && !fromDataSource.get().isEmpty()){
                return fromDataSource.get();
            } else {
                Optional<Collection<V>> fromCollectionPersistence = mapPersistence.values();
                if(fromCollectionPersistence.isPresent() && !fromCollectionPersistence.get().isEmpty()){
                    return fromCollectionPersistence.get();
                }
            }
            return inMemoryMap.values();
        }
    }


}
