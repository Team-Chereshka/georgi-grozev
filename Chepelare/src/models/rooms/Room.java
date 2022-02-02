package models.rooms;

import exceptions.ExceptionMessages;
import models.bookings.Booking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Room {
    private int numberOfPlaces;
    private double pricePerDay;
    private List<Booking> bookings;
    private Map<LocalDate, LocalDate> datesWhenAvailable;
    private int ID;


    public Room(int numberOfPlaces, double pricePerDay) {
        this.setNumberOfPlaces(numberOfPlaces);
        this.setPricePerDay(pricePerDay);
        this.bookings = new ArrayList<>();
        this.datesWhenAvailable = new LinkedHashMap<>();
    }

    public void setNumberOfPlaces(int numberOfPlaces) {
        if (numberOfPlaces >= 0) {
            this.numberOfPlaces = numberOfPlaces;
        } else {
            throw new IllegalArgumentException(String.format(ExceptionMessages.ILLEGAL_VALUE_MESSAGE, "number of places", 0));
        }
    }

    public void setPricePerDay(double pricePerDay) {
        if (pricePerDay >= 0) {
            this.pricePerDay = pricePerDay;
        } else {
            throw new IllegalArgumentException(String.format(ExceptionMessages.ILLEGAL_LENGTH_MESSAGE, "price per day", 0));
        }
    }

    public int getID() {
        return ID;
    }

    public Map<LocalDate, LocalDate> getDatesWhenAvailable() {
        return datesWhenAvailable;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setDatesWhenAvailable(LocalDate startDate, LocalDate endDate) {
        if (datesWhenAvailable.containsKey(startDate)) {
            if (datesWhenAvailable.get(startDate).isBefore(endDate)) {
                datesWhenAvailable.put(startDate, endDate);
            }
        } else {
            datesWhenAvailable.put(startDate, endDate);
        }
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public boolean isAvailable(LocalDate startDate, LocalDate endDate) {
        boolean isAvailable = false;

        for (Map.Entry<LocalDate, LocalDate> entry : datesWhenAvailable.entrySet()) {
            LocalDate availabilityStart = entry.getKey();
            LocalDate availabilityEnd = entry.getValue();
            if (startDate.isAfter(availabilityStart) || startDate.isEqual(availabilityStart)) {
                if (endDate.isBefore(availabilityEnd) || endDate.isEqual(availabilityEnd)) {
                    isAvailable = true;
                    break;
                }
            }
        }
        return isAvailable;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    public void updateAvailability(LocalDate startDate, LocalDate endDate) {
        for (Map.Entry<LocalDate, LocalDate> entry : datesWhenAvailable.entrySet()) {
            LocalDate availabilityStart = entry.getKey();
            LocalDate availabilityEnd = entry.getValue();
            if (startDate.isAfter(availabilityStart) || startDate.isEqual(availabilityStart)) {
                if (endDate.isBefore(availabilityEnd) || endDate.isEqual(availabilityEnd)) {
                    this.datesWhenAvailable.remove(availabilityStart);
                    this.datesWhenAvailable.put(availabilityStart, startDate);
                    this.datesWhenAvailable.put(endDate, availabilityEnd);
                }
            }
            break;
        }
    }
}
