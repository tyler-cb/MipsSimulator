package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Simulation.Circuitry.Part;
import Simulation.Helper;
import Structures.Vector2;
import org.json.simple.JSONObject;

import java.util.List;

public class AddConstant extends Part {
    private int addNum;
    private int inWidth;

    public AddConstant(Vector2 pos, Integer addNum, Integer inWidth, SimulationController simController) {
        super(pos, 2, 2, "Add"+addNum, List.of(inWidth), List.of(inWidth), simController);
        this.addNum = addNum;
        this.inWidth = inWidth;
    }

    public AddConstant(SimulationController simController, JSONObject json) throws Exception {
        this(new Vector2((JSONObject) json.get("pos")),
                ((Long) json.get("addNum")).intValue(),
                ((Long) json.get("inWidth")).intValue(),
                simController
                );
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put("addNum", getAddNum());
        json.put("inWidth", getInWidth());
        return json;
    }

    @Override
    public void run() {
        outPins.get(0).setStatus(inPins.get(0).getStatus() + addNum);
    }

    public int getAddNum() {
        return addNum;
    }

    public int getInWidth() {
        return inWidth;
    }
}
