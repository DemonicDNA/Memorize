package datastructures.collections;

import java.util.Collection;

public interface InMemoryCollection<T> {
     public void putAll(Collection<T> dataCollection);
     public Collection<T> getAll();
}
