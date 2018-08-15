package datastructures.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class ArrayListAsPersistence<T> implements CollectionPersistence<T> {

    private Collection<T> collection = new ArrayList<>();

    @Override
    public void putAll(Collection<T> dataCollection) {
        collection = dataCollection;
    }

    @Override
    public Optional<Collection<T>> getAll() {
        return Optional.ofNullable(collection);
    }
}
