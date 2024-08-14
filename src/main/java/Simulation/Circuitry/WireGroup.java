package Simulation.Circuitry;

import Interface.CircuitPanel;
import Structures.Vector2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;

public class WireGroup {
    private ArrayList<Wire> wires;

    private OutPin outPin;
    private ArrayList<InPin> inPins;
    private Integer status;
    private Integer bits;
    private boolean isControl;

    public WireGroup(ArrayList<Wire> wires, OutPin outPin, ArrayList<InPin> inPins, Integer status, Integer bits, boolean isControl) {
        this.wires = wires;
        this.outPin = outPin;
        this.inPins = inPins;
        this.status = status;
        this.bits = bits;
        this.isControl = isControl;
    }

    public WireGroup(ArrayList<Wire> wires, OutPin outPin, ArrayList<InPin> inPins, Integer status, Integer bits) {
        this(wires, outPin, inPins, status, bits, false);
    }

    public void paint(Graphics2D g, CircuitPanel panel) {
        for (Wire wire : wires) {
            wire.paint(g, panel);
        }
    }

    public JSONArray toJson() {
        // Returns all the group's wires jsons in a JSONArray
        JSONArray json = new JSONArray();

        for (Wire wire: wires) {
            JSONObject wireJson = new JSONObject();
            wireJson.put("bits", wire.getBits());
            wireJson.put("isControl", wire.isControl());
            if (wire.getWaypoints() != null) {
                JSONArray wpJson = new JSONArray();
                for (Vector2 wp: wire.getWaypoints()) {
                    wpJson.add(wp.toJson());
                }
                wireJson.put("waypoints", wpJson);
            }

            JSONObject startJson = new JSONObject();
            startJson.put("part", wire.getStartPin().getParent().getId());
            startJson.put("id", wire.getStartPin().getId());
            wireJson.put("start", startJson);

            JSONObject endJson = new JSONObject();
            endJson.put("part", wire.getEndPin().getParent().getId());
            endJson.put("id", wire.getEndPin().getId());
            wireJson.put("end", endJson);

            json.add(wireJson);
        }

        return json;
    }

    public ArrayList<Wire> getWires() {
        return wires;
    }

    public void setWires(ArrayList<Wire> wires) {
        this.wires = wires;
    }

    public void addWire(Wire wire) {
        wires.add(wire);
    }

    public void addInPin(InPin pin) { inPins.add(pin); }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
        for(Wire wire : wires) {
            wire.setStatus(status);
        }
        for(InPin inPin: inPins) {
            inPin.setStatus(status);
        }
    }
}
