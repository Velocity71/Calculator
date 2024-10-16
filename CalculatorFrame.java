import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

class CalculatorFrame extends Frame {
    public CalculatorFrame() {
        super("Calculator");
        setSize(500, 500);
        setVisible(true);

        // stops the program when the user closes the window
        addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }});
    }
}
