package src;

import javax.swing.*;

public class GameGUI extends JFrame {// could make it not extend JFrame by declaring and initializing a JFrame in the
                                     // initializeFrame method
    protected static final int DEFAULT_WIDTH = 800;
    protected static final int DEFAULT_HEIGHT = 800;
    private JPanel mainPanel;
    static Database db; // is default so that only package classes have access

    public GameGUI() {
        ImageCache.preloadImages();
        setupDB(true);// TODO: Move the call to the method to HighScore Screen Later
        initializeFrame();
    }

    // TODO: This method should likely be called in highScore screen class instead of here
    // we start a new thread for this so that we dont need to wait for the db to
    // connect before we allow the user to click the buttons on screen
    //we also make the method package-private, so there is no access modifier
    void setupDB(boolean isUsingLocalDB) {
        new Thread(() -> db = new Database(isUsingLocalDB)).start();
    }

    void setupImages() {
        new Thread(() -> ImageCache.preloadImages()).start();
    }

    public static void main(String[] args) {
        // SwingUtilities.invokeLater(new Runnable() {
        // @Override
        // public void run() {
        // new GameGUI();
        // }
        // });

        // starts code by creating new GameGUI obj
        // this code is exactly the same as the above commented out code
        // this line just uses a lambda in place of a anonymous class
        SwingUtilities.invokeLater(() -> new GameGUI());

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

    // TODO: add class and method for highscore screen

    public void toGameMenu(GameGUI flip) {
        clearPanel();
        GameMenu gameMenu = new GameMenu(flip);
        mainPanel.add(gameMenu);
        updatePanel(mainPanel);
    }

    public void toEasyGame(GameGUI flip) {
        clearPanel();
        EasyGame eg = new EasyGame(flip, 4, 4);// to make this better there should be an exception thrown if eg does not
                                               // make 16 cards
        mainPanel.add(eg);
        updatePanel(mainPanel);
    }

    public void toMediumGame(GameGUI flip) {
        clearPanel();
        MediumGame mg = new MediumGame(flip, 5, 4);
        mainPanel.add(mg);
        updatePanel(mainPanel);
    }

    public void toHardGame(GameGUI flip) {
        clearPanel();
        HardGame hg = new HardGame(flip, 4, 6);
        mainPanel.add(hg);
        updatePanel(mainPanel);
    }

    public void toEndGame(GameGUI flip) {
        clearPanel();
        EndGame endGame = new EndGame(flip);
        mainPanel.add(endGame);
        updatePanel(mainPanel);
    }

    public void clearPanel() {
        mainPanel.removeAll();
    }

    public void updatePanel(JPanel panel) {
        panel.revalidate();
        panel.repaint();
    }

}