import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * <b>This</b> entire java class once started will display the functions of the code through the application's terminal
 * <b>Within</b> the "Display" public class, the code will run through all and display all the functions inputted through the code
 */

public class Display {

    /**
     * <b>This</b> entire class holds and displays all menu options for a user to interact with from the application's terminal, from adding, viewing, and deleting players
     * to adding a text file containing player's information
     */

    Scanner scanner = new Scanner(System.in);//Allows a user to enter values
    Search s;
    public Display(String file) {
        s = new Search(file);
    }
    public Display runLoop() {
        String input;
        while (true) {//A while loop that will continue the menu options until the user enters "quit"
            System.out.println("\n(1:).View (2:).Add (3:).Edit (4:).Remove (5:).Search (6:).Load (7:).Save (8:).Quit");//This will display the menu options
            input = ask("Choose: ");
            if (input.equals("8")) break;
            if (input.equals("1")) s.Info.values().forEach(System.out::println);
            else if (input.equals("2")) setPlayerData();
            else if (input.equals("3")) editPlayer();
            else if (input.equals("4")) System.out.println(s.removePlayer(ask("Remove Player:")) ? "Removed." : "Not found.");
            else if (input.equals("5")) System.out.println(getPlayerData(ask("Search Player: ")));
            else if (input.equals("6")) System.out.println(s.loadFromFile(ask("File to Load: ")) ? "Loaded." : "Failed.");
            else if (input.equals("7")) System.out.println(s.save() ? "Saved." : "Failed.");
            else System.out.println("Invalid choice, please try again.");
        }
        return this;
    }
    public Display setPlayerData() {//This section will ask to enter a player name. If one already exits then it will return a message
        String name = ask("Player: ");
        if (s.hasPlayer(name)) {
            System.out.println("Player already exists.");
            return this;
        }
        String avatar = ask("Avatar: ");
        int level = parseInt("Level: ");
        int rank = parseInt("WorldRanking: ");
        Info i = new Info(name, avatar, level, rank);
        if (s.addPlayer(i)) System.out.println("Added.");
        return this;
    }
    public String getPlayerData(String name) {//This section will be used to search for a player. If player not found then a message will appear.
        Info i = s.getPlayer(name);
        return i == null ? "Not found." : i.toString();
    }
    public Display editPlayer() {//This section is used when the "edit" option is selected
        String player = ask("Player to edit: ");
        if (!s.hasPlayer(player)) {
            System.out.println("Player not found.");
            return this;
        }
        Info current = s.getPlayer(player); //Updated code will allow users to select which option to make changes
        System.out.println("Please choose: ");
        System.out.println("(A or a): Avatar Name");
        System.out.println("(L or l): Level");
        System.out.println("(W or w): WorldRank");
        String choose = ask("Please choose: ");

        String avatar = current.getAvatar(); //Receives info for changes
        int level = current.getLevel();
        int worldrank = current.getWorldRanking();

        switch (choose) { //Within the terminal, gives the user options to select which info will be changed and updated
            case "A", "a": avatar = ask("New Avatar: ");
                break;
            case "L", "l": level = parseInt("New Level: ");
                break;
            case "W", "w": worldrank = parseInt("New World Ranking: ");
                break;
            default: System.out.println("Invalid choice.");
                return this;
        }
        Info updated = new Info(player, avatar, level, worldrank); //This section will confirm the information has been changed successfully
        s.updatePlayer(player, updated);
        System.out.println("Updated.");
        return this;
    }
    public String ask(String msg) {//This section will allow a user to move to the next line if no errors are present
        System.out.print(msg);
        return scanner.nextLine().trim();
    }
    public int parseInt(String msg) {
        try { return Integer.parseInt(ask(msg)); }
        catch (Exception e) { return 0; }
    }
    public static void main(String[] args) {//Main method
        new Display("players.txt").runLoop();
    }

}




