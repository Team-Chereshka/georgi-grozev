package engine;

import database.Database;
import exceptions.AuthorizationFailedException;

import java.util.Scanner;

public class Engine {
    public static void main(String[] args) throws AuthorizationFailedException {
        Scanner scanner = new Scanner(System.in);
        Database database = new Database();
        InputReader reader = new InputReader();


        while (true) {
            String input = scanner.nextLine();
            if (input.equals("End")) {
                break;
            }
            reader.readLine(input);
        }
    }
}
