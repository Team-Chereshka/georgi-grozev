import java.util.Scanner;

public class pr01_HornetWings {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int wingFlaps = Integer.parseInt(scanner.nextLine());
        double distanceFor1000flaps = Double.parseDouble(scanner.nextLine());
        int endurance = Integer.parseInt(scanner.nextLine());

        int restTime = (wingFlaps / endurance) * 5;
        int travelTime = wingFlaps / 100;
        double distance = (wingFlaps / 1000) * distanceFor1000flaps;

        System.out.printf("%.2f m.%n", distance);
        System.out.printf("%d s.", restTime + travelTime);
    }
}
