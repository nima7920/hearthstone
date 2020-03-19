
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import heros.*;
import cards.*;

public class Player {
    // defining basic fields:
    // main fields:
    private String name, password;
    private long gems = 50;
    private Hero hero;
    private long id;
    private ArrayList<String> availableCards;
    private ArrayList<String> deckCards;
    // defining heros:
    private Hero mage = new Mage(), rogue = new Rogue(), warlock = new Warlock();
    // auxiliary fields:
    private JSONArray availableCardsJSON;
    private JSONArray deckCardsJSON;
    private FileReader profileReader;
    private FileWriter profileWriter;
    private File profileFile;
    private JSONObject profileFields;

    // class constructor:
    public Player() {
        availableCards = new ArrayList<>();
        deckCards = new ArrayList<>();
        availableCardsJSON = new JSONArray();
        deckCardsJSON = new JSONArray();
        profileFields = new JSONObject();
        hero = mage;  // default hero
        deckCards = mage.getDeckCards();
    }

    // defining getters and setters:
// Name:
    public void setName(String s) {
        this.name = s;
    }

    public String getName() {
        return this.name;
    }

    // Password
    public void setPassword(String s) {
        this.password = s;
    }

    public String getPassword() {
        return this.password;
    }

    // Gems:
    public void setGems(long g) {
        this.gems = g;
    }

    public long getGems() {
        return this.gems;
    }

    // hero
    public Hero getHero() {
        return hero;
    }

    public void setHero(String heroName) throws IOException, org.json.simple.parser.ParseException {
        if(heroName.equals("mage"))
            this.hero=mage;
        if(heroName.equals("rogue"))
            this.hero=rogue;
        if(heroName.equals("warlock"))
            this.hero=warlock;
        updateDeck();
    }

    // id
    public long getId() {
        return id;
    }

    // available cards:
    public ArrayList<String> getAvailableCards() {
        return availableCards;
    }

    // deck cards:
    public ArrayList<String> getDeckCards() {
        return deckCards;
    }

    public void createProfile(String Name, String Password) throws IOException {
        // setting fields in class:
        profileWriter = new FileWriter("profiles//" + Name + ".JSON");
        profileReader = new FileReader("profiles//" + Name + ".JSON");
        setName(Name);
        setPassword(Password);
        this.id = System.currentTimeMillis();
        availableCards=new ArrayList<>(Arrays.asList("polymorph","dreadscale","friendly smith","arcane explosion","arcane servant","fireball","murloc raider","river crocolisk",
                "stonetusk boar","silverback patriarch","hellfire","the black knight"));
// adding fields to JSON Object
        profileFields.put("name", getName());
        profileFields.put("password", getPassword());
        profileFields.put("gems", getGems());
        profileFields.put("hero", hero.toString());
        profileFields.put("availableCards", availableCards);
        profileFields.put("mage",mage.getDeckCards());
        profileFields.put("rogue",rogue.getDeckCards());
        profileFields.put("warlock",warlock.getDeckCards());
        profileFields.put("id", id);
// adding fields to profile
        profileWriter.write(profileFields.toJSONString());
        profileWriter.flush();
        profileWriter.close();
        profileReader.close();
    }

    public ArrayList<String> getHeroDeck(String heroName){
        return Hero.getHero(heroName).getDeckCards();
    }

    // isProfileExist checks whether a profile with a given name exists:
    public boolean isProfileExist(String Name) {
        profileFile = new File("profiles//" + Name + ".JSON");
        return profileFile.exists();
    }

    // returns 0 if profile doesn't exist,-1 if profile exists but password is wrong and 1 if everything goes right:
    public int loginProfile(String Name, String Password) throws IOException, ParseException, org.json.simple.parser.ParseException {
        if (isProfileExist(Name)) {
            profileReader = new FileReader("profiles//" + Name + ".JSON");

//            profileWriter = new FileWriter("profiles//" + Name + ".JSON");
            // temporary objects for reading the file:
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(profileReader);
            profileFields = (JSONObject) obj;
            if (Password.equals((String) profileFields.get("password"))) {  // right password inserted

                // setting profile fields:
                setName((String) profileFields.get("name"));
                setPassword((String) profileFields.get("password"));
                setGems((long) profileFields.get("gems"));
                this.id = (long) profileFields.get("id");
                if(profileFields.get("hero").equals("mage"))
                    this.hero=mage;
                if(profileFields.get("hero").equals("rogue"))
                    this.hero=rogue;
                if(profileFields.get("hero").equals("warlock"))
                    this.hero=warlock;
                availableCards = (ArrayList) profileFields.get("availableCards");
                mage.setDeckCards( (ArrayList) profileFields.get("mage"));
                rogue.setDeckCards((ArrayList) profileFields.get("rogue"));
                warlock.setDeckCards((ArrayList) profileFields.get("warlock"));
                updateDeck();
                profileReader.close();
                return 1; // logged in successfully
            } else {
                profileReader.close();
                return -1; // password is wrong
            }
        } else {
            return 0; // no such profile exists
        }
    }

    public void deleteProfile() throws IOException {
        profileFile.delete();
    }

    // updates the data written in profile:
    public void updateProfile() throws IOException {

        // rewriting field in JSON object:
        profileFields.put("username", getName());
        profileFields.put("password", getPassword());
        profileFields.put("gems", getGems());
        profileFields.put("id", id);
        profileFields.put("hero", hero.toString());
        profileFields.put("availableCards", availableCards);
        profileFields.put("mage",mage.getDeckCards());
        profileFields.put("rogue",rogue.getDeckCards());
        profileFields.put("warlock",warlock.getDeckCards());

        profileWriter = new FileWriter("profiles//" + getName() + ".JSON");
        // writing JSON Object in file:
        profileWriter.write(profileFields.toJSONString());
        profileWriter.flush();
        profileWriter.close();
    }

    // method to add and remove card to Card Arrays:

    public void addAvailableCard(String cardName) {
        availableCards.add(cardName);
    }

    // method is defined int to determine the final action:
    // returns 0 if deck is full,-1 if there are two same cards in the deck,-2 if card is not in hero class,
    // -3 if card is not available,and 1 if addition is successful

    public int addDeckCard(String cardName) throws IOException, org.json.simple.parser.ParseException {
        if (deckCards.size() >= 30) // deck is full
            return 0;
        if (deckCards.indexOf(cardName) < deckCards.lastIndexOf(cardName)) // there two cards with same name as cardName
            return -1;
        if (availableCards.contains(cardName) != true)
            return -3;
        Card tempCard = Card.getCardObject(cardName); // not null,because we know that cardName is in available cards
        if (!tempCard.getCardClass().equals("neutral") && !tempCard.getCardClass().equals(getHero().toString())) // card is in illegal class
            return -2;

        deckCards.add(cardName);
        return 1;
    }

    public void updateDeck() throws IOException, org.json.simple.parser.ParseException {
      deckCards=hero.getDeckCards();
    }

    public void removeAvailableCard(String cardName) {
        availableCards.remove(cardName);
    }

    public void removeDeckCard(String cardName) {
        deckCards.remove(cardName);
        deckCards.remove(cardName);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
