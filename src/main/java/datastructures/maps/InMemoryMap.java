package datastructures.maps;

import java.util.Collection;

public interface InMemoryMap<K,V>{
    public V put(K key, V value);
    public V remove(K key);
    public V get(K key);
    public Collection<V> values();
}
