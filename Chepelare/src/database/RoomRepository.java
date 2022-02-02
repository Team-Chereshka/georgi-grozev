package database;
import models.rooms.Room;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class RoomRepository implements Repository<Room> {

    private Map<Integer, Room> rooms;
    private int idCounter;

    public RoomRepository() {
        this.rooms = new LinkedHashMap<>();
        this.idCounter = 1;
    }

    @Override
    public Map<Integer, Room> getItems() {
        return Collections.unmodifiableMap(this.rooms);
    }

    @Override
    public Room getItemByID(int ID) {
        Room roomToReturn = null;
        if (this.rooms.containsKey(ID)) {
            roomToReturn = rooms.get(ID);
        }
        return roomToReturn;
    }

    @Override
    public void addItem(Room room) {
        rooms.put(idCounter, room);
        idCounter++;
    }

    @Override
    public int getIDCounter() {
        return this.idCounter;
    }
}
