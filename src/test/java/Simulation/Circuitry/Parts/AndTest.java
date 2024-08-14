package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Structures.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class AndTest {
    @Test
    public void and() {
        SimulationController simCMock = mock(SimulationController.class);
        And and = new And(new Vector2(0, 0), simCMock);

        and.run();
        assertEquals(0, and.getOutPins().get(0).getStatus());

        and.getInPins().get(0).setStatus(1);
        and.getInPins().get(1).setStatus(1);
        and.run();

        assertEquals(1, and.getOutPins().get(0).getStatus());
    }
}
