package datastructures.collections;

import java.util.Collection;
import java.util.Optional;

public interface CollectionPersistence<T> {
    public void putAll(Collection<T> dataCollection);
    public Optional<Collection<T>> getAll();
}
