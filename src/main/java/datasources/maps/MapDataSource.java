package datasources.maps;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface MapDataSource<K,V> {

    public Optional<Collection<V>> values();
    public Optional<V> get(K key);
    public Optional<Set<Map.Entry<K, V>>> entrySet();
}
