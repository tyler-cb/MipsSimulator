package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Structures.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class NotTest {
    @Test
    public void not() {
        SimulationController simCMock = mock(SimulationController.class);
        Not not = new Not(new Vector2(0, 0), simCMock);

        not.run();
        assertEquals(1, not.getOutPins().get(0).getStatus());

        not.getInPins().get(0).setStatus(1);
        not.run();
        assertEquals(0, not.getOutPins().get(0).getStatus());
    }
}
