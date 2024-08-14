package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Interface.InstructionViewer;
import Simulation.Circuitry.Part;
import Structures.Memory;
import Structures.Vector2;
import org.json.simple.JSONObject;

import java.util.List;

public class InstructionMemory extends Part {

    // Every instruction is 1 word (32 bits), conveniently, a java Integer is also 32 bits long
    private Memory memory;
    private InstructionViewer viewer;

    public InstructionMemory(Vector2 pos, SimulationController simController) {
        super(pos, 5, 5, "Instruction Memory", List.of(32), List.of(32), simController);
        memory = new Memory(512);
        viewer = new InstructionViewer(this);
    }

    public InstructionMemory(SimulationController simController, JSONObject json) throws Exception {
        this(new Vector2((JSONObject) json.get("pos")),
                simController);
    }

    @Override
    public void run() {
        int address = inPins.get(0).getStatus();
        List<Integer> word = memory.getWord(address);
        int output = 0;
        for (int i = 0; i < 4; i++) {
            output += (word.get(3-i) << (8*i));
        }
        outPins.get(0).setStatus(output);
    }

    @Override
    public void leftClicked() {
        super.leftClicked();
    }

    @Override
    public void rightClicked() {
        viewer.setVisible(true);
    }

    public int getSize() {
        // Size here refers to amount of instructions it can store rather than bytes.
        return memory.getCapacity() / 4;
    }

    public Memory getMemory() {
        return memory;
    }
}
