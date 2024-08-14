package Controllers;

import Interface.CircuitPanel;
import Simulation.Circuitry.InPin;
import Simulation.Circuitry.OutPin;
import Simulation.Circuitry.Wire;
import Simulation.Circuitry.WireGroup;
import Structures.Vector2;

import java.util.ArrayList;
import java.util.List;

public class WireController {
    private ArrayList<WireGroup> wireGroupList;
    private CircuitPanel panel;

    public WireController(CircuitPanel panel) {
        wireGroupList = new ArrayList<>();
        this.panel = panel;
    }

    public Wire newWire(OutPin out, InPin in, boolean isControl) {
        Wire wire = new Wire(out.getBits(), out.getPos(), out, in.getPos(), in, isControl);
        if (out.getWireGroup() != null) {
            out.getWireGroup().addWire(wire);
            in.setWireGroup(out.getWireGroup());
            out.getWireGroup().addInPin(in);
        } else {
            WireGroup wg = new WireGroup(new ArrayList<>(List.of(wire)), out, new ArrayList<>(List.of(in)),
                    out.getStatus(), out.getBits(), isControl);
            out.setWireGroup(wg);
            in.setWireGroup(wg);
            wireGroupList.add(wg);
        }
        ArrayList<Vector2> wireWaypoints = new ArrayList<>();
        float halfwayX = (out.getPos().x + in.getPos().x)/2f;
        wireWaypoints.add(new Vector2(halfwayX, out.getPos().y));
        wireWaypoints.add(new Vector2(halfwayX, in.getPos().y));
        wire.setWaypoints(wireWaypoints);
        return wire;
    }

    public Wire newWire(OutPin out, InPin in) {
        Wire wire = newWire(out, in, false);
        return wire;
    }

    public Wire newWire(OutPin out, InPin in, boolean isControl, ArrayList<Vector2> waypoints) {
        Wire wire = newWire(out, in, isControl);
        wire.setWaypoints(waypoints);
        return wire;
    }

    public ArrayList<WireGroup> getWireGroupList() {
        return wireGroupList;
    }
}
