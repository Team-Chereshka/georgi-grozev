package hell;

import hell.heroes.Assassin;
import hell.heroes.Barbarian;
import hell.heroes.Wizard;
import hell.interfaces.Hero;
import hell.interfaces.Item;
import hell.interfaces.Recipe;
import hell.items.CommonItem;
import hell.items.RecipeItem;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tokens = scanner.nextLine().split("\\s+");
        String command = tokens[0];

        Map<String, Hero> heroList = new LinkedHashMap<>();
        while (true) {
            switch (command) {
                case "Hero":
                    addHero(tokens, heroList);
                    break;

                case "Item":
                    addItem(tokens, heroList);
                    break;

                case "Recipe":
                    addRecipe(tokens, heroList);
                    break;

                case "Inspect":
                    inspectHero(tokens, heroList);
                    break;

                case "Quit":
                    quit(heroList);

            }

            if (command.equals("Quit")) {
                break;
            }
            tokens = scanner.nextLine().split("\\s+");
            command = tokens[0];

        }
    }

    private static void quit(Map<String, Hero> heroList) {
        Map<String, Hero> sortedMap = heroList.entrySet().stream().sorted((e2, e1) -> {
            long sumOfStrAgiIntFirst = e1.getValue().getStrength() + e1.getValue().getAgility() + e1.getValue().getIntelligence();
            long sumOfStrAgiIntSecond = e2.getValue().getStrength() + e2.getValue().getAgility() + e2.getValue().getIntelligence();

            int result = Long.compare(sumOfStrAgiIntFirst, sumOfStrAgiIntSecond);

            if (result == 0) {
                long sumOfHPDamageFirst = e1.getValue().getHitPoints() + e1.getValue().getDamage();
                long sumOfHPDamageSecond = e2.getValue().getHitPoints() + e2.getValue().getDamage();
                result = Long.compare(sumOfHPDamageFirst, sumOfHPDamageSecond);
            }

            return result;
        }).collect(Collectors.toMap(
                e -> e.getKey(),
                e -> e.getValue(),
                (x, y) -> null,
                () -> new LinkedHashMap<>()
        ));

        int counter = 1;
        for (Hero hero : sortedMap.values()) {
            String heroName = hero.getName();
            String heroClass = hero.getClass().getSimpleName();
            long hitPoints = hero.getHitPoints();
            long damage = hero.getDamage();
            long strength = hero.getStrength();
            long agility = hero.getAgility();
            long intelligence = hero.getIntelligence();
            Collection<Item> items = heroList.get(heroName).getItems();

            System.out.printf("%d. %s: %s%n", counter, heroClass, heroName);
            System.out.printf("###HitPoints: %d%n", hitPoints);
            System.out.printf("###Damage: %d%n", damage);
            System.out.printf("###Strength: %d%n", strength);
            System.out.printf("###Agility: %d%n", agility);
            System.out.printf("###Intelligence: %d%n", intelligence);

            if (items.isEmpty()) {
                System.out.printf("###Items: None%n");
            } else {
                System.out.print("###Items: ");
                StringBuilder itemsToPrint = new StringBuilder();
                for (Item item : items) {
                    itemsToPrint.append(item.getName()).append(", ");
                }
                itemsToPrint.delete(itemsToPrint.length() - 2, itemsToPrint.length() - 1);
                System.out.printf("%s%n", itemsToPrint);
            }
            counter++;
        }
    }

    private static void inspectHero(String[] tokens, Map<String, Hero> heroList) {
        String heroName = tokens[1];
        String heroClass = heroList.get(heroName).getClass().getSimpleName();
        long heroHitPoints = heroList.get(heroName).getHitPoints();
        long heroDamage = heroList.get(heroName).getDamage();
        long strength = heroList.get(heroName).getStrength();
        long agility = heroList.get(heroName).getAgility();
        long intelligence = heroList.get(heroName).getIntelligence();
        Collection<Item> items = heroList.get(heroName).getItems();

        System.out.printf("Hero: %s, Class: %s%n", heroName, heroClass);
        System.out.printf("HitPoints: %d, Damage: %d%n", heroHitPoints, heroDamage);
        System.out.printf("Strength: %d%n", strength);
        System.out.printf("Agility: %d%n", agility);
        System.out.printf("Intelligence: %d%n", intelligence);

        if (items.isEmpty()) {
            System.out.printf("Items: None%n");
        } else {
            System.out.printf("Items:%n");
            for (Item item : items) {
                String itemName = item.getName();
                int itemStrength = item.getStrengthBonus();
                int itemAgility = item.getAgilityBonus();
                int itemIntelligence = item.getIntelligenceBonus();
                int itemHitPoints = item.getHitPointsBonus();
                int itemDamage = item.getDamageBonus();

                System.out.printf("###Item: %s%n", itemName);
                System.out.printf("###+%d Strength%n", itemStrength);
                System.out.printf("###+%d Agility%n", itemAgility);
                System.out.printf("###+%d Intelligence%n", itemIntelligence);
                System.out.printf("###+%d HitPoints%n", itemHitPoints);
                System.out.printf("###+%d Damage%n", itemDamage);
            }
        }
    }

    private static void addRecipe(String[] tokens, Map<String, Hero> heroList) {
        String recipeName = tokens[1];
        String heroName = tokens[2];
        int strengthBonus = Integer.parseInt(tokens[3]);
        int agilityBonus = Integer.parseInt(tokens[4]);
        int intelligenceBonus = Integer.parseInt(tokens[5]);
        int hitPointsBonus = Integer.parseInt(tokens[6]);
        int damageBonus = Integer.parseInt(tokens[7]);
        List<String> requiredItems = new ArrayList<>();

        for (int i = 8; i < tokens.length; i++) {
            requiredItems.add(tokens[i]);
        }

        Recipe recipe = new RecipeItem(recipeName, strengthBonus, agilityBonus, intelligenceBonus,
                hitPointsBonus, damageBonus, requiredItems);
        heroList.get(heroName).addRecipe(recipe);
        System.out.printf("Added recipe - %s to Hero - %s%n", recipeName, heroName);
    }

    private static void addItem(String[] tokens, Map<String, Hero> heroList) {
        String itemName = tokens[1];
        String heroNameToAdd = tokens[2];
        int strengthBonus = Integer.parseInt(tokens[3]);
        int agilityBonus = Integer.parseInt(tokens[4]);
        int intelligenceBonus = Integer.parseInt(tokens[5]);
        int hitPointsBonus = Integer.parseInt(tokens[6]);
        int damageBonus = Integer.parseInt(tokens[7]);
        Item item = new CommonItem(itemName, strengthBonus, agilityBonus, intelligenceBonus, hitPointsBonus, damageBonus);
        heroList.get(heroNameToAdd).addItem(item);
        System.out.printf("Added item - %s to Hero - %s%n", itemName, heroNameToAdd);
    }

    private static void addHero(String[] tokens, Map<String, Hero> heroList) {
        String heroName = tokens[1];
        String heroType = tokens[2];

        switch (heroType) {
            case "Assassin":
                Hero assassin = new Assassin(heroName);
                heroList.put(heroName, assassin);
                break;

            case "Barbarian":
                Hero barbarian = new Barbarian(heroName);
                heroList.put(heroName, barbarian);
                break;

            case "Wizard":
                Hero wizard = new Wizard(heroName);
                heroList.put(heroName, wizard);
                break;
        }
        System.out.printf("Created %s - %s%n", heroType, heroName);
    }
}