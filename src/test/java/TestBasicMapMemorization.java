import datasources.maps.InMemoryMapDataSource;
import managment.MapMemoryManager;
import datastructures.maps.HashMapAsInMemoryMap;
import datastructures.maps.HashMapAsPersistence;
import datastructures.maps.InMemoryMap;
import datastructures.maps.MapPersistence;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import synchronizer.maps.DataMapSynchronizer;
import synchronizer.maps.DefaultDataMapSynchronizer;
import utils.TestObject;

import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

@Test
public class TestBasicMapMemorization {

    private InMemoryMapDataSource<UUID, TestObject> dataSource;
    private MapPersistence<UUID, TestObject> persistence;
    private InMemoryMap<UUID, TestObject> inMemoryPersistence;
    private DataMapSynchronizer<UUID, TestObject> defaultDataSynchronizer = new DefaultDataMapSynchronizer<>();

    @BeforeMethod
    void setUp(){
        dataSource = new InMemoryMapDataSource<>();
        persistence = new HashMapAsPersistence<>();
        inMemoryPersistence = new HashMapAsInMemoryMap<>();
    }


    @Test
    void testGetFromDataSource() throws InterruptedException {

        MapMemoryManager<UUID, TestObject> inMemoryMapMemoryManager =
                new MapMemoryManager<>(dataSource, persistence, inMemoryPersistence, defaultDataSynchronizer);
        final UUID uuid = UUID.randomUUID();
        dataSource.put(uuid, new TestObject(uuid, "Guy"));

        assertNotNull(inMemoryMapMemoryManager.get(uuid));
        Thread.sleep(700);
        assertEquals(persistence.get(uuid).get(), dataSource.get(uuid).get());
        assertEquals(inMemoryPersistence.get(uuid), dataSource.get(uuid).get());
    }

    @Test
    void testGetFromMapPersistence(){

        MapMemoryManager<UUID, TestObject> inMemoryMapMemoryManager =
                new MapMemoryManager<>(dataSource, persistence, inMemoryPersistence, defaultDataSynchronizer);
        final UUID uuid = UUID.randomUUID();
        persistence.put(uuid, new TestObject(uuid, "Guy"));

        assertNotNull(inMemoryMapMemoryManager.get(uuid));
    }

    @Test
    void testGetFromInMemoryMapPersistence(){

        MapMemoryManager<UUID, TestObject> inMemoryMapMemoryManager =
                new MapMemoryManager<>(dataSource, persistence, inMemoryPersistence, defaultDataSynchronizer);
        final UUID uuid = UUID.randomUUID();
        inMemoryPersistence.put(uuid, new TestObject(uuid, "Guy"));

        assertNotNull(inMemoryMapMemoryManager.get(uuid));
    }

    @Test
    void testGet_nonExistent(){

        MapMemoryManager<UUID, TestObject> inMemoryMapMemoryManager =
                new MapMemoryManager<>(dataSource, persistence, inMemoryPersistence, defaultDataSynchronizer);
        final UUID uuid = UUID.randomUUID();

        assertNull(inMemoryMapMemoryManager.get(uuid));
    }
}
