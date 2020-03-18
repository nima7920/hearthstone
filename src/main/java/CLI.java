import cards.Card;
import heros.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class CLI {
    private Player currentPlayer = new Player();
    private String currentMenu; // a parameter to determine the menu in which the player is
    private logWriter logger;
    private Store store;
    private final String help = "Hearthstone Command Line Interface guide: \n " +
            "-General Commands: \n" +
            "\t exit: logout from current profile \n" +
            "\t exit-a: exit the game \n " +
            "\t delete-player: delete current profile \n" +
            "-Menu commands: \n" +
            "\t collections: move to profile collections \n" +
            "\t store: move to Game Store \n" +
            "-Collections commands: \n" +
            "\t ls-a-hero: show all heros \n" +
            "\t ls-m-hero: show current hero \n" +
            "\t select [hero name]: select a specific hero \n" +
            "\t ls-a-cards: show all the cards that player owns \n" +
            "\t ls-m-cards: show the cards in players deck \n" +
            "\t ls-n-cards: show the cards that can be added to it \n" +
            "\t add [card name]: add a specific card to your deck \n" +
            "\t remove [card name]: remove a specific card from your deck \n" +
            "-Store commands: \n" +
            "\t buy [card name]: buy a specific card \n" +
            "\t wallet: show player's current wallet \n" +
            "\t sell [card name]: sell a card from player's cards \n" +
            "\t ls-s: show the cards that player can sell \n" +
            "\t ls-b: show the cards that player can buy";

    public CLI(Player player) throws IOException {
        currentPlayer = player;
        currentMenu = "main";
        logger = new logWriter(player);
        store = new Store(player);
    }

    public void deletePlayer() throws IOException {
        logger.deletePlayer();
        currentPlayer.deleteProfile();
    }

    // 'command' is the main method of CLI class,which reiceves commands from input and performs the proper action:
    public void Command(String s) throws IOException, ParseException {
// handling general commands:
        // exit command:
        if (s.equals("exit-a")) { // 'exit-a' command
            currentPlayer.updateProfile();
            logger.exiting(0);
            System.exit(0);
        }
        // help command
        else if (s.equals("hearthstone--help")) { // help command
            logger.writeLog("HELP ", "");
            System.out.println(help);

        }
        // collections menu:
        else if (s.equals("collections")) { // collections menu
            logger.writeLog("MENU:", "collections");
            currentMenu = "collections";
            System.out.println("-collections;your cards are:");
            for (String x : currentPlayer.getAvailableCards()) {
                System.out.println(x);
            }

        }
        // show owned hero
        else if (s.equals("ls-m-hero")) {
            if (currentMenu.equals("collections")) {
                logger.writeLog("SHOW:", "current hero");
                System.out.println("Your current hero is:" + currentPlayer.getHero().toString());
            } else {
                logger.writeLog("ERROR:", "not in collections menu when showing current hero");
                System.out.println("-You are not in collections menu.current menu is:" + currentMenu);
            }

        } else if (s.equals("ls-a-hero")) {
            if (currentMenu.equals("collections")) {
                logger.writeLog("SHOW LIST", "all hero");
                System.out.println("All Heros are:Mage-Rogue-Warlock");
            } else {
                logger.writeLog("ERROR:", "not in collections menu when showing all heros");
                System.out.println("-You are not in collections menu.current menu is:" + currentMenu);
            }

        } else if (s.startsWith("select ")) {
            if (currentMenu.equals("collections")) {
                if (Hero.getHero(s.substring(7)) != null) {
                    if(s.substring(7).equals("mage")) {
                        currentPlayer.setHero(s.substring(7));
                        logger.writeLog("SELECT", "selected hero " + s.substring(7));
                        System.out.println("-Hero selected successfully");
                    }else{
                        logger.writeLog("ERROR:", "selected hero " + s.substring(7)+" is locked");
                        System.out.println("-Selected hero is locked");
                    }
                } else {
                    logger.writeLog("ERROR:", "Selected hero doesn't exist:" + s.substring(7));
                    System.out.println("-Hero doesn't exists " + s.substring(8));
                }
            } else {
                logger.writeLog("ERROR:", "not in collections menu when selecting a hero");
                System.out.println("-You are not in collections menu.current menu is:" + currentMenu);
            }

        } else if (s.equals("ls-a-cards")) {
            if (currentMenu.equals("collections")) {
                logger.writeLog("SHOW LIST", "all owned cards");
                System.out.println("-Your cards are:");
                for (String x : currentPlayer.getAvailableCards()) {
                    System.out.println(x);
                }
            } else {
                logger.writeLog("ERROR:", "not in collections menu when showing all owned cards");
                System.out.println("-You are not in collections menu.current menu is:" + currentMenu);
            }

        } else if (s.equals("ls-m-cards")) { //
            if (currentMenu.equals("collections")) {
                logger.writeLog("SHOW LIST", "deck cards of hero "+currentPlayer.getHero().toString());
                System.out.println("-Current hero:"+currentPlayer.getHero().toString()+":");
                printDeckCards();
            } else {
                logger.writeLog("ERROR:", "not in collections menu when showing current deck");
                System.out.println("-You are not in collections menu.current menu is:" + currentMenu);
            }


        } else if (s.equals("ls-n-cards")) {
            if (currentMenu.equals("collections")) {
                logger.writeLog("SHOW LIST", "addable cards");
                printAddableCards();
            } else {
                logger.writeLog("ERROR:", "not in collections menu when showing addable cards");
                System.out.println("-You are not in collections menu.current menu is:" + currentMenu);
            }


        }

        else if (s.startsWith("add ")) {
            if (currentMenu.equals("collections")) {
                switch (currentPlayer.addDeckCard(s.substring(4))) {
                    case 1: {
                        logger.writeLog("ADD", s.substring(4) + " to deck");
                        System.out.println("-Card added successfully");
                        break;
                    }
                    case 0: {
                        logger.writeLog("ERROR:", "deck is full for adding " + s.substring(4));
                        System.out.println("-Deck is full");
                        break;
                    }
                    case -1: {
                        logger.writeLog("ERROR:", "there are two cards of " + s.substring(4) + " in deck");
                        System.out.println("-there are two cards of this type in deck");
                        break;
                    }
                    case -2: {
                        logger.writeLog("ERROR:", s.substring(4) + " is in an illegal class");
                        System.out.println("-Selected Card is in illegal class");
                        break;
                    }
                    case -3: {
                        logger.writeLog("ERROR", s.substring(4) + " does not exist in available cards");
                        System.out.println("-Card doesn't exist in your available cards");
                        break;
                    }
                }
            } else {
                logger.writeLog("ERROR:", "not in collections menu when adding a card to deck");
                System.out.println("-You are not in collections menu.current menu is:" + currentMenu);
            }


        } else if (s.startsWith("remove ")) {
            if (currentMenu.equals("collections")) {
                if (!currentPlayer.getDeckCards().contains(s.substring(7))) {
                    logger.writeLog("ERROR:", s.substring(7) + " is not in deck to be removed");
                    System.out.println("-Card is not in your deck");
                } else {
                    currentPlayer.removeDeckCard(s.substring(7));
                    logger.writeLog("REMOVE", s.substring(7) + " from deck");
                    System.out.println("-Card removed successfully");
                }
            } else {
                logger.writeLog("ERROR:", "not in collections menu when removing a card from deck");
                System.out.println("-You are not in collections menu.current menu is:" + currentMenu);
            }


        }
        // store menu:
        else if (s.equals("store")) {
            currentMenu = "store";
            logger.writeLog("MENU", "store");
            System.out.println("Welcome to Game Store!");
            store.updateShowcaseCards();
            store.printCards();

        } else if (s.equals("wallet")) {
            if (currentMenu.equals("store")) {
                logger.writeLog("SHOW", "wallet");
                System.out.println("-your total gems:" + currentPlayer.getGems());
            } else {
                logger.writeLog("ERROR:", "not in store menu when showing wallet");
                System.out.println("-You are not in store menu.current menu is:" + currentMenu);
            }
        } else if (s.equals("ls-b")) {
            if (currentMenu.equals("store")) {
                logger.writeLog("SHOW LIST", "store showcase");
                store.printCards();
            } else {
                logger.writeLog("ERROR:", "not in store menu when showing all showcase cards");
                System.out.println("-You are not in store menu.current menu is:" + currentMenu);
            }
        } else if (s.equals("ls-s")) {
            if (currentMenu.equals("store")) {
                logger.writeLog("SHOW LIST", "player's salable cards");
                System.out.println("-Your cards that you can sell are:(after selling,you can buy that card again)");
                for (String x : currentPlayer.getAvailableCards()) {
                    System.out.println(x);
                }
            } else {
                logger.writeLog("ERROR:", "not in store menu when showing salable cards");
                System.out.println("-You are not in store menu.current menu is:" + currentMenu);
            }
        } else if (s.startsWith("buy ")) {
            if (currentMenu.equals("store")) {
                logger.writeLog("BUY", "card " + s.substring(4));
                store.buyCard(s.substring(4));
            } else {
                logger.writeLog("ERROR:", "not in store menu when buying a card");
                System.out.println("-You are not in store menu.current menu is:" + currentMenu);
            }
        } else if (s.startsWith("sell ")) {
            if (currentMenu.equals("store")) {
                logger.writeLog("SELL", "card " + s.substring(5));
                store.sellCard(s.substring(5));
            } else {
                logger.writeLog("ERROR:", "not in store menu when selling a card");
                System.out.println("-You are not in store menu.current menu is:" + currentMenu);
            }
        } else if (s.equals("test")) {
            System.out.println(currentPlayer.getDeckCards().toString());
            currentPlayer.setHero("rogue");
            System.out.println(currentPlayer.getDeckCards().toString());
            currentPlayer.setHero("warlock");
            System.out.println(currentPlayer.getDeckCards().toString());
            currentPlayer.setHero("mage");
        } else {
            logger.writeLog("ERROR:", "command not recognized");
            System.out.println("-command not recognized");
        }
    }

    public void logout() throws IOException {
        logger.exiting(1);
    }

    private void printDeckCards() {
        System.out.println("-Your deck cards are:");
        ArrayList<String> crds = currentPlayer.getDeckCards();
        for (int i = 0; i < crds.size(); i++) {
            if (crds.indexOf(crds.get(i)) == i) {
                int j = (crds.lastIndexOf(crds.get(i)) == i) ? 1 : 2;
                System.out.println("\t" + crds.get(i) + ":" + j);

            }
        }
    }

    private void printAddableCards() throws IOException, ParseException {
        System.out.println("-Cards that you can add to your deck are:");
        for (String x : currentPlayer.getAvailableCards()) {
            Card crd = Card.getCardObject(x);
            if (currentPlayer.getDeckCards().indexOf(x) == currentPlayer.getDeckCards().lastIndexOf(x)
                    && (crd.getCardClass().equals("neutral") || crd.getCardClass().equals(currentPlayer.getHero().toString()))) {
                System.out.println("\t" + x);
            }
        }
    }

}
