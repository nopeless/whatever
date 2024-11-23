package src;

import java.io.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameMenu extends JPanel implements CenterButtonsOnPanel {
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private GameGUI flip;
    private IntroScreen intro;
    private ButtonBox buttonBoxPanel;
    private JButton infoButton;

    public GameMenu(GameGUI flip) {
        this.flip = flip;
        initializeGridBagLayout(this);
        initButtons();
        buttonBoxPanel = new ButtonBox();
        intro = new IntroScreen();
        // buttonBox.initializeButtonBox();
        if (RunChecker.createCheckFile()) {
            toInfoPanel();
        } else {
            toButtonBox();
        }
    }

    public GameMenu getGameMenu() {
        return this;
    }

    private void toInfoPanel() {
        removeAll();
        add(intro);
        revalidate();
        repaint();
    }

    private void toButtonBox() {
        removeAll();
        add(buttonBoxPanel);
        buttonBoxPanel.setVisible(true);
        revalidate();
        repaint();
    }


    private void initButtons() {
        easyButton = new JButton("Easy");
        mediumButton = new JButton("Medium");
        hardButton = new JButton("Hard");
        infoButton = new JButton("See Game Info");

        easyButton.addActionListener(e -> flip.toEasyGame(flip));
        mediumButton.addActionListener(e -> flip.toMediumGame(flip));
        hardButton.addActionListener(e -> flip.toHardGame(flip));
        infoButton.addActionListener(e -> toInfoPanel());

        easyButton.setBackground(Color.GREEN);
        mediumButton.setBackground(Color.YELLOW);
        hardButton.setBackground(Color.RED);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    class ButtonBox extends JPanel {
        public ButtonBox() {
            initializeButtonPanel(this, easyButton, mediumButton, hardButton);
    
            add(Box.createVerticalGlue());
            add(Box.createVerticalStrut(20));
            add(infoButton);
            infoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            setBackground(new Color(0, 0, 0, 128));
            setOpaque(false);
        }

    }

    class IntroScreen extends JPanel {
        private JButton okButton;
        private JLabel info;

        public IntroScreen() {
            initializeIntroScreen();
        }

        public void initializeIntroScreen() {
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
            setPreferredSize(new Dimension(400, 200));
            setLayout(new BorderLayout());

            okButton = new JButton("OK");
            okButton.addActionListener(e -> toButtonBox());
            info = new JLabel("Intro Screen: Give some info about the game here");
            add(info);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setBackground(Color.LIGHT_GRAY);
            buttonPanel.add(okButton);

            add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    static class RunChecker {
        // check if theres a file
        // if there is set/return true
        // else create the file
        private static File checkFile = new File("./private/check.txt");

        // private static boolean fileExists;
        public static boolean createCheckFile() {
            try {
                return checkFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
