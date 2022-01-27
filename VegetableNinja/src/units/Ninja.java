package units;

import interfaces.Vegetable;

import java.util.ArrayList;
import java.util.List;

public class Ninja {
    private String name;
    private int power;
    private int stamina;
    private List<Vegetable> listOfCollectedVegetables;
    private int row;
    private int col;
    private int initialRow;
    private int initialCol;

    private static final int DEFAULT_POWER = 1;
    private static final int DEFAULT_STAMINA = 1;

    public Ninja(String name) {
        this.name = name;
        this.power = DEFAULT_POWER;
        this.stamina = DEFAULT_STAMINA;
        this.listOfCollectedVegetables = new ArrayList<>();
    }

    public void reduceStamina() {
        this.stamina -= 1;
    }

    public void collect(Vegetable vegetable) {
        this.listOfCollectedVegetables.add(vegetable);
    }

    public boolean eat() {
        boolean isMeloLemonMelon = false;
        for (Vegetable vegetable : this.listOfCollectedVegetables) {
            if (vegetable.getClass().getSimpleName().equals("MeloLemonMelon")) {
                isMeloLemonMelon = true;
            } else {
                this.setPower(this.getPower() + vegetable.getPowerChange());
                this.setStamina(this.getStamina() + vegetable.getStaminaChange());
            }
        }
        listOfCollectedVegetables.clear();
        return isMeloLemonMelon;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public int getStamina() {
        return stamina;
    }

    public List<Vegetable> getListOfCollectedVegetables() {
        return listOfCollectedVegetables;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPower(int power) {
        this.power = Math.max(power, 0);
    }

    public void setStamina(int stamina) {
        this.stamina = Math.max(stamina, 0);
    }

    public void setInitialRow(int initialRow) {
        this.initialRow = initialRow;
    }

    public void setInitialCol(int initialCol) {
        this.initialCol = initialCol;
    }

    public int getInitialRow() {
        return initialRow;
    }

    public int getInitialCol() {
        return initialCol;
    }

    @Override
    public String toString() {
        return String.format("Winner: %s%n" +
                "Power: %d%n" +
                "Stamina: %d%n", this.getName(), this.getPower(), this.getStamina());
    }
}
