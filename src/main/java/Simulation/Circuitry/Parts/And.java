package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Interface.CircuitPanel;
import Simulation.Circuitry.Part;
import Simulation.Circuitry.Pin;
import Simulation.Helper;
import Structures.Vector2;
import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.List;

public class And extends Part {
    public And(Vector2 pos, SimulationController simController) {
        super(pos, 2, 2, "AND", List.of(1, 1), List.of(1), simController);
        this.getFlags().add("runMoreThanOnce");
    }

    public And(SimulationController simController, JSONObject json) throws Exception {
        this(new Vector2((JSONObject) json.get("pos")), simController);
    }

    @Override
    public void run() {
        if (inPins.get(0).getStatus() == 1 && inPins.get(1).getStatus() == 1) {
            outPins.get(0).setStatus(1);
        }
        else {
            outPins.get(0).setStatus(0);
        }
    }

    @Override
    public void paint(Graphics2D g2d, CircuitPanel panel) {
        Vector2 screenVector = Helper.getScreenCoordinates(new Vector2(getPos().x, getPos().y+getHeight()), panel);

        g2d.setColor(Part.color);
        g2d.fill(new Arc2D.Double(screenVector.x, screenVector.y, getWidth()*panel.getZoom(), getHeight()*panel.getZoom(), 90, -180, Arc2D.OPEN));
        g2d.fillRect((int) screenVector.x, (int) screenVector.y, (int) (getWidth()*panel.getZoom()/1.95), (int) (getHeight()*panel.getZoom()));

        int fontHeight = g2d.getFontMetrics().getHeight();
        int fontWidth = g2d.getFontMetrics().stringWidth(getLabel());
        Vector2 fontVector = Helper.getScreenCoordinates(new Vector2(getPos().x+getWidth()/2f, getPos().y+getHeight()/2f), panel);
        fontVector.x -= fontWidth/2f;
        fontVector.y += fontHeight/4f;
        g2d.setColor(Color.BLACK);
        g2d.drawString(getLabel(), fontVector.x, fontVector.y);
        for (Pin pin : inPins) {
            pin.paint(g2d, panel);
        }
        for (Pin pin : outPins) {
            pin.paint(g2d, panel);
        }
    }
}
