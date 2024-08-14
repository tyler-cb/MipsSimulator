package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Simulation.Circuitry.Part;
import Simulation.Helper;
import Structures.Vector2;
import org.json.simple.JSONObject;

import java.util.List;

public class ProgramCounter extends Part {

    public ProgramCounter(Vector2 pos, SimulationController simController) {
        super(pos, 3, 3, "Program Counter", List.of(32), List.of(32), simController);
    }

    public ProgramCounter(SimulationController simController, JSONObject json) throws Exception {
        this(new Vector2((JSONObject) json.get("pos")), simController);
    }

    @Override
    public void run() {
        Integer input = Helper.getTwosComplement(inPins.get(0).getStatus(), 18);
        outPins.get(0).setStatus(input);
    }

    @Override
    public void leftClicked() {
        super.leftClicked();
    }
}
