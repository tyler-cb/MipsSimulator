package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Simulation.Circuitry.OutPin;
import Structures.Vector2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ControlUnitTest {
    @Test
    public void controlUnit() {
        SimulationController simCMock = mock(SimulationController.class);
        ControlUnit controlUnit = new ControlUnit(new Vector2(0, 0), simCMock);
        ArrayList<Integer> opcodes = new ArrayList<>(List.of(0, 2, 4, 5, 8, 12, 13, 35, 43));

        // regWrite, ALUSrc, memWrite, ALUOp, memToReg, memRead, branch, branchNotEqual, jump, regDst
        ArrayList<List<Integer>> desiredOutputs = new ArrayList<>(List.of( // -1 = doesn't matter
                List.of(1, 0, 0, 2, 0, 0, 0, 0, 0, 1), // R-format
                List.of(0, 0, 0, -1, 0, 0, 0, 0, 1, 0), // Jump
                List.of(0, 0, 0, 1, -1, 0, 1, 0, 0, 0), // Beq
                List.of(0, 0, 0, 1, -1, 0, 0, 1, 0, 0), // Bne
                List.of(1, 1, 0, 0, 0, 0, 0, 0, 0, 0), // addi
                List.of(1, 1, 0, 3, 0, 0, 0, 0, 0, 0), // andi
                List.of(1, 1, 0, 4, 0, 0, 0, 0, 0, 0), // ori
                List.of(1, 1, 0, 0, 1, 1, 0, 0, 0, 0), // lw
                List.of(0, 1, 1, 0, -1, 0, 0, 0, 0, -1) // sw
        ));

        for (int i = 0; i < opcodes.size(); i++) {
            controlUnit.getInput().setStatus(opcodes.get(i));
            controlUnit.run();
            List<Integer> currDesiredOutputs = desiredOutputs.get(i);
            for (int j = 0; j < currDesiredOutputs.size(); j++) {
                int desired = currDesiredOutputs.get(j);
                if (desired != -1) {
                    OutPin out = controlUnit.getOutPins().get(j);
                    assertEquals(desired, out.getStatus());
                }
            }
        }
    }
}
