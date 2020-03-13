
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;

import heros.*;
import cards.*;

public class Player {
    // defining basic fields:
    // main fields:
    private String name, password;
    private int gems = 50;
    private Hero hero;
    private ArrayList<String> availableCards;
    private ArrayList<String> deckCards;

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
        hero = new Mage();  // default hero

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
    public void setGems(int g) {
        this.gems = g;
    }

    public int getGems() {
        return this.gems;
    }

    // hero
    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }


    public void createProfile(String Name, String Password) throws IOException {
        // setting fields in class:
        profileWriter = new FileWriter("profiles//" + Name + ".JSON");
        profileReader = new FileReader("profiles//" + Name + ".JSON");
        setName(Name);
        setPassword(Password);

// adding fields to JSON Object
        profileFields.put("username", getName());
        profileFields.put("password", getPassword());
        profileFields.put("gems", getGems());
        profileFields.put("hero", hero.toString());
        profileFields.put("availableCards", availableCards);
        profileFields.put("deckCards", deckCards);

// adding fields to profile
        profileWriter.write(profileFields.toJSONString());
        profileWriter.flush();
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
            profileWriter = new FileWriter("profiles//" + Name + ".JSON");
            // temporary objects for reading the file:
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(profileReader);
            profileFields = (JSONObject) obj;
            if (Password.equals((String) profileFields.get("password"))) {  // right password inserted

                // setting profile fields:
                setName((String) profileFields.get("name"));
                setPassword((String) profileFields.get("password"));
                setGems((int) profileFields.get("gems"));
                setHero(Hero.getHero((String) profileFields.get("hero")));
                availableCards = (ArrayList) profileFields.get("availableCards");
                deckCards = (ArrayList) profileFields.get("deckCards");

                return 1; // logged in successfully
            } else {
                return -1; // password is wrong
            }
        } else {
            return 0; // no such profile exists
        }
    }

    // updates the data written in profile:
    public void updateProfile() throws IOException {

        // rewriting field in JSON object:
        profileFields.put("username", getName());
        profileFields.put("password", getPassword());
        profileFields.put("gems", getGems());
        profileFields.put("hero", hero.toString());
        profileFields.put("availableCards", availableCards);
        profileFields.put("deckCards", deckCards);

        // writing JSON Object in file:
        profileWriter.write(profileFields.toJSONString());
        profileWriter.flush();
    }

    // method to add and remove card to Card Arrays:

    public void addAvailableCard(String cardName) {
        availableCards.add(cardName);
    }

    // method is defined int to determine the final action:
    // returns 0 if deck is full,-1 if there are two same cards in the deck,and 1 if addition is successful
    public int addDeckCard(String cardName) {
        if (deckCards.size() >= 30) // deck is full
            return 0;
        if (deckCards.indexOf(cardName) < deckCards.lastIndexOf(cardName)) // there two cards with same name as cardName
            return -1;
        deckCards.add(cardName);
        return 1;
    }

    public void removeAvailableCard(String cardName) {
        availableCards.remove(cardName);
    }

    public void removeDeckCard(String cardName) {
        deckCards.remove(cardName);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
