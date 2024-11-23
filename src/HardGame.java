package src;
public class HardGame extends GameBoardInitialization{
    //TODO: add extra stuff here that will make the game more difficult
    private static final int DELAY = 1000;

    public HardGame(GameGUI flip, int rows, int columns) {
        super("Hard Memory Match Card Game", flip, rows, columns, DELAY);
        addCardsToGame();
    }
    
    @Override //would be best to throw an exception if the right amount of cards arnt creates, but im not going to create my own exception right now
    public void addCardsToGame(){
        createAndAddCardsToArrayList(22);
        createAndAddBombCards(8);
        shuffleCards();
    }
   
    //might need to override the isGameOver() funtion to account for the bomb Cards


    
}
