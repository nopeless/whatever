package src;

import javax.swing.*;
import java.awt.*;

public class EndGame extends JPanel implements CenterButtonsOnPanel {
    private JButton menu;
    private JButton addHighScore;
    private JButton exit;
    private GameGUI flip;

    public EndGame(GameGUI flip) {
        this.flip = flip;
        initializeGridBagLayout(this);
        initButtons();
        addButtons();
    }

    private void addButtons() {
         add(Box.createVerticalGlue());
        initializeButtonPanel(this, menu, addHighScore, exit); // Add buttons
         add(Box.createVerticalGlue());
    }

    private void initButtons() {
        menu = new JButton("To Menu");
        addHighScore = new JButton("Add your score to high score leader board.");
        exit = new JButton("Exit");

        menu.addActionListener(e -> flip.toGameMenu(flip));
        addHighScore.addActionListener(e -> System.out.println("To highscore"));
        exit.addActionListener(e -> System.exit(0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        // super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
