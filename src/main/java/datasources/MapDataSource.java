package datasources;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MapDataSource<K,V> implements DataSource<K,V>{

    private Map<K,V> datasource = new ConcurrentHashMap<K, V>();

    public Collection<V> getAll() {
        return datasource.values();
    }

    public V get(K key) {
        return datasource.get(key);
    }

    public void put(K key, V val){
        datasource.put(key,val);
    }

    public Set<Map.Entry<K, V>> entrySet(){
        return datasource.entrySet();
    }
}
