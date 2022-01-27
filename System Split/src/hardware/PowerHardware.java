package hardware;

public class PowerHardware extends BaseHardware {

    private static final String HARDWARE_TYPE = "Power";

    public PowerHardware(String name, int maxCapacity, int maxMemory) {
        super(name, HARDWARE_TYPE,
                maxCapacity - ((maxCapacity * 3) / 4),
                maxMemory + ((maxMemory * 3) / 4));

    }


}
