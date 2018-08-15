package maps;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMap<K,V> implements InMemoryMapPersistence<K,V>{

    private Map<K,V> inMemoryMap = new ConcurrentHashMap<K, V>();

    public V put(K key, V value) {
        return inMemoryMap.put(key, value);
    }

    public V remove(K key) {
        return inMemoryMap.remove(key);
    }

    public V get(K key) {
        return inMemoryMap.get(key);
    }

    @Override
    public Collection<V> values() {
        return inMemoryMap.values();
    }
}
