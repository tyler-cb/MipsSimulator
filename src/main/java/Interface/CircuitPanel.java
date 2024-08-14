package Interface;

import Controllers.SimulationController;
import Controllers.WireController;
import Simulation.Circuitry.Part;
import Simulation.Circuitry.Wire;
import Simulation.Circuitry.WireGroup;
import Simulation.Helper;
import Structures.Vector2;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class CircuitPanel extends JPanel {
    private float zoom; // Number of pixels per sim grid. (Default 20px to 1 grid)
    public Vector2 cameraPos;
    private SimulationController simController;
    private WireController wireController;


    public CircuitPanel() {
        cameraPos = new Vector2(0, 0);
        zoom = 20;

        wireController = new WireController(this);
        simController = new SimulationController(this, wireController);

        this.setSize(new Dimension(500, 500));

    }

    public void paint (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Use antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(new Font(g2d.getFont().getFontName(), Font.PLAIN, 10));

        paintGrid(g2d);
        for (WireGroup wireGroup : wireController.getWireGroupList()) {
            wireGroup.paint(g2d, this);
        }
        for (Map.Entry<String, Part> entry : simController.getPartList().entrySet()) {
            Part part = entry.getValue();
            part.paint(g2d, this);
        }
    }

    private void paintGrid(Graphics2D g2d) {
        int gridSize = 1;

        float bottomLeftX = cameraPos.x - (getWidth()/ (2f*zoom));
        float bottomLeftY = cameraPos.y - (getHeight()/(2f*zoom));

        float topRightX = cameraPos.x + (getWidth()/ (2f*zoom));
        float topRightY = cameraPos.y + (getHeight()/(2f*zoom));

        float minHorizontal = (float) (Math.ceil(bottomLeftX/gridSize) * gridSize);
        float maxHorizontal = (float) (Math.floor(topRightX/gridSize) * gridSize);

        float minVertical = (float) (Math.ceil(bottomLeftY/gridSize) * gridSize);
        float maxVertical = (float) (Math.floor(topRightY/gridSize) * gridSize);

        for (float i = minHorizontal; i <= maxHorizontal; i = i + gridSize) {
            int drawX = (int) Helper.getScreenCoordinates(new Vector2(i, 0), this).x;
            g2d.setColor(new Color(210, 210, 210));
            g2d.fillRect(drawX, 0, 2, getHeight());
        }

        for (float i = minVertical; i <= maxVertical; i = i + gridSize) {
            int drawY = (int) Helper.getScreenCoordinates(new Vector2(0, i), this).y;

            g2d.setColor(new Color(210, 210, 210));
            g2d.fillRect(0, drawY, getWidth(), 2);
        }
    }
    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public void changeZoom(float delta) {
        this.zoom += delta;
    }

    public SimulationController getSimController() {
        return simController;
    }

    public WireController getWireController() {
        return wireController;
    }

    public void setWireController(WireController wireController) {
        this.wireController = wireController;
    }
}
