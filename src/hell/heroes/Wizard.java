package hell.heroes;

import hell.entities.miscellaneous.HeroInventory;
import hell.interfaces.Hero;
import hell.interfaces.Item;
import hell.interfaces.Recipe;

import java.util.ArrayList;
import java.util.Collection;

public class Wizard implements Hero {
    private String name;
    private long strength;
    private long agility;
    private long intelligence;
    private long hitPoints;
    private long damage;
    private HeroInventory inventory;

    private static final long DEFAULT_STRENGTH = 25;
    private static final long DEFAULT_AGILITY = 25;
    private static final long DEFAULT_INTELLIGENCE = 100;
    private static final long DEFAULT_HITPOINTS = 100;
    private static final long DEFAULT_DAMAGE = 250;

    public Wizard(String name) {
        this.name = name;
        this.strength = DEFAULT_STRENGTH;
        this.agility = DEFAULT_AGILITY;
        this.intelligence = DEFAULT_INTELLIGENCE;
        this.hitPoints = DEFAULT_HITPOINTS;
        this.damage = DEFAULT_DAMAGE;
        this.inventory = new HeroInventory();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public long getStrength() {
        return this.strength;
    }

    @Override
    public long getAgility() {
        return this.agility;
    }

    @Override
    public long getIntelligence() {
        return this.intelligence;
    }

    @Override
    public long getHitPoints() {
        return this.hitPoints;
    }

    @Override
    public long getDamage() {
        return this.damage;
    }

    @Override
    public Collection<Item> getItems() {
        return this.inventory.getCommonItems().values();
    }

    @Override
    public void addItem(Item item) {
        this.inventory.addCommonItem(item);
        this.updateStats();
    }

    @Override
    public void addRecipe(Recipe recipe) {
        this.inventory.addRecipeItem(recipe);
        this.updateStats();
    }

    private void updateStats() {
        this.strength = DEFAULT_STRENGTH + inventory.getTotalStrengthBonus();
        this.agility = DEFAULT_AGILITY + inventory.getTotalAgilityBonus();
        this.intelligence = DEFAULT_INTELLIGENCE + inventory.getTotalIntelligenceBonus();
        this.hitPoints = DEFAULT_HITPOINTS + inventory.getTotalHitPointsBonus();
        this.damage = DEFAULT_DAMAGE + inventory.getTotalDamageBonus();
    }
}
