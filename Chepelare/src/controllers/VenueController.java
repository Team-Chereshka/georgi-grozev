package controllers;

import database.Database;
import exceptions.AuthorizationFailedException;
import exceptions.ExceptionMessages;
import models.venues.Venue;
import views.CommonView;
import views.VenueView;


public class VenueController implements Controller {
    private UserController userController;

    public VenueController(UserController userController) {
        this.userController = userController;
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
                case "All" -> showAll();
                case "Details" -> showDetails(arguments);
                case "Rooms" -> showRooms(arguments);
                case "Add" -> addVenue(arguments);
            }
        } catch (IllegalArgumentException | AuthorizationFailedException e) {
            CommonView.printDefaultMessage(e.getMessage());
        }
    }


    private void showAll() {
        VenueView.showAll();
    }

    private void showRooms(String arguments) throws AuthorizationFailedException {
        int venueID = Integer.parseInt(arguments.split("=")[1]);
        if (!userController.hasLoggedInUser()) {
            throw new IllegalArgumentException(ExceptionMessages.NO_LOGGED_USER);
        } else if (!this.userController.getCurrentUser().isAdminOrUser()) {
            throw new AuthorizationFailedException(ExceptionMessages.ILLEGAL_RIGHTS);
        } else if (!Database.getVenueRepository().getItems().containsKey(venueID)) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NON_EXISTING_VENUE_ID, venueID));
        } else {
            VenueView.showAllRoomsInVenue(venueID);
        }
    }

    private void addVenue(String arguments) throws AuthorizationFailedException {
        String[] argumentsAsArray = arguments.split("&");
        String venueName = argumentsAsArray[0].split("=")[1].replace("%20", " ");
        String venueAddress = argumentsAsArray[1].split("=")[1];
        String venueDescription = argumentsAsArray[2].split("=")[1].replace("%20", " ");
        if (!userController.hasLoggedInUser()) {
            throw new IllegalArgumentException(ExceptionMessages.NO_LOGGED_USER);
        } else if (!this.userController.getCurrentUser().isAdmin()) {
            throw new AuthorizationFailedException(ExceptionMessages.ILLEGAL_RIGHTS);
        } else {
            Venue venue = new Venue(venueName, venueAddress, venueDescription);
            int id = Database.getVenueRepository().getIDCounter();
            venue.setId(id);
            Database.getVenueRepository().addItem(venue);
            VenueView.addVenue(venueName, id);
        }
    }

    private void showDetails(String arguments) throws AuthorizationFailedException {
        int venueID = Integer.parseInt(arguments.split("=")[1]);
        if (!userController.hasLoggedInUser()) {
            throw new IllegalArgumentException(ExceptionMessages.NO_LOGGED_USER);
        } else if (!this.userController.getCurrentUser().isAdminOrUser()) {
            throw new AuthorizationFailedException(ExceptionMessages.ILLEGAL_RIGHTS);
        } else if (Database.getVenueRepository().getItemByID(venueID) == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NON_EXISTING_VENUE_ID, venueID));
        } else {
            VenueView.showVenueDetails(venueID);
        }
    }

}
