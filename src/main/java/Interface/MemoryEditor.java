package Interface;

import Structures.Memory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MemoryEditor extends JFrame {
    private Memory memory;
    public MemoryEditor(Memory memory) {
        setMinimumSize(new Dimension(400, 600));

        JScrollPane scrollPane = new JScrollPane();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(memory.getCapacity(), 2, 3, 0));
        scrollPane.setViewportView(panel);

        for (int i = 0; i < memory.getCapacity(); i++) {

            JLabel label = new JLabel(Integer.toString(i), SwingConstants.CENTER);
            panel.add(label);

            int b = memory.getByte(i);
            // Format with leading 0's
            String byteString = String.format("%8s", Integer.toBinaryString(b)).replace(" ", "0");
            JTextField byteField = new JTextField(byteString);
            panel.add(byteField);
        }

        add(scrollPane);

        setVisible(true);
    }

}
