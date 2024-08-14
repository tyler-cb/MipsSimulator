package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Structures.Vector2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class AddConstantTest {

    @Test
    public void addRunTest() {
        SimulationController simCMock = mock(SimulationController.class);
        AddConstant adder = new AddConstant(new Vector2(0, 0), 25, 32, simCMock);

        adder.getInPins().get(0).setStatus(50);

        adder.run();

        assertEquals(75, adder.getOutPins().get(0).getStatus());
    }
}
