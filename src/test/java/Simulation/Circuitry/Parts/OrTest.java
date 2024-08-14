package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Simulation.Circuitry.OutPin;
import Structures.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class OrTest {
    @Test
    public void or() {
        SimulationController simCMock = mock(SimulationController.class);
        Or or = new Or(new Vector2(0, 0), simCMock);
        OutPin out = or.getOutPins().get(0);

        or.run();
        assertEquals(0, out.getStatus());

        or.getInPins().get(0).setStatus(1);
        or.run();
        assertEquals(1, out.getStatus());

        or.getInPins().get(0).setStatus(0);
        or.getInPins().get(1).setStatus(1);
        or.run();
        assertEquals(1, out.getStatus());
    }
}
