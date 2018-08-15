package managment;

import datasources.DataSource;
import maps.InMemoryMapPersistence;
import maps.MapPersistence;
import synchronizer.DataMapSynchronizer;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class AbstractMapMemoryManager<K,V>{

    protected final DataSource<K,V> dataSource;
    protected final MapPersistence<K,V> mapPersistence;
    private final InMemoryMapPersistence<K,V> inMemoryPersistence;
    private final Object lockObject = new Object();

    public AbstractMapMemoryManager(DataSource<K,V> dataSource, MapPersistence<K,V> mapPersistence,
                                    InMemoryMapPersistence<K,V> inMemoryPersistence, DataMapSynchronizer<K,V> dataMapSynchronizer) {
        this.dataSource = dataSource;
        this.mapPersistence = mapPersistence;
        this.inMemoryPersistence = inMemoryPersistence;
        Executors.newSingleThreadScheduledExecutor().schedule(()->{
            synchronized (lockObject) {
                try {
                    dataMapSynchronizer.synchronize(dataSource, mapPersistence, inMemoryPersistence);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        },500, TimeUnit.MILLISECONDS);
    }

    public V get(K key){
        synchronized (lockObject) {
            Optional<V> fromDataSource = getFromDataSource(key);
            return fromDataSource.orElseGet(() -> {
                Optional<V> fromMapPersistence = getFromMapPersistence(key);
                return fromMapPersistence.orElseGet(() -> inMemoryPersistence.get(key));
            });
        }
    }

    public Collection<V> getAll(){
        synchronized (lockObject) {
            Optional<Collection<V>> fromDataSource = getAllFromDataSource();
            return fromDataSource.orElseGet(() -> {
                Optional<Collection<V>> fromMapPersistence = getAllFromMapPersistence();
                return fromMapPersistence.orElseGet(inMemoryPersistence::values);
            });
        }
    }

    protected abstract Optional<V> getFromDataSource(K key);
    protected abstract Optional<Collection<V>> getAllFromDataSource();

    protected abstract Optional<V> getFromMapPersistence(K key);
    protected abstract Optional<Collection<V>> getAllFromMapPersistence();

}
