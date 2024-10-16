// Utilizes the Shunting Yard Algorythm to convert equations from infix notation to Reverse Polish notation
// See <https://en.wikipedia.org/wiki/Shunting_yard_algorithm> for more information

import java.util.ArrayList;
import java.util.Stack;
import java.util.HashMap;

import java.lang.ArithmeticException;

class EquationSolver {

    // used to store the completed notation in notate()
    // used to evaluate the equation in solve()
    private static ArrayList<String> equation;

    // used to sort the operators and place them correctly into the equation in notate()
    private static Stack<String> operators;

    // used to check the precedence of an operator in notate()
    private static HashMap<String, Integer> precedence = new HashMap<String, Integer>();

    // add all values to the precedence HashMap
    static {
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
    }

    // converts an equation in infix notation to Reverse Polish notation
    private static String[] notate(String[] input) {

        equation = new ArrayList<String>();
        operators = new Stack<String>();

        for (int i = 0; i < input.length; i++) {

            // check if the string is a number
            if (isNumber(input[i])) {
                equation.add(input[i]);

            // check if the string is an operator
            } else if (isOperator(input[i])) {

                // if the stack is empty, push directly to the stack
                // if the stack is not empty, pop off operators and add them to the equation
                //  until the operator is of the same or more importance than the top operator on the stack
                while (!operators.isEmpty() && checkPrecedence(operators.peek(), input[i])) {
                    equation.add(operators.pop());
                }
                operators.push(input[i]);

            // throw exception if an unknown character is found
            } else {
                throw new ArithmeticException("Invalid Character.");
            }
        }

        // pop all remaining operators to the output
        while (!operators.isEmpty()) {
            equation.add(operators.pop());
        }
        return toArray(equation);
    }

    // solves the equation in Reverse Polish notation
    public static String solve(String[] input) {
        equation = toArrayList(notate(input));

        // solves each equation from the inside out and returns the single number leftover
        for (int i = 0; i <= equation.size(); i++) {
            if (equation.size() == 1) {break;}
            if (isOperator(equation.get(i))) {
                equation.set(i, evaluate(equation.get(i-2), equation.get(i-1), equation.get(i)));
                equation.remove(i-1);
                equation.remove(i-2);
                i-=2;
            }
        }
        return equation.get(0);
    }

    // return true if the given string is a number
    private static boolean isNumber(String str) {
        try {int i = Integer.parseInt(str);}
        catch (NumberFormatException e) {return false;}
        return true;
    }

    // return true if the given string is an operator
    private static boolean isOperator(String str) {
        switch (str) {
            case "+": return true;
            case "-": return true;
            case "*": return true;
            case "/": return true;
            default: return false;
        }
    }

    // return true only if the first operator has more precedence than the second operator
    private static boolean checkPrecedence(String operator1, String operator2) {
        if (precedence.get(operator1) > precedence.get(operator2)) {return true;}
        else {return false;}
    }

    // return an array copy of an ArrayList
    private static String[] toArray(ArrayList<String> arrList) {
        String[] arr = new String[arrList.size()];
        for (int i = 0; i < arr.length; i++) {arr[i] = arrList.get(i);}
        return arr;
    }

    // returns an ArrayList copy of an array
    private static ArrayList<String> toArrayList(String[] arr) {
        ArrayList<String> arrList = new ArrayList<String>();
        for (int i = 0; i < arr.length; i++) {arrList.add(arr[i]);}
        return arrList;
    }

    // returns an evaluation of the equation formed by (operand1 operator operand2)
    private static String evaluate(String operand1, String operand2, String operator) {
        switch (operator) {
            case "+": return String.valueOf(Integer.parseInt(operand1) + Integer.parseInt(operand2));
            case "-": return String.valueOf(Integer.parseInt(operand1) - Integer.parseInt(operand2));
            case "*": return String.valueOf(Integer.parseInt(operand1) * Integer.parseInt(operand2));
            case "/": return String.valueOf(Integer.parseInt(operand1) / Integer.parseInt(operand2));
            default: throw new ArithmeticException("An error occured while evaluating the equation.");
        }
    }
}
