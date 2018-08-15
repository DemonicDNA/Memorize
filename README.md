# Memorize
Given a DataSource (might be REST, or any other DataSource you implement) the MemoryManager will allow the service to keep functioning even if the DataSource service stopped being reachable.

Note: Right now it is implemented for Maps and Collections.

# How to use (Maps):
1) Implement the MapDataSource interface (Should usually be a service that exposes data i.e. REST Service).
2) Implement the MapPersistence interface (Should be implementation of a persistence for the data i.e. Redis).
3) Implement the InMemoryMap (Should be an in memory implementation i.e. Guava or standard java map).
4) Implement DataMapSynchronizer or use the DefaultDataMapSynchronizer to synchronize the data between the DataSource and the Persistence Maps.

```java
MapMemoryManager<UUID, TestObject> inMemoryMapMemoryManager =
                new MapMemoryManager<>(dataSource, persistence, inMemoryPersistence, defaultDataSynchronizer);
//will get the value for a given key from the DataSource if possible, then from Persistence if possible, then from InMemory
mapMemoryManager.get(key); 
//will get all values from the DataSource if possible, then from Persistence if possible, then from InMemory
mapMemoryManager.values();
```


# How to use (Collections):
1) Implement the CollectionDataSource interface (Should usually be a service that exposes data i.e. REST Service).
2) Implement the CollectionPersistence interface (Should be implementation of a persistence for the data i.e. Redis).
3) Implement the InMemoryCollection (Should be an in memory implementation i.e. Guava or standard java collection).
4) Implement DataCollectionSynchronizer or use the DefaultDataCollectionSynchronizer to synchronize the data between the DataSource and the Persistence Maps.

```java
CollectionMemoryManager<TestObject> collectionMemoryManager =
                new CollectionMemoryManager<>(dataSource, persistence, inMemoryPersistence, defaultDataSynchronizer);
//will get all from the DataSource if possible, then from Persistence if possible, then from InMemory
collectionMemoryManager.getAll();
```



