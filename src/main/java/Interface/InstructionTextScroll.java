package Interface;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class InstructionTextScroll extends JScrollPane {
    private JTextArea text;
    private JTextArea lineNumbers;
    private int lines;

    public InstructionTextScroll(int lines) {
        super();
        text = new JTextArea();
        lineNumbers = new JTextArea();

        lineNumbers.setBackground(Color.LIGHT_GRAY);
        lineNumbers.setEditable(false);

        // Set up lines
        StringBuilder lineNums = new StringBuilder();
        StringBuilder newlines = new StringBuilder();
        for (int i = 0; i < lines; i++) {
            newlines.append(System.getProperty("line.separator"));
            lineNums.append(i).append(System.getProperty("line.separator"));
        }
        text.setText(newlines.toString());
        lineNumbers.setText(lineNums.toString());
        text.setCaretPosition(0);
        setRowHeaderView(lineNumbers);
        setViewportView(text);

        setSize(200, 400);
    }

    public JTextArea getTextArea() {
        return text;
    }
}
