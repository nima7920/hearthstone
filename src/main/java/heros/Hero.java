package heros;

import cards.*;

import java.util.ArrayList;

public class Hero {
    private int hp;
    private String heroType;

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getHeroType() {
        return heroType;
    }

    public void setHeroType(String heroType) {
        this.heroType = heroType;
    }

    public static Hero getHero(String hero) {
        switch (hero) {
            case "mage": {
                return new Mage();
            }
            case "rogue": {
                return new Rogue();

            }
            case "warlock": {
                return new Warlock();

            }
            default: {
                return null;

            }
        }
    }

    @Override
    public String toString() {
        return this.heroType;
    }
}
