package synchronizer;

import datasources.DataSource;
import maps.InMemoryMapPersistence;
import maps.MapPersistence;

public interface DataMapSynchronizer<K,V> {

    default public void synchronize(DataSource<K, V> dataSource, MapPersistence<K, V> mapPersistence,
                                    InMemoryMapPersistence<K, V> inMemoryMapPersistence) {

        dataSource.entrySet().forEach(kvEntry -> {
            mapPersistence.put(kvEntry.getKey(), kvEntry.getValue());
            inMemoryMapPersistence.put(kvEntry.getKey(), kvEntry.getValue());
        });
    }

}
