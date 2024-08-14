package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Simulation.Circuitry.Part;
import Structures.Vector2;
import org.json.simple.JSONObject;

import java.util.List;

public class Or extends Part {
    public Or(Vector2 pos, SimulationController simController) {
        super(pos, 2, 2, "Or", List.of(1,1), List.of(1), simController);
        this.getFlags().add("runMoreThanOnce");
    }

    public Or(SimulationController simController, JSONObject json) throws Exception {
        this(new Vector2((JSONObject) json.get("pos")), simController);
    }

    @Override
    public void run() {
        if (getInPins().get(0).getStatus() == 1 || getInPins().get(1).getStatus() == 1) {
            getOutPins().get(0).setStatus(1);
        }
        else {
            getOutPins().get(0).setStatus(0);
        }
    }
}
