package vegetables;

public class Royal extends BaseVegetable {
    private static final int POWER_CHANGE = 20;
    private static final int STAMINA_CHANGE = 10;
    private static final int MOVES_TO_REGROW = 10;


    public Royal(int row, int column) {
        super(POWER_CHANGE, STAMINA_CHANGE, MOVES_TO_REGROW, row, column);
    }

}
