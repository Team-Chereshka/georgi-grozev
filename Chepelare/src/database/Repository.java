package database;

import java.util.Map;

public interface Repository<T> {

    Map<Integer, T> getItems();
    T getItemByID(int ID);
    void addItem(T item);
    int getIDCounter();

}
