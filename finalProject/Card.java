package finalProject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Card extends JButton implements ActionListener {
    protected boolean isFaceUp;
    protected ImageIcon icon;
    protected static int actionPerformedCounter = 0;//keeps track of how many cards have been flipped
    protected static Icon clickedIcon;
    protected static Card clickedCard;
    protected Game game;
    protected static int numOfFaceUpCards;

    public Card(String imagePath, Game game) {
        icon = ImageCache.getImage(imagePath);
        setIcon(null);//this sets the face down icon
        isFaceUp = false;
        this.game = game;
        setDisabledIcon(icon);//this sets the face up icon
        addActionListener(this);
    }

    // click one card
    // click another card
    // check if they match
    // if they do match leave them face up and disable their buttons
    // if they do not match have them flip back down after a few seconds
    @Override
    public void actionPerformed(ActionEvent e) {
            setIcon(icon);
            isFaceUp = true;
        if (actionPerformedCounter % 2 == 0) {// if first card is selected
            clickedCard = this;
            clickedIcon = this.icon;
            setEnabled(false);
            
        }else {// if second card is selected
            if (clickedIcon.equals(icon)) {// if the clicked card icon in previous turn equals the card clicked icon this
                                                // turn
                Game.setCardDisabled(clickedCard);
                Game.setCardDisabled(this);
                numOfFaceUpCards += 2;
                game.isGameOver(game);//TODO: doesnt really make sense to be checking what type game is every time there is a match
            } else {//else if the 2 clicked cards don't match

                game.waitIfNoMatch(clickedCard, this);//
            }
        }
        actionPerformedCounter++;
    }  

    public void setFaceUp(boolean isFaceUp) {
        this.isFaceUp = isFaceUp;
    }

}
