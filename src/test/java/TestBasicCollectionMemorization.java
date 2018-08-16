import datasources.collections.InMemoryCollectionDataSource;
import datastructures.collections.ArrayListAsInMemoryCollection;
import datastructures.collections.ArrayListAsPersistence;
import datastructures.collections.CollectionPersistence;
import datastructures.collections.InMemoryCollection;
import managment.CollectionMemoryManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import synchronizer.collections.DataCollectionSynchronizer;
import synchronizer.collections.DefaultDataCollectionSynchronizer;
import utils.TestObject;

import java.util.Arrays;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@Test
public class TestBasicCollectionMemorization {

    private InMemoryCollectionDataSource<TestObject> dataSource;
    private CollectionPersistence<TestObject> persistence;
    private InMemoryCollection<TestObject> inMemoryPersistence;
    private DataCollectionSynchronizer<TestObject> defaultDataSynchronizer = new DefaultDataCollectionSynchronizer<>();

    @BeforeMethod
    void setUp(){
        dataSource = new InMemoryCollectionDataSource<>();
        persistence = new ArrayListAsPersistence<>();
        inMemoryPersistence = new ArrayListAsInMemoryCollection<>();
    }

    @Test
    void testGetAllFromDataSource() throws InterruptedException {

        CollectionMemoryManager<TestObject> collectionMemoryManager =
                new CollectionMemoryManager<>(dataSource, persistence, inMemoryPersistence, defaultDataSynchronizer);

        dataSource.putAll(Arrays.asList(new TestObject(UUID.randomUUID(), "Guy"),
                                        new TestObject(UUID.randomUUID(), "Tal")));


        Thread.sleep(700);
        assertEquals(collectionMemoryManager.getAll().size(), 2);
        assertEquals(dataSource.getAll().get().size(), 2);
        assertEquals(persistence.getAll().get(), dataSource.getAll().get());
        assertEquals(persistence.getAll().get().size(),2);
        assertEquals(inMemoryPersistence.getAll(), dataSource.getAll().get());
        assertEquals(inMemoryPersistence.getAll().size(), 2);
    }

    @Test
    void testGetAllFromPersistence(){

        CollectionMemoryManager<TestObject> collectionMemoryManager =
                new CollectionMemoryManager<>(dataSource, persistence, inMemoryPersistence, defaultDataSynchronizer);

        persistence.putAll(Arrays.asList(new TestObject(UUID.randomUUID(), "Guy"),
                new TestObject(UUID.randomUUID(), "Tal")));

        assertNotNull(collectionMemoryManager.getAll());
        assertEquals(collectionMemoryManager.getAll().size(), 2);
    }

    @Test
    void testGetAllFromInMemory(){

        CollectionMemoryManager<TestObject> collectionMemoryManager =
                new CollectionMemoryManager<>(dataSource, persistence, inMemoryPersistence, defaultDataSynchronizer);

        inMemoryPersistence.putAll(Arrays.asList(new TestObject(UUID.randomUUID(), "Guy"),
                new TestObject(UUID.randomUUID(), "Tal")));

        assertNotNull(collectionMemoryManager.getAll());
        assertEquals(collectionMemoryManager.getAll().size(), 2);
    }

    @Test
    void testGetAll_nonExistent(){

        CollectionMemoryManager<TestObject> collectionMemoryManager =
                new CollectionMemoryManager<>(dataSource, persistence, inMemoryPersistence, defaultDataSynchronizer);

        assertTrue(collectionMemoryManager.getAll().isEmpty());
    }


}
