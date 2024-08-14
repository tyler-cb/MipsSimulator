package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Simulation.Circuitry.Part;
import Structures.Vector2;
import org.json.simple.JSONObject;

import java.util.List;

public class Not extends Part {
    public Not(Vector2 pos, SimulationController simController) {
        super(pos, 2, 1, "Not", List.of(1), List.of(1), simController);
    }

    public Not(SimulationController simController, JSONObject json) throws Exception {
        this(new Vector2((JSONObject) json.get("pos")), simController);
    }

    @Override
    public void run() {
        if (getInPins().get(0).getStatus() == 1) {
            getOutPins().get(0).setStatus(0);
        }
        else if (getInPins().get(0).getStatus() == 0) {
            getOutPins().get(0).setStatus(1);
        }
    }
}
