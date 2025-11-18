/**
 * <b>This</b> java class really focuses on being the building block for the code
 * <b>Within</b> this class attributes are named and then constructors are then given to the attributes to act as placeholders
 */

public class Info {//Attributes for player's info, this is what going to be displayed
    String PlayerName;
    String AvatarName;
    int ClassLevel;
    int WorldRanking;
    boolean Qualified;

    public Info(String Player, String Avatar, int Level, int Ranking) {//Constructor for the player's info (think as placeholders)
        this.PlayerName = Player; //placeholder for player's name
        this.AvatarName = Avatar; //placeholder for avatar's name
        this.ClassLevel = Level;  //placeholder for player's class level
        this.WorldRanking = Ranking; //placeholder for player's world ranking
        this.Qualified = WorldRanking <= 500; //will analyze player's world ranking and make a decision depending on if level is more or less than 500
    }

    public Info(String name, String avatar, int level, int rank, String qualified) { //Constructor created for the Unit Test
    }

    public String getPlayerName() {
        return PlayerName;
    }
    public String getAvatarName() {
        return AvatarName;
    }
    public int getClassLevel()
    {return ClassLevel;}
    public boolean isQualified() {//This section checks player's World Ranking and determines qualification or not
        if (WorldRanking > 500){
            return Boolean.parseBoolean("No");
        } else {
            return Qualified;
        }
    }
    public String toString() {//Returns user data as a string
        return "Player: " + PlayerName + " - Avatar: " + AvatarName + " - ClassLevel: " + ClassLevel +
                " - WorldRanking: " + WorldRanking + " - Qualified: " + (Qualified ? "Yes" : "No");
    }
    public String toFile() {//Returns user data from a file as a String
        return PlayerName + "-" + AvatarName + "-" + ClassLevel + "-" + WorldRanking + "-" + Qualified;
    }

    public String getAvatar() { //code will get and return a value for the avatar
        return AvatarName;
    }

    public int getLevel() { //code will get and return a value for the level
        return ClassLevel;
    }

    public int getWorldRanking() { //code will get and return a value for the world level
        return WorldRanking;
    }
}


