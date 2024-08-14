package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Simulation.Circuitry.InPin;
import Simulation.Circuitry.OutPin;
import Structures.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ALUControlTest {
    ALUControl aluControl;
    @BeforeEach
    public void setup() {
        SimulationController simCMock = mock(SimulationController.class);
        aluControl = new ALUControl(new Vector2(0, 0), simCMock);
    }

    @Test
    public void ALUControl() {
        InPin funct = aluControl.getInPins().get(0);
        OutPin output = aluControl.getOutPins().get(0);
        InPin control = aluControl.getControlPin();

        control.setStatus(2); // R-Format
        funct.setStatus(32);
        aluControl.run();
        assertEquals(2, output.getStatus());

        funct.setStatus(34);
        aluControl.run();
        assertEquals(6, output.getStatus());

        funct.setStatus(36);
        aluControl.run();
        assertEquals(0, output.getStatus());

        funct.setStatus(37);
        aluControl.run();
        assertEquals(1, output.getStatus());

        // Non R Format

        control.setStatus(0);
        aluControl.run();
        assertEquals(2, output.getStatus());

        control.setStatus(1);
        aluControl.run();
        assertEquals(6, output.getStatus());

        control.setStatus(3);
        aluControl.run();
        assertEquals(0, output.getStatus());

        control.setStatus(4);
        aluControl.run();
        assertEquals(1, output.getStatus());
    }
}
