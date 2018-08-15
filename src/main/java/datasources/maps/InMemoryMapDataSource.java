package datasources.maps;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMapDataSource<K,V> implements MapDataSource<K,V> {

    private Map<K,V> mapDataSource = new ConcurrentHashMap<K, V>();

    @Override
    public Optional<Collection<V>> values() {
        return Optional.of(mapDataSource.values());
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(mapDataSource.get(key));
    }

    @Override
    public Optional<Set<Map.Entry<K, V>>> entrySet(){
        return Optional.of(mapDataSource.entrySet());
    }

    public void put(K key, V val){
        mapDataSource.put(key,val);
    }

}
