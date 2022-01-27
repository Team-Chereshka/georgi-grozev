package software;

public class ExpressSoftware extends BaseSoftware {
    private static final String SOFTWARE_TYPE = "Express";
    public ExpressSoftware(String name, String hardwareComponentName, int capacityConsumption, int memoryConsumption) {
        super(name, SOFTWARE_TYPE, hardwareComponentName, capacityConsumption,
                memoryConsumption * 2);
    }
}
