import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class pr04_HornetArmada {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());


        Map<String, Map<String, Integer>> activities = new LinkedHashMap<>(); //CONTAINS: LegionName, <SoldierType, Activity>
        Map<String, Map<String, Integer>> legionSoldierCounts = new LinkedHashMap<>(); //CONTAINS: LegionName, <SoldierType, Count>

        for (int i = 0; i < n; i++) {
            String input = scanner.nextLine();
            String activityAndLegionName = input.split(" -> ")[0];
            int lastActivity = Integer.parseInt(activityAndLegionName.split(" = ")[0]);
            String legionName = activityAndLegionName.split(" = ")[1];

            String soldierTypeAndSoldierCount = input.split(" -> ")[1];
            String soldierType = soldierTypeAndSoldierCount.split(":")[0];
            int soldierCount = Integer.parseInt(soldierTypeAndSoldierCount.split(":")[1]);

            fillActivities(activities, lastActivity, legionName, soldierType);
            fillSoldierCount(legionSoldierCounts, legionName, soldierType, soldierCount);
        }

        String command = scanner.nextLine();

        if (command.contains("\\")) {
            int activity = Integer.parseInt(command.split("\\\\")[0]);
            String soldierType = command.split("\\\\")[1];

            Map<String, Integer> filteredMap = filterFirstCase(activities, legionSoldierCounts, activity, soldierType); //CONTAINS: <Legion, Count>
            Map<String, Integer> sortedMap = getSortedMap(filteredMap);
            printFirstCase(sortedMap);

        } else {
            Map<String, Integer> filteredMap = filterSecondCase(activities, command); //CONTAINS:  <Legion, Activity>
            Map<String, Integer> sortedMap = getSortedMap(filteredMap);
            printSecondCase(sortedMap);
        }
    }


    private static void printFirstCase(Map<String, Integer> sortedMap) {
        for (String legionName : sortedMap.keySet()) {
            System.out.printf("%s -> %d%n", legionName, sortedMap.get(legionName));
        }
    }

    private static void printSecondCase(Map<String, Integer> sortedMap) {
        for (String legionName : sortedMap.keySet()) {
            System.out.printf("%d : %s%n", sortedMap.get(legionName), legionName);
        }
    }

    private static Map<String, Integer> getSortedMap(Map<String, Integer> filteredMap) {
        Map<String, Integer> sortedMap = filteredMap.entrySet().stream().sorted((e2, e1) -> {
            int parameter1 = e1.getValue();
            int parameter2 = e2.getValue();

            return Integer.compare(parameter1, parameter2);
        }).collect(Collectors.toMap(
                e -> e.getKey(),
                e -> e.getValue(),
                (x, y) -> null,
                () -> new LinkedHashMap<>()
        ));
        return sortedMap;
    }

    private static Map<String, Integer> filterFirstCase(Map<String, Map<String, Integer>> activities, Map<String, Map<String, Integer>> legionSoldierCounts, int activity, String soldierType) {
        Map<String, Integer> filteredMap = new LinkedHashMap<>();
        for (String legionName : activities.keySet()) {
            for (String soldier : activities.get(legionName).keySet()) {
                if (activities.get(legionName).get(soldier) < activity) {
                    if (soldier.equals(soldierType)) {
                        int count = legionSoldierCounts.get(legionName).get(soldier);
                        filteredMap.put(legionName, count);
                    }
                }
            }
        }
        return filteredMap;
    }

    private static Map<String, Integer> filterSecondCase(Map<String, Map<String, Integer>> activities, String command) {
        Map<String, Integer> filteredMap = new LinkedHashMap<>();

        for (String legionName : activities.keySet()) {
            int maxActivity = Integer.MIN_VALUE;
            if (activities.get(legionName).containsKey(command)) {
                for (String soldierType : activities.get(legionName).keySet()) {
                    if (activities.get(legionName).get(soldierType) > maxActivity) {
                        maxActivity = activities.get(legionName).get(soldierType);
                    }
                }
                filteredMap.put(legionName, maxActivity);
            }
        }
        return filteredMap;
    }

    private static void fillSoldierCount(Map<String, Map<String, Integer>> legionSoldierCounts, String legionName, String soldierType, int soldierCount) {
        if (legionSoldierCounts.containsKey(legionName)) {
            if (legionSoldierCounts.get(legionName).containsKey(soldierType)) {
                legionSoldierCounts.get(legionName).put(soldierType, legionSoldierCounts.get(legionName).get(soldierType) + soldierCount);
            } else {
                legionSoldierCounts.get(legionName).putIfAbsent(soldierType, soldierCount);
            }
        } else {
            legionSoldierCounts.putIfAbsent(legionName, new LinkedHashMap<>());
            legionSoldierCounts.get(legionName).put(soldierType, soldierCount);
        }
    }

    private static void fillActivities(Map<String, Map<String, Integer>> activities, int lastActivity, String legionName, String soldierType) {
        if (activities.containsKey(legionName)) {
            if (activities.get(legionName).containsKey(soldierType)) {
                if (activities.get(legionName).get(soldierType) < lastActivity) {
                    activities.get(legionName).put(soldierType, lastActivity);
                }
            } else {
                activities.get(legionName).putIfAbsent(soldierType, lastActivity);
            }
        } else {
            activities.putIfAbsent(legionName, new LinkedHashMap<>());
            activities.get(legionName).put(soldierType, lastActivity);
        }
    }
}
