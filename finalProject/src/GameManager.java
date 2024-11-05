package finalProject.src;

import java.awt.*;
import javax.swing.Icon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public abstract class GameManager{
    protected GameGUI flip;
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
    private GameInitialization init;

    public GameManager(String title, GameGUI flip, int rows, int columns, int delay) {
        this.flip = flip;
        this.delay = delay;
        init = new GameInitialization(title, flip, rows, columns, this);
        // saveData();// TODO: remove later, should go in HighScores class when created
    }

    public GameInitialization getInit(){
        return init;
    }

    

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
        for (Component card : init.getBoardArrayList()) {
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
        for (Component card : init.getBoardArrayList()) {
            Card cardy = (Card) card;
            if (!cardy.getFaceUp()) {
                cardy.setEnabled(true);
                cardy.setDisabledIcon(cardy.getIcony());
            }
        }
    }

    protected void isGameOver() {
        if (getNumOfFaceUpCards() == 16) {
            flip.clearPanel(); 
            flip.toGameMenu(flip); //temp way to just send you back to difficulty selection menu
            // TODO: send to end game sceen(that shows score, asks if you want to upload
            // TODO: score), which will then send you back to menu, after you click 'ok'
        }
    }

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