import Controllers.SimulationController;
import Controllers.WireController;
import Interface.CircuitPanel;
import Interface.RegistersPanel;
import Simulation.Circuitry.Parts.InstructionMemory;
import Simulation.Circuitry.Parts.ProgramCounter;
import Simulation.Circuitry.Parts.Registers;
import Structures.Memory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

class InstructionTesting {
    SimulationController simulationController;
    Memory instructionMemory;
    List<Integer> registerMem;

    @BeforeEach
    public void setup() {
        CircuitPanel mockPanel = mock(CircuitPanel.class);
        RegistersPanel mockRegisterPanel = mock(RegistersPanel.class);
        WireController wireController = new WireController(mockPanel);
        simulationController = new SimulationController(mockPanel, wireController);
        simulationController.setRegistersPanel(mockRegisterPanel);
        File testSetup = new File("../resources/MIPS_Model.json");
        try {
            simulationController.loadFile(testSetup);
        } catch (Exception e) {
            fail("Test JSON failed to load, "+e.getMessage());
        }
        instructionMemory = ((InstructionMemory) simulationController.getPartList().get("instructionMem")).getMemory();
        registerMem = ((Registers) simulationController.getPartList().get("registers")).getRegisters();
        simulationController.cycle();
    }

    @Test
    public void add() {
        registerMem.set(1, 50);
        registerMem.set(2, 25);
        instructionMemory.setWord(4, List.of(0b00000000,0b00100010,0b00011000,0b00100000)); // add $r3 $r1 $r2
        simulationController.cycle();
        assertEquals(75, registerMem.get(3));
    }

    @Test
    public void sub() {
        registerMem.set(1, 25);
        registerMem.set(2, 100);
        instructionMemory.setWord(4, List.of(0b00000000,0b00100010,0b00011000,0b00100010)); // sub $r3 $r1 $r2
        simulationController.cycle();
        assertEquals(75, registerMem.get(3));
    }

    @Test
    public void addi() {
        registerMem.set(1, 50);
        instructionMemory.setWord(4,List.of(0b00100000,0b00100011,0b00000001,0b11110100)); // addi $r3 $r1 500
        simulationController.cycle();
        assertEquals(550, registerMem.get(3));
    }

    @Test
    public void and() {
        registerMem.set(1, 31);
        registerMem.set(2, 7);
        instructionMemory.setWord(4, List.of(0b00000000,0b00100010,0b00011000,0b00100100)); // and $r3 $r1 $r2
        simulationController.cycle();
        assertEquals(7, registerMem.get(3));
    }

    @Test
    public void or() {
        registerMem.set(1, 31);
        registerMem.set(2, 7);
        instructionMemory.setWord(4, List.of(0b00000000,0b00100010,0b00011000,0b00100101)); // or $r3 $r1 $r2
        simulationController.cycle();
        assertEquals(31, registerMem.get(3));
    }

    @Test
    public void andi() {
        registerMem.set(1, 31);
        instructionMemory.setWord(4, List.of(0b00110000,0b00100011,0b00000000,0b00000111)); // andi $r3 $r1 7
        simulationController.cycle();
        assertEquals(7, registerMem.get(3));
    }

    @Test
    public void ori() {
        registerMem.set(1, 31);
        instructionMemory.setWord(4, List.of(0b00110100,0b00100011,0b00000000,0b00000111)); // or $r3 $r1 7
        simulationController.cycle();
        assertEquals(31, registerMem.get(3));
    }

    @Test
    public void lwsw() {
        registerMem.set(1, 50);
        instructionMemory.setWord(4, List.of(0b10101100,0b00000001,0b00000000,0b00000100)); // sw $r1 $zero 4
        simulationController.cycle();
        instructionMemory.setWord(8, List.of(0b10001100,0b00000011,0b00000000,0b00000100)); // lw $r3 $zero 4
        simulationController.cycle();
        assertEquals(50, registerMem.get(3));
    }

    @Test
    public void beq() {
        instructionMemory.setWord(4, List.of(0b00010000,0b00000000,0b00000000,0b00000100)); // beq $zero $zero 4
        simulationController.cycle();
        ProgramCounter pc = (ProgramCounter) simulationController.getPartList().get("pc");
        assertEquals(24, pc.getInPins().get(0).getStatus());
    }

    @Test
    public void bne() {
        registerMem.set(1, 20);
        instructionMemory.setWord(4, List.of(0b00010100,0b00100000,0b00000000,0b00000100)); // bne $zero $r1 4
        simulationController.cycle();
        ProgramCounter pc = (ProgramCounter) simulationController.getPartList().get("pc");
        assertEquals(24, pc.getInPins().get(0).getStatus());
    }

    @Test
    public void jump() {
        instructionMemory.setWord(4, List.of(0b00001000,0b00000000,0b00000000,0b00001100)); // j 12
        simulationController.cycle();
        ProgramCounter pc = (ProgramCounter) simulationController.getPartList().get("pc");
        assertEquals(48, pc.getInPins().get(0).getStatus());
    }
}
