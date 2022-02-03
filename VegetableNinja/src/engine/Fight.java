package engine;

import units.Ninja;

public class Fight {

    public static void initiateFight(Ninja firstNinja, Ninja secondNinja) {
        Ninja winner = battle(firstNinja, secondNinja);
        firstNinja.setStamina(firstNinja.getStamina() - 1);
        System.out.println(winner);
    }

    private static Ninja battle(Ninja firstNinja, Ninja secondNinja) {
        int ninja1Power = firstNinja.getPower();
        int ninja2Power = secondNinja.getPower();

        Ninja winner;

        if (ninja1Power >= ninja2Power) {
            winner = firstNinja;
        } else {
            winner = secondNinja;
        }

        return winner;
    }

}
