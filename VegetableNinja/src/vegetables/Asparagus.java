package vegetables;

public class Asparagus extends BaseVegetable {
    private static final int POWER_CHANGE = 5;
    private static final int STAMINA_CHANGE = -5;
    private static final int MOVES_TO_REGROW = 2;


    public Asparagus(int row, int column) {
        super(POWER_CHANGE, STAMINA_CHANGE, MOVES_TO_REGROW, row, column);
    }

}
