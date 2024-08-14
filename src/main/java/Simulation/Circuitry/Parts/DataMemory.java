package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Interface.MemoryEditor;
import Simulation.Circuitry.InPin;
import Simulation.Circuitry.OutPin;
import Simulation.Circuitry.Part;
import Structures.Memory;
import Structures.Vector2;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataMemory extends Part {
    InPin memWriteControl;
    InPin memReadControl;
    InPin address;
    InPin dataIn;
    OutPin dataOut;
    Memory memory;
    public DataMemory(Vector2 pos, SimulationController simController) {
        super(pos, 3, 5, "Data Memory", List.of(32, 32), List.of(32), simController);
        memWriteControl = new InPin(this, new Vector2(1.5f, 5), 1, true);
        addPin(memWriteControl);
        memReadControl = new InPin(this, new Vector2(1.5f, 0), 1, true);
        addPin(memReadControl);
        this.memory = new Memory(1024);
        address = getInPins().get(1);
        dataIn = getInPins().get(0);
        dataOut = getOutPins().get(0);
    }

    public DataMemory(SimulationController simController, JSONObject json) throws Exception {
        this(new Vector2((JSONObject) json.get("pos")),
                simController);
    }

    @Override
    public void run() {
        if (memWriteControl.getStatus() == 1) {
            memory.setWord(address.getStatus(), convertToWord(dataIn.getStatus()));
        }
        if (memReadControl.getStatus() == 1) {
            int status = 0;
            List<Integer> word = memory.getWord(address.getStatus());
            for (int i = 0; i < 4; i++) {
                status += (word.get(3-i) << (8*i));
            }
            dataOut.setStatus(status);
        }
    }

    private List<Integer> convertToWord(Integer data) {
        String dataString = Integer.toBinaryString(data);
        dataString = String.format("%32s", dataString).replace(" ", "0");
        ArrayList<Integer> word = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            String dataByte = dataString.substring(i*8, (i*8)+8);
            word.add(Integer.parseInt(dataByte, 2));
        }
        return word;
    }

    @Override
    public void rightClicked() {
        MemoryEditor memoryViewer = new MemoryEditor(memory);
    }

    public InPin getMemWriteControl() {
        return memWriteControl;
    }

    public InPin getMemReadControl() {
        return memReadControl;
    }
}
