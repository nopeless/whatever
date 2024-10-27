package finalProject;

import java.util.Date;

public class Data {
    private int score;
    private Game gameType;
    private String name;
    private Date time;

    public Data(int score, Game gameType, String name) {
        this.score = score;
        this.gameType = gameType;
        this.name = name;
        time = new Date();
        // call method to save data to database(DB is just going to be a txt file, or
        // csv, or json[prob a csv that seems easiest])
    }

}
