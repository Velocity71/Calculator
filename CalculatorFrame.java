import java.awt.Frame;
import java.awt.Label;
import java.awt.Button;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

class CalculatorFrame extends Frame {

    private ArrayList<String> equation = new ArrayList<String>();
    private String block = "";
    Label display;
    private boolean answerDisplayed = false;

    public CalculatorFrame() {
        super("Calculator");
        setSize(200, 300); // size of frame is 500x500
        setUndecorated(true); // deletes title bar, as it overlays on part of the frame
        populateFrame();
        setVisible(true);
    }
    // populates the calculator frame with all the buttons and labels
    /* map of calculator:

        display
        C S % /
        7 8 9 x
        4 5 6 -
        1 2 3 +
        q 0 . =

        buttons are 50x50, display is 50x200

        object explanation:
        display: display the equation/answer
        C: clear the equation
        0-9: add number to equation
        +-x/: add operator to equation
        .: add decimal to equation (not working)
        %: turn decimal to percentage (not working)
        Q: quit the program
    */
    private void populateFrame() {

        display = new Label("", Label.RIGHT);
        display.setBounds(0, 0, 200, 50);
        display.setFont(new Font("Arial", Font.PLAIN, 30));
        add(display);

        Button clear = new Button("C");
        clear.setBounds(0, 50, 50, 50);
        clear.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {display.setText(""); equation.clear(); block = "";}});
        add(clear);

        Button sign = new Button("±");
        sign.setBounds(50, 50, 50, 50);
        sign.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addCharacter("±");}});
        add(sign);

        Button percentage = new Button("%");
        percentage.setBounds(100, 50, 50, 50);
        add(percentage);

        Button divide = new Button("/");
        divide.setBounds(150, 50, 50, 50);
        divide.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addCharacter("/");}});
        add(divide);

        Button seven = new Button("7");
        seven.setBounds(0, 100, 50, 50);
        seven.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addCharacter("7");}});
        add(seven);

        Button eight = new Button("8");
        eight.setBounds(50, 100, 50, 50);
        eight.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addCharacter("8");}});
        add(eight);

        Button nine = new Button("9");
        nine.setBounds(100, 100, 50, 50);
        nine.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addCharacter("9");}});
        add(nine);

        Button multiply = new Button("x");
        multiply.setBounds(150, 100, 50, 50);
        multiply.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addCharacter("*");}});
        add(multiply);

        Button four = new Button("4");
        four.setBounds(0, 150, 50, 50);
        four.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addCharacter("4");}});
        add(four);

        Button five = new Button("5");
        five.setBounds(50, 150, 50, 50);
        five.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addCharacter("5");}});
        add(five);

        Button six = new Button("6");
        six.setBounds(100, 150, 50, 50);
        six.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addCharacter("6");}});
        add(six);

        Button subtract = new Button("-");
        subtract.setBounds(150, 150, 50, 50);
        subtract.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addCharacter("-");}});
        add(subtract);

        Button one = new Button("1");
        one.setBounds(0, 200, 50, 50);
        one.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addCharacter("1");}});
        add(one);

        Button two = new Button("2");
        two.setBounds(50, 200, 50, 50);
        two.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addCharacter("2");}});
        add(two);

        Button three = new Button("3");
        three.setBounds(100, 200, 50, 50);
        three.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addCharacter("3");}});
        add(three);

        Button add = new Button("+");
        add.setBounds(150, 200, 50, 50);
        add.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addCharacter("+");}});
        add(add);

        Button quit = new Button("Q");
        quit.setBounds(0, 250, 50, 50);
        quit.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {System.exit(0);}});
        add(quit);

        Button zero = new Button("0");
        zero.setBounds(50, 250, 50, 50);
        zero.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addCharacter("0");}});
        add(zero);

        Button decimal = new Button(".");
        decimal.setBounds(100, 250, 50, 50);
        decimal.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {addCharacter(".");}});
        add(decimal);

        Button solve = new Button("=");
        solve.setBounds(150, 250, 50, 50);
        solve.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {submitEquation();}});
        add(solve);

        // the last item added to a frame has wierd bounds, so I add a label that has no bounds so all the buttons appear on the screen
        Label lastLabel = new Label();
        add(lastLabel);
    }

    // add a character to the equation, numbers inbetween operators are kept grouped because it means they are multi-digit numbers (i.e. 11, 143)
    private void addCharacter(String str) {
        if (isInt(str) || isDouble(str)) {
            if (answerDisplayed) {block = ""; answerDisplayed = false;}
            block += str;
        } else if (isOperator(str)) {
            equation.add(block);
            equation.add(str);
            block = "";
        } else if (str.equals("±")) { // a macron(¯) is used to show a number is negative
            if (block.isEmpty()) {block = "¯";}
            else if (block.charAt(0) == '¯') {block = block.substring(1);}
            else {block = "¯" + block;}
        } else if (str.equals(".")) {
            if (block.isEmpty()) {block = "0.";}
            else {block += str;}
        }
        display.setText(toString(equation) + block);
    }

    // solves the equation the user gave, and displays it
    private void submitEquation() {
        equation.add(block);
        // because the java compiler does not see a macron symbol as a negative symbol, we must replace it with a dash
        for (int i = 0; i < equation.size(); i++) {equation.set(i, equation.get(i).replace("¯", "-"));}
        String answer = EquationSolver.solve(toArray(equation)).replace("-", "¯"); // return the negative symbol to the macron for continuity
        equation.clear();
        block = "";
        addCharacter(answer);
        answerDisplayed = true;
    }

    // return true if the given string is a number
    private static boolean isInt(String str) {
        try {int i = Integer.parseInt(str);}
        catch (NumberFormatException e) {return false;}
        return true;
    }

    private static boolean isDouble(String str) {
        try {double d = Double.parseDouble(str);}
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

    // returns a string version of an arraylist
    private static String toString(ArrayList<String> arrList) {
        String str = "";
        for (int i = 0; i < arrList.size(); i++) {str += arrList.get(i);}
        return str;
    }

    // returns a array version of an arraylist
    private static String[] toArray(ArrayList<String> arrList) {
        String[] arr = new String[arrList.size()];
        for (int i = 0; i < arr.length; i++) {arr[i] = arrList.get(i);}
        return arr;
    }
}