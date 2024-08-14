package Simulation.Circuitry;

import Controllers.SimulationController;
import Interface.CircuitPanel;
import Simulation.Helper;
import Structures.Vector2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Part extends CircuitObject {
    private String id;
    private Vector2 pos;
    private int width;
    private int height;
    protected ArrayList<InPin> inPins;
    protected ArrayList<OutPin> outPins;
    private String label;
    public final static Color color = new Color(134, 226, 255);

    private SimulationController simController;

    protected ArrayList<String> flags;

    public Part(Vector2 pos, int width, int height, String label, List<Integer> inPinWidths, List<Integer> outPinWidths, SimulationController simController) {
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.label = label;
        inPins = new ArrayList<>();
        outPins = new ArrayList<>();
        this.simController = simController;

        initializeInPins(inPinWidths);
        initializeOutPins(outPinWidths);

        this.flags = new ArrayList<>();
    }

    public Part(Vector2 pos, int width, int height, String label, SimulationController simController) {
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.label = label;
        inPins = new ArrayList<>();
        outPins = new ArrayList<>();
        this.simController = simController;
        this.flags = new ArrayList<>();
    }

    public Part() { }

    protected void initializeInPins(List<Integer> widths) {
        if (inPins == null) {
            inPins = new ArrayList<>();
        }
        for (int i = 0; i < widths.size(); i++) {
            float yPos = (i+1)*(getHeight()/(widths.size()+1f));
            addPin(new InPin(this, new Vector2(0f, yPos), widths.get(i) ));
        }
    }

    protected void initializeOutPins(List<Integer> widths) {
        if (outPins == null) {
            outPins = new ArrayList<>();
        }
        for (int i = 0; i < widths.size(); i++) {
            float yPos = (i+1)*(getHeight()/(widths.size()+1f));
            addPin(new OutPin(i, this, new Vector2(getWidth(), yPos), widths.get(i) ));
        }
    }

    public List<Integer> getInWidths() {
        List<Integer> list = new ArrayList<>();
        for (Pin p : getInPins()) {
            list.add(p.getBits());
        }
        return list;
    }

    public List<Integer> getOutWidths() {
        List<Integer> list = new ArrayList<>();
        for (Pin p : getOutPins()) {
            list.add(p.getBits());
        }
        return list;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("pos", getPos().toJson());
        json.put("type", getClass().getSimpleName());
        JSONArray jsonFlags = new JSONArray();
        for (String flag : getFlags()) {
            jsonFlags.add(flag);
        }
        json.put("flags", jsonFlags);
        return json;
    }

    @Override
    public void paint(Graphics2D g2d, CircuitPanel panel) {
        Vector2 screenVector = Helper.getScreenCoordinates(new Vector2(pos.x, pos.y+height), panel);

        g2d.setColor(color);
        g2d.fillRect((int) screenVector.x, (int) screenVector.y, (int) (width*panel.getZoom()), (int) (height*panel.getZoom()));

        int fontHeight = g2d.getFontMetrics().getHeight();
        int fontWidth = g2d.getFontMetrics().stringWidth(label);
        Vector2 fontVector = Helper.getScreenCoordinates(new Vector2(pos.x+width/2f, pos.y+height/2f), panel);
        fontVector.x -= fontWidth/2f;
        fontVector.y += fontHeight/4f;
        g2d.setColor(Color.BLACK);
        g2d.drawString(label, fontVector.x, fontVector.y);
        for (Pin pin : inPins) {
            pin.paint(g2d, panel);
        }
        for (Pin pin : outPins) {
            pin.paint(g2d, panel);
        }
    }

    @Override
    public void leftClicked() {
        System.out.println("Clicked "+ getId());
    }

    @Override
    public void rightClicked() {};

    public abstract void run();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void addPin(InPin pin) {
        inPins.add(pin);
    };

    public void addPin(OutPin pin) {
        outPins.add(pin);
    }

    public ArrayList<InPin> getInPins() {
        return inPins;
    }

    public void setInPins(ArrayList<InPin> inPins) {
        this.inPins = inPins;
    }

    public ArrayList<OutPin> getOutPins() {
        return outPins;
    }

    public void setOutPins(ArrayList<OutPin> outPins) {
        this.outPins = outPins;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public SimulationController getSimController() {
        return simController;
    }

    public void setSimController(SimulationController simController) {
        this.simController = simController;
    }

    public ArrayList<String> getFlags() {
        return flags;
    }
}
