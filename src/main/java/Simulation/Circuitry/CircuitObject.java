package Simulation.Circuitry;

import Interface.CircuitPanel;

import java.awt.*;

public abstract class CircuitObject {

    public abstract void paint(Graphics2D g2d, CircuitPanel panel);

    public void leftClicked() {}

    public void rightClicked() {}
}
