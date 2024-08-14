package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Simulation.Circuitry.InPin;
import Simulation.Circuitry.OutPin;
import Structures.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class RegistersTest {
    @Test
    public void registers() {
        SimulationController simCMock = mock(SimulationController.class);
        Registers registers = new Registers(new Vector2(0, 0), simCMock);

        InPin readReg1 = registers.getInPins().get(3);
        InPin readReg2 = registers.getInPins().get(2);
        InPin writeReg = registers.getInPins().get(1);
        InPin writeData = registers.getInPins().get(0);
        InPin controlRegWrite = registers.getControlRegWrite();

        OutPin readData1 = registers.getOutPins().get(1);
        OutPin readData2 = registers.getOutPins().get(0);

        controlRegWrite.setStatus(1);

        writeReg.setStatus(0);
        writeData.setStatus(100);
        registers.run();

        readReg1.setStatus(0);
        assertEquals(0, readData1.getStatus()); // $zero cannot be changed.

        writeReg.setStatus(1);
        registers.run();

        writeReg.setStatus(2);
        writeData.setStatus(200);
        registers.run();

        controlRegWrite.setStatus(0);

        readReg1.setStatus(1);
        readReg2.setStatus(2);
        registers.run();

        assertEquals(100, readData1.getStatus());
        assertEquals(200, readData2.getStatus());


    }
}
