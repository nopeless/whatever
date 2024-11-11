package finalProject.src;
public class HardGame extends GameManager{
    //TODO: add extra stuff here that will make the game more difficult
    private static final int TWO_SECOND_DELAY = 2000;

    public HardGame(GameGUI flip, int rows, int columns) {
        super("Hard Memory Card Game", flip, rows, columns, TWO_SECOND_DELAY);

    }
    
    protected void isGameOver() {
        if (getNumOfFaceUpCards() == 24) {
            // TODO: send to end game sceen(that shows score, asks if you want to upload
            // TODO: score), which will then send you back to menu, after you click 'ok'
        }
    }


    
}
