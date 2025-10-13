import java.io.*;
import java.util.*;

public abstract class Edit {
    protected String filename;
    final Map<String, Info> Info = new HashMap<>();//Stores users in a HashMap (data table)

    public boolean loadFromFile(String file) {
        Info.clear();
        boolean loaded = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split("-");
                if (p.length == 4) {
                    String name = p[0].trim();
                    String avatar = p[1].trim();
                    int level = Integer.parseInt(p[2].trim());
                    int rank = Integer.parseInt(p[3].trim());

                    Info i = new Info(name, avatar, level, rank);
                    Info.put(name.toLowerCase(), i);
                    loaded = true;
                } else {
                    System.out.println("Invalid line skipped: " + line);
                }
            }

            if (loaded) {
                System.out.println("File loaded.");
            } else {
                System.out.println("No valid entries found.");
            }

            return loaded;
        } catch (IOException e) {
            System.out.println("Failed to read file.");
            return false;
        }
    }


    public boolean saveToFile(String file) {//This section ties te "Map" section to help store people info
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Info i : Info.values())
                writer.write(i.toFile() + "\n");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean save() {
        return saveToFile(filename);
    }
}


