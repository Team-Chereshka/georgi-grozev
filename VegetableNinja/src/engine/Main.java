package engine;

import fields.Field;
import units.Ninja;
import vegetables.Mushroom;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String firstNinjaName = scanner.nextLine();
        Ninja firstNinja = new Ninja(firstNinjaName);

        String secondNinjaName = scanner.nextLine();
        Ninja secondNinja = new Ninja(secondNinjaName);

        String[] fieldDimensions = scanner.nextLine().split("\\s+");

        int rows = Integer.parseInt(fieldDimensions[0]);
        int cols = Integer.parseInt(fieldDimensions[1]);

        char[][] fieldMatrix = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            char[] currentRow = scanner.nextLine().toCharArray();
            fieldMatrix[i] = currentRow;
        }

        Field field = new Field(fieldMatrix);
        field.convertToObjects(fieldMatrix, firstNinja, secondNinja);
        MovementController movementController = new MovementController(field);

        boolean winnerFound = false;

        char[] movementCommand = scanner.nextLine().toCharArray();

        String whoIsNextToMove = "first";

        while (true) {
            for (char currentMovement : movementCommand) {
                if (whoIsNextToMove.equals("first")) {
                    if (firstNinja.getStamina() >= 0) {
                        winnerFound = movementController.giveDirection(currentMovement, firstNinja, secondNinja);
                    }

                    if (winnerFound) {
                        break;
                    }

                    if (firstNinja.getStamina() == 0) {
                        boolean meloLemonMelonFoundByFirst = firstNinja.eat();
                        if (meloLemonMelonFoundByFirst) {
                            secondNinja.punishment();
                        }
                        whoIsNextToMove = "second";
                    }
                    field.setTurnCounter(field.getTurnCounter() + 1);
                } else {
                    if (secondNinja.getStamina() >= 0) {
                        winnerFound = movementController.giveDirection(currentMovement, secondNinja, firstNinja);
                    }

                    if (winnerFound) {
                        break;
                    }

                    if (secondNinja.getStamina() == 0) {
                        boolean meloLemonMelonFoundBySecond = secondNinja.eat();
                        if (meloLemonMelonFoundBySecond) {
                            firstNinja.punishment();
                        }
                        whoIsNextToMove = "first";
                    }
                    field.setTurnCounter(field.getTurnCounter() + 1);
                }
            }
            if (winnerFound) {
                break;
            }
            movementCommand = scanner.nextLine().toCharArray();
        }
    }
}

