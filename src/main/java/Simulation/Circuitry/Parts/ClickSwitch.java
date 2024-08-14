package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Simulation.Circuitry.OutPin;
import Simulation.Circuitry.Part;
import Structures.Vector2;
import org.json.simple.JSONObject;

public class ClickSwitch extends Part {
    private OutPin out;
    public ClickSwitch(Vector2 pos, SimulationController simController) {
        super(pos, 2, 1, "SWITCH", simController);
        out = new OutPin(0, this, new Vector2(2, 0.5f), 2);
        addPin(out);
    }

    public ClickSwitch(SimulationController simController, JSONObject json) throws Exception {
        this(new Vector2((JSONObject) json.get("pos")),
                simController);
    }

    @Override
    public void leftClicked() {
        if (out.getStatus() == 1) {
            out.setStatus(0);
        }
        else {
            out.setStatus(1);
        }
    }

    @Override
    public void run() {

    }
}
