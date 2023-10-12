import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Calculator implements ActionListener{
    JFrame frame;
    JPanel panel;
    JTextField textField;
    JButton equals, division, multiply, subtract, addition, decimal, zero, one,
            two, three, four, five, six, seven, eight, nine, delete, C;
    String mem1;
    String mem2;
    String op;
    public Calculator() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setTitle("Calculator") ;
        frame.setResizable(false);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(100, 40));
        textField.setFont(new Font("Courier New", Font.PLAIN, 30));
        textField.setBackground(new Color(0xfff1e5));
        textField.setEditable(false);
        textField.setText("0");
        textField.setHorizontalAlignment(SwingConstants.RIGHT);

        equals = new JButton("=");
        division = new JButton("/");
        zero = new JButton("0");
        decimal = new JButton(".");
        addition = new JButton("+");
        subtract = new JButton("-");
        multiply = new JButton("x");
        one = new JButton("1");
        two = new JButton("2");
        three = new JButton("3");
        four = new JButton("4");
        five = new JButton("5");
        six = new JButton("6");
        seven = new JButton("7");
        eight = new JButton("8");
        nine = new JButton("9");
        delete = new JButton("D");
        C = new JButton("C");

        decimal.setFont(new Font("Courier New", Font.PLAIN, 30));
        decimal.addActionListener(other);
        equals.setFont(new Font("Courier New", Font.PLAIN, 30));
        equals.addActionListener(other);
        addition.setFont(new Font("Courier New", Font.PLAIN, 30));
        addition.addActionListener(operation);
        subtract.setFont(new Font("Courier New",Font.PLAIN, 30));
        subtract.addActionListener(operation);
        division.setFont(new Font("Courier New", Font.PLAIN, 30));
        division.addActionListener(operation);
        multiply.setFont(new Font("Courier New", Font.PLAIN, 30));
        multiply.addActionListener(operation);
        C.setFont(new Font("Courier New", Font.PLAIN, 30));
        C.addActionListener(other);
        delete.setFont(new Font("Courier New", Font.PLAIN, 30));
        delete.addActionListener(other);
        zero.setFont(new Font("Courier New", Font.PLAIN, 30));
        zero.addActionListener(numbers);
        one.setFont(new Font("Courier New", Font.PLAIN, 30));
        one.addActionListener(numbers);
        two.setFont(new Font("Courier New", Font.PLAIN, 30));
        two.addActionListener(numbers);
        three.setFont(new Font("Courier New", Font.PLAIN, 30));
        three.addActionListener(numbers);
        four.setFont(new Font("Courier New", Font.PLAIN, 30));
        four.addActionListener(numbers);
        five.setFont(new Font("Courier New", Font.PLAIN, 30));
        five.addActionListener(numbers);
        six.setFont(new Font("Courier New", Font.PLAIN, 30));
        six.addActionListener(numbers);
        seven.setFont(new Font("Courier New", Font.PLAIN, 30));
        seven.addActionListener(numbers);
        eight.setFont(new Font("Courier New", Font.PLAIN, 30));
        eight.addActionListener(numbers);
        nine.setFont(new Font("Courier New", Font.PLAIN, 30));
        nine.addActionListener(numbers);

        JPanel row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.LINE_AXIS));
        row1.add(Box.createHorizontalGlue());
        row1.add(C);
        row1.add(delete);
        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.LINE_AXIS));
        row2.add(seven);
        row2.add(eight);
        row2.add(nine);
        row2.add(multiply);
        JPanel row3 = new JPanel();
        row3.setLayout(new BoxLayout(row3, BoxLayout.LINE_AXIS));
        row3.add(four);
        row3.add(five);
        row3.add(six);
        row3.add(subtract);
        JPanel row4 = new JPanel();
        row4.setLayout(new BoxLayout(row4, BoxLayout.LINE_AXIS));
        row4.add(one);
        row4.add(two);
        row4.add(three);
        row4.add(addition);
        JPanel row5 = new JPanel();
        row5.setLayout(new BoxLayout(row5, BoxLayout.LINE_AXIS));
        row5.add(decimal);
        row5.add(zero);
        row5.add(division);
        row5.add(equals);

        panel = new JPanel();
        panel.setBackground(Color.lightGray);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(textField);
        panel.add(row1);
        panel.add(row2);
        panel.add(row3);
        panel.add(row4);
        panel.add(row5);

        frame.add(panel);
        frame.setVisible(true);
        frame.pack();
    }


    public static void main(String[] args) {
        new Calculator();
    }

    public void delete() {
        String current = textField.getText();
        if (!current.equals("")){
            String newString = current.substring(0, current.length() - 1);
            textField.setText(newString);
        }
    }

    public void clear() {
        if (!textField.getText().equals("")) {
            textField.setText("");
        }
        op = null;
        mem1 = null;
        mem2 = null;
    }
    public void selectOperator(String operator) {
        if (Objects.nonNull(op)) {
            System.out.println("this was triggered");
            String temp = mem1;
            String temp2 = textField.getText();
            mem1 = continuousCalculation(op, temp, temp2);
            textField.setText("");
            op = operator;
        } else {
            op = operator;
            mem1 = textField.getText();
            textField.setText("");
        }
    }

    public String continuousCalculation(String op, String o1, String o2) {
        float num1 = Float.parseFloat(o1);
        float num2 = Float.parseFloat(o2);
        switch(op) {
            case "+":
                return Float.toString(num1 + num2);
            case "-":
                return Float.toString(num1 - num2);
            case "x":
                return Float.toString(num1 * num2);
            case "/":
                return Float.toString(num1 / num2);
            default:
                return "-1";
        }
    }
    public void append(String text) {
        if (textField.getText().equals("0") || textField.getText().equals("0.0")) {
            textField.setText(text);
        } else {
            String current = textField.getText();
            String newString = current + text;
            textField.setText(newString);
        }
    }
    public void calculate() {
        mem2 = textField.getText();
        float num1 = Float.parseFloat(mem1);
        float num2 = Float.parseFloat(mem2);
        float result = 0;
        switch(op) {
            case "+":
                result = num1 + num2;
                textField.setText(Float.toString(result));
                mem1 = Float.toString(result);
                op = null;
                break;
            case "-":
                result = num1 - num2;
                textField.setText(Float.toString(result));
                mem1 = Float.toString(result);
                op = null;
                break;
            case "x":
                result = num1 * num2;
                textField.setText(Float.toString(result));
                mem1 = Float.toString(result);
                op = null;
                break;
            case "/":
                result = num1 / num2;
                textField.setText(Float.toString(result));
                mem1 = Float.toString(result);
                op = null;
                break;
        }
        fixOutput();
    }
    public void fixOutput() {
        String og = textField.getText();
        if (og.substring(og.length() - 2, og.length()).equals(".0")) {
            textField.setText(og.substring(0, og.length() - 2));
        }
    }
    ActionListener numbers = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton event = (JButton) e.getSource();
            if (event == one) {
                append("1");
            } else if (event == two) {
                append("2");
            } else if (event == three) {
                append("3");
            } else if (event == four) {
                append("4");
            } else if (event == five) {
                append("5");
            } else if (event == six) {
                append("6");
            } else if (event == seven) {
                append("7");
            } else if (event == eight) {
                append("8");
            } else if (event == nine) {
                append("9");
            } else if (event == zero) {
                append("0");
            }
        }
    };
    ActionListener operation = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton event = (JButton) e.getSource();
            if (event == multiply) {
                selectOperator("x");
            } else if (event == division) {
                selectOperator("/");
            } else if (event == addition) {
                selectOperator("+");
            } else if (event == subtract) {
                selectOperator("-");
            }
        }
    };
    ActionListener other = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton event = (JButton) e.getSource();
            if (event == equals) {
                calculate();
            } else if (event == delete) {
                delete();
            } else if (event == C) {
                clear();
            } else if (event == decimal) {
                if (!textField.getText().contains(".")) {
                    append(".");
                }
            }
        }
    };
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
