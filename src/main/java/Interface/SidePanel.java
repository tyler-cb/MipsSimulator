package Interface;

import javax.swing.*;
import java.awt.*;

public class SidePanel extends JPanel {
    private JButton cycleButton;
    private JButton stepButton;
    private CircuitPanel parent;
    private RegistersPanel registersPanel;

    public SidePanel(CircuitPanel parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));

        this.cycleButton = new JButton("Cycle");
        initializeCycle();
        buttons.add(cycleButton);

        this.stepButton = new JButton("Step");
        initializeStep();
        buttons.add(stepButton);
        add(buttons, BorderLayout.NORTH);

        registersPanel = new RegistersPanel(parent.getSimController());
        add(registersPanel, BorderLayout.CENTER);
    }

    private void initializeCycle() {
        cycleButton.addActionListener(e -> {
            try {
                parent.getSimController().cycle();
            } catch (Exception ex) {
                ErrorWindow ew = new ErrorWindow(ex);
            }
            parent.repaint();
        });
    }

    private void initializeStep() {
        stepButton.addActionListener(e -> {
            try {
                parent.getSimController().step();
            } catch (Exception ex) {
                ErrorWindow ew = new ErrorWindow(ex);
            }
            parent.repaint();
        });
    }
}
