package datastructures.maps;

import java.util.Collection;
import java.util.Optional;

public interface MapPersistence<K,V> {

    public Optional<V> put(K key, V value);
    public Optional<V> remove(K key);
    public Optional<V> get(K key);
    public Optional<Collection<V>> values();
}
