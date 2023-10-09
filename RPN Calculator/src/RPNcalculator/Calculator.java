package RPNcalculator;

import javax.swing.*;
import java.util.Stack;

public class Calculator {
    private JPanel Panel;
    private JButton Zero;
    private JButton bracket1;
    private JButton Two;
    private JButton One;
    private JButton Three;
    private JButton bracket2;
    private JButton plus;
    private JButton Four;
    private JButton Five;
    private JButton Six;
    private JButton minus;
    private JButton Seven;
    private JButton Eight;
    private JButton Nine;
    private JButton multiplied;
    private JButton divided;
    private JButton delete;
    private JTextField TextField;
    private JButton equal;
    private JButton convert;
    private JRadioButton RPNRadioButton;
    private JRadioButton infixRadioButton;

    public Calculator(){
        Zero.addActionListener(actionEvent -> {
            TextField.setText(TextField.getText() + "0");
        });
        One.addActionListener(actionEvent -> {
            TextField.setText(TextField.getText() + "1");
        });
        Two.addActionListener(actionEvent -> {
            TextField.setText(TextField.getText() + "2");
        });
        Three.addActionListener(actionEvent -> {
            TextField.setText(TextField.getText() + "3");
        });
        Four.addActionListener(actionEvent -> {
            TextField.setText(TextField.getText() + "4");
        });
        Five.addActionListener(actionEvent -> {
            TextField.setText(TextField.getText() + "5");
        });
        Six.addActionListener(actionEvent -> {
            TextField.setText(TextField.getText() + "6");
        });
        Seven.addActionListener(actionEvent -> {
            TextField.setText(TextField.getText() + "7");
        });
        Eight.addActionListener(actionEvent -> {
            TextField.setText(TextField.getText() + "8");
        });
        Nine.addActionListener(actionEvent -> {
            TextField.setText(TextField.getText() + "9");
        });
        plus.addActionListener(actionEvent -> {
            TextField.setText(TextField.getText() + "+");
        });
        minus.addActionListener(actionEvent -> {
            TextField.setText(TextField.getText() + "-");
        });
        multiplied.addActionListener(actionEvent -> {
            TextField.setText(TextField.getText() + "*");
        });
        divided.addActionListener(actionEvent -> {
            TextField.setText(TextField.getText() + "/");
        });
        bracket1.addActionListener(actionEvent -> {
            TextField.setText(TextField.getText() + "(");
        });
        bracket2.addActionListener(actionEvent -> {
            TextField.setText(TextField.getText() + ")");
        });
        delete.addActionListener(actionEvent -> {
            TextField.setText("");
        });
        convert.addActionListener(actionEvent -> {
            String rpnExpression = infixToRPN(TextField.getText());
            TextField.setText(rpnExpression);
        });
        equal.addActionListener(actionEvent -> {
            if(RPNRadioButton.isSelected()){
                String rpnExpression = calculateRPN(TextField.getText());
                TextField.setText(rpnExpression);
            }
            else if(infixRadioButton.isSelected()){
                String rpnExpression = infixToRPN(TextField.getText());
                String result = calculateRPN(rpnExpression);
                TextField.setText(result);
            }
        });
        RPNRadioButton.addActionListener(actionEvent -> {
            if(RPNRadioButton.isSelected()){
                infixRadioButton.setSelected(false);
            }
        });
        infixRadioButton.addActionListener(actionEvent -> {
            if(infixRadioButton.isSelected()){
                RPNRadioButton.setSelected(false);
            }
        });
    }

    public static String infixToRPN(String infixExpression) {
        StringBuilder output = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < infixExpression.length(); i++) {
            char token = infixExpression.charAt(i);

            if (Character.isLetterOrDigit(token)) {
                output.append(token);
            } else if (token == '(') {
                operatorStack.push(token);
            } else if (token == ')') {
                output.append(" ");
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    output.append(operatorStack.pop());
                }
                if (!operatorStack.isEmpty() && operatorStack.peek() == '(') {
                    operatorStack.pop();
                }
            } else {
                output.append(" ");
                while (!operatorStack.isEmpty() && priority(token) <= priority(operatorStack.peek())) {
                    output.append(operatorStack.pop());
                    output.append(" ");
                }
                operatorStack.push(token);
            }
        }

        output.append(" ");

        while (!operatorStack.isEmpty()) {
            output.append(operatorStack.pop());
            output.append(" ");
        }

        return output.toString();
    }

    private static int priority(char operator) {
        switch (operator) {
            case '+': return 1;
            case '-': return 1;
            case '*': return 2;
            case '/': return 2;
            default: return 0;
        }
    }

    public static String calculateRPN(String expression) {

        StringBuilder output = new StringBuilder();
        Stack<Double> stack = new Stack<>();
        String[] token = expression.split(" ");

        for (String tokens : token) {
            if (isNumber(tokens)) {
                double value = Double.parseDouble(tokens);
                stack.push(value);
            } else {
                double operand2 = stack.pop();
                double operand1 = stack.pop();

                double result = Operation(tokens, operand1, operand2);
                stack.push(result);
            }
        }

        output.append(stack.pop().toString());

        return output.toString();
    }

    public static boolean isNumber(String s){

        for (int i = 0; i < s.length(); i++){
            char token = s.charAt(i);
            if (!Character.isLetterOrDigit(token)) {
                return false;
            }
        }
        return true;
    }

    private static double Operation(String operator, double operand1, double operand2) {
        double result = 0;
        if(operator.equals("+"))
            result = operand1 + operand2;
        else if(operator.equals("-"))
            result = operand1 - operand2;
        else if(operator.equals("*"))
            result = operand1 * operand2;
        else if(operator.equals("/"))
            result = operand1 / operand2;

        return result;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setContentPane(new Calculator().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}