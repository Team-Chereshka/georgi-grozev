package views;

import database.Database;
import models.bookings.Booking;
import models.rooms.Room;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RoomView {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static void addRoom(int roomID) {
        System.out.printf("The room with ID %d has been created successfully.%n", roomID);
    }

    public static void addPeriod(int roomID) {
        System.out.printf("The period has been added to room with ID %d.%n", roomID);
    }

    public static void successfulBooking(LocalDate startDate, LocalDate endDate, double totalPrice) {
        System.out.printf("Room booked from %s to %s for $%.2f%n", startDate.format(formatter), endDate.format(formatter),totalPrice);
    }

    public static void viewBookings(int roomID) {
        Room currentRoom = Database.getRoomRepository().getItemByID(roomID);
        if (currentRoom.getBookings().isEmpty()) {
            System.out.printf("There are no bookings for this room.%n");
        } else {
            System.out.printf("Room bookings:%n");
            double totalBookingPrice = 0;
            for (Booking booking : currentRoom.getBookings()) {
                String startDate = booking.getStartDate().format(formatter);
                String endDate = booking.getEndDate().format(formatter);
                double totalPrice = booking.getTotalPrice();
                totalBookingPrice += totalPrice;
                System.out.printf("* %s - %s ($%.2f)%n", startDate, endDate, totalPrice);
            }
            System.out.printf("Total booking price: $%.2f%n", totalBookingPrice);
        }
    }
}
