package database;

import models.bookings.Booking;
import java.util.*;

public class BookingRepository implements Repository<Booking> {

    private Map<Integer, Booking> bookings;
    private int idCounter;

    public BookingRepository() {
        this.bookings = new LinkedHashMap<>();
        this.idCounter = 1;
    }

    @Override
    public Map<Integer, Booking> getItems() {
        return Collections.unmodifiableMap(this.bookings);
    }

    @Override
    public Booking getItemByID(int ID) {
        Booking bookingToReturn = null;
        if (this.bookings.containsKey(ID)) {
            bookingToReturn = bookings.get(ID);
        }
        return bookingToReturn;
    }

    @Override
    public void addItem(Booking booking) {
        bookings.put(idCounter, booking);
        idCounter++;
    }

    @Override
    public int getIDCounter() {
        return this.idCounter;
    }
}
