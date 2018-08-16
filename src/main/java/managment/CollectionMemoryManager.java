package managment;

import datastructures.collections.CollectionPersistence;
import datastructures.collections.InMemoryCollection;
import datasources.collections.CollectionDataSource;
import synchronizer.collections.DataCollectionSynchronizer;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CollectionMemoryManager<T> {

    private static final int SYNCHRONIZER_MILLIS_DELAY = 500;
    private final Object lockObject = new Object();
    private final CollectionPersistence<T> collectionPersistence;
    private final InMemoryCollection<T> inMemoryCollection;

    public CollectionMemoryManager(CollectionDataSource<T> collectionDataSource,
                                   CollectionPersistence<T> collectionPersistence,
                                   InMemoryCollection<T> inMemoryCollection,
                                   DataCollectionSynchronizer<T> dataCollectionSynchronizer) {
        this.collectionPersistence = collectionPersistence;
        this.inMemoryCollection = inMemoryCollection;
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            synchronized (lockObject) {
                try {
                    /*
                      we synchronize the data eagerly and not lazy since we want the manager to be as close as possible
                      to the source of truth
                     */
                    dataCollectionSynchronizer.synchronize(collectionDataSource, collectionPersistence, inMemoryCollection);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, SYNCHRONIZER_MILLIS_DELAY, TimeUnit.MILLISECONDS);
    }


    public Collection<T> getAll() {
        synchronized (lockObject) {
            Optional<Collection<T>> fromCollectionPersistence = collectionPersistence.getAll();
            if (fromCollectionPersistence.isPresent() && !fromCollectionPersistence.get().isEmpty()) {
                return fromCollectionPersistence.get();
            }

            return inMemoryCollection.getAll();
        }
    }

}
