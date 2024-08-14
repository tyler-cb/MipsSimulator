package Interface;

import Simulation.Circuitry.Part;
import Simulation.Helper;
import Structures.Vector2;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Map;

public class CircuitMouseListener extends MouseAdapter {

    private CircuitPanel panel;
    private Vector2 lastClicked;

    public CircuitMouseListener(CircuitPanel panel) {
        this.panel = panel;
        lastClicked = new Vector2(0, 0);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Vector2 simCoords = Helper.getSimCoordinates(new Vector2(e.getX(), e.getY()), panel);
        for (Map.Entry<String, Part> entry : panel.getSimController().getPartList().entrySet()) {
            String id = entry.getKey();
            Part part = entry.getValue();
            if (simCoords.within(part.getPos(), part.getWidth(), part.getHeight())) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    part.leftClicked();
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    part.rightClicked();
                }
                panel.revalidate();
                panel.repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastClicked.x = e.getX();
        lastClicked.y = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        float deltaX = (-e.getX() + lastClicked.x)/ panel.getZoom();
        float deltaY = (e.getY() - lastClicked.y)/ panel.getZoom();

        panel.cameraPos.x += deltaX;
        panel.cameraPos.y += deltaY;

        lastClicked.x = e.getX();
        lastClicked.y = e.getY();

        panel.revalidate();
        panel.repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        panel.changeZoom(-e.getWheelRotation());
        panel.revalidate();
        panel.repaint();
    }
}
