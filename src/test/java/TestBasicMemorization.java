import datasources.MapDataSource;
import managment.AbstractMapMemoryManager;
import managment.DefaultMapMemoryManager;
import maps.InMemoryMap;
import maps.InMemoryMapPersistence;
import maps.MapPersistence;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import synchronizer.DataMapSynchronizer;
import synchronizer.DefaultDataMapSynchronizer;
import utils.TestObject;

import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

@Test
public class TestBasicMemorization {

    private MapDataSource<UUID, TestObject> dataSource;
    private MapPersistence<UUID, TestObject> mapPersistence;
    private InMemoryMapPersistence<UUID, TestObject> inMemoryMapPersistence;
    private DataMapSynchronizer<UUID, TestObject> objectDataMapSynchronizer = new DefaultDataMapSynchronizer<>();

    @BeforeMethod
    void setUp(){
        dataSource = new MapDataSource<>();
        mapPersistence = new InMemoryMap<>();
        inMemoryMapPersistence = new InMemoryMap<>();
    }


    @Test
    void testGetFromDataSource() throws InterruptedException {

        AbstractMapMemoryManager<UUID, TestObject> inMemoryMapMemoryManager =
                new DefaultMapMemoryManager<>(dataSource, mapPersistence, inMemoryMapPersistence, objectDataMapSynchronizer);
        final UUID uuid = UUID.randomUUID();
        dataSource.put(uuid, new TestObject(uuid, "Guy"));

        assertNotNull(inMemoryMapMemoryManager.get(uuid));
        Thread.sleep(700);
        assertEquals(mapPersistence.get(uuid), dataSource.get(uuid));
        assertEquals(inMemoryMapPersistence.get(uuid), dataSource.get(uuid));
    }

    @Test
    void testGetFromMapPersistence(){

        AbstractMapMemoryManager<UUID, TestObject> inMemoryMapMemoryManager =
                new DefaultMapMemoryManager<>(dataSource, mapPersistence, inMemoryMapPersistence, objectDataMapSynchronizer);
        final UUID uuid = UUID.randomUUID();
        mapPersistence.put(uuid, new TestObject(uuid, "Guy"));

        assertNotNull(inMemoryMapMemoryManager.get(uuid));
    }

    @Test
    void testGetFromInMemoryMapPersistence(){

        AbstractMapMemoryManager<UUID, TestObject> inMemoryMapMemoryManager =
                new DefaultMapMemoryManager<>(dataSource, mapPersistence, inMemoryMapPersistence, objectDataMapSynchronizer);
        final UUID uuid = UUID.randomUUID();
        inMemoryMapPersistence.put(uuid, new TestObject(uuid, "Guy"));

        assertNotNull(inMemoryMapMemoryManager.get(uuid));
    }

    @Test
    void testGetFromInMemoryMapPersistence_nonExistent(){

        AbstractMapMemoryManager<UUID, TestObject> inMemoryMapMemoryManager =
                new DefaultMapMemoryManager<>(dataSource, mapPersistence, inMemoryMapPersistence, objectDataMapSynchronizer);
        final UUID uuid = UUID.randomUUID();

        assertNull(inMemoryMapMemoryManager.get(uuid));
    }
}
