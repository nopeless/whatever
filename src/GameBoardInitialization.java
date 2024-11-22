package src;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.Collections;

public class GameBoardInitialization extends JPanel {
    private GameManager game;
    private int rows;
    private int columns;
    private ArrayList<Component> boardArrayList;

    public GameBoardInitialization(String title, GameGUI flip, int rows, int columns, int delay) {
        this.rows = rows;
        this.columns = columns;
        boardArrayList = new ArrayList<>();
        game = new GameManager(flip, delay, this);
        flip.setTitle(title);
        initializeBoardPanel();
        //initializeBoardWithCards();
    }

    public ArrayList<Component> getBoardArrayList() {
        return boardArrayList;
    }

    public void setBoardArrayList(ArrayList<Component> listOfButtons) {
        boardArrayList = listOfButtons;
    }

    // sets up some basic info for the board
    public void initializeBoardPanel() {
        setLayout(new GridLayout(rows, columns));
        setLocation((GameGUI.DEFAULT_WIDTH / 2), (GameGUI.DEFAULT_HEIGHT));
        setPreferredSize(new Dimension((GameGUI.DEFAULT_WIDTH - 50), (GameGUI.DEFAULT_HEIGHT - 50)));
        setVisible(true);
        revalidate();
        repaint();
    }

    // shuffles AND adds cards to Panel
    public void shuffleCards() {
        Collections.shuffle(boardArrayList);
        for (Component card : boardArrayList) {
            add(card);
        }
    }

    // creates card objects and adds them to an ArrayList
    public void createAndAddCardsToArrayList(int amount) {
        for (int i = 0; i < amount; i++) {
            if (i % 2 == 0) {// might break if rows or columns is odd instead of even. especially columns
                createSetOfCards(i / 2);
            }
        }
    }

    private void createSetOfCards(int index) {
        boardArrayList.add(new Card(ImageCache.getImageFile(index), game));
        boardArrayList.add(new Card(ImageCache.getImageFile(index), game));
    }

    
    private void createSetOfBombCards() {
        boardArrayList.add(new BombCard(game));
        boardArrayList.add(new BombCard(game));
    }

    public void addCardsToGame(){
        createAndAddCardsToArrayList(rows * columns);
        shuffleCards();
    }

    public void createAndAddBombCards(int amount) {
        for (int i = 0; i < amount; i++) {
            if (i % 2 == 0) {
                createSetOfBombCards();
            }
        }
    }
}