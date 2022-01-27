import java.util.*;
import java.util.stream.Stream;

public class pr03_HornetAssault {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] beehives = Arrays.stream(scanner.nextLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

        List<Integer> hornets = new LinkedList<>(Stream.of((scanner.nextLine().split("\\s+")))
                .map(Integer::parseInt).toList());

        for (int i = 0; i < beehives.length; i++) {
            int sumOfHornetPower = 0;
            for (int hornet : hornets) {
                sumOfHornetPower += hornet;
            }

            int remainingBees = beehives[i] - sumOfHornetPower;

            if (remainingBees < 0) {
                beehives[i] = 0;
            } else if (remainingBees == 0) {
                beehives[i] = 0;
                hornets.remove(0);

            } else {
                beehives[i] = remainingBees;
                hornets.remove(0);
            }
        }

        String winner = "None";

        for (int beehive : beehives) {
            if (beehive > 0) {
                winner = "Bees";
                break;
            }
        }

        if (winner.equals("None")) {
            winner = "Hornets";
        }

        switch (winner) {
            case "Bees":
                for (int beehive : beehives) {
                    if (beehive != 0) {
                        System.out.printf("%d ", beehive);
                    }
                }
                break;

            case "Hornets":
                for (int hornet : hornets) {
                    System.out.printf("%d ", hornet);
                }
                break;
        }
    }
}
