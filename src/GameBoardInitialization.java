package finalProject.src;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.Collections;

public class GameBoardInitialization extends JPanel {
    private GameGUI flip;//don't really need this as of now
    private GameManager game;
    private int rows;
    private int columns;
    private ArrayList<Component> boardArrayList;



    public GameBoardInitialization(String title, GameGUI flip, int rows, int columns, GameManager game) {
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
    
    public void setGame(GameManager game){
        this.game = game;
    }
    
    public ArrayList<Component> getBoardArrayList(){
        return boardArrayList;
    }

    public void setBoardArrayList(ArrayList<Component> listOfButtons){
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

    // shuffles the ArrayList of cards and adds them to panel
    public void initializeBoardWithCards() {
        createAndAddCardsToArrayList();
        shuffleCards();
    }

    private void shuffleCards(){
        Collections.shuffle(boardArrayList);
        for (Component card : boardArrayList) {
            add(card);
        }
    }

    // creates card objects and adds them to an ArrayList
    public void createAndAddCardsToArrayList() {
        // TODO: this method leads to an error when creating hard or medium game because
        // there are not enough sprite files
        //int counter = 0;
        int iconIndex = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (j % 2 == 0) {//might break if rows or columns is odd instead of even. especially columns
                    createSetOfCards(iconIndex);
                    iconIndex++;
                }
                //counter++;
            }

        }
    }

    public void createSetOfCards(int index){
        // there are two Cards being created here so that there can be two cards with
        // the same picture
        boardArrayList.add(new Card(ImageCache.getImageFile(index), game));
        boardArrayList.add(new Card(ImageCache.getImageFile(index), game));
        System.out.println(ImageCache.getImageFile(index));
    }
}