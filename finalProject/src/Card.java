package finalProject.src;

import javax.swing.*;


//note: don't need to implement actionListener bc Java knows to use the ActionListener functional interface because of the context in which the lambda expression is used.
public class Card extends JButton {
    private boolean isFaceUp;
    private ImageIcon icony;//rename later
    private ImageIcon backIcon = null;//rename later
    private GameManager game;

    public Card(String imagePath, GameManager game) {
        this.game = game;
        icony = ImageCache.getImage(imagePath);
        backIcon = ImageCache.getImage(ImageCache.getImagePathArrayElement(8)); 
        setFaceUp(false);
        setIcon(backIcon);
        //setDisabledSelectedIcon(backIcon);
        setDisabledIcon(icony);
        //addActionListener(this);
        addActionListener(e -> game.new CardClickHandler(this));
        }
    //replaced this code with the above lambda expression, lambdas are neat.
    // @Override
    // public void actionPerformed(ActionEvent e) {
    //     //CardClickHandler1 click = new CardClickHandler1(game);
    //     //click.handleCardClick(this);
    //     GameManager.CardClickHandler click = game.new CardClickHandler();
    //     click.handleCardClick(this);
    // }

    public void setFaceUp(boolean isFaceUp) {
        this.isFaceUp = isFaceUp;
        updateIconBasedOnFaceUp();
    }
    public boolean getFaceUp(){
        return isFaceUp;
    }

    public void setCardMatched() {
        setEnabled(false);
        game.setNumOfFaceUpCards((game.getNumOfFaceUpCards() + 1));
    }

    public void updateIconBasedOnFaceUp() {
        if (isFaceUp) {
            setEnabled(false);
        } else {
            setEnabled(true);
        }
    }
    public Icon getIcony(){
        return icony;
    }
    public Icon getBackIcon(){
        return backIcon;
    }

}
