package finalProject.src;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JPanel;
import java.util.Collections;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
//TODO: cards can be clicked again after being matched, should make sure that buttons are getting disabled correctly

public abstract class Game extends JPanel {
    protected GameGUI flip;
    protected GridLayout gridLayout;
    protected int rows;
    protected int columns;
    protected ArrayList<Component> boardArrayList;
    protected int score; // TODO: score is unimplemented for now, can maybe make score based on gameType,
                         // like
                         // TODO: you get more points per card match in HardGame than in EasyGame
                         // TODO: and make it so you get more points if you take less time to finish the
                         // TODO: game, and also more points for the less amount of card flips it takes
                         // you to
                         // TODO: finish the game.
    private String gameType;
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
        gameType = Data.getGameTypeToString(this);
        //saveData();// TODO: remove later, should go in HighScores class when created
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // all methods before this manage the initialization, methods after this manage
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////// the
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////// game
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////// once
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////// it
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////// has
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////// been
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////// started,
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////// *these
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////// could
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////// be
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////// 2
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////// separate
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////// classes*
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // this method turns 2 selected cards face down after a set time 'delay'
    // this is so that when 2 non matching cards are selected there is a short delay
    // where you can see the cards for some time before they are they flipped back
    // face down
    protected void waitIfNoMatch(Card card1, Card card2) {
        disableAllCards(card1, card2);// doessnt work
        // need to .setEnabled(false), but then that shows the disabled/ face up pic
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

    // disables all cards from being pressed
    // this is done so that the person must wait until the 2 incorrectly selected
    // cards turn back face down before any other cards can be pressed
    protected void disableAllCards(Card card1, Card card2) {
        for (Component card : boardArrayList) {
            Card cardy = (Card) card;
            if (!cardy.isFaceUp) {
                if (cardy != card1 && cardy != card2) {
                    cardy.setDisabledIcon(cardy.getBackIcon());
                }
                cardy.setEnabled(false);
            }
        }
    }

    // Only enables cards that are face down because if they are face up it means
    // they have been matched so we want them to stay disabled//have to downcast
    // to get access to
    // isFaceUp field, should be safe because only Card types are being stored
    // boardArrayList
    protected void enableAllCardsNotMatched() {
        for (Component card : boardArrayList) {
            Card cardy = (Card) card;
            if (!cardy.isFaceUp) {
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

    // // this method will create a new Data Obj, TODO: is more appropiate for
    // // TODO: this method to go in HighScores, or another class
    // protected void saveData() {
    //     Data data = new Data(3000, Data.getGameTypeToString(this), "Luke");// testing data/database obj
    //     Database db = new Database();
    //     db.insertDataIntoUsers(data);
    //     db.insertDataIntoScores(data);
    //     // db.printAllData();
    //     db.selectAllData(10);
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

    public void handleCardClick(Card card) {
        card.setFaceUp(true);
        if (getActionPerformedCounter() % 2 == 0) {// if first card is selected
            setClickedCardAndIcon(card);
        } else {// if second card is selected
            if (getClickedIcon().equals(card.getIcony())) {// if the clicked card icon in previous turn equals the card
                                                           // clicked icon this turn
                getClickedCard().setCardMatched();
                card.setCardMatched();
                isGameOver();
            } else {// else if the 2 clicked cards don't match
                waitIfNoMatch(getClickedCard(), card);//
            }
        }
        incrementActionPerformedCounter();
    }

    public void setClickedCardAndIcon(Card card) {
        clickedCard = card;
        clickedIcon = card.getIcony();
    }

}