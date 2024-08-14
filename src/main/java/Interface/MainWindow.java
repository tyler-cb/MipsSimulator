package Interface;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private CircuitPanel circuitPanel;
    private MenuBar menuBar;
    private SidePanel sidePanel;
    public MainWindow() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        setLayout(new BorderLayout());
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        circuitPanel = new CircuitPanel();
        circuitPanel.setPreferredSize(new Dimension(560, 600));
        add(circuitPanel, BorderLayout.CENTER);
        CircuitMouseListener mouseListener = new CircuitMouseListener(circuitPanel);
        circuitPanel.addMouseListener(mouseListener);
        circuitPanel.addMouseMotionListener(mouseListener);
        circuitPanel.addMouseWheelListener(mouseListener);

        menuBar = new MenuBar(circuitPanel.getSimController());
        setJMenuBar(menuBar);

        sidePanel = new SidePanel(circuitPanel);
        sidePanel.setBackground(Color.WHITE);
        sidePanel.setPreferredSize(new Dimension(240, 600));
        add(sidePanel, BorderLayout.WEST);

        setTitle("MIPS Processor Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);

    }
}
