package fields;

import engine.Fight;
import interfaces.Vegetable;
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

    public boolean movement(char currentMovement, Ninja ninjaToMove, Ninja passiveNinja) {
        int currentRow = ninjaToMove.getRow();
        int currentCol = ninjaToMove.getCol();
        boolean checkForWinner = false;

        switch (currentMovement) {
            case 'U':
                checkForWinner = moveNinja(ninjaToMove, passiveNinja, currentRow - 1, currentCol);
                break;
            case 'D':
                checkForWinner = moveNinja(ninjaToMove, passiveNinja, currentRow + 1, currentCol);
                break;
            case 'L':
                checkForWinner = moveNinja(ninjaToMove, passiveNinja, currentRow, currentCol - 1);
                break;
            case 'R':
                checkForWinner = moveNinja(ninjaToMove, passiveNinja, currentRow, currentCol + 1);
                break;
            default:
                break;
        }


        fieldRefresh(ninjaToMove, passiveNinja);
        fieldRegrowth();

        return checkForWinner;
    }

    private boolean moveNinja(Ninja ninjaToMove, Ninja passiveNinja, int newRow, int newCol) {
        boolean winnerFound = false;
        if (!isOutOfBounds(this.fieldOfObjects, newRow, newCol)) {
            boolean ninjaFound = checkForNinjas(this.fieldOfObjects, newRow, newCol, ninjaToMove, passiveNinja);
            boolean vegetableFound = checkForVegetables(this.fieldOfObjects, newRow, newCol);

            if (ninjaFound) {
                initiateFight(ninjaToMove, passiveNinja);
                winnerFound = true;
            } else if (vegetableFound) {
                BaseVegetable vegetable = (BaseVegetable) fieldOfObjects[newRow][newCol];
                boolean isPickedUp = vegetable.getPickedUp(turnCounter);

                if (isPickedUp) {
                    ninjaToMove.getListOfCollectedVegetables().add(vegetable);
                    vegetable.setSteppedOn(true);
                    ninjaToMove.setRow(newRow);
                    ninjaToMove.setCol(newCol);
                    ninjaToMove.setStamina(ninjaToMove.getStamina() - 1);
                }
            } else if (ninjaToMove.getInitialRow() == newRow && ninjaToMove.getInitialCol() == newCol) {
                ninjaToMove.setRow(newRow);
                ninjaToMove.setCol(newCol);
                ninjaToMove.setStamina(ninjaToMove.getStamina() - 1);
            }

        } else {
            ninjaToMove.setStamina(ninjaToMove.getStamina() - 1);
        }
        return winnerFound;
    }

    private void initiateFight(Ninja firstNinja, Ninja secondNinja) {
        Fight fight = new Fight(firstNinja, secondNinja);
        Ninja winner = fight.battle();

        System.out.println(winner);

    }

    private void fieldRefresh(Ninja ninjaToMove, Ninja passiveNinja) {
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

    private void fieldRegrowth() {

        for (BaseVegetable vegetable : listOfVegetablesOnField) {
            vegetable.regrow(this.turnCounter);
        }
    }

    private boolean checkForVegetables(Object[][] fieldOfObjects, int row, int col) {
        boolean vegetableFound = false;
        if (fieldOfObjects[row][col] instanceof Vegetable) {
            vegetableFound = true;
        }

        if (fieldOfObjects[row][col] instanceof Blank) {
            vegetableFound = false;
        }

        return vegetableFound;
    }

    private boolean checkForNinjas(Object[][] fieldOfObjects, int row, int col, Ninja ninjaToMove, Ninja passiveNinja) {
        boolean ninjaFound = false;

        if ((row == passiveNinja.getRow() && col == passiveNinja.getCol())) {
            ninjaFound = true;
        }
        return ninjaFound;
    }

    private boolean isOutOfBounds(Object[][] field, int row, int col) {
        boolean result = false;

        if (row < 0 || col < 0 || row > field.length - 1 || col > field.length - 1) {
            result = true;
        }
        return result;
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }

    public int getTurnCounter() {
        return turnCounter;
    }
}
