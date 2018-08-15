package datasources;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface DataSource<K,V> {

    public Collection<V> getAll();
    public V get(K key);
    public Set<Map.Entry<K, V>> entrySet();
}
