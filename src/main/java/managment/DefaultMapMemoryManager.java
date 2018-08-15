package managment;

import datasources.DataSource;
import maps.InMemoryMapPersistence;
import maps.MapPersistence;
import synchronizer.DataMapSynchronizer;

import java.util.Collection;
import java.util.Optional;

public class DefaultMapMemoryManager<K,V> extends AbstractMapMemoryManager<K,V>{

    public DefaultMapMemoryManager(DataSource<K,V> dataSource, MapPersistence<K, V> mapPersistence, InMemoryMapPersistence<K, V> inMemoryPersistence,
                                   DataMapSynchronizer<K,V> dataMapSynchronizer) {
        super(dataSource, mapPersistence, inMemoryPersistence, dataMapSynchronizer);
    }

    @Override
    protected Optional<V> getFromDataSource(K key) {
        return Optional.ofNullable(dataSource.get(key));
    }

    @Override
    protected Optional<Collection<V>> getAllFromDataSource() {
        return Optional.ofNullable(dataSource.getAll());
    }

    @Override
    protected Optional<V> getFromMapPersistence(K key) {
        return Optional.ofNullable(mapPersistence.get(key));
    }

    @Override
    protected Optional<Collection<V>> getAllFromMapPersistence() {
        return Optional.ofNullable(mapPersistence.values());
    }
}
