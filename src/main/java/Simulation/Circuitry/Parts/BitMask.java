package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Simulation.Circuitry.Part;
import Structures.Vector2;
import org.json.simple.JSONObject;

import java.util.List;

public class BitMask extends Part {
    private int start;
    private int end;
    private int inWidth;
    public BitMask(Vector2 pos, Integer inputWidth, Integer start, Integer end, SimulationController simController) {
        super(pos, 3, 1, "Mask" + start.toString() + "-" + end.toString(), List.of(inputWidth), List.of(end + 1 - start), simController);
        this.start = start;
        this.end = end;
        this.inWidth = inputWidth;
    }

    public BitMask(SimulationController simController, JSONObject json) throws Exception {
        this(new Vector2((JSONObject) json.get("pos")),
                ((Long) json.get("inWidth")).intValue(),
                ((Long) json.get("start")).intValue(),
                ((Long) json.get("end")).intValue(),
                simController);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put("inWidth", getInWidth());
        json.put("start", getStart());
        json.put("end", getEnd());
        return json;
    }

    @Override
    public void run() {
        String status = Integer.toBinaryString(getInPins().get(0).getStatus());
        status = String.format("%"+(inWidth)+"s", status).replaceAll(" ", "0");
        int output = Integer.parseInt(status.substring(inWidth-end-1, inWidth-start), 2);
        getOutPins().get(0).setStatus(output);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getInWidth() {
        return inWidth;
    }
}
