package finalProject.src;
//easyGame extends game
//game calls GameInit
public class EasyGame extends GameManager {
    private static final int TWO_SECOND_DELAY = 2000;

    public EasyGame(GameGUI flip, int rows, int columns) {
        super("Easy", flip, rows, columns, TWO_SECOND_DELAY);
    }

    @Override
    public void isGameOver() {
        if (getNumOfFaceUpCards() == 16) {
            flip.clearPanel(); 
            flip.toGameMenu(flip); //temp way to just send you back to difficulty selection menu
            // TODO: send to end game sceen(that shows score, asks if you want to upload
            // TODO: score), which will then send you back to menu, after you click 'ok'
            //sendToEndGameScreen();
        }
    }

    //TODO: method to update score

}
