package datasources.collections;

import java.util.Collection;
import java.util.Optional;

public interface CollectionDataSource<T> {
    Optional<Collection<T>> getAll();
}
