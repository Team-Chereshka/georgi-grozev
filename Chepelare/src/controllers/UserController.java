package controllers;

import database.Database;
import exceptions.ExceptionMessages;
import models.users.BaseUserImpl;
import models.users.RegularUser;
import models.users.VenueAdmin;
import views.CommonView;
import views.UserView;

public class UserController implements Controller {
    private boolean hasLoggedInUser;
    private BaseUserImpl currentUser;

    public UserController() {
        this.hasLoggedInUser = false;
        this.currentUser = null;
    }

    @Override
    public void receiveCommand(String url) {
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
                case "Register" -> register(arguments);
                case "Login" -> login(arguments);
                case "MyProfile" -> viewProfile();
                case "Logout" -> logout();
            }
        } catch (IllegalArgumentException e) {
            CommonView.printDefaultMessage(e.getMessage());
        }
    }



    private void register(String arguments) {
        String[] argumentsAsArray = arguments.split("&");
        String username = argumentsAsArray[0].split("=")[1];
        String password = argumentsAsArray[1].split("=")[1];
        String confirmedPassword = argumentsAsArray[2].split("=")[1];
        String role = argumentsAsArray[3].split("=")[1];


        if (!password.equals(confirmedPassword)) {
            throw new IllegalArgumentException(ExceptionMessages.PASSWORDS_DO_NOT_MATCH);
        } else if (this.hasLoggedInUser) {
            throw new IllegalArgumentException(ExceptionMessages.USER_ALREADY_LOGGED_IN);
        } else if (Database.getUserRepository().getItemByUsername(username) != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.USERNAME_ALREADY_EXISTS, username));
        } else {
            BaseUserImpl registeredUser = null;
            switch (role) {
                case "user" -> registeredUser = new RegularUser(username, password, role);
                case "venueAdmin" -> registeredUser = new VenueAdmin(username, password, role);
            }
            if (registeredUser != null) {
                Database.getUserRepository().addItem(registeredUser);
                int id = Database.getUserRepository().getIDCounter();
                registeredUser.setID(id);
                UserView.registrationSuccessful(username);
            }
        }

    }

    private void logout() {
        if (!hasLoggedInUser) {
            throw new IllegalArgumentException(ExceptionMessages.NO_LOGGED_USER);
        } else {
            UserView.logout(this.currentUser.getUsername());
            this.hasLoggedInUser = false;
            this.currentUser = null;
        }
    }

    private void viewProfile() {
        if (!this.hasLoggedInUser) {
            throw new IllegalArgumentException(ExceptionMessages.NO_LOGGED_USER);
        } else {
            UserView.viewMyProfile(this.currentUser);
        }
    }

    private void login(String arguments) {
        String[] argumentsAsArray = arguments.split("&");
        String username = argumentsAsArray[0].split("=")[1];
        String password = argumentsAsArray[1].split("=")[1];

        if (hasLoggedInUser) {
            throw new IllegalArgumentException(ExceptionMessages.USER_ALREADY_LOGGED_IN);
        } else if (Database.getUserRepository().getItemByUsername(username) == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.USERNAME_DOES_NOT_EXIST, username));
        } else if ((Database.getUserRepository().getItemByUsername(username)) != null && (password.hashCode() != Database.getUserRepository().getItemByUsername(username).getPasswordHash())) {
            throw new IllegalArgumentException(ExceptionMessages.WRONG_PASSWORD);
        } else {
            this.hasLoggedInUser = true;
            this.currentUser = Database.getUserRepository().getItemByUsername(username);
            UserView.login(username);
        }
    }

    public boolean hasLoggedInUser() {
        return this.hasLoggedInUser;
    }

    public BaseUserImpl getCurrentUser() {
        return currentUser;
    }
}
