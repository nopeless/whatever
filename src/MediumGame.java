package src;

public class MediumGame extends GameManager{
    private static final int DELAY = 1500;

    public MediumGame(GameGUI flip, int rows, int columns) {
        super("Medium", flip, rows, columns, DELAY);
        addCardsToGame();
    }

    @Override
    public void addCardsToGame(){
        getInit().createAndAddCardsToArrayList(16);
        getInit().createAndAddBombCards(4);
        getInit().shuffleCards();
    }


}
