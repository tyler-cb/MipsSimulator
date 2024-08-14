package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class RegisterEditor extends JFrame {

    private List<Integer> registers;
    private List<JTextField> textFields;

    public RegisterEditor(List<Integer> registers) {
        setMinimumSize(new Dimension(400, 600));
        this.registers = registers;
        this.textFields = new ArrayList<>();

        JButton save = new JButton("Save");
        save.addActionListener(e -> {
            this.save();
        });

        JScrollPane scrollPane = new JScrollPane();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(save);

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(registers.size(), 2, 20, 0));
        panel.add(registerPanel);
        scrollPane.setViewportView(panel);

        for (int i = 0; i < registers.size(); i++) {
            JLabel label = new JLabel(Integer.toString(i));
            registerPanel.add(label);

            JTextField textField = new JTextField(registers.get(i).toString());
            registerPanel.add(textField);
            textFields.add(textField);
        }

        add(scrollPane);
        setVisible(true);
    }

    private void save() {
        for (int i = 0; i < registers.size(); i++) {
            JTextField textField = textFields.get(i);
            registers.set(i, Integer.parseInt(textField.getText()));
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }
}
