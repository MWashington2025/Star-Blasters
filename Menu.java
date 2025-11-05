import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame {
    private Display Menu; // Extends the "Display.java" Class
    StringBuilder sb = new StringBuilder();

    public Menu() {
        Menu = new Display("players.txt"); //Extends the Display information

        setTitle("Star Blasters"); //This section sets up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        getContentPane().setBackground(Color.black);
        setLayout(new GridLayout(2, 2, 200, 200));

        JButton viewButton = new JButton("View"); //What the buttons will show on the menu
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton removeButton = new JButton("Remove");
        JButton searchButton = new JButton("Search");
        JButton loadButton = new JButton("Load");
        JButton saveButton = new JButton("Save");
        JButton quitButton = new JButton("Quit");

        add(viewButton); //This section adds the buttons to the GUI
        add(addButton);
        add(editButton);
        add(removeButton);
        add(searchButton);
        add(loadButton);
        add(saveButton);
        add(quitButton);

        viewButton.addActionListener(e -> viewPlayers()); //This section gives the buttons their action
        addButton.addActionListener(e -> addPlayer());
        editButton.addActionListener(e -> editPlayer());
        removeButton.addActionListener(e -> removePlayer());
        searchButton.addActionListener(e -> searchPlayer());
        loadButton.addActionListener(e -> loadFile());
        saveButton.addActionListener(e -> saveFile());
        quitButton.addActionListener(e -> System.exit(0));
    }
    private void viewPlayers() { //Allows users to view all players on the list
        Menu.s.Info.values().forEach(i -> sb.append(i.toString()).append("\n"));
        JOptionPane.showMessageDialog(this, sb.length() == 0 ? "No information available." : sb.toString());
    }
    private void addPlayer() { //Allows users to add players to the list
        String name = JOptionPane.showInputDialog(this, "Please enter a name:");
        if (name == null || name.isEmpty()) return;
        if (Menu.s.hasPlayer(name)) {
            JOptionPane.showMessageDialog(this, "Player already exists."); //If the player is already on the list
            return;
        }
        String avatar = JOptionPane.showInputDialog(this, "Enter a Avatar:"); //This section will display actions depending on what's selected
        int level = parseInt(JOptionPane.showInputDialog(this, "Enter a Level:"));
        int rank = parseInt(JOptionPane.showInputDialog(this, "Enter a World Ranking:"));
        Info i = new Info(name, avatar, level, rank);
        Menu.s.addPlayer(i);
        JOptionPane.showMessageDialog(this, "Player has been added.");
    }
    private void editPlayer() {
        if (Menu.s.Info.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No players available to edit.");
            return;
        }
        String[] playerNames = Menu.s.Info.keySet().toArray(new String[0]);
        String player = (String) JOptionPane.showInputDialog(
                this,"Select a player to edit:","Edit Player",
                JOptionPane.PLAIN_MESSAGE,null,
                playerNames, playerNames[0]);
        if (player == null) return;
        Info current = Menu.s.getPlayer(player);
        if (current == null) {
            JOptionPane.showMessageDialog(this, "Player not found.");
            return;
        }
        String[] options = {"Avatar", "Class Level", "WorldRank"};
        String choice = (String) JOptionPane.showInputDialog(
                this,"Select field to edit:","Edit Info",
                JOptionPane.PLAIN_MESSAGE,null,
                options, options[0]);
        if (choice == null) return;
        String avatar = current.getAvatar();
        int level = current.getLevel();
        int Worldrank = current.getWorldRanking();
        switch (choice) {
            case "Avatar":
                avatar = JOptionPane.showInputDialog(this, "New Avatar:", avatar);
                break;
            case "Class Level":
                level = parseInt(JOptionPane.showInputDialog(this, "New Class Level:", level));
                break;
            case "WorldRank":
                Worldrank = parseInt(JOptionPane.showInputDialog(this, "New World Ranking:", Worldrank));
                break;
        }
        Info updated = new Info(player, avatar, level, Worldrank);
        Menu.s.updatePlayer(player, updated);
        JOptionPane.showMessageDialog(this, "Info has been updated.");
    }
    private void removePlayer() { //Allows users to remove players to the list
        String name = JOptionPane.showInputDialog(this, "Please enter player name to remove:");
        if (Menu.s.removePlayer(name))
            JOptionPane.showMessageDialog(this, "Player removed.");
        else
            JOptionPane.showMessageDialog(this, "Player not found.");
    }
    private void searchPlayer() { //Allows users to look up players info on the list
        String name = JOptionPane.showInputDialog(this, "Search Player:");
        String result = Menu.getPlayerData(name);
        JOptionPane.showMessageDialog(this, result);
    }
    private void loadFile() { //Allows users to load a file from their system
        String file = JOptionPane.showInputDialog(this, "File to Load:");
        boolean success = Menu.s.loadFromFile(file);
        JOptionPane.showMessageDialog(this, success ? "File loaded." : "Failed to load file.");
    }
    private void saveFile() { //Allows users to save the file loaded
        boolean success = Menu.s.save();
        JOptionPane.showMessageDialog(this, success ? "File saved." : "Failed to save file.");
    }
    private int parseInt(String text) { //This section will convert a string into an integer (For the edit section)
        try {
            return Integer.parseInt(text.trim());
        } catch (Exception e) {
            return 0;
        }
    }
    public static void main(String[] args) { //main method
        SwingUtilities.invokeLater(() -> {
            Menu gui = new Menu();
            gui.setVisible(true);
        });
    }
}

