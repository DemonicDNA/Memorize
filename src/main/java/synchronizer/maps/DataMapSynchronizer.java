package synchronizer.maps;

import datasources.maps.MapDataSource;
import datastructures.maps.InMemoryMap;
import datastructures.maps.MapPersistence;

public interface DataMapSynchronizer<K,V> {

    default public void synchronize(MapDataSource<K, V> mapDataSource, MapPersistence<K, V> mapPersistence,
                                    InMemoryMap<K, V> inMemoryMap) {

        mapDataSource.entrySet().ifPresent(entries -> entries.forEach(kvEntry -> {
            mapPersistence.put(kvEntry.getKey(), kvEntry.getValue());
            inMemoryMap.put(kvEntry.getKey(), kvEntry.getValue());
        }));
    }

}
