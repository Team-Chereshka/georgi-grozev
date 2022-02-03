package engine;

import fields.Field;
import units.Ninja;
import vegetables.BaseVegetable;

public class MovementController {
    private Field field;

    public MovementController(Field field) {
        this.field = field;
    }

    public boolean giveDirection(char currentMovement, Ninja ninjaToMove, Ninja passiveNinja) {
        int currentRow = ninjaToMove.getRow();
        int currentCol = ninjaToMove.getCol();
        boolean winnerFound = false;

        switch (currentMovement) {
            case 'U' -> winnerFound = moveNinja(ninjaToMove, passiveNinja, currentRow - 1, currentCol);
            case 'D' -> winnerFound = moveNinja(ninjaToMove, passiveNinja, currentRow + 1, currentCol);
            case 'L' -> winnerFound = moveNinja(ninjaToMove, passiveNinja, currentRow, currentCol - 1);
            case 'R' -> winnerFound = moveNinja(ninjaToMove, passiveNinja, currentRow, currentCol + 1);
        }
        this.field.fieldRefresh(ninjaToMove, passiveNinja);
        this.field.fieldRegrowth();

        return winnerFound;
    }

    private boolean moveNinja(Ninja ninjaToMove, Ninja passiveNinja, int newRow, int newCol) {
        boolean winnerFound = false;
        if (!isOutOfBounds(this.field.getFieldOfObjects(), newRow, newCol)) {
            boolean ninjaFound = ObjectChecker.checkForNinjas(newRow, newCol, passiveNinja);
            boolean vegetableFound = ObjectChecker.checkForVegetables(this.field.getFieldOfObjects(), newRow, newCol);

            if (ninjaFound) {
                Fight.initiateFight(ninjaToMove, passiveNinja);
                winnerFound = true;

            } else if (vegetableFound) {
                BaseVegetable vegetable = (BaseVegetable) this.field.getFieldOfObjects()[newRow][newCol];
                boolean isPickedUp = vegetable.getPickedUp(this.field.getTurnCounter());

                if (isPickedUp) {
                    ninjaToMove.pickUpVegetable(vegetable, newRow, newCol);
                    vegetable.setSteppedOn(true);
                }
            } else if (ninjaToMove.getInitialRow() == newRow && ninjaToMove.getInitialCol() == newCol) {
                ninjaToMove.returnToInitialPosition(newRow, newCol);
            }

        } else {
            ninjaToMove.setStamina(ninjaToMove.getStamina() - 1);
        }
        return winnerFound;
    }

    private boolean isOutOfBounds(Object[][] field, int row, int col) {
        boolean result = false;

        if (row < 0 || col < 0 || row > field.length - 1 || col > field.length - 1) {
            result = true;
        }
        return result;
    }

}
