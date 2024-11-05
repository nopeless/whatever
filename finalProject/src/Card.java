package finalProject.src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Card extends JButton implements ActionListener {
    private boolean isFaceUp;
    private ImageIcon icony;
    private ImageIcon backIcon = null;
    private GameManager game;

    public Card(String imagePath, GameManager game) {
        this.game = game;
        icony = ImageCache.getImage(imagePath);
        backIcon = ImageCache.getImage(ImageCache.getImagePathArrayElement(8)); 
        setFaceUp(false);
        setIcon(backIcon);
        //setDisabledSelectedIcon(backIcon);
        setDisabledIcon(icony);
        addActionListener(this);
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardClickHandler click = new CardClickHandler(game);
        click.handleCardClick(this);
    }

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
