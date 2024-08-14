package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Simulation.Circuitry.Part;
import Simulation.Helper;
import Structures.Vector2;
import org.json.simple.JSONObject;

import java.util.List;

public class SignExtend extends Part {
    public SignExtend(Vector2 pos, Integer inPinWidth, Integer outPinWidth, SimulationController simController) {
        super(pos, 2, 2, "Extend", List.of(inPinWidth), List.of(outPinWidth), simController);
    }

    public SignExtend(SimulationController simController, JSONObject json) {
        this(   new Vector2((JSONObject) json.get("pos")),
                32,
                32,
                simController);
    }

    @Override
    public void run() {
        getOutPins().get(0).setStatus(getInPins().get(0).getStatus());
    }
}
