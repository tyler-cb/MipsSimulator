package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Structures.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class MultiplexerTest {
    @Test
    public void multiplexer() {
        SimulationController simCMock = mock(SimulationController.class);
        Multiplexer multiplexer = new Multiplexer(new Vector2(0, 0), 32, true, true, simCMock);

        multiplexer.getInPins().get(0).setStatus(20);
        multiplexer.getInPins().get(1).setStatus(40);
        multiplexer.getControlSignalPin().setStatus(0);
        multiplexer.run();
        assertEquals(20, multiplexer.getOutPins().get(0).getStatus());

        multiplexer.getControlSignalPin().setStatus(1);
        multiplexer.run();
        assertEquals(40, multiplexer.getOutPins().get(0).getStatus());
    }
}
