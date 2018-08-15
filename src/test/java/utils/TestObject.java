package utils;

import java.util.UUID;

public class TestObject {

    private UUID uuid;
    private String name;

    public TestObject(UUID uuid, String string) {
        this.uuid = uuid;
        this.name = string;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
