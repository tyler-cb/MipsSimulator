package Simulation.Circuitry;

import Structures.Vector2;

public class OutPin extends Pin {
    public OutPin(Integer id, Part parent, Vector2 pos, int bits) {
        super(id, parent, pos, bits);
    }

    public void setStatus(int status) {
        super.setStatus(status);
        if (getWireGroup() != null) {
            getWireGroup().setStatus(status);
        }
    }
}
