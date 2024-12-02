package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

//This class need implemented so that on main panel you see a button that will lead you to a screen with high scores displayed

public class HighScore extends JPanel implements CenterButtonPanel{
    private GameGUI flip;
    private String name;
    private Data data;
    private JTable highscoreTable;//for showing top 10 high scores
    private JScrollPane highscoreView;
    private JToggleButton toggleButtonFordDB;//for choosing local or remote db
    private JTextField nameField;//for entering the name that the user wants to associate with their highScore
    private Score score;//the score the player achieved when playing the game


    public HighScore(GameGUI flip, Score score){
        this.flip = flip;
        this.score = score;

        if (this.score == null) {
            this.score = new Score();
        }

        setUpAndAddComponents();
    }

    private void setUpAndAddComponents(){
//        initializeGridBagLayout(this);//could not use this and the components will be centered but on the top of the panel instead of the true center
        initComponents();
        initializeCenteredPanel(this, toggleButtonFordDB, highscoreView, nameField);
    }

    public void initComponents(){
        //initialize the JButtons, JTable, and any other components you want here

        // 10 rows, 4 columns (name, score, gameType, timestamp)
        initHighScoreTable();

        // initialize db toggle button
        toggleButtonFordDB = new JToggleButton("Use Remote DB");
        toggleButtonFordDB.addActionListener(e -> {
            if (toggleButtonFordDB.isSelected()) {
                useRemoteDB();
                toggleButtonFordDB.setText("Use Local DB");
            } else {
                useLocalDB();
                toggleButtonFordDB.setText("Use Remote DB");
            }
            // update the high score table
            initHighScoreTable();
        });

        // initialize name field
        nameField = new JTextField();
        nameField.addActionListener(e -> {
            name = nameField.getText();
            setData();
            GameGUI.db.insertDataIntoScores(data);
            // update the high score table so user shows up
            // TODO: fix user double submitting
            initHighScoreTable();
        });

        // this makes namefield the default focus
        nameField.requestFocus();
        // namefield should be approximately 20 characters wide and 1 row tall
        nameField.setColumns(20);
    }

    public void addComponents() {}

    //this method should be called if the user hits the toggle button to change if they want to use the local/remote db
    private void useRemoteDB(){
        if(GameGUI.db.getSoup() == false) return; //checks if the db is already on the remote db and ends the method if it is
        GameGUI.db.closeConnection();
        GameGUI.db.getRemoteDBConnection();
    }

    private void useLocalDB(){
        if(GameGUI.db.getSoup() == true) return;
        GameGUI.db.closeConnection();
        GameGUI.db.getLocalDBConnection();
    }

    //this is a separate method from init Components bc initializing the table will likely take up a good few lines of code and we want to separate logic and make things readable
    private void initHighScoreTable(){
        ArrayList<Data> data = GameGUI.db.selectDataFromScores(10);
        String[] columnNames = {"Name", "Score", "Game Type", "Time"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Populate table model
        for (Data d : data) {
            Object[] row = {d.getName(), d.getScore(), d.getGameType(), d.getTime()};
            model.addRow(row);
        }

        if (highscoreTable != null) {
            highscoreTable.setModel(model);
        } else {
            highscoreTable = new JTable(model);
        }

        if (highscoreView != null) {
            highscoreView.setViewportView(highscoreTable);
        } else {
            highscoreView = new JScrollPane(highscoreTable);
        }

        // make scroll fit height
        highscoreTable.setFillsViewportHeight(true);
        highscoreTable.setPreferredScrollableViewportSize(highscoreTable.getPreferredSize());
    }

    private void setData(){
        data = new Data(score.getScore() , score.getGameType(), name);
    }
}
