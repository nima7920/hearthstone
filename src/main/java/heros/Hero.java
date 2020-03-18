package heros;

import cards.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Hero {
    private int hp;
    private String heroType;
    protected ArrayList<String> deckCards=new ArrayList<>();
public Hero(){

    // adding primary cards:
    deckCards=new ArrayList<>(Arrays.asList("arcane explosion","arcane servant","fireball","murloc raider","river crocolisk",
            "stonetusk boar","silverback patriarch","hellfire","the black knight"));
//    deckCards.add("arcane explosion");
//    deckCards.add("arcane servant");
//    deckCards.add("fireball");
//    deckCards.add("murloc raider");
//    deckCards.add("river crocolisk");
//    deckCards.add("stonetusk boar");
//    deckCards.add("silverback patriarch");
//    deckCards.add("hellfire");
//    deckCards.add("the black knight");
}
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

    public ArrayList<String> getDeckCards() {
        return deckCards;
    }

    public void setDeckCards(ArrayList<String> deckCards) {
        this.deckCards = deckCards;
    }

    // return -1 if card doesn't exist,0 if card is in wrong class and 1 if action is complete
    public int addCard(String cardname) throws IOException, ParseException {
        Card tempCard=Card.getCardObject(cardname);
        if(tempCard!=null){
            if(tempCard.getCardType().equals(this.heroType) || tempCard.getCardType().equals("neutral")){
                deckCards.add(cardname);
                return 1;
            }else{
                return 0;
            }
        }else{
            return -1;
        }
    }

    // return 0 if card doesn't exist in deck and 1 if action is complete
    public int removeCard(String cardname){
        if(deckCards.contains(cardname)){
            deckCards.remove(cardname);
            return 1;
        }else{
            return 0;
        }
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
