package database;


import models.venues.Venue;

import java.util.*;

public class VenueRepository implements Repository<Venue> {
    private Map<Integer, Venue> venues;
    private int idCounter;

    public VenueRepository() {
        this.venues = new LinkedHashMap<>();
        this.idCounter = 1;
    }

    @Override
    public Map<Integer, Venue> getItems() {
        return Collections.unmodifiableMap(this.venues);
    }

    @Override
    public Venue getItemByID(int ID) {
        Venue venueToReturn = null;
        if (this.venues.containsKey(ID)) {
            venueToReturn = venues.get(ID);
        }
        return venueToReturn;
    }

    @Override
    public void addItem(Venue venue) {
        this.venues.put(idCounter, venue);
        this.idCounter++;
    }


    @Override
    public int getIDCounter() {
        return idCounter;
    }
}
