package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Simulation.Circuitry.InPin;
import Simulation.Circuitry.Part;
import Structures.Vector2;
import org.json.simple.JSONObject;

import java.util.List;

public class ALU extends Part {
    private InPin controlPin;
    public ALU(Vector2 pos, SimulationController simController) {
        super(pos, 2, 4, "ALU", List.of(32, 32), List.of(32, 1), simController);
        controlPin = new InPin(this, new Vector2(1f, 0), 4, true);
        getFlags().add("lowPriority");
        addPin(controlPin);
    }

    public ALU(SimulationController simController, JSONObject json) throws Exception {
        this(new Vector2((JSONObject) json.get("pos")),
                simController);
    }

    @Override
    public void run() {
        int instruction = controlPin.getStatus();
        int in1 = inPins.get(0).getStatus();
        int in2 = inPins.get(1).getStatus();
        switch (instruction) {
            case 0: // And
                outPins.get(0).setStatus(in1 & in2);
                break;
            case 1: // Or
                outPins.get(0).setStatus(in1 | in2);
                break;
            case 2: // Add
                outPins.get(0).setStatus(in1 + in2);
                break;
            case 6: // Subtract
                outPins.get(0).setStatus(in1 - in2);
                break;
        }
        // Set zero pin (1 if output is zero, 0 if not)
        if (outPins.get(0).getStatus() == 0) {
            getOutPins().get(1).setStatus(1);
        }
        else {
            getOutPins().get(1).setStatus(0);
        }
    }

    public InPin getControlPin() {
        return controlPin;
    }
}
