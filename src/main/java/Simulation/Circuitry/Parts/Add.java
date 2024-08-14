package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Simulation.Circuitry.InPin;
import Simulation.Circuitry.Part;
import Simulation.Helper;
import Structures.Vector2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class Add extends Part {
    public Add(Vector2 pos, List<Integer> inPinWidths, SimulationController simController) {
        super(pos, 3, 2, "Add", inPinWidths, List.of(32), simController);
    }

    public Add(SimulationController simController, JSONObject json) throws Exception {
        this(   new Vector2((JSONObject) json.get("pos")),
                Helper.jsonToIntList((JSONArray) json.get("inWidths")),
                simController);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        JSONArray inWidths = new JSONArray();
        inWidths.addAll(getInWidths());
        json.put("inWidths", inWidths);
        return json;
    }

    @Override
    public void run() {
        int output = 0;
        for (InPin pin : getInPins()) {
            output += pin.getStatus();
        }
        getOutPins().get(0).setStatus(output);
    }
}
