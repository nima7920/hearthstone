import cards.Card;
import cards.Minion;
import heros.Hero;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Store {
    private Player currentPlayer;
    private ArrayList<String> showcaseCards;

    public Store(Player player) {
        this.currentPlayer = player;
    }

    public void updateShowcaseCards() throws IOException, ParseException {
        showcaseCards = new ArrayList<>();
        Card.updateCardLists();
        for (String x : Card.getAllCards()) {
            if (!currentPlayer.getAvailableCards().contains(x))
                showcaseCards.add(x);
        }
    }

    public void printCards() throws IOException, ParseException {
        Card tempcard;
        System.out.println("-Here are the cards that you can buy:");
        for (String x : showcaseCards) {
            tempcard = Card.getCardObject(x);
            if (tempcard != null) {
                System.out.println("\t" + tempcard.getName() + ":");
                System.out.println("\t -Class:" + tempcard.getCardClass());
                System.out.println("\t -Type:" + tempcard.getCardType());
                System.out.println("\t -Rarity:" + tempcard.getRarity().toString());
                System.out.println("\t -Mana Cost:" + tempcard.getManaCost());
                if (tempcard.getCardType().equals("minion")) {
                    System.out.println("\t -Attack:" + ((Minion) tempcard).getAttack());
                    System.out.println("\t -HP:" + ((Minion) tempcard).getHp());
                }
                System.out.println("\t -description:" + tempcard.getDescription());
                System.out.println("\t -Gems Cost:" + tempcard.getGemsCost());
            }
            System.out.println();
        }
    }

    public void buyCard(String cardName) throws IOException, ParseException {
        if (showcaseCards.contains(cardName)) {
            Card tempCard = Card.getCardObject(cardName);
            if (currentPlayer.getGems() >= tempCard.getGemsCost()) {
                currentPlayer.addAvailableCard(cardName);
                currentPlayer.setGems(currentPlayer.getGems()-tempCard.getGemsCost());
                updateShowcaseCards();
                System.out.println("-Card bought successfully!");
            } else {
                System.out.println("-you don't have enough gems");
            }
        } else {
            System.out.println("-Card is not in Store Showcase");
        }
    }

    // returns 0 if player doesn't have the card,-1 if card is in one of the decks and 1 if card is sold
    public int sellCard(String cardName) throws IOException, ParseException {
        if (currentPlayer.getAvailableCards().contains(cardName)) {
            boolean inMage=false,inRogue=false,inWarlock=false;
            Hero initHero=currentPlayer.getHero();
            currentPlayer.setHero("mage");
            inMage=currentPlayer.getDeckCards().contains(cardName);
            currentPlayer.setHero("rogue");
            inRogue=currentPlayer.getDeckCards().contains(cardName);
            currentPlayer.setHero("warlock");
            inWarlock=currentPlayer.getDeckCards().contains(cardName);
            currentPlayer.setHero(initHero.toString());
            if(inMage || inRogue || inWarlock){
                return -1;
//                System.out.println("-Card is in one of your decks");
            }else {
                Card tempCard = Card.getCardObject(cardName);
                currentPlayer.setGems(currentPlayer.getGems() + (long) tempCard.getGemsCost());
//                currentPlayer.removeDeckCard(cardName);
                currentPlayer.removeAvailableCard(cardName);
//                System.out.println("Card is sold");
                updateShowcaseCards();
                return 1;
            }
        }else{
            return 0;
        }
    }

}
