package views;

import database.Database;
import models.rooms.Room;
import models.venues.Venue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VenueView {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static void showAll() {
        if (Database.getVenueRepository().getItems().isEmpty()) {
            System.out.printf("There are currently no venues to show.%n");
        } else {
            for (Map.Entry<Integer, Venue> entry : Database.getVenueRepository().getItems().entrySet()) {
                String venueName = entry.getValue().getName();
                String venueAddress = entry.getValue().getAddress();
                int numberOfRooms = entry.getValue().getRooms().size();
                System.out.printf("*[%d] %s, located at %s%n", entry.getKey(), venueName, venueAddress);
                System.out.printf("Free rooms: %d%n", numberOfRooms);
            }
        }
    }

    public static void showVenueDetails(int venueID) {
        Venue venue = Database.getVenueRepository().getItemByID(venueID);
        String venueName = venue.getName();
        String venueAddress = venue.getAddress();
        String venueDescription = venue.getDescription();
        List<Room> roomList = venue.getRooms();

        System.out.printf("%s%n", venueName);
        System.out.printf("Located at %s%n", venueAddress);
        System.out.printf("Description: %s%n", venueDescription);
        if (venue.getRooms().isEmpty()) {
            System.out.printf("No rooms are currently available.%n");
        } else {
            System.out.printf("Available rooms:%n");
            for (Room room : roomList) {
                int numberOfPlaces = room.getNumberOfPlaces();
                double pricePerDay = room.getPricePerDay();
                System.out.printf("* %d places ($%.2f per day)%n", numberOfPlaces, pricePerDay);
            }
        }
    }


    public static void addVenue(String name, int id) {
        System.out.printf("The venue %s with ID %d has been created successfully.%n", name, id);
    }

    public static void showAllRoomsInVenue(int venueID) {
        Venue venue = Database.getVenueRepository().getItemByID(venueID);
        List<Room> roomsInVenue = venue.getRooms();
        String venueName = venue.getName();

        boolean foundAtLeastOneAvailable = false;

        if (!roomsInVenue.isEmpty()) {
            foundAtLeastOneAvailable = true;
        }

        System.out.printf("Available rooms for venue %s:%n", venueName);
        if (foundAtLeastOneAvailable) {
            for (Room room : roomsInVenue) {
                int roomID = room.getID();
                int numberOfPlaces = room.getNumberOfPlaces();
                double pricePerDay = room.getPricePerDay();
                System.out.printf(" *[%d] %d places, $%.2f per day%n", roomID, numberOfPlaces, pricePerDay);
                if (room.getDatesWhenAvailable().isEmpty()) {
                    System.out.printf("This room is not currently available.%n");
                } else {
                    Map<LocalDate, LocalDate> sortedAvailabilityMap = room.getDatesWhenAvailable().entrySet().stream().sorted((e1, e2) -> {
                        LocalDate e1StartDate = e1.getKey();
                        LocalDate e2StartDate = e2.getKey();
                        return e1StartDate.compareTo(e2StartDate);
                    }).collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (x, y) -> null,
                            LinkedHashMap::new
                    ));
                    System.out.printf("Available dates:%n");

                    for (Map.Entry<LocalDate, LocalDate> availableDate : sortedAvailabilityMap.entrySet()) {
                        String startDate = availableDate.getKey().format(formatter);
                        String endDate = availableDate.getValue().format(formatter);
                        System.out.printf("- %s - %s%n", startDate, endDate);
                    }
                }
            }
        } else {
            System.out.printf("No rooms are currently available.%n");
        }
    }
}
