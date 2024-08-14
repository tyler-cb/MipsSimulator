package Controllers;

import Interface.CircuitPanel;
import Interface.RegistersPanel;
import Simulation.Circuitry.*;
import Simulation.Circuitry.Parts.*;
import Simulation.Helper;
import Structures.Exceptions.JSONException;
import Structures.Vector2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;

public class SimulationController {

    // Partlist maps partId to part
    private PartList partList;
    private CircuitPanel panel;

    private WireController wireController;

    private Queue<Part> runQueue;

    private Part start;

    private ArrayList<Part> runInCycle;

    private Registers registers;
    private RegistersPanel registersPanel;

    public SimulationController(CircuitPanel panel, WireController wireController) {
        partList = new PartList();
        runQueue = new LinkedList<>();
        runInCycle = new ArrayList<>();

        this.panel = panel;
        this.wireController = wireController;
    }

    public void appendRunQueue(Part part) {
        if (!runQueue.contains(part)) {
            runQueue.add(part);
        }
    }

    public void step() {
        if (runQueue.isEmpty()) {
            runQueue.add(start);
        }
        Part currPart = null;
        while (currPart == null || (runInCycle.contains(currPart) && !currPart.getFlags().contains("runMoreThanOnce"))) {
            for (Part p : runQueue) {
                if (p.getFlags().contains("priority")) {
                    currPart = p;
                    runQueue.remove(p);
                    break;
                }
            }
            // If no priority parts were found in queue, get the next item.
            if (currPart == null || runInCycle.contains(currPart)) {
                currPart = runQueue.poll();
            }
            if (currPart != null && currPart.getFlags().contains("lowPriority") && !runQueue.isEmpty()) {
                Boolean prioCheck = false;
                for (Part p : runQueue) {
                    if (!(p.getFlags().contains("lowPriority"))) {
                        prioCheck = true;
                    }
                }
                if (prioCheck) { // If there was a non-low-prio part found
                    appendRunQueue(currPart);
                    continue;
                }
            }
            if (runQueue.isEmpty() && currPart == null) {
                newCycle();
                break;
            }
        }
        // Only run the part if it hasn't been run this cycle OR if it is allowed to run more than once.
        if (currPart != null && (!runInCycle.contains(currPart) || currPart.getFlags().contains("runMoreThanOnce"))) {
            currPart.run();
            runInCycle.add(currPart);
        }
    }

    public void cycle() {
        if (runQueue.isEmpty()) {
            runQueue.add(start);
        }
        Part currPart;
        while (!runQueue.isEmpty()) {
            currPart = null;
            for (Part p: runQueue) { // Check for priority parts
                if (p.getFlags().contains("priority")) {
                    currPart = p;
                    runQueue.remove(p);
                    break;
                }
            }
            // If no priority parts were found in queue, get the next item.
            if (currPart == null) {
                currPart = runQueue.poll();
            }
            if (currPart.getFlags().contains("lowPriority") && !runQueue.isEmpty()) {
                Boolean prioCheck = false;
                for (Part p : runQueue) {
                    if (!(p.getFlags().contains("lowPriority"))) {
                        prioCheck = true;
                    }
                }
                if (prioCheck) { // If there was a non-low-priority part found
                    appendRunQueue(currPart);
                    continue;
                }
            }
            // Only run the part if it hasn't been run this cycle OR if it is allowed to run more than once.
            if (!runInCycle.contains(currPart) || currPart.getFlags().contains("runMoreThanOnce")) {
                currPart.run();
                runInCycle.add(currPart);
                if (currPart == registers) {
                    registersPanel.registerDataChange(registers.getRegisters());
                }
            }
        }
        newCycle();
    }

    private void newCycle() {
        for (Map.Entry<String, Part> entry: partList.entrySet()) {
            Part p = entry.getValue();
            for (InPin in: p.getInPins()) {
                in.setChangedInCurrentCycle(false);
            }
        }
        runInCycle.clear();
    }

    public HashMap<String, Part> getPartList() {
        return partList;
    }

    public void setPartList(PartList partList) {
        this.partList = partList;
    }

    public void addPart(String partId, Part part) {
        partList.put(partId, part);
    }

    public void loadFile(File file) throws JSONException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader(file);
        JSONObject json;
        json = (JSONObject) parser.parse(reader);

        JSONObject parts = (JSONObject) json.get("parts");
        PartList newPartList = new PartList();
        for (Object partId: parts.keySet()) {
            Part newPart = loadNewPart((String) partId, (JSONObject) parts.get(partId));
            newPartList.put((String) partId, newPart);
        }

        WireController newWireController = new WireController(panel);
        JSONArray wires = (JSONArray) json.get("wires");
        if (wires != null) {
            for (Object o : wires) {
                if (!(o instanceof JSONObject)) {
                    throw new JSONException("Wires failed to parse");
                }
                loadWire((JSONObject) o, newPartList, newWireController);
            }
        }
        partList = newPartList;
        wireController = newWireController;
        panel.setWireController(newWireController);

        try {
            String startId = ((String) json.get("start")).toLowerCase();
            Part startPart = partList.get(startId);
            if (startPart == null) {
                throw new JSONException("Cannot find starting part " + startId);
            }
            this.start = startPart;
        } catch (Exception e) {
            throw new JSONException("Could not find starting part");
        }

        panel.repaint();
    }

    private Part loadNewPart(String id, JSONObject contents) throws JSONException {
        Class<?> partClass;
        try {
            partClass = Class.forName("Simulation.Circuitry.Parts." + contents.get("type"));
        } catch (ClassNotFoundException e) {
            throw new JSONException("Cannot find part type " + contents.get("type"), e);
        }
        if (!Part.class.isAssignableFrom(partClass)) {
            throw new JSONException("Cannot find part type: " + contents.get("type").toString());
        }
        Constructor<?> partConstructor;
        try {
            partConstructor = partClass.getConstructor(SimulationController.class, JSONObject.class);
        } catch (NoSuchMethodException e) {
            throw new JSONException("Cannot instantiate part type: " + partClass.getSimpleName() + " (Missing constructor)", e);
        }
        Part newPart;
        try {
            newPart = (Part) partConstructor.newInstance(this, contents);
        } catch (Exception e) {
            throw new JSONException("Failed to instantiate part: " + id, e);
        }
        try {
            JSONArray flags = (JSONArray) contents.get("flags");
            if (flags != null) {
                for (Object j : flags) {
                    newPart.getFlags().add((String) j);
                }
            }
        } catch (Exception e) {
            throw new JSONException("Could not parse flags for part " + id);
        }
        if (partClass == Registers.class) {
            registers = (Registers) newPart;
            getRegistersPanel().display(registers);
        }
        return newPart;
    }

    private Wire loadWire(JSONObject wireJson, PartList partList, WireController wireController) throws JSONException {
        JSONObject start = (JSONObject) wireJson.get("start");
        JSONObject end = (JSONObject) wireJson.get("end");
        if (start == null) {
            throw new JSONException("Wire doesn't include start part");
        }
        if (end == null) {
            throw new JSONException("Wire doesn't include end part");
        }
        JSONArray waypointsJson = (JSONArray) wireJson.get("waypoints");
        Boolean isControl = (Boolean) wireJson.get("isControl");

        OutPin startPin;
        InPin endPin;
        try {
            startPin = partList.get(start.get("part")).getOutPins().get(Helper.parseJsonInteger(start.get("id")));
            endPin = partList.get(end.get("part")).getInPins().get(Helper.parseJsonInteger(end.get("id")));
        } catch (NullPointerException e) {
            throw new JSONException("Could not find pins for wire", e);
        }

        if (startPin == null) {
            throw new JSONException("Could not find start pin for wire");
        }
        if (endPin == null) {
            throw new JSONException("Could not find end pin for wire");
        }

        if (waypointsJson != null) {
            ArrayList<Vector2> waypoints = new ArrayList<>();
            for (Object wp : waypointsJson) {
                if (!(wp instanceof JSONObject)) {
                    throw new JSONException("Failed to parse WireGroup from JSON");
                }
                try {
                    waypoints.add(new Vector2((JSONObject) wp));
                } catch (ClassCastException e) {
                    throw new JSONException("Failed to parse waypoints from JSON", e);
                }
            }
            return wireController.newWire(startPin, endPin, isControl, waypoints);
        }
        return wireController.newWire(startPin, endPin, isControl);
    }

    public void saveFile(File file) throws IOException, JSONException {
        FileWriter fr = new FileWriter(file, false);
        JSONObject json = new JSONObject();

        JSONObject parts = new JSONObject();
        for (Map.Entry<String, Part> entry : partList.entrySet()) {
            JSONObject partJson = entry.getValue().toJSON();
            parts.put(entry.getKey(), partJson);
        }
        json.put("parts", parts);

        JSONArray wiresJson = new JSONArray();
        for (WireGroup wg : wireController.getWireGroupList()) {
            for (Object wireJson : wg.toJson()) {
                if (!(wireJson instanceof JSONObject)) {
                    throw new JSONException("Wire group failed to convert to json");
                }
                wiresJson.add(wireJson);
            }
        }
        json.put("wires", wiresJson);
        json.put("start", start.getId());

        fr.write(json.toJSONString());
        fr.close();
    }

    public Registers getRegisters() {
        return registers;
    }

    public RegistersPanel getRegistersPanel() {
        return registersPanel;
    }

    public void setRegistersPanel(RegistersPanel registersPanel) {
        this.registersPanel = registersPanel;
    }
}


