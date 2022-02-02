package models.users;

import exceptions.ExceptionMessages;
import models.bookings.Booking;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseUserImpl {

    private String username;
    private int passwordHash;
    private int ID;
    private String role;
    private List<Booking> bookings;

    public BaseUserImpl(String username, String password, String role) {
        this.setUsername(username);
        this.setPassword(password);
        this.role = role;
        this.bookings = new ArrayList<>();
        this.ID = 0;
    }

    public void setUsername(String username) {
        if (username.length() >= 5) {
            this.username = username;
        } else {
            throw new IllegalArgumentException(String.format(ExceptionMessages.ILLEGAL_LENGTH_MESSAGE, "username", 5));
        }
    }

    public void setPassword(String password) {
        if (password.length() >= 6) {
            this.passwordHash = password.hashCode();
        } else {
            throw new IllegalArgumentException(String.format(ExceptionMessages.ILLEGAL_LENGTH_MESSAGE, "password", 6));
        }
    }

    public String getUsername() {
        return username;
    }

    public int getPasswordHash() {
        return passwordHash;
    }

    public String getRole() {
        return role;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isAdminOrUser(){
        if (this.getRole().equals("venueAdmin") || this.getRole().equals("user")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isAdmin() {
        return this.getRole().equals("venueAdmin");
    }

}
