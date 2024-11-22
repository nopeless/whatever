package src;
public class HardGame extends GameBoardInitialization{
    //TODO: add extra stuff here that will make the game more difficult
    private static final int DELAY = 1000;

    public HardGame(GameGUI flip, int rows, int columns) {
        super("Hard Memory Match Card Game", flip, rows, columns, DELAY);
        addCardsToGame();
    }
    
    @Override
    public void addCardsToGame(){
        createAndAddCardsToArrayList(16);
        createAndAddBombCards(4);
        shuffleCards();
    }
   
    //might need to override the isGameOver() funtion to account for the bomb Cards


    
}
