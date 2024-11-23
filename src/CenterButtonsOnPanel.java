package src;

import javax.swing.*;
import java.awt.*;

public interface CenterButtonsOnPanel {

    ImageIcon backgroundIcon = ImageCache.getImageIcon(ImageCache.getImageFile("Other", "trees", "jpg"));
    Image backgroundImage = backgroundIcon.getImage();


    default JPanel initializeButtonPanel(JPanel parentPanel, JButton... buttons) {
        parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));

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
        parentPanel.add(buttonPanel);

        return parentPanel;
    }
    default void initializeGridBagLayout(JPanel parentPanel){
        parentPanel.setPreferredSize(new Dimension(GameGUI.DEFAULT_WIDTH, GameGUI.DEFAULT_HEIGHT));
        parentPanel.setBounds(0, 0, GameGUI.DEFAULT_WIDTH, GameGUI.DEFAULT_HEIGHT);
        parentPanel.setLayout(new GridBagLayout());
    }

    
}