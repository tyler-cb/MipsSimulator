package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Structures.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ShiftTest {
    @Test
    public void shift() {
        SimulationController simCMock = mock(SimulationController.class);
        Shift shiftRight2 = new Shift(new Vector2(0, 0), 2, simCMock);

        shiftRight2.getInPins().get(0).setStatus(16);
        shiftRight2.run();
        assertEquals(4, shiftRight2.getOutPins().get(0).getStatus());

        Shift shiftLeft2 = new Shift(new Vector2(0, 0), -2, simCMock);
        shiftLeft2.getInPins().get(0).setStatus(16);
        shiftLeft2.run();
        assertEquals(64, shiftLeft2.getOutPins().get(0).getStatus());
    }
}
