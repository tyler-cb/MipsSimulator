package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Structures.Vector2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class BitMaskTest {

    @Test
    public void bitMask() {
        SimulationController simCMock = mock(SimulationController.class);
        ArrayList<BitMask> testMasks = new ArrayList<>();
        BitMask bitMask26to31 = new BitMask(new Vector2(0, 0), 32, 26, 31, simCMock);
        BitMask bitMask21to25 = new BitMask(new Vector2(0, 0), 32, 21, 25, simCMock);
        BitMask bitMask16to20 = new BitMask(new Vector2(0, 0), 32, 16, 20, simCMock);
        BitMask bitMask11to15 = new BitMask(new Vector2(0, 0), 32, 11, 15, simCMock);
        BitMask bitMask0to15 = new BitMask(new Vector2(0, 0), 32, 0, 15, simCMock);
        testMasks.add(bitMask26to31);
        testMasks.add(bitMask21to25);
        testMasks.add(bitMask16to20);
        testMasks.add(bitMask11to15);
        testMasks.add(bitMask0to15);

        for (BitMask bm : testMasks) {
            bm.getInPins().get(0).setStatus(0b00100000000000010000000000110010); // addi $r1 $zero 50
            bm.run();
        }

        assertEquals(8, bitMask26to31.getOutPins().get(0).getStatus());
        assertEquals(0, bitMask21to25.getOutPins().get(0).getStatus());
        assertEquals(1, bitMask16to20.getOutPins().get(0).getStatus());
        assertEquals(0, bitMask11to15.getOutPins().get(0).getStatus());
        assertEquals(50, bitMask0to15.getOutPins().get(0).getStatus());
    }
}
