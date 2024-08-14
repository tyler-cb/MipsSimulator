package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Structures.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ALUTest {
    ALU alu;
    @BeforeEach
    public void setup() {
        SimulationController simCMock = mock(SimulationController.class);
        alu = new ALU(new Vector2(0, 0), simCMock);
    }

    @Test
    public void aluAdd() {
        alu.getInPins().get(0).setStatus(50);
        alu.getInPins().get(1).setStatus(25);
        alu.getControlPin().setStatus(2);

        alu.run();

        assertEquals(75, alu.getOutPins().get(0).getStatus());
        assertEquals(0, alu.getOutPins().get(1).getStatus());
    }

    @Test
    public void aluSub() {
        alu.getInPins().get(0).setStatus(100);
        alu.getInPins().get(1).setStatus(50);
        alu.getControlPin().setStatus(6);

        alu.run();

        assertEquals(50, alu.getOutPins().get(0).getStatus());
        assertEquals(0, alu.getOutPins().get(1).getStatus());
    }

    @Test
    public void aluSubZero() {
        alu.getInPins().get(0).setStatus(50);
        alu.getInPins().get(1).setStatus(50);
        alu.getControlPin().setStatus(6);

        alu.run();

        assertEquals(0, alu.getOutPins().get(0).getStatus());
        assertEquals(1, alu.getOutPins().get(1).getStatus());
    }

    @Test
    public void aluAnd() {
        alu.getInPins().get(0).setStatus(170); // 10101010
        alu.getInPins().get(1).setStatus(85);  // 01010101
        alu.getControlPin().setStatus(0);

        alu.run();

        assertEquals(0, alu.getOutPins().get(0).getStatus());
        assertEquals(1, alu.getOutPins().get(1).getStatus());
    }

    @Test
    public void aluOr() {
        alu.getInPins().get(0).setStatus(170); // 10101010
        alu.getInPins().get(1).setStatus(85);  // 01010101
        alu.getControlPin().setStatus(1);

        alu.run();

        assertEquals(255, alu.getOutPins().get(0).getStatus());
        assertEquals(0, alu.getOutPins().get(1).getStatus());
    }
}
