package Interface;

import Controllers.SimulationController;
import Structures.Exceptions.JSONException;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MenuBar extends JMenuBar {

    SimulationController simController;
    JMenu fileMenu;
    JMenuItem helpMenu;

    public MenuBar(SimulationController simController) {
        this.simController = simController;
        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");

        initFileMenu();
        initHelpMenu();

        add(fileMenu);
        add(helpMenu);
    }

    private void initFileMenu() {
        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));

        loadItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            File init = new File(".");
            fileChooser.setCurrentDirectory(init);
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    simController.loadFile(file);
                } catch (ParseException | JSONException | IOException ex) {
                    ErrorWindow ew = new ErrorWindow("Error Loading " + file.getName(), ex);

                }
            }
        });

        fileMenu.add(loadItem);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

        saveItem.addActionListener(e -> {

            // Modify file chooser to add confirmation on overwrite
            JFileChooser fileChooser = new JFileChooser() {
                @Override
                public void approveSelection() {
                    File f = getSelectedFile();
                    if (f.exists()) {
                        int result = JOptionPane.showConfirmDialog(this, "The file "+f.getName()+" exists, overwrite?", "Overwrite", JOptionPane.YES_NO_CANCEL_OPTION);
                        switch (result) {
                            case JOptionPane.YES_OPTION:
                                super.approveSelection();
                                return;
                            case JOptionPane.CANCEL_OPTION:
                                cancelSelection();
                                return;
                            case JOptionPane.CLOSED_OPTION:
                                return;
                            case JOptionPane.NO_OPTION:
                                return;
                        }
                    }
                    super.approveSelection();
                }
            };
            File init = new File(".");
            fileChooser.setCurrentDirectory(init);
            int response = fileChooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    simController.saveFile(file);
                } catch (IOException | JSONException ex) {
                    ErrorWindow ew = new ErrorWindow(ex);
                }
            }
        });

        fileMenu.add(saveItem);
    }

    private void initHelpMenu() {
        //String eol = System.get
        JMenuItem gettingStarted = new JMenuItem("Getting Started");
        JMenuItem instructionsRegisters = new JMenuItem("Instructions & Registers");

        gettingStarted.addActionListener(e -> {
            JFrame helpWindow = new JFrame();
            JLabel helpText = new JLabel("<html>"+
                    "Use the file menu to load a JSON.<br>" +
                    "This should load the selected circuit into the program.<br>" +
                    "View the registers on the left side of the screen, or right click on the register component.<br>" +
                    "Right click on the instruction memory to edit it.<br>" +
                    "You can also view the data memory by right clicking<br>",
                    SwingConstants.CENTER);
            helpWindow.add(helpText);
            helpWindow.setMinimumSize(new Dimension(400, 200));
            helpWindow.pack();
            helpWindow.setVisible(true);
        });

        instructionsRegisters.addActionListener(e -> {
            JFrame helpWindow = new JFrame();
            JLabel helpText = new JLabel("<html>"+
                    "Registers 0-31 are labeled $zero, and then $r1-$r31.<br>"+
                    "Supported Instructions are addi, andi, ori, lw, sw, beq, bne, add, sub, and, or<br>"+
                    "Right click the instruction memory to edit it.<br>"+
                    "Enter MIPS assembly into the left text field and click 'translate'<br>"+
                    "When you are satisfied, click 'save' to save the machine code into memory",
                    SwingConstants.CENTER);
            helpWindow.add(helpText);
            helpWindow.setMinimumSize(new Dimension(400, 200));
            helpWindow.pack();
            helpWindow.setVisible(true);
        });

        helpMenu.add(gettingStarted);
        helpMenu.add(instructionsRegisters);
    }
}
