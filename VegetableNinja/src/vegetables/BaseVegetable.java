package vegetables;

import interfaces.Vegetable;

public abstract class BaseVegetable implements Vegetable {

    private int powerChange;
    private int staminaChange;
    private int movesToRegrow;
    private int row;
    private int column;
    private int eatenAtTurn;
    private int currentTurn;
    boolean isPickedUp;
    boolean steppedOn;

    public BaseVegetable(int powerChange, int staminaChange, int movesToRegrow, int row, int column) {
        this.powerChange = powerChange;
        this.staminaChange = staminaChange;
        this.movesToRegrow = movesToRegrow;
        this.row = row;
        this.column = column;
        this.eatenAtTurn = 0;
        this.isPickedUp = false;
        this.steppedOn = false;
    }

    public boolean getPickedUp(int turnCounter) {
        if (eatenAtTurn == 0) {
            this.eatenAtTurn = turnCounter;
            this.isPickedUp = true;
        }
        return this.isPickedUp;

    }

    public void regrow(int turnCounter) {

        if (this.steppedOn) {
            this.eatenAtTurn++;
        }

        if (turnCounter - this.eatenAtTurn >= movesToRegrow) {
            this.eatenAtTurn = 0;
        }
        this.currentTurn = turnCounter;
    }

    public void setSteppedOn(boolean steppedOn) {
        this.steppedOn = steppedOn;
    }

    @Override
    public int getPowerChange() {
        return this.powerChange;
    }

    @Override
    public int getStaminaChange() {
        return this.staminaChange;
    }

    @Override
    public int getMovesToRegrow() {
        return this.movesToRegrow;
    }

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public int getCol() {
        return this.column;
    }
}
