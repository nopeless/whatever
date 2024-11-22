package src;
public class HardGame extends GameManager{
    //TODO: add extra stuff here that will make the game more difficult
    private static final int ONE_SECOND_DELAY = 1000;

    public HardGame(GameGUI flip, int rows, int columns) {
        super("Hard Memory Match Card Game", flip, rows, columns, ONE_SECOND_DELAY);
        addCardsToGame();
    }
    
    @Override
    public void addCardsToGame(){
        getInit().createAndAddCardsToArrayList(16);
        getInit().createAndAddBombCards(4);
        //getInit().createAndAddDecoyCards(4);
        getInit().shuffleCards();
    }
   
    //might need to override the isGameOver() funtion to account for the bomb Cards


    
}
