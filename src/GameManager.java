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
    private int actionPerformedCounter;
    private GameBoardInitialization init;
    private Stack<Card> cardStack;

    public GameManager(String title, GameGUI flip, int rows, int columns, int delay) {
        this.flip = flip;
        this.delay = delay;
        cardStack = new Stack<>();
        init = new GameBoardInitialization(title, flip, rows, columns, this);
        startTrackingScore(this);
        //db = new Database();//TODO: move this to HighScore Screen later
        // saveData();//
    }

    public GameBoardInitialization getInit() {
        return init;
    }

    public void addCardsToGame(){
        init.createAndAddCardsToArrayList(16);
        init.shuffleCards();
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
            //System.out.println("Gammeee over");
        }
    }

    public void gameOver() {
        endGameTasks();
        score.doMathForScore();
        saveData(); //move save data to HighScore screen later
    }

    public void gameOverBomb() {
        score.setScore(0);
        endGameTasks();
        saveData();
    }

    private void endGameTasks() {
        score.endGame();
        flip.clearPanel();
        flip.toEndGame(flip);
    }

    // this method will create a new Data Obj, TODO: is more appropiate for
    // TODO: this method to go in HighScores, or another class
    protected void saveData() {
        System.out.println("real score: " + score.getScore());
        Data data = new Data(score.getScore(), Data.getGameTypeToString(this), "Luke");// testing
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

    // protected void markCardsAsMatched(Card card) {
    //     cardStack.peek().setFaceUp();
    //     card.setFaceUp();
    // }

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
                isGameOver();
            } else {
                waitIfNoMatch(cardStack.peek(), card);
                cardStack.pop();
            }
        }

        private void checkCardIsBombCard(Card currentCard) {
            Card previousCard = cardStack.peek();
            if (previousCard instanceof BombCard && currentCard instanceof BombCard) {
                gameOverBomb();
            }
        }

        private boolean isMatch(Card card) {
            return cardStack.peek().getIcony().equals(card.getIcony());
        }

    }

}