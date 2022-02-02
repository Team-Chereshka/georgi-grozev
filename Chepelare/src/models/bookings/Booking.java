package models.bookings;
import database.Database;
import models.rooms.Room;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;


public class Booking {
    private Room room;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;
    private String comment;

    public Booking(int roomID, LocalDate startDate, LocalDate endDate) {
        this.setRoom(roomID);
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = calculateTotalPrice();
    }

    private double calculateTotalPrice() {
        double roomPricePerDay = this.room.getPricePerDay();
        long numberOfDays = DAYS.between(startDate, endDate);
        return roomPricePerDay * numberOfDays;
    }

    private void setRoom(int roomID) {
        this.room = Database.getRoomRepository().getItemByID(roomID);
    }



    public LocalDate getStartDate() {
        return startDate;
    }



    public LocalDate getEndDate() {
        return endDate;
    }



    public double getTotalPrice() {
        return totalPrice;
    }



    public void setComment(String comments) {
        this.comment = comments;
    }
}
