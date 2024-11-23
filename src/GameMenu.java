package src;

import java.io.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
//import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
//import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameMenu extends JPanel implements CenterButtonsOnPanel {
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private GameGUI flip;
    private Image backgroundImage;
    private ImageIcon backgroundIcon;
    private IntroScreen intro;
    private ButtonBox buttonBoxPanel;
    private JButton infoButton;

    public GameMenu(GameGUI flip) {
        this.flip = flip;

        backgroundIcon = ImageCache.getImageIcon(ImageCache.getImageFile("Other", "trees", "jpg"));
        backgroundImage = backgroundIcon.getImage();

        initializeGameMenuPanel();
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

    // add buttons to panel
    private void initializeGameMenuPanel() {
        setPreferredSize(new Dimension(GameGUI.DEFAULT_WIDTH, GameGUI.DEFAULT_HEIGHT));
        setBounds(0, 0, GameGUI.DEFAULT_WIDTH, GameGUI.DEFAULT_HEIGHT);
        setLayout(new GridBagLayout());

        // setGridBagLayoutConstraintsToCenter();
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
            //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            initializeButtonPanel(this, easyButton, mediumButton, hardButton);
    
            add(Box.createVerticalGlue());
            add(Box.createVerticalStrut(20));
            add(infoButton);
            infoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            setBackground(new Color(0, 0, 0, 128));
            setOpaque(false);
        }
        // public void initializeButtonBox() {
        // setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // JPanel buttonPanel = new JPanel();
        // buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        // buttonPanel.add(Box.createHorizontalGlue());
        // buttonPanel.add(easyButton);
        // buttonPanel.add(Box.createHorizontalStrut(10));
        // buttonPanel.add(mediumButton);
        // buttonPanel.add(Box.createHorizontalStrut(10));
        // buttonPanel.add(hardButton);
        // buttonPanel.add(Box.createHorizontalGlue());
        // buttonPanel.setBackground(new Color(0,0,0,128));
        // buttonPanel.setBackground(new Color(0,0,0,128));
        // buttonPanel.setOpaque(false);

        // add(buttonPanel);
        // add(Box.createVerticalGlue());
        // add(Box.createVerticalStrut(20));
        // add(infoButton);

        // easyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // mediumButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // hardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // infoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // setBackground(new Color(0,0,0,128));
        // setOpaque(false);
        // }
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
