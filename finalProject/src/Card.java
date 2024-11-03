package finalProject.src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Card extends JButton implements ActionListener {
    protected boolean isFaceUp;
    protected ImageIcon icony;
    private ImageIcon backIcon = null;
    protected Game game;

    public Card(String imagePath, Game game) {
        icony = ImageCache.getImage(imagePath);
        System.out.println(icony);
        backIcon = ImageCache.getImage(ImageCache.getImagePathArrayElement(8)); 
       // setIcon(null); // Set the face-down icon (null) (button should be enabled)
        setFaceUp(false);
        this.game = game;
        setIcon(backIcon);
        setDisabledIcon(icony);
        //setDisabledIcon(backIcon); // Set the face-up icon (loaded image)
        //setIcon(null);
        addActionListener(this);
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.handleCardClick(this);
    }

    public void setFaceUp(boolean isFaceUp) {
        this.isFaceUp = isFaceUp;
        updateIconBasedOnFaceUp();
    }

    public void setCardMatched() {
        setEnabled(false);
        //setFaceUp(true);//this is extra bc card is already face up bc it was clicked on
        game.setNumOfFaceUpCards((game.getNumOfFaceUpCards() + 1));
    }

    public void updateIconBasedOnFaceUp() {
        if (isFaceUp) {
            //setDisabledIcon(icony);
            setEnabled(false);
        } else {
            //setIcon(backIcon);
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
