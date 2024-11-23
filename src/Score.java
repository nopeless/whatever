package src;

//import java.util.Timer;

public class Score {

    private int score;
    private byte gameDifficultyScoreMultiplier;
    private long startTime;
    private long endTime;
    private static final int BASE_SCORE = 1000;
    private static final int TIME_FACTOR = 10000;


    public Score(GameBoardInitialization init){
        gameDifficultyScoreMultiplier = setGameDifficultyScoreMultiplier(init);
    }

    public void startGame() {
        startTime = System.currentTimeMillis();
    }

    public void endGame() {
        endTime = System.currentTimeMillis();
    }

    public long getElapsedTime() {
        return endTime - startTime;
    }

    public void doMathForScore(){
        long elapsedTime = getElapsedTime();
        double timeFactor = (double) elapsedTime / TIME_FACTOR;
        int calculatedScore = (int) (BASE_SCORE / (1 + timeFactor));
        score = calculatedScore * gameDifficultyScoreMultiplier;
        }

    public byte setGameDifficultyScoreMultiplier(GameBoardInitialization init){
        if (init instanceof EasyGame){
            return 1;
        }else if (init instanceof MediumGame){
            return 2;
        }else{
            return 3;
        }
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }
}
