package engine;

import controllers.RoomController;
import controllers.UserController;
import controllers.VenueController;
import exceptions.AuthorizationFailedException;
import exceptions.ExceptionMessages;

public class InputReader {
    private String input;
    RoomController roomController;
    UserController userController;
    VenueController venueController;

    public InputReader() {
        this.userController = new UserController();
        this.venueController = new VenueController(userController);
        this.roomController = new RoomController(userController, venueController);
    }

    public void readLine(String input) throws AuthorizationFailedException {
        this.input = input;
        giveCommand();
    }

    public void giveCommand() throws AuthorizationFailedException {
        String[] arguments = this.input.split("/");
        String controller = arguments[1];
        String url = arguments[2];

        switch (controller) {
            case "Users" -> this.userController.receiveCommand(url);
            case "Venues" -> this.venueController.receiveCommand(url);
            case "Rooms" -> this.roomController.receiveCommand(url);
            default -> throw new IllegalArgumentException(ExceptionMessages.ILLEGAL_URL);
        }
    }
}
