package engine;

import interfaces.Vegetable;
import units.Ninja;
import vegetables.Blank;

public class ObjectChecker {

    public static boolean checkForNinjas(int row, int col, Ninja passiveNinja) {
        boolean ninjaFound = false;

        if ((row == passiveNinja.getRow() && col == passiveNinja.getCol())) {
            ninjaFound = true;
        }
        return ninjaFound;
    }

    public static boolean checkForVegetables(Object[][] fieldOfObjects, int row, int col) {
        boolean vegetableFound = false;
        if (fieldOfObjects[row][col] instanceof Vegetable) {
            vegetableFound = true;
        }

        if (fieldOfObjects[row][col] instanceof Blank) {
            vegetableFound = false;
        }

        return vegetableFound;
    }

}
