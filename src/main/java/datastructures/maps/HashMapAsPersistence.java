package datastructures.maps;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapAsPersistence<K,V> implements MapPersistence<K,V> {

    private Map<K,V> inMemoryMap = new ConcurrentHashMap<K, V>();

    @Override
    public Optional<V> put(K key, V value) {
        return Optional.ofNullable(inMemoryMap.put(key,value));
    }

    @Override
    public Optional<V> remove(K key) {
        return Optional.ofNullable(inMemoryMap.remove(key));
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(inMemoryMap.get(key));
    }

    @Override
    public Optional<Collection<V>> values() {
        return Optional.of(inMemoryMap.values());
    }
}
