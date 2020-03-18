import cards.Card;
import cards.Minion;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) throws ParseException, org.json.simple.parser.ParseException, IOException {
        Card.updateCardLists();
        // logging in to account
        Scanner input = new Scanner(System.in);

        while (true) {
            Player player = new Player();
            System.out.println("-Already have an account?(y/n)");
            boolean entered = false;
            while (!entered) {
                String answer = input.nextLine();
                if(answer.toLowerCase().equals("exit-a"))
                    System.exit(0);
                switch (answer.toLowerCase()) {
                    case ("y"): {
                        System.out.println("-User name:");
                        String username = input.nextLine();
                        System.out.println("-password:");
                        String password = input.nextLine();
                        int result = player.loginProfile(username, password);
                        while (result != 1) {
                            if (result == 0) {
                                System.out.println("-Profile doesn't exist.Please try again");
                                System.out.println("-User name:");
                                username = input.nextLine();
                                System.out.println("-password:");
                                password = input.nextLine();
                                result = player.loginProfile(username, password);
                            } else {
                                System.out.println("-Wrong password inserted.Please try again");
                                System.out.println("-password:");
                                password = input.nextLine();
                                result = player.loginProfile(username, password);
                            }
                        }
                        System.out.println("-logged in successfully,welcome " + player.getName() + "!");
                        entered = true;
                        break;
                    }
                    case ("n"): {
                        System.out.println("-Warning!If the profile that you are creating already exists,previous contents will be removed.");
                        System.out.println("-User name:");
                        String username = input.nextLine();
                        System.out.println("-password:");
                        String password = input.nextLine();
                        player.createProfile(username, password);
                        System.out.println("-account created!");
                        entered = true;
                        break;
                    }
                    default: {
                        System.out.println("-answer not recognized.please try again");
                        break;
                    }

                }
            }
            CLI cli = new CLI(player);
            // receiving commands:
            while (true) {
                String s=input.nextLine().toLowerCase().trim();

                if(s.equals("exit")){
                    System.out.println("-logging out...");
                    cli.logout();
                    break;
                }else if(s.equals("delete-player")) {
                    System.out.println("-password:");
                    String t=input.nextLine().toLowerCase().trim();
                    if(player.getPassword().equals(t)) {
                        cli.deletePlayer();
                        System.out.println("-profile deleted.");
                        break;
                    }else{
                        System.out.println("-wrong password");
                        continue;
                    }

                }
                else{
                    cli.Command(s);
            }
            }
        }
    }
}
