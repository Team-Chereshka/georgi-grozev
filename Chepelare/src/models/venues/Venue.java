package models.venues;

import exceptions.ExceptionMessages;
import models.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class Venue {
    private String name;
    private String address;
    private String description;
    private List<Room> rooms;
    private int id;

    public Venue(String name, String address, String description) {
        this.setName(name);
        this.setAddress(address);
        this.description = description;
        this.rooms = new ArrayList<>();
    }

    public void setName(String name) {
        if (name.length() >= 3) {
            this.name = name;
        } else {
            throw new IllegalArgumentException(String.format(ExceptionMessages.ILLEGAL_LENGTH_MESSAGE, "venue name", 3));
        }
    }

    public void setAddress(String address) {
        if (address.length() >= 3) {
            this.address = address;
        } else {
            throw new IllegalArgumentException(String.format(ExceptionMessages.ILLEGAL_LENGTH_MESSAGE, "venue address", 3));
        }
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getDescription() {
        return this.description;
    }


    public List<Room> getRooms() {
        return this.rooms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }
}
