package Simulation.Circuitry;

import Interface.CircuitPanel;
import Simulation.Helper;
import Structures.Vector2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Wire extends CircuitObject {

    private Integer bits;
    private Integer status;
    private Vector2 start;
    private OutPin startPin;
    private InPin endPin;
    private Vector2 end;
    private Color colorOff = new Color(255, 46, 46);
    private Color colorOn = new Color(0, 255, 13);
    private boolean isControl;

    private ArrayList<Vector2> waypoints;

    public Wire(Integer bits, Vector2 start, OutPin startPin, Vector2 end, InPin endPin, boolean isControl) {

        if (-1 > bits || bits > 32) {
            throw new IllegalArgumentException();
        }
        this.bits = bits;
        this.start = start;
        this.startPin = startPin;
        this.end = end;
        this.endPin = endPin;
        this.status = 0;
        this.isControl = isControl;

        if (isControl) {
            colorOff = new Color(87, 146, 255);
            colorOn = new Color(0, 87, 255);
        }
    }

    @Override
    public void paint(Graphics2D g2d, CircuitPanel panel) {
        if (status == 0) {
            g2d.setColor(colorOff);
        }
        else {
            g2d.setColor(colorOn);
        }
        ArrayList<Vector2> points = new ArrayList<>();
        points.add(Helper.getScreenCoordinates(start, panel));
        if (waypoints != null) {
            for (Vector2 waypoint : waypoints) {
                points.add(Helper.getScreenCoordinates(waypoint, panel));
            }
        }
        points.add(Helper.getScreenCoordinates(end, panel));
        for (int i = 0; i < points.size()-1; i++) {
            Vector2 screenVector1 = points.get(i);
            Vector2 screenVector2 = points.get(i+1);

            g2d.setStroke(new BasicStroke(3));
            g2d.draw(new Line2D.Float(screenVector1.x, screenVector1.y, screenVector2.x, screenVector2.y));
        }

    }

    @Override
    public void leftClicked() {
        System.out.println(getStatus());
    }

    @Override
    public void rightClicked() {

    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        if (Math.pow(2, bits) - 1 < status || status < -Math.pow(2, bits) - 1) {
            throw new IllegalArgumentException("Status of wire outside of allowed values");
        }
        this.status = status;
    }

    public ArrayList<Vector2> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(ArrayList<Vector2> waypoints) {
        this.waypoints = waypoints;
    }

    public Integer getBits() {
        return bits;
    }

    public void setBits(Integer bits) {
        this.bits = bits;
    }

    public Vector2 getStart() {
        return start;
    }

    public void setStart(Vector2 start) {
        this.start = start;
    }

    public Vector2 getEnd() {
        return end;
    }

    public void setEnd(Vector2 end) {
        this.end = end;
    }

    public Color getColorOff() {
        return colorOff;
    }

    public void setColorOff(Color colorOff) {
        this.colorOff = colorOff;
    }

    public Color getColorOn() {
        return colorOn;
    }

    public void setColorOn(Color colorOn) {
        this.colorOn = colorOn;
    }

    public boolean isControl() {
        return isControl;
    }

    public void setControl(boolean control) {
        isControl = control;
    }

    public OutPin getStartPin() {
        return startPin;
    }

    public void setStartPin(OutPin startPin) {
        this.startPin = startPin;
    }

    public InPin getEndPin() {
        return endPin;
    }

    public void setEndPin(InPin endPin) {
        this.endPin = endPin;
    }
}
