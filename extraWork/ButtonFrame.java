package extraWork;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.NumericShaper;

import javax.swing.*;

public class ButtonFrame extends JFrame {
    private JPanel buttonPanel;
    private JLabel text;
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private static int num;

    public ButtonFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        // create buttons
        JButton yellowButton = new JButton("Yellow");
        JButton blueButton = new JButton("Blue");
        JButton redButton = new JButton("Red");
        JButton countButton = new JButton("Counting Button");
        

        buttonPanel = new JPanel();
        // add buttons to panel
        buttonPanel.add(yellowButton);
        buttonPanel.add(blueButton);
        buttonPanel.add(redButton);
        buttonPanel.add(countButton);


        
        text = new JLabel();
        // add panel to frame
        add(buttonPanel);
        add(text, BorderLayout.SOUTH);
        // create button actions
        ColorAction yellowAction = new ColorAction(Color.YELLOW);
        ColorAction blueAction = new ColorAction(Color.BLUE);
        ColorAction redAction = new ColorAction(Color.RED);
        NumberAction numAction = new NumberAction();

        // associate actions with buttons
        yellowButton.addActionListener(yellowAction);
        blueButton.addActionListener(blueAction);
        redButton.addActionListener(redAction);
        countButton.addActionListener(numAction);
    }

    private class ColorAction implements ActionListener {
        private Color backgroundColor;

        public ColorAction(Color c) {
            backgroundColor = c;
        }

        public void actionPerformed(ActionEvent event) {
            buttonPanel.setBackground(backgroundColor);
        }
    }
    private class NumberAction implements ActionListener {
        private int number = 0;

        public void actionPerformed(ActionEvent event) {
            number++;
            text.setText(Integer.toString(number));
            System.out.println(Integer.toString(number));
        }
    }
}