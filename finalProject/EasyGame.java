package finalProject;

public class EasyGame extends Game {
    private static final int TWO_SECOND_DELAY = 2000;

    public EasyGame(GameGUI flip, int rows, int columns) {
        super("Easy Memory Card Game", flip, rows, columns, TWO_SECOND_DELAY);
        initializeBoardPanel();
        initializeBoardWithCards();
        // flip.updateFrame();
    }

    protected void isGameOver() {
        if (Card.numOfFaceUpCards == 16) {
            flip.clearPanel(); 
            flip.toGameMenu(flip); //temp way to just send you back to difficulty selection menu
            // TODO: send to end game sceen(that shows score, asks if you want to upload
            // TODO: score), which will then send you back to menu, after you click 'ok'
        }
    }

}
