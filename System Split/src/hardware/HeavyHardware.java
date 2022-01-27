package hardware;

public class HeavyHardware extends BaseHardware {
    private static final String HARDWARE_TYPE = "Heavy";

    public HeavyHardware(String name, int maxCapacity, int maxMemory) {
        super(name, HARDWARE_TYPE,
                2 * maxCapacity,
                maxMemory - (maxMemory / 4));
    }
}
