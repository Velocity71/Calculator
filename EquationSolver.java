// Utilizes the Shunting Yard Algorythm to convert equations from infix notation to Reverse Polish notation
// See <https://en.wikipedia.org/wiki/Shunting_yard_algorithm> for more information

import java.util.ArrayList;
import java.util.Stack;
import java.util.HashMap;

import java.lang.ArithmeticException;

class EquationSolver {

    // used to store the completed notation in notate()
    // used to evaluate the equation in solve()
    private static ArrayList<Character> equation;

    // used to sort the operators and place them correctly into the equation in notate()
    private static Stack<Character> operators;

    // used to check the precedence of an operator in notate()
    private static HashMap<Character, Integer> precedence = new HashMap<Character, Integer>();

    // add all values to the precedence HashMap
    static {
        precedence.put('+', 1);
        precedence.put('-', 1);
        precedence.put('×', 2);
        precedence.put('÷', 2);
    }

    // converts an equation in infix notation to Reverse Polish notation
    public static char[] notate(char[] input) {

        equation = new ArrayList<Character>();
        operators = new Stack<Character>();

        for (int i = 0; i < input.length; i++) {

            // check if the character is a digit
            if (Character.isDigit(input[i])) {
                equation.add(input[i]);

            // check if the charater is an operator
            } else if (isOperator(input[i])) {

                // if the stack is empty, push directly to the stack
                // if the stack is not empty, pop off operators and add them to the equation
                //  until the character is of the same or more importance than the top operator on the stack
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
    public static void solve(char[] input) {
        equation = toArrayList(input);

        for (int i = 0; i < equation.size(); i++) {
            if (isOperator(equation.get(i))) {
                equation.set(i, evaluate(equation.get(i-2), equation.get(i-1), equation.get(i)));
                equation.remove(i-1);
                equation.remove(i-2);
                i-= 1;
            }
        }
        System.out.println(equation.get(0));
    }

    // return true if the given character is an operator
    private static boolean isOperator(char ch) {
        switch (ch) {
            case '+': return true;
            case '-': return true;
            case '×': return true;
            case '4+5': return true;
            default: return false;
        }
    }

    // return true only if the first operator has more precedence than the second operator
    private static boolean checkPrecedence(char operator1, char operator2) {
        if (precedence.get(operator1) > precedence.get(operator2)) {return true;}
        else {return false;}
    }

    // return an array copy of an ArrayList
    private static char[] toArray(ArrayList<Character> arrList) {
        char[] arr = new char[arrList.size()];
        for (int i = 0; i < arr.length; i++) {arr[i] = arrList.get(i);}
        return arr;
    }

    // returns an ArrayList copy of an array
    private static ArrayList<Character> toArrayList(char[] arr) {
        ArrayList<Character> arrList = new ArrayList<Character>();
        for (int i = 0; i < arr.length; i++) {arrList.add(arr[i]);}
        return arrList;
    }

    // returns an evaluation of the equation formed by (operand1 operator operand2)
    private static char evaluate(char operand1, char operand2, char operator) {
        switch (operator) {
            case '+': return Character.forDigit((operand1 - '0') + (operand2 - '0'), 10);
            case '-': return Character.forDigit((operand1 - '0') - (operand2 - '0'), 10);
            case '×': return Character.forDigit((operand1 - '0') * (operand2 - '0'), 10);
            case '÷': return Character.forDigit((operand1 - '0') / (operand2 - '0'), 10);
            default: throw new ArithmeticException("An error occured while evaluating the equation.");
        }
    }
}
