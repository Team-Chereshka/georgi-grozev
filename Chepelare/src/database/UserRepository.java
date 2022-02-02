package database;

import models.users.BaseUserImpl;

import java.util.*;

public class UserRepository implements Repository<BaseUserImpl> {
    private Map<Integer, BaseUserImpl> users;
    private int idCounter;

    public UserRepository() {
        this.users = new LinkedHashMap<>();
        this.idCounter = 1;
    }

    @Override
    public Map<Integer, BaseUserImpl> getItems() {
        return Collections.unmodifiableMap(users);
    }

    @Override
    public BaseUserImpl getItemByID(int ID) {
        BaseUserImpl userToReturn = null;
        if (this.users.containsKey(ID)) {
            userToReturn = users.get(ID);
        }
        return userToReturn;
    }

    public BaseUserImpl getItemByUsername(String username) {
        BaseUserImpl userToReturn = null;
        for (BaseUserImpl user : users.values()) {
            if (user.getUsername().equals(username)) {
                userToReturn = user;
                break;
            }
        }
        return userToReturn;
    }

    @Override
    public void addItem(BaseUserImpl user) {
        users.put(this.idCounter, user);
        this.idCounter++;
    }


    @Override
    public int getIDCounter() {
        return this.idCounter;
    }

}
