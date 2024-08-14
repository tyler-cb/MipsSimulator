package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Structures.Vector2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AddTest {

    @Test
    public void addRunTest() {
        SimulationController simCMock = mock(SimulationController.class);
        Add adder = new Add(new Vector2(0, 0), List.of(32, 32), simCMock);

        adder.getInPins().get(0).setStatus(50);
        adder.getInPins().get(1).setStatus(25);

        adder.run();

        assertEquals(75, adder.getOutPins().get(0).getStatus());
    }
}