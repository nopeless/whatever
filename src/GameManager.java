package src;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.util.Stack;

public class GameManager {
    protected GameGUI flip;
    protected Score score; 
    private int delay;
    private int actionPerformedCounter;
    private GameBoardInitialization init;
    private Stack<Card> cardStack;

    public GameManager(GameGUI flip, int delay, GameBoardInitialization init) {
        this.flip = flip;
        this.delay = delay;
        this.init = init;
        cardStack = new Stack<>();
        startTrackingScore();
        //db = new Database();//TODO: move this to HighScore Screen later
        // saveData();//
    }

    public void startTrackingScore() {
        score = new Score(init);
        score.startGame();
    }

    // this method turns 2 selected cards face down after a set time 'delay'
    // this is so that when 2 non matching cards are selected there is a short delay
    // where you can see the cards for some time before they are they flipped back
    // face down
    protected void waitIfNoMatch(Card card1, Card card2) {
        disableAllCards(card1, card2);
        Timer timer = new Timer(delay, new ActionListener() {
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

    // Has to be a better way to do this
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

    // Has to be a better way to do this
    protected void enableAllCardsNotMatched() {
        for (Component card : init.getBoardArrayList()) {
            Card cardy = (Card) card;
            if (!cardy.getFaceUp()) {
                cardy.setEnabled(true);
                cardy.setDisabledIcon(cardy.getIcony());
            }
        }
    }

    public void isGameOver(Boolean didWin) {
        if (cardStack.size() == (init.getBoardArrayList().size() / 2)) {//TODO: game over doesnt work if bombCards are in deck
            gameOver(didWin);
        }
    }

    public void gameOver(Boolean didWin) {
        score.endGame();
        if (didWin) score.doMathForScore();
        //if (!didWin) score.setScore(0);
        System.out.println("score: " + score.getScore());
        flip.clearPanel();
        flip.toEndGame(flip);
        }

    // this method will create a new Data Obj, TODO: is more appropiate for
    // TODO: this method to go in HighScores, or another class
    protected void saveData() {
        //System.out.println("real score: " + score.getScore());
        Data data = new Data(score.getScore(), Data.getGameTypeToString(init), "Luke");// testing: TODO: when the method is moved to highScore add var for name
        GameGUI.db.insertDataIntoUsers(data);
        GameGUI.db.insertDataIntoScores(data);
        // db.printAllData();
        GameGUI.db.selectDataFromScores(10);
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

    class CardClickHandler {
        public CardClickHandler(Card card) {
            handleCardClick(card);
        }

        public void handleCardClick(Card card) {
            card.setFaceUp(true);
            if (isFirstCardClick()) {
                handleFirstCardClick(card);
            } else {
                handleSecondCardClick(card);
            }
            incrementActionPerformedCounter();
            //System.out.println(getActionPerformedCounter() + "\n");
        }

        private boolean isFirstCardClick() {
            return getActionPerformedCounter() % 2 == 0;
        }

        private void handleFirstCardClick(Card card) {
            cardStack.push(card);
        }

        private void handleSecondCardClick(Card card) {
            if (isMatch(card)) {
                checkCardIsBombCard(card);
                //markCardsAsMatched(card);
                isGameOver(true);
            } else {
                waitIfNoMatch(cardStack.peek(), card);
                cardStack.pop();
            }
        }

        private void checkCardIsBombCard(Card currentCard) {
            Card previousCard = cardStack.peek();
            if (previousCard instanceof BombCard && currentCard instanceof BombCard) {
                gameOver(false);
            }
        }

        private boolean isMatch(Card currentCard) {
            Card previousCard = cardStack.peek();
            return previousCard.getIcony().equals(currentCard.getIcony());
        }

    }

}