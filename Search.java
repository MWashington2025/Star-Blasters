/**
 * <b>This</b> java class focuses on giving the user the ability to search, update, add, remove, and display for-
 * information (in this case player's info) within the application's terminal
 * <b>Within</b> this class also, when the user tries to search for players, the code will display a message depending on-
 * if the player entered by the user is within the system
 */

public class Search extends Edit {//Extends to the "Edit" class for uses
    public Search(String file) {
        this.filename = file;
        loadFromFile(file);
    }

    public Search() {

    }

    public boolean hasPlayer(String name) {//Checks to see if player is in the system
        return Info.containsKey(name.toLowerCase());
    }
    public Info getPlayer(String name) {//Receives player's info
        return Info.get(name.toLowerCase());
    }
    public boolean addPlayer(Info info) {//Adds player's info
        if (!hasPlayer(info.getPlayerName())) {
            Info.put(info.getPlayerName().toLowerCase(), info);
        }
        return false;
    }
    public boolean removePlayer(String PlayerName) {//Removes player's info
        return Info.remove(PlayerName.toLowerCase()) != null;
    }
    public void updatePlayer(String name, Info updatedInfo) {//Updates player's info
        if (hasPlayer(name)) {
            Info.put(name.toLowerCase(), updatedInfo);
        }
    }
    public void setPlayerName(String oldName, String newName) {//This section sets player's name
        Info p = getPlayer(oldName);
        if (p != null && !hasPlayer(newName)) {
            Info.remove(oldName.toLowerCase());
            Info updated = new Info(newName, p.getAvatarName(), p.ClassLevel, p.WorldRanking);
            Info.put(newName.toLowerCase(), updated);
        }
    }
    public void setAvatarName(String name, String newAvatar) {//This section sets player's avatar name
        Info p = getPlayer(name);
        if (p != null) {
            Info updated = new Info(p.getPlayerName(), newAvatar, p.ClassLevel, p.WorldRanking);
            Info.put(name.toLowerCase(), updated);
        }
    }
    public String getPlayerName(String name) {//Receives player's name
        Info p = getPlayer(name);
        return p != null ? p.getPlayerName() : "Not found.";
    }
    public String getAvatarName(String name) {//Receives player's avatar name
        Info p = getPlayer(name);
        return p != null ? p.getAvatarName() : "Not found.";
    }
}



