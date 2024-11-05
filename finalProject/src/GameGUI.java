package finalProject.src;

import java.awt.*;
import javax.swing.*;
//to run this code you need the sql driver, either reference it in your ide or run the code via the terminal with the following lines while in the parent dir of finalProject (i.e you should be in dir 'C:Users/USERNAME/project' and the 'project' dir should contain 'finalProject'):
//will need to update when more .java files are added to the project
//javac -cp "finalProject/lib/sqlite-jdbc-3.47.0.0.jar;." finalProject/src/GameGUI.java finalProject/src/GameMenu.java finalProject/src/ImageCache.java finalProject/src/EasyGame.java finalProject/src/MediumGame.java finalProject/src/HardGame.java finalProject/src/Game.java finalProject/src/Card.java finalProject/src/Data.java finalProject/src/Database.java //compiles code
//java -cp ".;finalProject/lib/sqlite-jdbc-3.47.0.0.jar" finalProject.src.GameGUI     //runs code

//TODO: right now in many of the classes I have just set the fields and methods to protected,
//TODO: but many of these should be changed to private or public as needed. 

//TODO: I also like the idea of making a highscore board, can maybe get practice implimenting an API that keeps track
//TODO: of all the scores gotten by anyone who has played the game and maybe display like the top 10 somewhere in the game : edit this has mostly been done at least locally

//TODO: Add some sounds, when you flip over matching pair, non matching pair, you finish the game, etc

public class GameGUI extends JFrame {// could make it not extend JFrame by declaring and initializing a JFrame in the
                                     // initializeFrame method
    protected static final int DEFAULT_WIDTH = 800;
    protected static final int DEFAULT_HEIGHT = 800;
    protected GameMenu gameMenu;
    protected JPanel mainPanel;

    public GameGUI() {
        ImageCache.preloadImages();
        initializeFrame();
    }

    public static void main(String[] args) {
        // this way of starting the game is somewhat unnecessary as it is more for
        // helping create multiple GameGUI's if you want to multithread this
        // application, however rn it is basically just creates a new GameGUI object
        // which kickstarts the rest of the code
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameGUI();
            }
        });
    }

    // initializes the frame and adds a blank panel
    public void initializeFrame() {
        // create panel
        mainPanel = new JPanel();
        add(mainPanel);

        // set frame properties
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        toGameMenu(this);
    }


    //Different classes will call these methods to change what is shown on the screen

    // TODO: add class and method for highscore screen

    // TODO: add class and method for end game screen

    public void toGameMenu(GameGUI flip) {
        mainPanel.add(new GameMenu(flip));
        updatePanel(mainPanel);
    }

    public void toEasyGame(GameGUI flip) {
        EasyGame eg = new EasyGame(flip, 4, 4);
        mainPanel.add(eg.getInit());
        updatePanel(mainPanel);
    }

    public void toMediumGame(GameGUI flip) {
        MediumGame mg = new MediumGame(flip, 4, 4);

        mainPanel.add(mg.getInit());
        updatePanel(mainPanel);
    }

    public void toHardGame(GameGUI flip) {
        HardGame hg = new HardGame(flip, 4, 4);
        mainPanel.add(hg.getInit());
        updatePanel(mainPanel);
    }

    public void clearPanel() {
        mainPanel.removeAll();
    }

    public void updatePanel(JPanel panel) {
        panel.revalidate(); // Refresh the main panel
        panel.repaint();
    }

}