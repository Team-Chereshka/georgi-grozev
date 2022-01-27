package vegetables;

public class Broccoli extends BaseVegetable {
    private static final int POWER_CHANGE = 10;
    private static final int STAMINA_CHANGE = 0;
    private static final int MOVES_TO_REGROW = 3;


    public Broccoli(int row, int column) {
        super(POWER_CHANGE, STAMINA_CHANGE, MOVES_TO_REGROW, row, column);
    }


}
