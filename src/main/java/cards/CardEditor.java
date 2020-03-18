package cards;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CardEditor {
    static void spelCard(String name,long manaCost,long gemsCost,String cardClass,String rarity,String description) throws IOException, ParseException {
        // writing Card File:
        FileWriter fileWriter=new FileWriter("cards//all cards//"+name+".JSON");
        JSONObject cardFields=new JSONObject();
        cardFields.put("name",name);
        cardFields.put("manaCost",manaCost);
        cardFields.put("gemsCost",gemsCost);
        cardFields.put("cardType","spel");
        cardFields.put("cardClass",cardClass);
        cardFields.put("rarity",rarity);
        cardFields.put("description",description);
        fileWriter.write(cardFields.toJSONString());
        fileWriter.flush();
        fileWriter.close();

        // adding card to card lists:
        FileReader frAC=new FileReader("cards//allCards.JSON");
        JSONParser parser=new JSONParser();
        Object objAC=parser.parse(frAC);
        JSONArray arrAC=(JSONArray) objAC;
        frAC.close();
        if(!arrAC.contains(name)){
            arrAC.add(name);
            FileWriter fw=new FileWriter("cards//allCards.JSON");
            fw.write(arrAC.toJSONString());
            fw.flush();
            fw.close();
            FileReader frSC=new FileReader("cards//spelCards.JSON");
            Object objSC=parser.parse(frSC);
            JSONArray arrSC=(JSONArray) objSC;
            arrSC.add(name);
            frSC.close();
            FileWriter fw2=new FileWriter("cards//spelCards.JSON");
            fw2.write(arrSC.toJSONString());
            fw2.flush();
            fw2.close();

        }

    }
    static void minionCard(String name,long manaCost,long gemsCost,String cardClass,String rarity,String description,int attack,int hp) throws IOException, ParseException {
        FileWriter fileWriter=new FileWriter("cards//all cards//"+name+".JSON");
        JSONObject cardFields=new JSONObject();
        cardFields.put("name",name);
        cardFields.put("manaCost",manaCost);
        cardFields.put("gemsCost",gemsCost);
        cardFields.put("cardType","minion");
        cardFields.put("cardClass",cardClass);
        cardFields.put("rarity",rarity);
        cardFields.put("description",description);
        cardFields.put("attack",attack);
        cardFields.put("hp",hp);
        fileWriter.write(cardFields.toJSONString());
        fileWriter.flush();
        fileWriter.close();

        // adding card to card lists:
        FileReader frAC=new FileReader("cards//allCards.JSON");
        JSONParser parser=new JSONParser();
        Object objAC=parser.parse(frAC);
        JSONArray arrAC=(JSONArray) objAC;
        frAC.close();
        if(!arrAC.contains(name)){
            arrAC.add(name);
            FileWriter fw=new FileWriter("cards//allCards.JSON");
            fw.write(arrAC.toJSONString());
            fw.flush();
            fw.close();
            FileReader frSC=new FileReader("cards//minionCards.JSON");
            Object objSC=parser.parse(frSC);
            JSONArray arrSC=(JSONArray) objSC;
            arrSC.add(name);
            frSC.close();
            FileWriter fw2=new FileWriter("cards//minionCards.JSON");
            fw2.write(arrSC.toJSONString());
            fw2.flush();
            fw2.close();

        }
    }
    public static void main(String[] args) throws IOException, ParseException {
//        FileWriter fwAC=new FileWriter("cards//allCards.JSON");
//        FileWriter fwMC=new FileWriter("cards//minionCards.JSON");
//        FileWriter fwSC=new FileWriter("cards//spelCards.JSON");
//        JSONArray arr=new JSONArray();
//        fwAC.write(arr.toJSONString());
//        fwMC.write(arr.toJSONString());
//        fwSC.write(arr.toJSONString());
//        fwAC.flush();
//        fwMC.flush();
//        fwSC.flush();
//        fwAC.close();
//        fwMC.close();
//        fwSC.close();

        spelCard("polymorph",4,3,"mage","common",
                "transform a minion into a 1\1 sheep");
     spelCard("friendly smith",1,1,"rogue","common","discover a weapon from any class.add it to your adventure deck with +2/+2");
       minionCard("dreadscale",3,17,"warlock","legendary","at the end of your turn,deal 1 damage to all other minions",4,2);

        minionCard("river crocolisk",2,3,"neutral","common",
                "",2,3);
        minionCard("arcane servant",2,3,"neutral","common",
                "",2,3);
        minionCard("silverback patriarch",3,4,"neutral","common",
                "taunt",1,4);
        minionCard("phantom militia",3,7,"neutral","rare",
                "echo taunt",2,4);
//        minionCard("elven archer",1,2,"neutral","common",
//                "battlecry:Deal 1 damage",1,1);
        minionCard("murloc raider",1,2,"neutral","common",
                "",2,1);
        minionCard("stonetusk boar",1,2,"neutral","rare",
                "charge",1,1);
        minionCard("rotten applebaum",5,8,"neutral",
                "rare","taunt deathrattle:restore 4 health to your hero",4,5);
        minionCard("the black knight",6,17,"neutral","legendary",
                "battlecry:destroy an enemy minion with taunt",4,5);
        minionCard("proud defender",4,5,"neutral","common",
                "taunt has +2 attack while you have no other minions",2,6);
//        minionCard("nima a",6,12,"neutral","epic",
//                "taunt",6,8);
//        minionCard("mojtaba olama",4,8,"neutral","rare",
//                "",3,4);
//        minionCard("sam sadat",5,8,"neutral","rare",
//                "",3,5);
//        minionCard("agha haghi",7,16,"neutral","legendary",
//                "",0,0);


        spelCard("fireball",4,4,"neutral",
                "common","deal 6 damage");
        spelCard("arcane missiles",1,3,"neutral",
                "common","deal 3 damage randomly split among all enemies");
        spelCard("assassinate",5,4,"neutral",
                "common","destroy an enemy minion");
        spelCard("sinister strike",1,3,"rogue",
                "common","deal 3 damage to enemy hero");
        spelCard("hellfire",4,3,"neutral",
                "common","deal 3 damage to ALL characters");
        spelCard("drain life",3,5,"warlock",
                "common","deal 2 damage,restore 2 health to your hero");


       //        spelCard("",1,1,"","","");
//        minionCard("",3,"","","",4,2);
        spelCard("arcane explosion",2,3,"neutral",
                "common","deal one damage to all enemy minions");
        spelCard("iron fist",3,6,"mage",
                "rare","deal 5 damage to enemy hero");
//        spelCard("",2,2,"neutral","common","add 2 health to your hero");
//        spelCard("",1,6,"neutral","rare","increase your manas by 3");
//        spelCard("",1,"","","");

    }
}
