package synchronizer.collections;

import datastructures.collections.CollectionPersistence;
import datastructures.collections.InMemoryCollection;
import datasources.collections.CollectionDataSource;

public interface DataCollectionSynchronizer<T> {

    default public void synchronize(CollectionDataSource<T> collectionDataSource, CollectionPersistence<T> collectionPersistence,
                                    InMemoryCollection<T> inMemoryCollection) {
        collectionDataSource.getAll().ifPresent(dataCollection-> {
            collectionPersistence.putAll(dataCollection);
            inMemoryCollection.putAll(dataCollection);
        });
    }

}
