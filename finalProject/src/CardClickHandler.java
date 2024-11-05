package finalProject.src;

public class CardClickHandler {
    private GameManager game;

    public CardClickHandler(GameManager game) {
        this.game = game;
    }

    public void handleCardClick(Card card) {
        card.setFaceUp(true);
        if (isFirstCardClick()) {
            handleFirstCardClick(card);
        } else {
            handleSecondCardClick(card);
        }
        game.incrementActionPerformedCounter();
    }

    private boolean isFirstCardClick() {
        return game.getActionPerformedCounter() % 2 == 0;
    }

    private void handleFirstCardClick(Card card) {
        game.setClickedCard(card);
        game.setClickedIcon(card.getIcony());
    }

    private void handleSecondCardClick(Card card) {
        if (isMatch(card)) {
            game.markCardsAsMatched(card);
            game.isGameOver();
        } else {
            game.waitIfNoMatch(game.getClickedCard(), card);
        }
    }

    private boolean isMatch(Card card) {
        return game.getClickedIcon().equals(card.getIcony());
    }
}