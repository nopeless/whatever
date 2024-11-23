package src;

import javax.swing.*;
import java.awt.*;

public interface CenterButtonPanel {

    ImageIcon backgroundIcon = ImageCache.getImageIcon(ImageCache.getImageFile("Other", "trees", "jpg"));
    Image backgroundImage = backgroundIcon.getImage();

    //
    default JPanel initializeButtonPanel(JPanel panel, JButton... buttons) {//JButton... allows for any amount of JButtons to be passed through as args
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(Box.createVerticalGlue());

        for (JButton button : buttons) {
            buttonPanel.add(button);
            buttonPanel.add(Box.createHorizontalStrut(10));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        buttonPanel.add(Box.createVerticalGlue());

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.setBackground(new Color(0, 0, 0, 128));
        buttonPanel.setOpaque(false);

        panel.add(buttonPanel);
        return panel;
    }

    //Im not 100% sure why this was needed to center elements on a panel but it is, and deleting it messes things up
    default void initializeGridBagLayout(JPanel panel){
        panel.setPreferredSize(new Dimension(GameGUI.DEFAULT_WIDTH, GameGUI.DEFAULT_HEIGHT));
        panel.setBounds(0, 0, GameGUI.DEFAULT_WIDTH, GameGUI.DEFAULT_HEIGHT);
        panel.setLayout(new GridBagLayout());
    }

    void addButtons();

    
}