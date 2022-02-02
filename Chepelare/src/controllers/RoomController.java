package controllers;


import database.Database;
import exceptions.AuthorizationFailedException;
import exceptions.ExceptionMessages;
import models.bookings.Booking;
import models.rooms.Room;
import views.CommonView;
import views.RoomView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class RoomController implements Controller {
    private UserController userController;
    private VenueController venueController;

    public RoomController(UserController userController, VenueController venueController) {
        this.userController = userController;
        this.venueController = venueController;
    }

    @Override
    public void receiveCommand(String url) throws AuthorizationFailedException {
        String command = "";
        String arguments = "";
        if (url.contains("?")) {
            command = url.split("\\?")[0];
            arguments = url.split("\\?")[1];
        } else {
            command = url;
        }
        try {
            switch (command) {
                case "Add" -> addRoom(arguments);
                case "AddPeriod" -> setAvailableDates(arguments);
                case "ViewBookings" -> viewBookings(arguments);
                case "Book" -> bookRoom(arguments);
            }
        } catch (IllegalArgumentException | AuthorizationFailedException e) {
            CommonView.printDefaultMessage(e.getMessage());
        }
    }

    private void viewBookings(String arguments) throws AuthorizationFailedException {
        int roomID = Integer.parseInt(arguments.split("=")[1]);
        if (!userController.hasLoggedInUser()) {
            throw new IllegalArgumentException(ExceptionMessages.NO_LOGGED_USER);
        } else if (!userController.getCurrentUser().isAdmin()) {
            throw new AuthorizationFailedException(ExceptionMessages.ILLEGAL_RIGHTS);
        } else if (!Database.getRoomRepository().getItems().containsKey(roomID)) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.ROOM_DOES_NOT_EXIST, roomID));
        } else {
            RoomView.viewBookings(roomID);
        }
    }

    private void bookRoom(String arguments) throws AuthorizationFailedException {
        int roomID = Integer.parseInt(arguments.split("&")[0].split("=")[1]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDate = LocalDate.parse(arguments.split("&")[1].split("=")[1], formatter);
        LocalDate endDate = LocalDate.parse(arguments.split("&")[2].split("=")[1], formatter);
        String comments = arguments.split("&")[3].split("=")[1].replace("%20", " ");
        Room room = null;
        if (!Database.getRoomRepository().getItems().containsKey(roomID)) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.ROOM_DOES_NOT_EXIST, roomID));
        } else {
            room = Database.getRoomRepository().getItemByID(roomID);
        }

        if (!userController.hasLoggedInUser()) {
            throw new IllegalArgumentException(ExceptionMessages.NO_LOGGED_USER);
        } else if (!userController.getCurrentUser().isAdminOrUser()) {
            throw new AuthorizationFailedException(ExceptionMessages.ILLEGAL_RIGHTS);
        } else if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException(ExceptionMessages.ILLEGAL_DATE_RANGE_MESSAGE);
        } else if (!room.isAvailable(startDate, endDate)) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.ROOM_NOT_AVAILABLE, startDate, endDate));
        } else {
            Booking booking = new Booking(roomID, startDate, endDate);
            booking.setComment(comments);
            Database.getBookingRepository().addItem(booking);
            room.addBooking(booking);
            room.updateAvailability(startDate, endDate);
            userController.getCurrentUser().addBooking(booking);
            RoomView.successfulBooking(startDate, endDate, booking.getTotalPrice());
        }
    }

    private void addRoom(String arguments) throws AuthorizationFailedException {
        int venueID = Integer.parseInt(arguments.split("&")[0].split("=")[1]);
        int numberOfPlaces = Integer.parseInt(arguments.split("&")[1].split("=")[1]);
        double pricePerDay = Double.parseDouble(arguments.split("&")[2].split("=")[1]);
        if (!userController.hasLoggedInUser()) {
            throw new IllegalArgumentException(ExceptionMessages.NO_LOGGED_USER);
        } else if (!userController.getCurrentUser().isAdmin()) {
            throw new AuthorizationFailedException(ExceptionMessages.ILLEGAL_RIGHTS);
        } else if (!Database.getVenueRepository().getItems().containsKey(venueID)) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NON_EXISTING_VENUE_ID, venueID));
        } else {
            Room room = new Room(numberOfPlaces, pricePerDay);
            int roomID = Database.getRoomRepository().getIDCounter();
            room.setID(roomID);
            Database.getRoomRepository().addItem(room);
            Database.getVenueRepository().getItemByID(venueID).addRoom(room);
            RoomView.addRoom(roomID);
        }
    }

    private void setAvailableDates(String arguments) throws AuthorizationFailedException {
        int roomID = Integer.parseInt(arguments.split("&")[0].split("=")[1]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDate = LocalDate.parse(arguments.split("&")[1].split("=")[1], formatter);
        LocalDate endDate = LocalDate.parse(arguments.split("&")[2].split("=")[1], formatter);

        if (!userController.hasLoggedInUser()) {
            throw new IllegalArgumentException(ExceptionMessages.NO_LOGGED_USER);
        } else if (!userController.getCurrentUser().isAdmin()) {
            throw new AuthorizationFailedException(ExceptionMessages.ILLEGAL_RIGHTS);
        } else if (!Database.getRoomRepository().getItems().containsKey(roomID)) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.ROOM_DOES_NOT_EXIST, roomID));
        } else if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException(ExceptionMessages.ILLEGAL_DATE_RANGE_MESSAGE);
        } else {
            Room currentRoom = Database.getRoomRepository().getItemByID(roomID);
            currentRoom.setDatesWhenAvailable(startDate, endDate);
            RoomView.addPeriod(roomID);
        }
    }


}
