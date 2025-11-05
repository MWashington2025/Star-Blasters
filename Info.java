public class Info {//Attributes for player's info
    String PlayerName;
    String AvatarName;
    int ClassLevel;
    int WorldRanking;
    boolean Qualified;

    public Info(String Player, String Avatar, int Level, int Ranking) {//Constructor
        this.PlayerName = Player;
        this.AvatarName = Avatar;
        this.ClassLevel = Level;
        this.WorldRanking = Ranking;
        this.Qualified = WorldRanking <= 500;
    }

    public Info() { //Constructor created for the Unit Test
    }

    public String getPlayerName() {
        return PlayerName;
    }
    public String getAvatarName() {
        return AvatarName;
    }
    public int getClassLevel(){return ClassLevel;}
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

    public String getAvatar() {

        return AvatarName;
    }

    public int getLevel() {
        return ClassLevel;
    }

    public int getWorldRanking() {
        return WorldRanking;
    }
}

