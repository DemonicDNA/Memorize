package maps;

import java.util.Collection;

public interface MapPersistence<K,V> {

    public V put(K key, V value);
    public V remove(K key);
    public V get(K key);
    public Collection<V> values();
}
