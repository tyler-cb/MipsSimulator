package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Structures.Vector2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class InstructionMemoryTest {
    @Test
    public void instructionMemory() {
        SimulationController simCMock = mock(SimulationController.class);
        InstructionMemory instructionMemory = new InstructionMemory(new Vector2(0, 0), simCMock);

        instructionMemory.getMemory().setWord(4, List.of(0b00100000,0b00000001,0b00000000,0b00110010));
        instructionMemory.getInPins().get(0).setStatus(4);
        instructionMemory.run();
        assertEquals(536936498, instructionMemory.getOutPins().get(0).getStatus());
    }
}
