import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pr02_HornetComm {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Map<String, List<String>> privateMessages = new LinkedHashMap<>();

        Map<String, List<String>> broadcasts = new LinkedHashMap<>();

        while (!input.equals("Hornet is Green")) {
            String firstQuery = input.split(" <-> ")[0];
            String secondQuery = input.split(" <-> ")[1];

            Pattern onlyDigits = Pattern.compile("^[0-9]+$");
            Matcher matchOnlyDigits = onlyDigits.matcher(firstQuery);

            Pattern anythingButDigits = Pattern.compile("^\\D+$");
            Matcher matchAnythingButDigits = anythingButDigits.matcher(firstQuery);

            Pattern digitsAndOrLetters = Pattern.compile("^[a-zA-Z0-9]*$");
            Matcher matchDigitsAndOrLetters = digitsAndOrLetters.matcher(secondQuery);


            boolean isPrivateMessage = matchOnlyDigits.find() && matchDigitsAndOrLetters.find();
            boolean isBroadcast = matchAnythingButDigits.find() && matchDigitsAndOrLetters.find();

            if (isPrivateMessage) {
                addPrivateMessage(privateMessages, firstQuery, secondQuery);
            } else if (isBroadcast) {
                addBroadcast(broadcasts, firstQuery, secondQuery);
            }
            input = scanner.nextLine();
        }
        printMessages(privateMessages, broadcasts);
    }

    private static void addPrivateMessage(Map<String, List<String>> privateMessages, String firstQuery, String secondQuery) {
        StringBuilder recipientCode = new StringBuilder();
        for (int i = firstQuery.length() - 1; i >= 0; i--) {
            recipientCode.append(firstQuery.charAt(i));
        }
        privateMessages.putIfAbsent(recipientCode.toString(), new ArrayList<>());
        privateMessages.get(recipientCode.toString()).add(secondQuery);
    }

    private static void addBroadcast(Map<String, List<String>> broadcasts, String firstQuery, String secondQuery) {
        StringBuilder newFrequency = new StringBuilder();

        for (int i = 0; i < secondQuery.length(); i++) {
            char currentChar = secondQuery.charAt(i);
            if (currentChar >= 65 && currentChar <= 90) {
                char newChar = (char) (currentChar + 32);
                newFrequency.append(newChar);
            } else if (currentChar >= 97 && currentChar <= 122) {
                char newChar = (char) (currentChar - 32);
                newFrequency.append(newChar);
            } else {
                newFrequency.append(currentChar);
            }
        }
        broadcasts.putIfAbsent(newFrequency.toString(), new ArrayList<>());
        broadcasts.get(newFrequency.toString()).add(firstQuery);
    }

    private static void printMessages(Map<String, List<String>> privateMessages, Map<String, List<String>> broadcasts) {
        System.out.printf("Broadcasts: %n");
        if (broadcasts.isEmpty()) {
            System.out.printf("None%n");
        } else {
            for (Map.Entry<String, List<String>> entry : broadcasts.entrySet()) {
                List<String> values = entry.getValue();
                for (String message : values) {
                    System.out.printf("%s -> %s%n", entry.getKey(), message);
                }
            }
        }
        System.out.printf("Messages: %n");
        if (privateMessages.isEmpty()) {
            System.out.printf("None%n");
        } else {
            for (Map.Entry<String, List<String>> entry : privateMessages.entrySet()) {
                List<String> values = entry.getValue();
                for (String message : values) {
                    System.out.printf("%s -> %s%n", entry.getKey(), message);
                }
            }
        }
    }
}
