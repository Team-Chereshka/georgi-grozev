package engine;

import units.Ninja;

public class Fight {

   private Ninja ninja1;
   private Ninja ninja2;

    public Fight(Ninja ninja1, Ninja ninja2) {
        this.ninja1 = ninja1;
        this.ninja2 = ninja2;
    }


    public Ninja battle() {
        int ninja1Power = this.ninja1.getPower();
        int ninja2Power = this.ninja2.getPower();

        Ninja winner;

        if (ninja1Power >= ninja2Power) {
            winner = ninja1;
        } else {
            winner = ninja2;
        }

        return winner;
    }
}
