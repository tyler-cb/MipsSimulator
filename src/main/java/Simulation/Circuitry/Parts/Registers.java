package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Interface.MemoryEditor;
import Interface.RegisterEditor;
import Simulation.Circuitry.InPin;
import Simulation.Circuitry.OutPin;
import Simulation.Circuitry.Part;
import Structures.Vector2;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Registers extends Part {
    private List<Integer> registers;
    private InPin readReg1;
    private InPin readReg2;
    private InPin writeReg;
    private InPin writeData;
    private OutPin readData1;
    private OutPin readData2;

    private InPin controlRegWrite;
    public Registers(Vector2 pos, SimulationController simController) {
        super(pos, 5, 6, "Registers", List.of(32, 5, 5, 5), List.of(32, 32), simController);
        Integer [] initialData = new Integer[32];
        Arrays.fill(initialData, 0);
        registers = Arrays.asList(initialData);
        controlRegWrite = new InPin(this, new Vector2(2.5f, 6), 1, true);
        addPin(controlRegWrite);
        this.readReg1 = getInPins().get(3);
        this.readReg2 = getInPins().get(2);
        this.writeReg = getInPins().get(1);
        this.writeData = getInPins().get(0);

        this.readData1 = getOutPins().get(1);
        this.readData2 = getOutPins().get(0);

        flags.add("runMoreThanOnce");

    }

    public Registers(SimulationController simController, JSONObject json) throws Exception {
        this(new Vector2((JSONObject) json.get("pos")), simController);
    }

    public List<Integer> getRegisters() {
        return registers;
    }

    public InPin getReadReg1() {
        return readReg1;
    }

    public InPin getReadReg2() {
        return readReg2;
    }

    public InPin getWriteReg() {
        return writeReg;
    }

    public InPin getWriteData() {
        return writeData;
    }

    public OutPin getReadData1() {
        return readData1;
    }

    public OutPin getReadData2() {
        return readData2;
    }

    public InPin getControlRegWrite() {
        return controlRegWrite;
    }

    @Override
    public void run() {
        readData1.setStatus(registers.get(readReg1.getStatus()));
        readData2.setStatus(registers.get(readReg2.getStatus()));

        if (controlRegWrite.getStatus() == 1 && writeData.changedInCurrentCycle()) {
            // $zero register cannot be modified
            if (writeReg.getStatus() != 0) {
                registers.set(writeReg.getStatus(), writeData.getStatus());
            }
        }
    }

    @Override
    public void rightClicked() {
        RegisterEditor editor = new RegisterEditor(registers);
    }
}
