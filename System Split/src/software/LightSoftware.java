package software;

public class LightSoftware extends BaseSoftware {
    private static final String SOFTWARE_TYPE = "Light";

    public LightSoftware(String name, String hardwareComponentName, int capacityConsumption, int memoryConsumption) {
        super(name, SOFTWARE_TYPE, hardwareComponentName,
                capacityConsumption + (capacityConsumption / 2),
                memoryConsumption - (memoryConsumption / 2));
    }
}
