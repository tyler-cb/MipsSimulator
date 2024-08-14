package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Simulation.Circuitry.InPin;
import Simulation.Circuitry.Part;
import Simulation.Helper;
import Structures.Vector2;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Multiplexer extends Part {
    private InPin controlSignalPin;
    private boolean increasing;
    private boolean controlSignalTop;
    public Multiplexer(Vector2 pos, Integer width, boolean controlSignalTop, boolean increasing, SimulationController simController) {
        super(pos, 1, 3, "Mux", List.of(width, width), List.of(width), simController);
        if (controlSignalTop) {
            controlSignalPin = new InPin(this, new Vector2(0.5f, 3), 1, false);
        } else {
            controlSignalPin = new InPin(this, new Vector2(0.5f, 0), 1, false);
        }
        // Alters the orientation of the multiplexer, if true, 0 is bottom input and it increases, false for 0 at top input.
        this.increasing = increasing;
        this.controlSignalTop = controlSignalTop;

        addPin(controlSignalPin);
    }

    public Multiplexer(SimulationController simController, JSONObject json) throws Exception {
        this(new Vector2((JSONObject) json.get("pos")), 32,
                ((boolean) json.get("controlSignalTop")), ( (boolean) json.get("increasing")), simController);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put("controlSignalTop", controlSignalTop);
        json.put("increasing", increasing);
        return json;
    }

    @Override
    public void run() {
        int selectedStatus;
        if (!increasing) {
            selectedStatus = inPins.get(1 - controlSignalPin.getStatus()).getStatus();
        } else {
            selectedStatus = inPins.get(controlSignalPin.getStatus()).getStatus();
        }
        outPins.get(0).setStatus(selectedStatus);
    }

    public InPin getControlSignalPin() {
        return controlSignalPin;
    }


}
