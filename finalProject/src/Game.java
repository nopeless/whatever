package finalProject.src;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JPanel;
import java.util.Collections;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public abstract class Game extends JPanel {
    protected GameGUI flip;
    private int rows;
    private int columns;
    private ArrayList<Component> boardArrayList;


    protected int score; // TODO: score is unimplemented for now, can maybe make score based on gameType,
                         // like
                         // TODO: you get more points per card match in HardGame than in EasyGame
                         // TODO: and make it so you get more points if you take less time to finish the
                         // TODO: game, and also more points for the less amount of card flips it takes
                         // TODO: you to finish the game.
    protected int delay;
    private int numOfFaceUpCards;
    private Card clickedCard;
    private Icon clickedIcon;
    private int actionPerformedCounter;

    public Game(String title, GameGUI flip, int rows, int columns, int delay) {
        this.flip = flip;
        this.rows = rows;
        this.columns = columns;
        this.delay = delay;
        flip.setTitle(title);
        boardArrayList = new ArrayList<>();
        // gameType = Data.getGameTypeToString(this);
        // saveData();// TODO: remove later, should go in HighScores class when created
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
    public Component initializeBoardWithCards() {
        createAndAddCardsToArrayList();
        Collections.shuffle(boardArrayList);
        for (Component card : boardArrayList) {
            add(card);
        }
        return this;
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
                    boardArrayList.add(new Card(ImageCache.getImagePathArrayElement(IconIndex), this));
                    boardArrayList.add(new Card(ImageCache.getImagePathArrayElement(IconIndex), this));
                    IconIndex++;
                }
                counter++;
            }

        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    // all methods before this manage the initialization, methods after this manage
    // the game state, once the game is in progress | these should be 2 classes not 1
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    
    // this method turns 2 selected cards face down after a set time 'delay'
    // this is so that when 2 non matching cards are selected there is a short delay
    // where you can see the cards for some time before they are they flipped back
    // face down
    protected void waitIfNoMatch(Card card1, Card card2) {
        disableAllCards(card1, card2);
        Timer timer = new Timer(delay, new ActionListener() {// had to look up some information about Timers and how to
                                                             // add actionListener to them
            @Override
            public void actionPerformed(ActionEvent event) {
                card1.setFaceUp(false);
                card2.setFaceUp(false);
                enableAllCardsNotMatched();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    //Has to be a better way to do this
    protected void disableAllCards(Card card1, Card card2) {
        for (Component card : boardArrayList) {
            Card cardy = (Card) card;
            if (!cardy.getFaceUp()) {
                if (cardy != card1 && cardy != card2) {
                    cardy.setDisabledIcon(cardy.getBackIcon());
                }
                cardy.setEnabled(false);
            }
        }
    }

        //Has to be a better way to do this
    protected void enableAllCardsNotMatched() {
        for (Component card : boardArrayList) {
            Card cardy = (Card) card;
            if (!cardy.getFaceUp()) {
                cardy.setEnabled(true);
                cardy.setDisabledIcon(cardy.getIcony());
            }
        }
    }

    abstract void isGameOver();

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // this method will create a new Data Obj, TODO: is more appropiate for
    // TODO: this method to go in HighScores, or another class
    // protected void saveData() {
    // Data data = new Data(3000, Data.getGameTypeToString(this), "Luke");// testing
    // data/database obj
    // Database db = new Database();
    // db.insertDataIntoUsers(data);
    // db.insertDataIntoScores(data);
    // // db.printAllData();
    // db.selectAllData(10);
    // }

    public int getNumOfFaceUpCards() {
        return numOfFaceUpCards;
    }

    public void setNumOfFaceUpCards(int numOfFaceUpCards) {
        this.numOfFaceUpCards = numOfFaceUpCards;
    }

    public Card getClickedCard() {
        return clickedCard;
    }

    public void setClickedCard(Card card) {
        clickedCard = card;
    }

    public Icon getClickedIcon() {
        return clickedIcon;
    }

    public void setClickedIcon(Icon icon) {
        clickedIcon = icon;
    }

    public int getActionPerformedCounter() {
        return actionPerformedCounter;
    }

    public void setActionPerformedCounter(int actionPerformedCounter) {
        this.actionPerformedCounter = actionPerformedCounter;
    }

    public void incrementActionPerformedCounter() {
        this.actionPerformedCounter++;
    }

    protected void markCardsAsMatched(Card card) {
        getClickedCard().setCardMatched();
        card.setCardMatched();
    }
    
}