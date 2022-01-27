package systems;

import hardware.BaseHardware;
import hardware.HeavyHardware;
import hardware.PowerHardware;
import software.BaseSoftware;
import software.ExpressSoftware;
import software.LightSoftware;

import java.util.ArrayList;
import java.util.List;

public class TheSystem {
    List<BaseHardware> registeredHardware;
    List<BaseHardware> dump;

    public TheSystem() {
        this.registeredHardware = new ArrayList<>();
        this.dump = new ArrayList<>();
    }


    public BaseHardware checkForHardware(String hardwareComponentName) {
        BaseHardware hardwareFound = null;

        for (BaseHardware baseHardware : registeredHardware) {
            if (baseHardware.getName().equals(hardwareComponentName)) {
                hardwareFound = baseHardware;
                break;
            }
        }
        return hardwareFound;
    }

    public void softwareRegistration(String command, String[] data) {
        String hardwareComponentName = data[0];
        String name = data[1];
        int capacity = Integer.parseInt(data[2]);
        int memory = Integer.parseInt(data[3]);

        BaseSoftware software = switch (command) {
            case "RegisterExpressSoftware" -> new ExpressSoftware(name, hardwareComponentName, capacity, memory);
            case "RegisterLightSoftware" -> new LightSoftware(name, hardwareComponentName, capacity, memory);
            default -> null;
        };

        BaseHardware hardwareToCheck = checkForHardware(hardwareComponentName);

        if (hardwareToCheck != null) {
            hardwareToCheck.registerSoftware(software, software.getCapacityConsumption(), software.getMemoryConsumption());
        }
    }

    public void hardwareRegistration(String command, String[] data) {
        String name = data[0];
        int capacity = Integer.parseInt(data[1]);
        int memory = Integer.parseInt(data[2]);
        BaseHardware hardware = switch (command) {
            case "RegisterPowerHardware" -> new PowerHardware(name, capacity, memory);
            case "RegisterHeavyHardware" -> new HeavyHardware(name, capacity, memory);
            default -> null;
        };
        this.registeredHardware.add(hardware);
    }

    public void releaseSoftware(String[] data) {
        String hardwareComponentName = data[0];
        String softwareName = data[1];

        BaseHardware hardwareToCheck = checkForHardware(hardwareComponentName);

        if (hardwareToCheck != null) {
            BaseSoftware softwareToCheck = hardwareToCheck.checkForSoftware(softwareName);
            if (softwareToCheck != null) {
                hardwareToCheck.removeSoftware(softwareToCheck);
            }
        }
    }

    public void analyze() {
        int countOfSoftwareComponents = 0;
        int memoryInUse = 0;
        int maxMemory = 0;
        int capacityInUse = 0;
        int maxCapacity = 0;
        for (BaseHardware hardware : registeredHardware) {
            if (!hardware.getListOfSoftware().isEmpty()) {
                for (BaseSoftware software : hardware.getListOfSoftware()) {
                    countOfSoftwareComponents++;
                }
            }
            memoryInUse += hardware.getMemoryUsed();
            maxMemory += hardware.getMaxMemory();
            capacityInUse += hardware.getCapacityUsed();
            maxCapacity += hardware.getMaxCapacity();
        }

        System.out.printf("System Analysis%n");
        System.out.printf("Hardware Components: %d%n", registeredHardware.size());
        System.out.printf("Software Components: %d%n", countOfSoftwareComponents);
        System.out.printf("Total Operational Memory: %d / %d%n", memoryInUse, maxMemory);
        System.out.printf("Total Capacity Taken: %d / %d%n", capacityInUse, maxCapacity);
    }

    public void split() {

        List<BaseHardware> heavyHardware = new ArrayList<>();
        for (BaseHardware hardware : registeredHardware) {
            if(hardware.getType().equals("Power")) {
                System.out.println(hardware);
            } else {
                heavyHardware.add(hardware);
            }
        }
        for (BaseHardware hardware : heavyHardware) {
            System.out.println(hardware);
        }
    }

    public void dumpHardware(String hardwareComponentName) {
        for (BaseHardware hardware : registeredHardware) {
            if (hardware.getName().equals(hardwareComponentName)) {
                dump.add(hardware);
                registeredHardware.remove(hardware);
                break;
            }
        }
    }

    public void restoreHardware(String hardwareComponentName) {
        for (BaseHardware hardware : dump) {
            if (hardware.getName().equals(hardwareComponentName)) {
                registeredHardware.add(hardware);
                dump.remove(hardware);
                break;
            }
        }
    }

    public void destroyHardware(String hardwareComponentName) {
        for (BaseHardware hardware : dump) {
            if (hardware.getName().equals(hardwareComponentName)) {
                dump.remove(hardware);
                break;
            }
        }
    }

    public void dumpAnalyze() {
        int powerHardwareComponents = 0;
        int heavyHardwareComponents = 0;
        int expressSoftwareComponents = 0;
        int lightSoftwareComponents = 0;
        int totalDumpedMemory = 0;
        int totalDumpedCapacity = 0;
        for (BaseHardware hardware : dump) {
            if (hardware.getType().equals("Power")) {
                powerHardwareComponents++;
            } else {
                heavyHardwareComponents++;
            }

            for (BaseSoftware software : hardware.getListOfSoftware()) {
                if (software.getType().equals("Express")) {
                    expressSoftwareComponents++;
                } else {
                    lightSoftwareComponents++;
                }
                totalDumpedCapacity += software.getCapacityConsumption();
                totalDumpedMemory += software.getMemoryConsumption();
            }

        }
        System.out.printf("Dump Analysis%n");
        System.out.printf("Power Hardware Components: %d%n", powerHardwareComponents);
        System.out.printf("Heavy Hardware Components: %d%n", heavyHardwareComponents);
        System.out.printf("Express Software Components: %d%n", expressSoftwareComponents);
        System.out.printf("Light Software Components: %d%n", lightSoftwareComponents);
        System.out.printf("Total Dumped Memory: %d%n", totalDumpedMemory);
        System.out.printf("Total Dumped Capacity: %d%n", totalDumpedCapacity);
    }


}
