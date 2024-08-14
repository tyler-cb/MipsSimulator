package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Simulation.Circuitry.InPin;
import Simulation.Circuitry.OutPin;
import Simulation.Circuitry.Part;
import Structures.Vector2;
import org.json.simple.JSONObject;

import java.util.List;

public class ALUControl extends Part {
    private InPin controlPin;
    private InPin functPin;
    private OutPin outPin;
    public ALUControl(Vector2 pos, SimulationController simController) {
        super(pos, 2, 2, "ALU Control", List.of(16), List.of(4), simController);
        controlPin = new InPin(this, new Vector2(1, 0), 3, true);
        addPin(controlPin);
        functPin = getInPins().get(0);
        outPin = getOutPins().get(0);
    }

    public ALUControl(SimulationController simController, JSONObject json) throws Exception {
        this(new Vector2((JSONObject) json.get("pos")),
                simController);
    }

    @Override
    public void run() {
        // R-Format
        if (controlPin.getStatus() == 0b10) {
            switch (functPin.getStatus()) {
                case 32: // add
                    outPin.setStatus(2);
                    break;
                case 34: // subtract
                    outPin.setStatus(6);
                    break;
                case 36: // and
                    outPin.setStatus(0);
                    break;
                case 37: // or
                    outPin.setStatus(1);
                    break;
            }
        }

        switch (controlPin.getStatus()) {
            case 0: // addi, lw, sw
                outPin.setStatus(2);
                break;
            case 1: // beq, bne
                outPin.setStatus(6);
                break;
            case 3: // andi
                outPin.setStatus(0);
                break;
            case 4: // ori
                outPin.setStatus(1);
        }

    }

    public InPin getControlPin() {
        return controlPin;
    }
}
