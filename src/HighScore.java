package src;

import javax.swing.*;

//This class need implemented so that on main panel you see a button that will lead you to a screen with high scores displayed

public class HighScore extends JPanel implements CenterButtonPanel{
    private GameGUI flip;
    private String name;
    private Data data;
    private JTable highScoretTable;//for showing high scores
    private JToggleButton toggleButtonFordDB;//for choosing local or remote db
    private JTextField nameField;//for entering the name that the user wants to associate with the highScore


    public HighScore(GameGUI flip){
        this.flip = flip;
        setUpAndAddComponents();
    }

    private void setUpAndAddComponents(){
        initializeGridBagLayout(this);//could not use this and the components will be centered but on the top of the panel instead of the true center
        initComponents();
        initializeCenteredPanel(this, toggleButtonFordDB, highScoretTable, nameField);
        addComponents();
    }

    public void initComponents(){
    //initialize the JButtons, JTable, and any other components you want here
    initHighScoreTable();
    }

    public void addComponents(){
        //add all components to the screen
    }

    //this method should be called if the user hits the toggle button to change if they want to use the local/remote db
    private void useRemoteDB(){
        if(GameGUI.db.getSoup() == false) return; //checks if the db is already on the remote db and returns if it is
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
        //init the JTable with the highScore data using the 'GameGUI.db.selectDataFromScores(10);' method

    }
    

}
