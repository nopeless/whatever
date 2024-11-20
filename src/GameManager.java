package src;

import java.awt.*;
import javax.swing.Icon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.util.Stack;

public abstract class GameManager {// doesnt really need to be abstract as of now because isGameOver can be taken
                                   // care of exclusively in this class
    protected GameGUI flip;
    protected Score score; // TODO: score is unimplemented for now, can maybe make score based on gameType,
                           // like
                           // TODO: you get more points per card match in HardGame than in EasyGame
                           // TODO: and make it so you get more points if you take less time to finish the
                           // TODO: game, and also more points for the less amount of card flips it takes
                           // TODO: you to finish the game.
    private int delay;
    private int numOfFaceUpCards;
    private int actionPerformedCounter;
    private GameBoardInitialization init;
    private Stack<Card> cardStack;

    public GameManager(String title, GameGUI flip, int rows, int columns, int delay) {
        this.flip = flip;
        this.delay = delay;
        cardStack = new Stack<>();
        init = new GameBoardInitialization(title, flip, rows, columns, this);
        startTrackingScore(this);
        // saveData();// TODO: remove later, should go in HighScores class when created
    }

    public GameBoardInitialization getInit() {
        return init;
    }

    public void startTrackingScore(GameManager game) {
        score = new Score(game);
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

    public void isGameOver() {
        if (cardStack.size() == (init.getBoardArrayList().size() / 2)) {
            gameOver();
            System.out.println("Gammeee over");
        }
    }

    // just a different way of checking if game is over, idk if we should use this
    // or the abstract method
    public void gameOver() {
        score.endGame();
        score.doMathForScore();
        //cleanup();
        flip.clearPanel();
        flip.toEndGame(flip);
    }

    // public void cleanup() {
    //     init = null; //for some reason making init null fucks things up
    // }

    // this method will create a new Data Obj, TODO: is more appropiate for
    // TODO: this method to go in HighScores, or another class
    protected void saveData() {
        Data data = new Data(3000, Data.getGameTypeToString(this), "Luke");// testing
        // data database obj
        Database db = new Database();
        db.insertDataIntoUsers(data);
        db.insertDataIntoScores(data);
        // db.printAllData();
        db.selectDataFromScores(10);
    }

    public int getNumOfFaceUpCards() {
        return numOfFaceUpCards;
    }

    public void setNumOfFaceUpCards(int numOfFaceUpCards) {
        this.numOfFaceUpCards = numOfFaceUpCards;
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
        // getClickedCard().setCardMatched();
        cardStack.peek().setCardMatched();
        card.setCardMatched();
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
            System.out.println(getActionPerformedCounter() + "\n");
        }

        private boolean isFirstCardClick() {
            return getActionPerformedCounter() % 2 == 0;
        }

        private void handleFirstCardClick(Card card) {
            // System.out.println("handleFirstCardClick");
            cardStack.push(card);
            // System.out.println("Added Card to stack, size: " + cardStack.size());
            // System.out.println(cardStack.peek().getIcony().toString());

        }

        private void handleSecondCardClick(Card card) {
            // System.out.println("Checking if card is a match");
            if (isMatch(card)) {
                checkCardIsBombCard(card);
                markCardsAsMatched(card);
                isGameOver();
            } else {
                waitIfNoMatch(cardStack.peek(), card);
                cardStack.pop();
                // System.out.println("Stack size decreased, Size: " + cardStack.size());
            }
        }

        private void checkCardIsBombCard(Card currentCard) {
            Card previousCard = cardStack.peek();
            if (previousCard instanceof BombCard && currentCard instanceof BombCard) {
                //System.out.println("Both cards are BombCards. Game Over!");
                gameOver();
            }
        }

        private boolean isMatch(Card card) {
            return cardStack.peek().getIcony().equals(card.getIcony());
        }

    }

}