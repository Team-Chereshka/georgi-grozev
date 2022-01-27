package hardware;

import software.BaseSoftware;

import java.util.ArrayList;
import java.util.List;

public class BaseHardware {
    private String name;
    private String type;
    private int maxCapacity;
    private int maxMemory;
    private int capacityUsed;
    private int memoryUsed;
    private List<BaseSoftware> listOfSoftware;

    public BaseHardware(String name, String type, int maxCapacity, int maxMemory) {
        this.name = name;
        this.type = type;
        this.maxCapacity = maxCapacity;
        this.maxMemory = maxMemory;
        this.listOfSoftware = new ArrayList<>();
        capacityUsed = 0;
        memoryUsed = 0;
    }

    public String getName() {
        return name;
    }

    public void registerSoftware(BaseSoftware software, int capacity, int memory) {
        if (this.capacityUsed + capacity <= maxCapacity && this.memoryUsed + memory <= maxMemory) {
            listOfSoftware.add(software);
            updateMemory(memory, '+');
            updateCapacity(capacity, '+');
        }

    }

    private void updateCapacity(int capacity, char function) {
        switch (function) {
            case '-' -> this.capacityUsed = this.capacityUsed - capacity;
            case '+' -> this.capacityUsed = this.capacityUsed + capacity;
        }
    }

    private void updateMemory(int memory, char function) {
        switch (function) {
            case '-' -> this.memoryUsed = this.memoryUsed - memory;
            case '+' -> this.memoryUsed = this.memoryUsed + memory;
        }

    }

    public BaseSoftware checkForSoftware(String softwareName) {
        BaseSoftware softwareToReturn = null;
        for (BaseSoftware baseSoftware : listOfSoftware) {
            if (baseSoftware.getName().equals(softwareName)) {
                softwareToReturn = baseSoftware;
            }
        }
        return softwareToReturn;
    }

    public void removeSoftware(BaseSoftware softwareToCheck) {
        int softwareMemory = softwareToCheck.getMemoryConsumption();
        int softwareCapacity = softwareToCheck.getCapacityConsumption();
        this.listOfSoftware.remove(softwareToCheck);
        updateMemory(softwareMemory, '-');
        updateCapacity(softwareCapacity, '-');
    }

    public List<BaseSoftware> getListOfSoftware() {
        return listOfSoftware;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getMaxMemory() {
        return maxMemory;
    }

    public int getCapacityUsed() {
        return capacityUsed;
    }

    public int getMemoryUsed() {
        return memoryUsed;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        int countOfExpressSoftware = 0;
        int countOfLightSoftware = 0;
        StringBuilder softwareComponents = new StringBuilder();

        if (!this.listOfSoftware.isEmpty()) {
            for (BaseSoftware baseSoftware : listOfSoftware) {
                if (baseSoftware.getType().equals("Express")) {
                    countOfExpressSoftware++;
                } else if (baseSoftware.getType().equals("Light")) {
                    countOfLightSoftware++;
                }
                softwareComponents.append(baseSoftware.getName()).append(", ");
            }
        } else {
            softwareComponents.append("None, ");
        }

        softwareComponents.replace(softwareComponents.length() - 2, softwareComponents.length() - 1, "");


        return "Hardware Component - " + this.getName() + System.lineSeparator() +
                "Express Software Components: " + countOfExpressSoftware + System.lineSeparator() +
                "Light Software Components: " + countOfLightSoftware + System.lineSeparator() +
                "Memory Usage: " + this.memoryUsed + " / " + this.maxMemory + System.lineSeparator() +
                "Capacity Usage: " + this.capacityUsed + " / " + this.maxCapacity + System.lineSeparator() +
                "Type: " + this.getType() + System.lineSeparator() +
                "Software Components: " + softwareComponents;
    }
}

