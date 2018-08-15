package datasources.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class InMemoryCollectionDataSource<T> implements CollectionDataSource<T> {

    private Collection<T> collection = new ArrayList<>();

    @Override
    public Optional<Collection<T>> getAll() {
        return Optional.ofNullable(collection);
    }

    public void putAll(Collection<T> dataCollection){
        collection = dataCollection;
    }
}
