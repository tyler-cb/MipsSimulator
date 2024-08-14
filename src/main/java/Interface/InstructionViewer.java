package Interface;

import Simulation.Circuitry.Parts.InstructionMemory;
import Simulation.Translation;
import Structures.Exceptions.TranslationException;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InstructionViewer extends JFrame {
    InstructionTextScroll mcTextField;
    InstructionTextScroll humanTextField;
    InstructionMemory memory;

    public InstructionViewer(InstructionMemory memory) {
        setLayout(new BorderLayout());

        this.memory = memory;
        mcTextField = new InstructionTextScroll(memory.getSize());
        humanTextField = new InstructionTextScroll(memory.getSize());
        mcTextField.getVerticalScrollBar().addAdjustmentListener(e -> humanTextField.getVerticalScrollBar().setValue(e.getValue()));
        humanTextField.getVerticalScrollBar().addAdjustmentListener(e -> mcTextField.getVerticalScrollBar().setValue(e.getValue()));

        Dimension minimumSize = new Dimension(400, 400);
        mcTextField.setMinimumSize(minimumSize);
        mcTextField.setPreferredSize(minimumSize);
        humanTextField.setMinimumSize(minimumSize);
        humanTextField.setPreferredSize(minimumSize);

        JButton translate = new JButton("Translate");
        translate.addActionListener(e -> {
            translateAction();
        });
        JButton save = new JButton("Save");
        save.addActionListener(e -> {
            saveAction();
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> {
            cancelAction();
        });

        JPanel leftSide = new JPanel(new BorderLayout());
        JPanel rightSide = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel mcLabel = new JLabel("Machine");
        JLabel humanLabel = new JLabel("Assembly");

        rightSide.add(mcLabel, BorderLayout.NORTH);
        rightSide.add(mcTextField);
        leftSide.add(humanLabel, BorderLayout.NORTH);
        leftSide.add(humanTextField);
        buttons.add(translate, gbc);
        buttons.add(save, gbc);
        buttons.add(cancel, gbc);

        add(leftSide, BorderLayout.WEST);
        add(buttons, BorderLayout.CENTER);
        add(rightSide, BorderLayout.EAST);

        setResizable(false);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }

    private void translateAction() {
        String[] lines = humanTextField.getTextArea().getText().split("\\n");
        StringBuilder translated = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            try {
                String line = lines[i].replace("\n", "").replace("\r", "");
                if (line.equals("")) {
                    translated.append("\n");
                    continue;
                }
                String trLine = Translation.translate(line);
                translated.append(trLine).append("\n");
            } catch (TranslationException ex) {
                translated.append("Error: ").append(ex.getMessage()).append("\n");
            }
        }
        mcTextField.getTextArea().setText(translated.toString());

        // Set scrolls back to top
        SwingUtilities.invokeLater(() -> {
            humanTextField.getVerticalScrollBar().setValue(0);
            mcTextField.getVerticalScrollBar().setValue(0);
            humanTextField.getViewport().revalidate();
            humanTextField.getViewport().repaint();
            mcTextField.getViewport().revalidate();
            mcTextField.getViewport().repaint();
        });
    }

    private void saveAction() {
        translateAction();
        String[] lines = mcTextField.getTextArea().getText().split("\\n");
        for (int i = 0; i < memory.getSize(); i++) {
            try {
                String line = lines[i].replace("\n", "").replace("\r", "");
                if (line.length() != 32) {
                    throw new NumberFormatException();
                }
                ArrayList<Integer> bytes = new ArrayList<>();
                for (int j = 0; j < line.length(); j += 8) {
                    String substr = line.substring(j, j + 8);
                    bytes.add(Integer.parseInt(substr, 2));
                }
                memory.getMemory().setWord(i*4, bytes);

            } catch (NumberFormatException | IndexOutOfBoundsException e ) {
                ArrayList<Integer> bytes = new ArrayList<>();
                for (int j = 0; j < 4; j++) {
                    bytes.add(0b00000000);
                }
                memory.getMemory().setWord(i*4, bytes);
            }
        }
    }

    private void cancelAction() {
        setVisible(false);
    }
}
