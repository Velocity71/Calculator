// Utilizes the Shunting Yard Algorythm to convert equations from infix notation to Reverse Polish notation
// See <https://en.wikipedia.org/wiki/Shunting_yard_algorithm> for more information

import java.util.ArrayList;
import java.util.Stack;
import java.util.HashMap;

class RPNotator {

    // used to store the completed notation
    private static ArrayList<Character> output;

    // used to sort the operators and place them correctly into the output
    private static Stack<Character> operators;

    // used to check the precedence of an operator
    private static HashMap<Character, Integer> precedence = new HashMap<Character, Integer>();

    // add all values to the map
    static {
        precedence.put('+', 1);
        precedence.put('-', 1);
        precedence.put('×', 2);
        precedence.put('÷', 2);
    }

    // converts an equation in infix notation to Reverse Polish notation
    public static void notate(char[] input) {

        output = new ArrayList<Character>();
        operators = new Stack<Character>();

        for (int i = 0; i < input.length; i++) {

            // check if the character is a digit
            if (Character.isDigit(input[i])) {
                output.add(input[i]);

            // check if the charater is an operator
            } else if (isOperator(input[i])) {

                // if the stack is empty, push directly to the stack
                // if the stack is not empty, pop off operators and add them to the output
                //  until the character is of the same or more importance than the top operator on the stack
                if (!operators.isEmpty()) {
                    while (checkPrecedence(input[i], operators.peek())) {
                        output.add(operators.pop());
                    }
                }
                operators.push(input[i]);
            }
        }

        // pop all remaining operators to the output
        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }

        System.out.println(output);
    }

    // return true if the given character is an operator
    private static boolean isOperator(char ch) {
        switch (ch) {
            case '+': return true;
            case '-': return true;
            case '×': return true;
            case '÷': return true;
            default: return false;
        }
    }

    // return true only if the first operator has more precedence than the second operator
    private static boolean checkPrecedence(char op1, char op2) {
        if (precedence.get(op1) > precedence.get(op2)) {return true;}
        else {return false;}
    }
}
