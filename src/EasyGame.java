package src;
public class EasyGame extends GameBoardInitialization{

    private static final int DELAY = 2000;

    public EasyGame(GameGUI flip, int rows, int columns) {
        super("Hard Memory Match Card Game", flip, rows, columns, DELAY);
        addCardsToGame();
    }

    //might need to override the isGameOver() funtion to account for the bomb Cards


    
}
