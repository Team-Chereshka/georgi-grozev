package fields;

import units.Ninja;
import vegetables.*;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private char[][] field;
    private Object[][] fieldOfObjects;
    private int turnCounter;
    private List<BaseVegetable> listOfVegetablesOnField;

    public Field(char[][] field) {
        this.field = field;
        this.fieldOfObjects = new Object[field.length][field[field.length - 1].length];
        this.turnCounter = 1;
        listOfVegetablesOnField = new ArrayList<>();
    }

    public void convertToObjects(char[][] field, Ninja firstNinja, Ninja secondNinja) {
        char firstNinjaInitial = firstNinja.getName().charAt(0);
        char secondNinjaInitial = secondNinja.getName().charAt(0);
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                char currentChar = field[i][j];

                if (currentChar == firstNinjaInitial) {
                    fieldOfObjects[i][j] = firstNinja;
                    firstNinja.setRow(i);
                    firstNinja.setInitialRow(i);
                    firstNinja.setCol(j);
                    firstNinja.setInitialCol(j);
                } else if (currentChar == secondNinjaInitial) {
                    fieldOfObjects[i][j] = secondNinja;
                    secondNinja.setRow(i);
                    secondNinja.setInitialRow(i);
                    secondNinja.setCol(j);
                    secondNinja.setInitialCol(j);
                } else {
                    switch (currentChar) {
                        case '-':
                            BaseVegetable blank = new Blank(i, j);
                            fieldOfObjects[i][j] = blank;
                            listOfVegetablesOnField.add(blank);
                            break;

                        case 'A':
                            BaseVegetable asparagus = new Asparagus(i, j);
                            fieldOfObjects[i][j] = asparagus;
                            listOfVegetablesOnField.add(asparagus);
                            break;

                        case 'B':
                            BaseVegetable broccoli = new Broccoli(i, j);
                            fieldOfObjects[i][j] = broccoli;
                            listOfVegetablesOnField.add(broccoli);
                            break;

                        case 'C':
                            BaseVegetable cherryBerry = new CherryBerry(i, j);
                            fieldOfObjects[i][j] = cherryBerry;
                            listOfVegetablesOnField.add(cherryBerry);
                            break;

                        case 'M':
                            BaseVegetable mushroom = new Mushroom(i, j);
                            fieldOfObjects[i][j] = mushroom;
                            listOfVegetablesOnField.add(mushroom);
                            break;

                        case 'R':
                            BaseVegetable royal = new Royal(i, j);
                            fieldOfObjects[i][j] = royal;
                            listOfVegetablesOnField.add(royal);
                            break;
                    }
                }
            }
        }
    }

    public void fieldRefresh(Ninja ninjaToMove, Ninja passiveNinja) {
        int ninjaCol = ninjaToMove.getCol();
        int ninjaRow = ninjaToMove.getRow();
        int passiveNinjaCol = passiveNinja.getCol();
        int passiveNinjaRow = passiveNinja.getRow();

        for (BaseVegetable vegetable : listOfVegetablesOnField) {

            int vegetableCol = vegetable.getCol();
            int vegetableRow = vegetable.getRow();

            if (((ninjaRow != vegetableRow) || (ninjaCol != vegetableCol)) && ((passiveNinjaRow != vegetableRow) || (passiveNinjaCol != vegetableCol))) {
                vegetable.setSteppedOn(false);
            } else {
                vegetable.setSteppedOn(true);
            }
        }
    }

    public void fieldRegrowth() {

        for (BaseVegetable vegetable : listOfVegetablesOnField) {
            vegetable.regrow(this.turnCounter);
        }
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public Object[][] getFieldOfObjects() {
        return fieldOfObjects;
    }
}
