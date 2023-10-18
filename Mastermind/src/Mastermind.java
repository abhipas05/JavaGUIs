import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Random;

public class Mastermind implements ActionListener {

    JFrame frame;
    JPanel panel;
    JPanel[] rows;
    JTextField[][] display;
    JTextField[] userInput;
    JTextField[] redPegs;
    JTextField[] whitePegs;
    JTextField inputForAttempts;
    JPanel userInputErrorPanel;
    JPanel initialErrorPanel;
    JPanel userInputPanel;
    JPanel submitButtonPanel;
    JPanel directUserInput;
    JPanel userInstructionsPanel;
    JPanel colors;
    JLabel tooManyAttemptsLabel;
    JLabel userInstructions;
    JLabel firstColors;
    JButton submitButton;
    String[] correctSequence;
    Random r;
    int numOfAttempts;
    int attemptNumber = 0;
    boolean submitButtonSelected = false;
    final int MAX_NUMBER_OF_ATTEMPTS = 20;
    final int DISPLAY_DIM = 27;
    final int USER_INPUT_DIM = 35;
    final int NUM_OF_BOXES_TO_BE_GUESSED = 4;

    public static void main(String[] args) {
        new Mastermind();
    }

    public Mastermind() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        generateMastermind();
    }

    public void generateMastermind() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Mastermind Setup");
        frame.setResizable(false);

        JPanel initTextPanel = new JPanel();
        JLabel initialLabel = new JLabel();
        initialLabel.setText("How many attempts would you like to have? (1-" + MAX_NUMBER_OF_ATTEMPTS + ")");
        initTextPanel.setBackground(new Color(0xd0efff));
        initTextPanel.setLayout(new BoxLayout(initTextPanel, BoxLayout.LINE_AXIS));
        initTextPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        initTextPanel.add(initialLabel);
        initTextPanel.add(Box.createRigidArea(new Dimension(30, 0)));

        JPanel inputPanel = new JPanel();
        inputForAttempts = new JTextField();
        JButton submitAttempts = new JButton();
        inputForAttempts.setPreferredSize(new Dimension(70, 20));
        inputForAttempts.setMinimumSize(new Dimension(70, 20));
        inputForAttempts.setMaximumSize(new Dimension(70, 20));
        inputForAttempts.setHorizontalAlignment(SwingConstants.RIGHT);
        submitAttempts.setText("Submit");
        submitAttempts.setPreferredSize(new Dimension(78, 20));
        submitAttempts.setMaximumSize(new Dimension(78, 20));
        submitAttempts.setMinimumSize(new Dimension(78, 20));
        submitAttempts.addActionListener(initialSubmitButton);

        initialErrorPanel = new JPanel();
        initialErrorPanel.setLayout(new BoxLayout(initialErrorPanel, BoxLayout.PAGE_AXIS));
        tooManyAttemptsLabel = new JLabel();
        tooManyAttemptsLabel.setForeground(new Color(0xB83D35));
        tooManyAttemptsLabel.setAlignmentX(Box.CENTER_ALIGNMENT);
        initialErrorPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        initialErrorPanel.add(tooManyAttemptsLabel);
        initialErrorPanel.add(Box.createRigidArea(new Dimension(0, 16)));
        initialErrorPanel.setBackground(new Color(0xd0efff));
        initialErrorPanel.setVisible(false);

        inputPanel.setBackground(new Color(0xd0efff));
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.LINE_AXIS));
        inputPanel.add(Box.createRigidArea(new Dimension(125, 0)));
        inputPanel.add(inputForAttempts);
        inputPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        inputPanel.add(submitAttempts);
        inputPanel.add(Box.createRigidArea(new Dimension(125, 0)));

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(new Color(0xd0efff));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(initTextPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 7)));
        panel.add(inputPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(initialErrorPanel);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public void generateMastermindBoard() {
        frame.remove(panel);
        frame.revalidate();
        frame.repaint();
        panel.removeAll();
        panel.revalidate();
        panel.repaint();

        display = new JTextField[numOfAttempts][NUM_OF_BOXES_TO_BE_GUESSED];
        rows = new JPanel[numOfAttempts];
        redPegs = new JTextField[numOfAttempts];
        whitePegs = new JTextField[numOfAttempts];
        for (int i = 0; i < numOfAttempts; i++) {
            redPegs[i] = new JTextField();
            whitePegs[i] = new JTextField();
            whitePegs[i].setBackground(Color.WHITE);
            whitePegs[i].setPreferredSize(new Dimension(DISPLAY_DIM, DISPLAY_DIM));
            whitePegs[i].setMinimumSize(new Dimension(DISPLAY_DIM, DISPLAY_DIM));
            whitePegs[i].setMaximumSize(new Dimension(DISPLAY_DIM, DISPLAY_DIM));
            whitePegs[i].setFont(new Font("Courier New", Font.BOLD, 16));
            whitePegs[i].setHorizontalAlignment(JTextField.CENTER);
            whitePegs[i].setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
            whitePegs[i].setEditable(false);
            whitePegs[i].setVisible(false);
            redPegs[i].setBackground(Color.RED);
            redPegs[i].setPreferredSize(new Dimension(DISPLAY_DIM, DISPLAY_DIM));
            redPegs[i].setMinimumSize(new Dimension(DISPLAY_DIM, DISPLAY_DIM));
            redPegs[i].setMaximumSize(new Dimension(DISPLAY_DIM, DISPLAY_DIM));
            redPegs[i].setFont(new Font("Courier New", Font.BOLD, 16));
            redPegs[i].setHorizontalAlignment(JTextField.CENTER);
            redPegs[i].setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
            redPegs[i].setEditable(false);
            redPegs[i].setVisible(false);
        }
        for (int i = 0; i < numOfAttempts; i++) {
            rows[i] = new JPanel();
            rows[i].setLayout(new BoxLayout(rows[i], BoxLayout.LINE_AXIS));
            rows[i].setBackground(new Color(0xd0efff));
            rows[i].add(Box.createRigidArea(new Dimension(30, 0)));
            rows[i].add(whitePegs[i]);
            rows[i].add(Box.createRigidArea(new Dimension(50, 0)));
            for (int j = 0; j < NUM_OF_BOXES_TO_BE_GUESSED; j++) {
                display[i][j] = new JTextField();
                display[i][j].setPreferredSize(new Dimension(DISPLAY_DIM, DISPLAY_DIM));
                display[i][j].setMaximumSize(new Dimension(DISPLAY_DIM, DISPLAY_DIM));
                display[i][j].setMinimumSize(new Dimension(DISPLAY_DIM, DISPLAY_DIM));
                display[i][j].setFont(new Font("Courier New", Font.BOLD, 16));
                display[i][j].setBackground(new Color(0xfff1e5));
                display[i][j].setHorizontalAlignment(JTextField.CENTER);
                display[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
                display[i][j].setEditable(false);
                rows[i].add(display[i][j]);
                rows[i].add(Box.createRigidArea(new Dimension(6, 0)));
            }
            rows[i].add(Box.createRigidArea(new Dimension(44, 0)));
            rows[i].add(redPegs[i]);
            rows[i].add(Box.createRigidArea(new Dimension(30, 0)));
        }

        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        for (int i = 0; i < numOfAttempts; i++) {
            panel.add(rows[i]);
            panel.add(Box.createRigidArea(new Dimension(0, 6)));
        }
        panel.add(Box.createRigidArea(new Dimension(0, 9)));
        panel.setBackground(new Color(0xd0efff));

        userInputPanel = new JPanel();
        userInputPanel.setLayout(new BoxLayout(userInputPanel, BoxLayout.PAGE_AXIS));
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 4)));

        userInstructionsPanel = new JPanel();
        userInstructions = new JLabel("Now type in your first color guess. Refer to the guide below!", SwingConstants.CENTER);
        userInstructions.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        userInstructionsPanel.add(userInstructions);
        userInputPanel.add(userInstructionsPanel);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        directUserInput = new JPanel();
        directUserInput.setLayout(new BoxLayout(directUserInput, BoxLayout.LINE_AXIS));
        directUserInput.add(Box.createRigidArea(new Dimension(30, 0)));
        userInput = new JTextField[NUM_OF_BOXES_TO_BE_GUESSED];
        for (int i = 0; i < NUM_OF_BOXES_TO_BE_GUESSED; i++) {
            userInput[i] = new JTextField();
            userInput[i].setPreferredSize(new Dimension(USER_INPUT_DIM, USER_INPUT_DIM));
            userInput[i].setMinimumSize(new Dimension(USER_INPUT_DIM, USER_INPUT_DIM));
            userInput[i].setMaximumSize(new Dimension(USER_INPUT_DIM, USER_INPUT_DIM));
            userInput[i].setFont(new Font("Courier New", Font.BOLD, 20));
            userInput[i].setBackground(new Color(0xfff1e5));
            userInput[i].setHorizontalAlignment(JTextField.CENTER);
            userInput[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
            directUserInput.add(userInput[i]);
            if (i != (NUM_OF_BOXES_TO_BE_GUESSED - 1)) {
                directUserInput.add(Box.createRigidArea(new Dimension(8, 0)));
            }
        }
        directUserInput.add(Box.createRigidArea(new Dimension(30, 0)));
        userInputPanel.add(directUserInput);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        submitButtonPanel = new JPanel();
        submitButton = new JButton();
        submitButton.setText("Submit Guess");
        submitButton.addActionListener(submitButtonAction);
        submitButtonPanel.add(submitButton);
        userInputPanel.add(submitButtonPanel);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        colors = new JPanel();
        firstColors = new JLabel();
        firstColors.setText("R = red, O = orange, Y = yellow, G = green, B = blue, P = pink");
        colors.add(Box.createRigidArea(new Dimension(10, 0)));
        colors.add(firstColors);
        colors.add(Box.createRigidArea(new Dimension(10, 0)));

        userInputPanel.add(colors);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        userInputErrorPanel = new JPanel();
        userInputErrorPanel.setLayout(new BoxLayout(userInputErrorPanel, BoxLayout.PAGE_AXIS));
        JLabel potentialErrorMessage = new JLabel("There was an error with the input. Try again.");
        potentialErrorMessage.setForeground(new Color(0xB83D35));
        potentialErrorMessage.setAlignmentX(Box.CENTER_ALIGNMENT);
        userInputErrorPanel.add(potentialErrorMessage);
        userInputErrorPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        userInputErrorPanel.setVisible(false);
        userInputPanel.add(userInputErrorPanel);

        generateCorrectSequence();

        panel.add(userInputPanel);
        frame.add(panel);
        frame.setTitle("Mastermind");
        frame.pack();
    }

    public void generateCorrectSequence() {
        r = new Random();
        correctSequence = new String[NUM_OF_BOXES_TO_BE_GUESSED];
        for (int i = 0; i < NUM_OF_BOXES_TO_BE_GUESSED; i++) {
            int randomNumber = r.nextInt(6) + 1;
            switch (randomNumber) {
                case 1:
                    correctSequence[i] = "O";
                    break;
                case 2:
                    correctSequence[i] = "Y";
                    break;
                case 3:
                    correctSequence[i] = "G";
                    break;
                case 4:
                    correctSequence[i] = "R";
                    break;
                case 5:
                    correctSequence[i] = "B";
                    break;
                case 6:
                    correctSequence[i] = "P";
                    break;
            }
        }
    }

    ActionListener initialSubmitButton = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean attemptNumIsInt = true;
            int potentialNumAttempts = 0;
            try {
                Integer.parseInt(inputForAttempts.getText());
            } catch (NumberFormatException n) {
                attemptNumIsInt = false;
            }
            if (attemptNumIsInt) {
                potentialNumAttempts = Integer.parseInt(inputForAttempts.getText());
                if (potentialNumAttempts <= MAX_NUMBER_OF_ATTEMPTS) {
                    numOfAttempts = potentialNumAttempts;
                    generateMastermindBoard();
                } else {
                    tooManyAttemptsLabel.setText("The number of attempts you typed was too many. Try again.");
                    initialErrorPanel.setVisible(true);
                    frame.pack();
                }
            } else {
                tooManyAttemptsLabel.setText("You did not type in a number into the textfield. Try again.");
                initialErrorPanel.setVisible(true);
                frame.pack();
            }

        }
    };

    public boolean validateUserInput() {
        boolean[] trueOrFalse = new boolean[NUM_OF_BOXES_TO_BE_GUESSED];
        for (int i = 0; i < NUM_OF_BOXES_TO_BE_GUESSED; i++) {
            trueOrFalse[i] = validateOneInput(userInput[i].getText());
            if (!trueOrFalse[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean validateOneInput(String s) {
        return (s.equalsIgnoreCase("O") || s.equalsIgnoreCase("R") ||
                s.equalsIgnoreCase("Y") || s.equalsIgnoreCase("G") ||
                s.equalsIgnoreCase("B") || s.equalsIgnoreCase("P"));
    }

    public String[] getUserInputValues() {
        String[] returned = new String[NUM_OF_BOXES_TO_BE_GUESSED];
        for (int i = 0; i < NUM_OF_BOXES_TO_BE_GUESSED; i++) {
            returned[i] = userInput[i].getText();
        }
        return returned;
    }

    public int[] compareToCorrect(String[] s) {
        System.out.println(Arrays.toString(s));
        System.out.println(Arrays.toString(correctSequence));
        int[] returned = new int[]{0, 0};
        boolean[] elementSelectedAlreadyCorrect = new boolean[NUM_OF_BOXES_TO_BE_GUESSED];
        boolean[] elementSelectedAlreadyGuess = new boolean[NUM_OF_BOXES_TO_BE_GUESSED];
        for (int i = 0; i < NUM_OF_BOXES_TO_BE_GUESSED; i++) {
            elementSelectedAlreadyGuess[i] = false;
            elementSelectedAlreadyCorrect[i] = false;
        }
        for (int i = 0; i < NUM_OF_BOXES_TO_BE_GUESSED; i++) {
            if (correctSequence[i].equalsIgnoreCase(s[i])) {
                returned[0]++;
                elementSelectedAlreadyCorrect[i] = true;
                elementSelectedAlreadyGuess[i] = true;
            }
        }
        for (int i = 0; i < NUM_OF_BOXES_TO_BE_GUESSED; i++) {
            for (int j = 0; j < NUM_OF_BOXES_TO_BE_GUESSED; j++) {
                if (correctSequence[i].equalsIgnoreCase(s[j]) &&
                        !elementSelectedAlreadyCorrect[i] && !elementSelectedAlreadyGuess[j]) {
                    returned[1]++;
                    elementSelectedAlreadyCorrect[i] = true;
                    elementSelectedAlreadyGuess[j] = true;
                }
            }
        }
        return returned;
    }

    public void setColor(JTextField j, String s) {
        String capitalized = s.toUpperCase();
        switch (capitalized) {
            case "O":
                j.setBackground(Color.ORANGE);
                break;
            case "Y":
                j.setBackground(Color.YELLOW);
                break;
            case "G":
                j.setBackground(Color.GREEN);
                break;
            case "R":
                j.setBackground(new Color(0xED7E7E));
                break;
            case "B":
                j.setBackground(new Color(0x9FBAED));
                break;
            case "P":
                j.setBackground(Color.PINK);
                break;
        }
    }

    ActionListener submitButtonAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            userInputErrorPanel.setVisible(false);
            frame.pack();
            if (validateUserInput()) {
                if (!submitButtonSelected) {
                    userInstructions.setText("<html><div style='text-align: center;'>Numbers in red signify the number of colors you gue" +
                            "ssed that are in the right position<br/>Numbers in white denote colors that exist in the sequence" +
                            " but are not in the right position<br/>" +
                            "Guess again and Good Luck!</html>");
                    userInstructionsPanel.add(Box.createRigidArea(new Dimension(20, 0)), 0);
                    userInstructionsPanel.add(Box.createRigidArea(new Dimension(20, 0)), 2);
                    userInstructionsPanel.revalidate();
                    userInstructionsPanel.repaint();
                    frame.pack();
                    submitButtonSelected = true;
                }
                for (int i = 0; i < NUM_OF_BOXES_TO_BE_GUESSED; i++) {
                    display[attemptNumber][i].setText(userInput[i].getText().toUpperCase());
                    setColor(display[attemptNumber][i], userInput[i].getText());
                }
                int[] numsOfRedAndWhite = compareToCorrect(getUserInputValues());
                redPegs[attemptNumber].setVisible(true);
                redPegs[attemptNumber].setText(Integer.toString(numsOfRedAndWhite[0]));
                whitePegs[attemptNumber].setVisible(true);
                whitePegs[attemptNumber].setText(Integer.toString(numsOfRedAndWhite[1]));
                if (numsOfRedAndWhite[0] == NUM_OF_BOXES_TO_BE_GUESSED) {
                    userInputPanel.removeAll();
                    userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                    userInputPanel.add(colors);
                    userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                    firstColors.setText("You Won! Congratulations!");
                    firstColors.setFont(new Font("Arial", Font.BOLD, 20));
                    firstColors.setForeground(Color.BLACK);
                    userInputPanel.revalidate();
                    userInputPanel.repaint();
                    rows[attemptNumber].setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, Color.GREEN));
                    frame.pack();
                }
                frame.pack();
                attemptNumber++;
                if (attemptNumber == numOfAttempts) {
                    userInputPanel.removeAll();
                    userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                    userInputPanel.add(colors);
                    userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                    firstColors.setText("You Lost. Better Luck Next Time!");
                    firstColors.setFont(new Font("Arial", Font.BOLD, 20));
                    firstColors.setForeground(Color.BLACK);
                    userInputPanel.revalidate();
                    userInputPanel.repaint();
                    frame.pack();
                }
            } else {
                userInputErrorPanel.setVisible(true);
                frame.pack();
            }
        }
    };


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
