package src;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.Collections;

public class GameBoardInitialization extends JPanel {
    private GameManager game;
    private int rows;
    private int columns;
    private ArrayList<Component> cardArrayList;
    private ArrayList<Component> winConditionCardsList;


    public GameBoardInitialization(String title, GameGUI flip, int rows, int columns, int delay) {
        this.rows = rows;
        this.columns = columns;
        cardArrayList = new ArrayList<>();
        game = new GameManager(flip, delay, this);
        flip.setTitle(title);
        initializeBoardPanel();
        //initializeBoardWithCards();
    }

    public ArrayList<Component> getCardArrayList() {
        return cardArrayList;
    }

    public ArrayList<Component> getWinConditionCardsList() {
        return winConditionCardsList;
    }

    public void setBoardArrayList(ArrayList<Component> listOfButtons) {
        cardArrayList = listOfButtons;
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
        Collections.shuffle(cardArrayList);
        for (Component card : cardArrayList) {
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
        winConditionCardsList = new ArrayList<>(cardArrayList);
    }

    private void createSetOfCards(int index) {
        cardArrayList.add(new Card(ImageCache.getImageFile(index), game));
        cardArrayList.add(new Card(ImageCache.getImageFile(index), game));
    }

    
    private void createSetOfBombCards() {
        cardArrayList.add(new BombCard(game));
        cardArrayList.add(new BombCard(game));
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