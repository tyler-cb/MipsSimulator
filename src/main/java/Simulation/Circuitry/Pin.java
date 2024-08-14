package Simulation.Circuitry;

import Interface.CircuitPanel;
import Simulation.Helper;
import Structures.Vector2;

import java.awt.*;

public abstract class Pin extends CircuitObject {
    private Part parent;
    private int id;
    private int status;

    // Determines the max value the pin should accept; Max value = (2^bits) - 1.
    private int bits;

    // Relative to parent's coordinate
    // ie if the parent is at (1,1) and the pin pos is (1,1), then the pins is at (2,2) in world space
    private Vector2 pos;
    private WireGroup wireGroup;
    private String label;

    public Pin(Integer id, Part parent, Vector2 pos, int bits) {
        this.id = id;
        this.parent = parent;
        this.pos = pos;

        if (-1 > bits || bits > 32) {
            throw new IllegalArgumentException();
        }
        this.bits = bits;
    }

    @Override
    public void paint(Graphics2D g2d, CircuitPanel panel) {

        int radius = (int) (0.5 * panel.getZoom());

        Vector2 screenVector = Helper.getScreenCoordinates(
                pos.add(parent.getPos()), panel
        );
        g2d.setColor(Color.ORANGE);
        g2d.fillOval((int) ( screenVector.x - 0.5*radius), (int) (screenVector.y - 0.5*radius), radius, radius);

        int fontHeight = g2d.getFontMetrics().getHeight();
        int fontWidth = g2d.getFontMetrics().stringWidth(String.valueOf(getStatus()));

        Vector2 fontVector = Helper.getScreenCoordinates(pos.add(parent.getPos()), panel);
        fontVector.x -= fontWidth/2f;
        fontVector.y += fontHeight/4f;
        g2d.setColor(Color.BLACK);
        g2d.drawString(Integer.toString(getStatus()), fontVector.x, fontVector.y);
    }

    @Override
    public void leftClicked() {

    }

    @Override
    public void rightClicked() {}

    public Part getParent() {
        return parent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {

        return status;
    }

    public void setStatus(int status) {
        if (Math.pow(2, bits) - 1 < status || status < -Math.pow(2, bits) ) {
            throw new IllegalArgumentException("Status of pin outside of allowed values");
        }
        this.status = status;
    }

    public int getBits() {
        return bits;
    }

    public Vector2 getPos() {
        // Returns position in simulation rather than position relative to parent
        Vector2 parentPos = parent.getPos();
        return new Vector2(parentPos.x+pos.x, parentPos.y+pos.y);
    }

    public WireGroup getWireGroup() {
        return wireGroup;
    }

    public void setWireGroup(WireGroup wireGroup) {
        this.wireGroup = wireGroup;
    }
}
