import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.File;

public class Menu extends JFrame {
    private Display Menu; // Extends the "Display.java" Class
    StringBuilder sb = new StringBuilder();
    private Connection connection;


    public Menu() {
        Menu = new Display("players.txt"); //Extends the Display information

        setTitle("Star Blasters"); //This section sets up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        getContentPane().setBackground(Color.black);
        setLayout(new GridLayout(2, 3, 20, 20));

        JButton viewButton = new JButton("View"); //What the buttons will show on the menu
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton removeButton = new JButton("Remove");
        JButton searchButton = new JButton("Search");
        JButton connectButton = new JButton("Connect to database");
        JButton quitButton = new JButton("Quit");

        add(viewButton); //This section adds the buttons to the GUI
        add(addButton);
        add(editButton);
        add(removeButton);
        add(searchButton);
        add(connectButton);
        add(quitButton);

        viewButton.addActionListener(e -> viewPlayers()); //This section gives the buttons their action
        addButton.addActionListener(e -> addPlayer());
        editButton.addActionListener(e -> editPlayer());
        removeButton.addActionListener(e -> removePlayer());
        searchButton.addActionListener(e -> searchPlayer());
        connectButton.addActionListener(e -> databaseconnection());
        quitButton.addActionListener(e -> System.exit(0));
    }
    private void viewPlayers() {
        sb.setLength(0);
        if (connection != null) {// This section will grab all info within the database
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM Contender");
                ResultSetMetaData meta = rs.getMetaData();
                int cols = meta.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= cols; i++) {
                        sb.append(meta.getColumnName(i)).append(": ").
                                append(rs.getString(i)).append("  ");
                    }
                    sb.append("\n");
                }
                JOptionPane.showMessageDialog(this, sb.length() == 0 ? "Player not found." : sb.toString());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }
    private void addPlayer() {
        String name = JOptionPane.showInputDialog(this, "Please enter a name:");
        if (name == null || name.isEmpty()) return;
        String avatar = JOptionPane.showInputDialog(this, "Enter an Avatar:");
        int level = parseInt(JOptionPane.showInputDialog(this, "Enter a Class Level:"));
        int rank = parseInt(JOptionPane.showInputDialog(this, "Enter a World Ranking:"));
        String qualified = JOptionPane.showInputDialog(this, "Does player qualify, yes or no: ");
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement( //This section will add the values into the database and it's columns
                    "INSERT INTO Contender (player_name, avatar_name, class_level, world_level, qualified) " +
                            "VALUES (?, ?, ?, ?, ?)")) {
                ps.setString(1, name);
                ps.setString(2, avatar);
                ps.setInt(3, level);
                ps.setInt(4, rank);
                ps.setString(5, qualified);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Player added to database.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        } else {
            if (Menu.s.hasPlayer(name)) {
                JOptionPane.showMessageDialog(this, "Player already exists.");
                return;
            }
        }
    }
    private void editPlayer() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Not connected to the database!");
            return;
        }
        try (Statement stmt = connection.createStatement()) { // Grabs all names from the Contender table
            ResultSet rs = stmt.executeQuery("SELECT player_name FROM Contender");
            java.util.List<String> names = new java.util.ArrayList<>();
            while (rs.next()) {
                names.add(rs.getString("player_name"));
            }
            String[] playerNames = names.toArray(new String[0]); // Creates a dropdown box with all names
            String selectedName = (String) JOptionPane.showInputDialog(
                    this,
                    "Select a player to edit:",
                    "Edit Player",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    playerNames,
                    playerNames[0]
            );
            if (selectedName == null) return; // Creates a dropdown box with all options that can be changed
            String[] fields = {"avatar", "class_level", "world_level"};
            String column = (String) JOptionPane.showInputDialog(
                    this,
                    "Select the field to update:",
                    "Edit Field",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    fields,
                    fields[0]
            );
            if (column == null) return;
            String newValue = JOptionPane.showInputDialog(this, "Enter the new value:");
            if (newValue == null || newValue.isEmpty()) return;
            String sql;
            if (column.equalsIgnoreCase("class_level") || column.equalsIgnoreCase("world_level")) {
                sql = "UPDATE Contender SET " + column + " = ? WHERE player_name = ?";
            } else {
                sql = "UPDATE Contender SET " + column + " = ? WHERE player_name = ?";
            }

            try (PreparedStatement ps = connection.prepareStatement(sql)) { //Will show the option depending on what's selected
                if (column.equalsIgnoreCase("class_level") || column.equalsIgnoreCase("world_level")) {
                    ps.setInt(1, Integer.parseInt(newValue));
                } else {
                    ps.setString(1, newValue);
                }
                ps.setString(2, selectedName);
                int rows = ps.executeUpdate();
                JOptionPane.showMessageDialog(this,
                        rows > 0 ? "Info updated." : "Player not found.");
            }
        } catch (SQLException e) {// If there's issues connecting to the database
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void removePlayer() {
        String name = JOptionPane.showInputDialog(this, "Enter player name:");
        if (name == null || name.isEmpty()) return;

        if (connection != null) {//Allows user to remove players from the database
            try (PreparedStatement ps = connection.prepareStatement("DELETE FROM Contender WHERE player_name = ?")) {
                ps.setString(1, name);
                int rows = ps.executeUpdate();
                JOptionPane.showMessageDialog(this, rows > 0 ? "Player removed." : "Player not found.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }
    private void searchPlayer() {
        String name = JOptionPane.showInputDialog(this, "Search Player:");
        if (name == null || name.isEmpty()) return;
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(//This section will search for te entered players info
                    "SELECT * FROM Contender WHERE player_name = ?")) {
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    StringBuilder sb = new StringBuilder();
                    ResultSetMetaData meta = rs.getMetaData();
                    int cols = meta.getColumnCount();
                    for (int i = 1; i <= cols; i++) {
                        sb.append(meta.getColumnName(i))
                                .append(": ")
                                .append(rs.getString(i))
                                .append("\n");
                    }
                    JOptionPane.showMessageDialog(this, sb.toString(),
                            "Player Found", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Player not found.",
                            "Not Found", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Error searching player: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int parseInt(String text) { //This section will convert a string into an integer (For the edit section)
        try {
            return Integer.parseInt(text.trim());
        } catch (Exception e) {
            return 0;
        }
    }

private void databaseconnection() { //Database connection
        JFileChooser data = new JFileChooser();//Allows a user to upload and connect to a database from SQLite
        data.setDialogTitle("Choose File: ");
        data.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter
                ("SQLite DB Files", "db", "sqlite"));
        if (data.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { //Will determine if file is good or not
            File dataFile = data.getSelectedFile();
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:" + dataFile.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Connected to database: " + dataFile.getName());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error connecting: " + ex.getMessage());
            }
        }
}
public static void main(String[] args) { //main method
        SwingUtilities.invokeLater(() -> {
            Menu gui = new Menu();
            gui.setVisible(true);
        });
    }
}

