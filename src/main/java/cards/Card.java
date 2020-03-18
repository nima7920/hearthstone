package cards;
// imported libraries:

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Card {
    // defining general Card fields:
    private String name;
    private String description;
    private long manaCost;
    private long gemsCost;
    private Rarity rarity;
    private String cardClass;
    private String cardType;

    // class static fields:
    private static ArrayList<String> allCards, minionCards, spelCards;
// defining getter and setter for fields:

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getManaCost() {
        return manaCost;
    }

    public void setManaCost(long manaCost) {
        this.manaCost = manaCost;
    }

    public long getGemsCost() {
        return gemsCost;
    }

    public void setGemsCost(long gemsCost) {
        this.gemsCost = gemsCost;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public String getCardClass() {
        return cardClass;
    }

    public void setCardClass(String cardClass) {
        this.cardClass = cardClass;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    // static methods of card class:
    // getCardObject takes the name of the card and returns its object:

    public static Card getCardObject(String cardName) throws IOException, ParseException {
        File cardFile = new File("cards//all cards//" + cardName + ".JSON");
        if (cardFile.exists()) {
            FileReader cardReader = new FileReader(cardFile);
            JSONObject cardFields = new JSONObject();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(cardReader);
            cardFields = (JSONObject) obj;
            Card crd = null;
            // checking card type:
            switch ((String) cardFields.get("cardType")) {
                case "minion": {
                    crd = new Minion();
                    crd.setName((String) cardFields.get("name"));
                    crd.setDescription((String) cardFields.get("description"));
                    crd.setCardClass((String) cardFields.get("cardClass"));
                    crd.setCardType((String) cardFields.get("cardType"));
                    crd.setGemsCost((long) cardFields.get("gemsCost"));
                    crd.setManaCost((long) cardFields.get("manaCost"));
                    crd.setRarity(Rarity.valueOf((String) cardFields.get("rarity")));
                    ((Minion) crd).setAttack((long) cardFields.get("attack"));
                    ((Minion) crd).setHp((long) cardFields.get("hp"));

                    break;

                }
                case "spel": {
                    crd = new Spel();
                    crd.setName((String) cardFields.get("name"));
                    crd.setDescription((String) cardFields.get("description"));
                    crd.setCardClass((String) cardFields.get("cardClass"));
                    crd.setCardType((String) cardFields.get("cardType"));
                    crd.setGemsCost((long) cardFields.get("gemsCost"));
                    crd.setManaCost((long) cardFields.get("manaCost"));
                    crd.setRarity(Rarity.valueOf((String) cardFields.get("rarity")));

                    break;

                }
            }
            cardReader.close();
            return crd;
        } else {
            return null;
        }
    }

    // updateCardLists updates allCards,minionCards and spelCards lists from files
    public static void updateCardLists() throws IOException, ParseException {
        // allCards array:
        FileReader fr = new FileReader("cards//allCards.JSON");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(fr);
        JSONArray arr = (JSONArray) obj;
        allCards = new ArrayList<String>();
        for (int i = 0; i < arr.size(); i++) {
            allCards.add((String) arr.get(i));
        }
        fr.close();
        // spelCards array:
        fr = new FileReader("cards//spelCards.JSON");
        obj = parser.parse(fr);
        arr = (JSONArray) obj;
        spelCards = new ArrayList<String>();
        for (int i = 0; i < arr.size(); i++) {
            spelCards.add((String) arr.get(i));
        }
        fr.close();
        // minionCards array:
        fr = new FileReader("cards//minionCards.JSON");
        obj = parser.parse(fr);
        arr = (JSONArray) obj;
        minionCards = new ArrayList<String>();
        for (int i = 0; i < arr.size(); i++) {
            minionCards.add((String) arr.get(i));
        }
        fr.close();
    }

    public static ArrayList<String> getAllCards() throws IOException, ParseException {
        Card.updateCardLists();
        return allCards;
    }

    public static ArrayList<String> getMinionCards() throws IOException, ParseException {
        Card.updateCardLists();
        return minionCards;
    }

    public static ArrayList<String> getSpelCards() throws IOException, ParseException {
        Card.updateCardLists();
        return spelCards;
    }

    @Override

    public String toString() {
        return this.name;
    }
}
