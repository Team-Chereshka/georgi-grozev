package views;

import models.bookings.Booking;
import models.users.BaseUserImpl;

import java.time.format.DateTimeFormatter;

public class UserView {


    public static void viewMyProfile(BaseUserImpl user) {
        System.out.printf("%s%n", user.getUsername());
        if (user.getBookings().isEmpty()) {
            System.out.printf("You have not made any bookings yet.%n");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            System.out.printf("Your bookings:%n");
            for (Booking booking : user.getBookings()) {
                String startDate = booking.getStartDate().format(formatter);
                String endDate = booking.getEndDate().format(formatter);
                double totalPrice = booking.getTotalPrice();
                System.out.printf("* %s - %s ($%.2f)%n", startDate, endDate, totalPrice);
            }
        }
    }

    public static void login(String username) {
        System.out.printf("The user %s has logged in.%n", username);
    }

    public static void registrationSuccessful(String username) {
        System.out.printf("The user %s has been registered and may login.%n", username);
    }

    public static void logout(String username) {
        System.out.printf("The user %s has logged out.%n", username);
    }
}
