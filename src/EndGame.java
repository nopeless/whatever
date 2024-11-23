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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Vertical layout
        initButtons();
        addButtons();
    }

    private void addButtons() {
        add(Box.createVerticalGlue()); // Push content to the center vertically
        initializeButtonPanel(this, menu, addHighScore, exit); // Add buttons
        add(Box.createVerticalGlue()); // Push content to the center vertically
    }

    private void initButtons() {
        menu = new JButton("To Menu");
        addHighScore = new JButton("Add your score to high score leader board.");
        exit = new JButton("Exit");

        menu.addActionListener(e -> flip.toGameMenu(flip));
        addHighScore.addActionListener(e -> System.out.println("To highscore"));
        exit.addActionListener(e -> System.exit(0));
    }
}
