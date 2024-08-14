package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Structures.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ProgramCounterTest {
    @Test
    public void programCounter() {
        SimulationController simCMock = mock(SimulationController.class);
        ProgramCounter pc = new ProgramCounter(new Vector2(0, 0), simCMock);

        pc.run();
        assertEquals(0, pc.getOutPins().get(0).getStatus());

        pc.getInPins().get(0).setStatus(16);
        pc.run();
        assertEquals(16, pc.getOutPins().get(0).getStatus());
    }
}
