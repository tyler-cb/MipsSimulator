package Interface;

import Controllers.SimulationController;
import Simulation.Circuitry.Parts.Registers;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class RegistersPanel extends JPanel {
    SimulationController controller;
    Registers registers = null;
    JLabel noRegister;
    RegisterTable registerTable;
    RegisterTableModel model;
    public RegistersPanel(SimulationController controller) {
        this.controller = controller;
        controller.setRegistersPanel(this);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Registers", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        noRegister = new JLabel("No Registers Loaded", SwingConstants.CENTER);
        add(noRegister, BorderLayout.CENTER);

        setVisible(true);
    }

    public void display(Registers registers) {
        remove(noRegister);

        JPanel tablePanel = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane();
        tablePanel.add(scroll);
        model = new RegisterTableModel(registers.getRegisters());
        registerTable = new RegisterTable(model);
        scroll.setViewportView(registerTable);
        add(tablePanel);
        tablePanel.setVisible(true);

        repaint();
    }

    public void registerDataChange(List<Integer> registerList) {
        model.update(registerList);
    }

    static class RegisterTableModel extends AbstractTableModel {
        private List<Integer> registers;
        private final String[] columnNames = {"Register", "Contents"};
        public RegisterTableModel(List<Integer> registers) {
            this.registers = registers;
        }

        public void update(List<Integer> newRegisters) {
            registers = newRegisters;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return registers.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) {
                if (rowIndex == 0) {
                    return "$zero";
                }
                return "$r" + (rowIndex);
            } else {
                return registers.get(rowIndex);
            }
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }

    static class RegisterTable extends JTable {
        public RegisterTable(AbstractTableModel model) {
            super(model);
        }
    }
}
