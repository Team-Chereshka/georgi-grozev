package software;

public abstract class BaseSoftware  {
    private String name;
    private String type;
    private String hardwareComponentName;
    private int capacityConsumption;
    private int memoryConsumption;

    public BaseSoftware(String name, String type, String hardwareComponentName, int capacityConsumption, int memoryConsumption) {
        this.name = name;
        this.type = type;
        this.hardwareComponentName = hardwareComponentName;
        this.capacityConsumption = capacityConsumption;
        this.memoryConsumption = memoryConsumption;
    }

    public String getName() {
        return name;
    }

    public int getCapacityConsumption() {
        return capacityConsumption;
    }

    public int getMemoryConsumption() {
        return memoryConsumption;
    }

    public String getType() {
        return this.type;
    }
}
