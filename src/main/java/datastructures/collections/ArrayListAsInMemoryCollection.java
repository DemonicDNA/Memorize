package datastructures.collections;

import java.util.ArrayList;
import java.util.Collection;

public class ArrayListAsInMemoryCollection<T> implements InMemoryCollection<T> {
    private Collection<T> collection = new ArrayList<>();

    @Override
    public void putAll(Collection<T> dataCollection) {
        this.collection = dataCollection;
    }

    @Override
    public Collection<T> getAll() {
        return collection;
    }
}
