package finalProject.src;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.Collections;

public class GameInitialization extends JPanel {
    protected GameGUI flip;
    private GameManager game;
    private int rows;
    private int columns;
    private ArrayList<Component> boardArrayList;



    public GameInitialization(String title, GameGUI flip, int rows, int columns, GameManager game) {
        this.flip = flip;
        this.rows = rows;
        this.columns = columns;
        boardArrayList = new ArrayList<>();
        this.game = game;
        flip.setTitle(title);

        initializeBoardPanel();
        initializeBoardWithCards();
    }
    public GameManager getGame(){
        return game;
    }
    public ArrayList<Component> getBoardArrayList(){
        return boardArrayList;
    }

    // this sets up some basic info for the board
    public void initializeBoardPanel() {
        setLayout(new GridLayout(rows, columns));
        setLocation((GameGUI.DEFAULT_WIDTH / 2), (GameGUI.DEFAULT_HEIGHT));
        setPreferredSize(new Dimension((GameGUI.DEFAULT_WIDTH - 50), (GameGUI.DEFAULT_HEIGHT - 50)));
        setVisible(true);
        revalidate();
        repaint();
    }

    // shuffles the cards and adds them to panel
    public void initializeBoardWithCards() {
        createAndAddCardsToArrayList();
        Collections.shuffle(boardArrayList);
        for (Component card : boardArrayList) {
            add(card);
        }
    }

    // creates card objects and adds them to an ArrayList
    public void createAndAddCardsToArrayList() {
        // TODO: this method leads to an error when creating hard or medium game because
        // there are not enough sprite files
        int counter = 0;
        int IconIndex = 0;
        for (int index = 0; index < rows; index++) {
            for (int index2 = 0; index2 < columns; index2++) {
                if (counter % 2 == 0) {
                    // there are two Cards being created here so that there can be two cards with
                    // the same picture
                    //System.out.println(game + "hehe");
                    boardArrayList.add(new Card(ImageCache.getImagePathArrayElement(IconIndex), game));
                    boardArrayList.add(new Card(ImageCache.getImagePathArrayElement(IconIndex), game));
                    IconIndex++;
                }
                counter++;
            }

        }
    }
}