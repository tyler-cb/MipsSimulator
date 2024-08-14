package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Simulation.Circuitry.Part;
import Simulation.Helper;
import Structures.Vector2;
import org.json.simple.JSONObject;

import java.util.List;

public class Shift extends Part {
    // Negative shift is left, positive is right. Shift should be non-zero.
    private final int shift;
    public Shift(Vector2 pos, int shift, SimulationController simController) throws IllegalArgumentException {
        super(pos, 2, 2, "Shift", List.of(32), List.of(32), simController);

        if (shift == 0) {
            throw new IllegalArgumentException("Shift amount cannot be zero.");
        } else if (shift < 0) {
            setLabel("Shift Left " + Math.abs(shift));
        }
        else {
            setLabel("Shift Right " + shift);
        }
        this.shift = shift;
    }

    public Shift(SimulationController simController, JSONObject json) throws Exception {
        this(new Vector2((JSONObject) json.get("pos")), Helper.parseJsonInteger(json.get("shift")), simController);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put("shift", shift);
        return json;
    }

    @Override
    public void run() {
        int status = getInPins().get(0).getStatus();
        int output;
        if (shift < 0) {
            // Left
            output = status << Math.abs(shift);
            output &= (1 << (32 - Math.abs(shift))) - 1;
        }
        else {
            // Right
            output = status >> shift;
        }
        getOutPins().get(0).setStatus(output);
    }
}
