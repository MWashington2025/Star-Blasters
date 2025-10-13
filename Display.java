import java.util.*;

public class Display {
    Scanner scanner = new Scanner(System.in);//Allows a user to enter values
    Search s;
    public Display(String file) {
        s = new Search(file);
    }
    public Display runLoop() {
        String input;
        while (true) {//A while loop that will continue the menu options until the user enters "quit"
            System.out.println("\n(VIEW).View (ADD).Add (EDIT).Edit (REMOVE).Remove (SEARCH).Search (LOAD).Load (SAVE).Save (QUIT).Quit");//This will display the menu options
            input = ask("Choose: ");
            if (input.equals("Quit")) break;
            if (input.equals("View")) s.Info.values().forEach(System.out::println);
            else if (input.equals("Add")) setPlayerData();
            else if (input.equals("Edit")) editPlayer();
            else if (input.equals("Remove")) System.out.println(s.removePlayer(ask("Remove Player:")) ? "Removed." : "Not found.");
            else if (input.equals("Search")) System.out.println(getPlayerData(ask("Search Player: ")));
            else if (input.equals("Load")) System.out.println(s.loadFromFile(ask("File to Load: ")) ? "Loaded." : "Failed.");
            else if (input.equals("Save")) System.out.println(s.save() ? "Saved." : "Failed.");
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
        String avatar = ask("New Avatar: ");
        int level = parseInt("New Level: ");
        int worldrank = parseInt("New World Ranking: ");
        Info updated = new Info(player, avatar, level, worldrank);
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




