import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SudokuGenerator implements ActionListener {

    JFrame frame;
    JPanel panel;
    JPanel[] rows;
    JTextField[][] textFields;
    JPanel extras;
    JButton checkButton;
    JLabel text;
    Random r;
    int size;
    int boxSize;
    int[][] finalCorrectSudoku;
    int[][] finalOutputSudoku;
    String[][] stringFinalOutputSudoku;
    boolean finished = false;


    public SudokuGenerator(int size) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.size = size;
        this.boxSize = (int)Math.sqrt((double)this.size);
        finalCorrectSudoku = new int[size][size];
        generateSudokuGUI();
    }

    public void generateSudokuGUI() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setTitle("Sudoku") ;
        frame.setResizable(false);

        textFields = new JTextField[9][9];
        for (int i = 0; i < textFields.length; i++) {
            for (int j = 0; j < textFields[i].length; j++) {
                textFields[i][j] = new JTextField();
                textFields[i][j].setPreferredSize(new Dimension(50, 50));
                textFields[i][j].setFont(new Font("Courier New", Font.PLAIN, 30));
                textFields[i][j].setBackground(new Color(0xfff1e5));
                textFields[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                textFields[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
                if (i % 3 == 0) {
                    textFields[i][j].setBorder(BorderFactory.createMatteBorder(2, 1, 1, 1, Color.BLACK));
                    if (i == 0) {
                        textFields[i][j].setBorder(BorderFactory.createMatteBorder(4, 1, 1, 1, Color.BLACK));
                    }
                    if (j % 3 == 0 && i == 0) {
                        textFields[i][j].setBorder(BorderFactory.createMatteBorder(4, 2, 1, 1, Color.BLACK));
                        if (j == 0) {
                            textFields[i][j].setBorder(BorderFactory.createMatteBorder(4, 4, 1, 1, Color.BLACK));
                        }
                    } else if (j % 3 == 0) {
                        textFields[i][j].setBorder(BorderFactory.createMatteBorder(2, 2, 1, 1, Color.BLACK));
                        if (j == 0) {
                            textFields[i][j].setBorder(BorderFactory.createMatteBorder(2, 4, 1, 1, Color.BLACK));
                        }
                    }
                    if (j % 3 == 2) {
                        textFields[i][j].setBorder(BorderFactory.createMatteBorder(2, 1, 1, 2, Color.BLACK));
                        if (j == 8) {
                            textFields[i][j].setBorder(BorderFactory.createMatteBorder(2, 1, 1, 4, Color.BLACK));
                        }
                        if (i == 0) {
                            textFields[i][j].setBorder(BorderFactory.createMatteBorder(4, 1, 1, 2, Color.BLACK));
                            if(j == 8) {
                                textFields[i][j].setBorder(BorderFactory.createMatteBorder(4, 1, 1, 4, Color.BLACK));
                            }
                        }
                    }
                } else if (i % 3 == 1) {
                    if (j % 3 == 2) {
                        textFields[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 2, Color.BLACK));
                        if (j == 8) {
                            textFields[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 4, Color.BLACK));
                        }
                    }
                    if (j % 3 == 0) {
                        textFields[i][j].setBorder(BorderFactory.createMatteBorder(2, 2, 1, 1, Color.BLACK));
                        if (j == 0) {
                            textFields[i][j].setBorder(BorderFactory.createMatteBorder(2, 4, 1, 1, Color.BLACK));
                        }
                    }
                } else if (i % 3 == 2) {
                    textFields[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 2, 1, Color.BLACK));
                    if (j % 3 == 0) {
                        textFields[i][j].setBorder(BorderFactory.createMatteBorder(1, 2, 2, 1, Color.BLACK));
                        if(j == 0) {
                            textFields[i][j].setBorder(BorderFactory.createMatteBorder(1, 4, 2, 1, Color.BLACK));
                        }
                    }
                    if (i == 8) {
                        textFields[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, Color.BLACK));
                        if (j % 3 == 0) {
                            textFields[i][j].setBorder(BorderFactory.createMatteBorder(1, 2, 4, 1, Color.BLACK));
                            if (j == 0) {
                                textFields[i][j].setBorder(BorderFactory.createMatteBorder(1, 4, 4, 1, Color.BLACK));
                            }
                        }
                    }
                    if (j % 3 == 2) {
                        textFields[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 2, 2, Color.BLACK));
                        if (i == 8) {
                            textFields[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 4, 2, Color.BLACK));
                        }
                        if (j == 8) {
                            textFields[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 2, 4, Color.BLACK));
                            if (i == 8) {
                                textFields[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 4, 4, Color.BLACK));

                            }
                        }
                    }
                }
            }
        }
        for (int i = 1; i < size; i = i + 3) {
            for (int j = 0; j < size; j = j + 3) {
                if (j == 0) {
                    textFields[i][j].setBorder(BorderFactory.createMatteBorder(1, 4, 1, 1, Color.BLACK));
                } else {
                    textFields[i][j].setBorder(BorderFactory.createMatteBorder(1, 2, 1, 1, Color.BLACK));
                }
            }
        }

        rows = new JPanel[9];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new JPanel();
            rows[i].setLayout(new BoxLayout(rows[i], BoxLayout.LINE_AXIS));
        }

        for (int i = 0; i < textFields.length; i++) {
            JPanel rigidAreaBorderLeft = new JPanel();
            rigidAreaBorderLeft.setBackground(new Color (0xd0efff));
            Dimension left = new Dimension(50,50);
            rigidAreaBorderLeft.setSize(left);
            rigidAreaBorderLeft.setPreferredSize(left);
            rigidAreaBorderLeft.setMaximumSize(left);
            rigidAreaBorderLeft.setMinimumSize(left);
            rows[i].add(rigidAreaBorderLeft);
            for (int j = 0; j < textFields[i].length; j++) {
                rows[i].add(textFields[i][j]);
            }
            JPanel rigidAreaBorderRight = new JPanel();
            rigidAreaBorderRight.setBackground(new Color (0xd0efff));
            Dimension right = new Dimension(50,50);
            rigidAreaBorderRight.setSize(right);
            rigidAreaBorderRight.setPreferredSize(right);
            rigidAreaBorderRight.setMaximumSize(right);
            rigidAreaBorderRight.setMinimumSize(right);
            rows[i].add(rigidAreaBorderRight);
        }

        panel = new JPanel();
        panel.setBackground(new Color (0xd0efff));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(Box.createRigidArea(new Dimension(50, 50)));
        for (int i = 0; i < rows.length; i++) {
            panel.add(rows[i]);
        }
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        extras = new JPanel();
        extras.setBackground(new Color (0xd0efff));
        extras.setLayout(new BoxLayout(extras, BoxLayout.LINE_AXIS));
        text = new JLabel("Press the button to check if your number was right!");
        checkButton = new JButton("Check");
        checkButton.addActionListener(checkNumber);
        extras.add(text);
        extras.add(Box.createRigidArea(new Dimension(10, 10)));
        extras.add(checkButton);
        panel.add(extras);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));

        generateSudokuNumbers();

        frame.add(panel);
        frame.setVisible(true);
        frame.pack();
    }

    public boolean checkDifference() {
        String[][] userInput = userInputCalculator();
        stringFinalOutputSudoku = new String[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (finalOutputSudoku[i][j] == 0) {
                    stringFinalOutputSudoku[i][j] = "";
                } else {
                    stringFinalOutputSudoku[i][j] = Integer.toString(finalOutputSudoku[i][j]);
                }
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!(userInput[i][j].equals(stringFinalOutputSudoku[i][j]))) {
                    return true;
                }
            }
        }
        return false;
    }

    public void generateSudokuNumbers() {
        r = new Random();
        int[][] correctSudoku = generateSudokuDiagonal(r);
        generateRemainingSudoku(correctSudoku);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                finalCorrectSudoku[i][j] = correctSudoku[i][j];
            }
        }
        int[][] finalArray = outputtedArray(correctSudoku);
        finalOutputSudoku = finalArray;
        output(finalOutputSudoku);
    }
    public String[][] userInputCalculator() {
        String[][] result = new String[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = textFields[i][j].getText();
            }
        }
        return result;
    }

    public int[][] generateSudokuDiagonal(Random r) {
        int[][] squareOne = new int[3][3];
        int[][] squareTwo = new int[3][3];
        int[][] squareThree = new int[3][3];
        int[][] result = new int[9][9];
        int temp = 0;
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < boxSize; j++) {
                do {
                    temp = r.nextInt(9) + 1;
                    if (!contains(squareOne, temp, 3, 0, 0)) {
                        squareOne[i][j] = temp;
                    }
                } while(squareOne[i][j] == 0);
            }
        }
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < boxSize; j++) {
                do {
                    temp = r.nextInt(9) + 1;
                    if (!contains(squareTwo, temp,3, 0, 0)) {
                        squareTwo[i][j] = temp;
                    }
                } while(squareTwo[i][j] == 0);
            }
        }
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < boxSize; j++) {
                do {
                    temp = r.nextInt(9) + 1;
                    if (!contains(squareThree, temp, 3,0, 0)) {
                        squareThree[i][j] = temp;
                    }
                } while(squareThree[i][j] == 0);
            }
        }
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < boxSize; j++) {
                result[i][j] = squareOne[i][j];
            }
        }
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < boxSize; j++) {
                result[i+3][j+3] = squareTwo[i][j];
            }
        }
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < boxSize; j++) {
                result[i+6][j+6] = squareThree[i][j];
            }
        }
        return result;
    }

    public boolean generateRemainingSudoku(int[][] sudoku) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (sudoku[i][j] == 0) {
                    for (int k = 1; k <= 9; k++) {
                        if (!(checkRow(sudoku,i,k)) &&
                                !(checkColumn(sudoku, j, k)) && !(checkBox(sudoku,i,j,k))) {
                            sudoku[i][j] = k;
                            if (generateRemainingSudoku(sudoku)) {
                                return true;
                            } else {
                                sudoku[i][j] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean contains(int[][] square, int value, int size, int rowAddend,int columnAddend) {
        boolean result = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (square[i + rowAddend][j + columnAddend] == value) {
                    result = true;
                }
            }
        }
        return result;
    }

    public int[][] outputtedArray(int[][] correctArray) {
        Random r = new Random();
        int column = 0;
        ArrayList<Integer> columnIndexes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 4; j++) {
                do {
                    column = r.nextInt(9);
                } while (columnIndexes.contains(column));
                columnIndexes.add(column);
            }
            for (int m : columnIndexes) {
                correctArray[i][m] = 0;
            }
            columnIndexes.clear();
        }
        return correctArray;
    }
    public boolean contains(int[][][] sudoku, int value) {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                if (sudoku[i][j][0] == value) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[][][] make3D(int[][] sudoku) {
        int[][][] output = new int[size][size][2];
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[i].length; j++) {
                output[i][j][0] = sudoku[i][j];
            }
        }
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[i].length; j++) {
                if (output[i][j][0] != 0) {
                    output[i][j][1] = 1;
                } else {
                    output[i][j][1] = 0;
                }
            }
        }
        return output;
    }

    public boolean contains(int[] numbers, int value) {
        for (int i : numbers) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }
    public void output(int[][] sudoku) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(sudoku[i][j] == 0) {
                    textFields[i][j].setText("");
                } else {
                    textFields[i][j].setText(Integer.toString(sudoku[i][j]));
                    textFields[i][j].setEditable(false);
                }
            }
        }
    }

    public boolean checkRow(int[][] sudoku, int rowIndex, int num) {
        boolean result = false;
        for(int i = 0; i < sudoku[rowIndex].length; i++) {
            if (sudoku[rowIndex][i] == num) {
                result = true;
            }
        }
        return result;
    }

    public boolean checkColumn(int[][] sudoku, int columnIndex, int num) {
        boolean result = false;
        for(int i = 0; i < sudoku.length; i++) {
            if (sudoku[i][columnIndex] == num) {
                result = true;
            }
        }
        return result;
    }
    public boolean checkBox(int[][] sudoku, int rowIndex, int columnIndex, int num) {
        int squareNum;
        int rowAdder = 0;
        int columnAdder = 0;
        if (rowIndex < 3) {
            if (columnIndex < 3) {
                squareNum = 1;
            } else if (columnIndex < 6) {
                squareNum = 2;
            } else {
                squareNum = 3;
            }
        } else if (rowIndex < 6) {
            if (columnIndex < 3) {
                squareNum = 4;
            } else if (columnIndex < 6) {
                squareNum = 5;
            } else {
                squareNum = 6;
            }
        } else {
            if (columnIndex < 3) {
                squareNum = 7;
            } else if (columnIndex < 6) {
                squareNum = 8;
            } else {
                squareNum = 9;
            }
        }

        switch (squareNum) {
            case 1:
                rowAdder = 0;
                columnAdder = 0;
                break;
            case 2:
                rowAdder = 0;
                columnAdder = 3;
                break;
            case 3:
                rowAdder = 0;
                columnAdder = 6;
                break;
            case 4:
                rowAdder = 3;
                columnAdder = 0;
                break;
            case 5:
                rowAdder = 3;
                columnAdder = 3;
                break;
            case 6:
                rowAdder = 3;
                columnAdder = 6;
                break;
            case 7:
                rowAdder = 6;
                columnAdder = 0;
                break;
            case 8:
                rowAdder = 6;
                columnAdder = 3;
                break;
            case 9:
                rowAdder = 6;
                columnAdder = 6;
                break;
        }
        return contains(sudoku, num, 3, rowAdder, columnAdder);
    }

    public int[][] differenceArrayCalculator() {
        int[][] differenceArray = new int[size][size];
        String[][] userInput = userInputCalculator();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((stringFinalOutputSudoku[i][j].equals(userInput[i][j]))) {
                    differenceArray[i][j] = 0;
                } else {
                    differenceArray[i][j] = 1;
                }
            }
        }
        return differenceArray;
    }

    public static void main(String[] args) {
        new SudokuGenerator(9);
    }
    public boolean noMoreZeros(int[][] numbers){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (numbers[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    ActionListener checkNumber = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (checkDifference()) {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (textFields[i][j].getBackground().equals(Color.RED) || textFields[i][j].getBackground().equals(Color.GREEN)) {
                            textFields[i][j].setBackground(new Color(0xfff1e5));
                        }
                    }
                }
                text.setText("The cells in green are correct and the cells in red are wrong. Keep going!");
                int[][] differenceArray = differenceArrayCalculator();
                String[][] userInput = userInputCalculator();
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (differenceArray[i][j] == 1) {
                            if (Integer.parseInt(userInput[i][j]) == finalCorrectSudoku[i][j]) {
                                textFields[i][j].setBackground(Color.GREEN);
                                textFields[i][j].setEditable(false);
                                finalOutputSudoku[i][j] = Integer.parseInt(userInput[i][j]);
                                if (noMoreZeros(finalOutputSudoku)) {
                                    text.setText("Congratulations! You finished the sudoku!");
                                    finished = true;
                                }
                            } else {
                                textFields[i][j].setBackground(Color.RED);
                            }
                        }
                    }
                }
            } else {
                if (!finished) {
                    text.setText("Please type in a number in one of the cells before pressing check.");
                }
            }
        }
    };
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
