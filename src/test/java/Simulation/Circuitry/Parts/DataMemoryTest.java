package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Structures.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class DataMemoryTest {
    @Test
    public void dataMemory() {
        SimulationController simCMock = mock(SimulationController.class);
        DataMemory dataMem = new DataMemory(new Vector2(0, 0), simCMock);

        dataMem.address.setStatus(4);
        dataMem.dataIn.setStatus(50);
        dataMem.memWriteControl.setStatus(1);
        dataMem.run();

        assertEquals(50, dataMem.memory.getWord(4).get(3));

        dataMem.memReadControl.setStatus(1);
        dataMem.run();

        assertEquals(50, dataMem.dataOut.getStatus());
    }
}
