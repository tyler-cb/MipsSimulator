package Simulation.Circuitry.Parts;

import Controllers.SimulationController;
import Simulation.Circuitry.InPin;
import Simulation.Circuitry.OutPin;
import Simulation.Circuitry.Part;
import Structures.Vector2;
import org.json.simple.JSONObject;

import java.util.List;

public class ControlUnit extends Part {
    private OutPin regDst;
    private OutPin jump;
    private OutPin branchNotEqual;
    private OutPin branch;
    private OutPin memRead;
    private OutPin memToReg;
    private OutPin ALUOp;
    private OutPin memWrite;
    private OutPin ALUSrc;
    private OutPin regWrite;
    private InPin input;
    public ControlUnit(Vector2 pos, SimulationController simController) {
        super(pos, 4, 5, "Control", List.of(6), List.of(1, 1, 1, 3, 1, 1, 1, 1, 1, 1), simController);
        getFlags().add("priority");
        regWrite = getOutPins().get(0);
        ALUSrc = getOutPins().get(1);
        memWrite = getOutPins().get(2);
        ALUOp = getOutPins().get(3);
        memToReg = getOutPins().get(4);
        memRead = getOutPins().get(5);
        branch = getOutPins().get(6);
        branchNotEqual = getOutPins().get(7);
        jump = getOutPins().get(8);
        regDst = getOutPins().get(9);
        input = getInPins().get(0);
    }

    public ControlUnit(SimulationController simController, JSONObject json) throws Exception {
        this(new Vector2((JSONObject) json.get("pos")),
                simController);
    }

    @Override
    public void run() {
        if (input.getStatus() == 0) {
            // R-Format Instruction
            regDst.setStatus(1);
            jump.setStatus(0);
            ALUSrc.setStatus(0);
            memToReg.setStatus(0);
            regWrite.setStatus(1);
            memRead.setStatus(0);
            memWrite.setStatus(0);
            branch.setStatus(0);
            branchNotEqual.setStatus(0);
            ALUOp.setStatus(2);
        }
        if (input.getStatus() == 2) {
            // jump
            regDst.setStatus(0);
            jump.setStatus(1);
            ALUSrc.setStatus(0);
            memToReg.setStatus(0);
            regWrite.setStatus(0);
            memRead.setStatus(0);
            memWrite.setStatus(0);
            branch.setStatus(0);
            branchNotEqual.setStatus(0);
        }
        if (input.getStatus() == 4) {
            // Branch if equal
            ALUSrc.setStatus(0);
            jump.setStatus(0);
            regWrite.setStatus(0);
            memRead.setStatus(0);
            memWrite.setStatus(0);
            branch.setStatus(1);
            branchNotEqual.setStatus(0);
            ALUOp.setStatus(1);
        }
        if (input.getStatus() == 5) {
            // Branch if not equal
            ALUSrc.setStatus(0);
            jump.setStatus(0);
            regWrite.setStatus(0);
            memRead.setStatus(0);
            memWrite.setStatus(0);
            branch.setStatus(0);
            branchNotEqual.setStatus(1);
            ALUOp.setStatus(1);
        }
        if (input.getStatus() == 8) {
            // addi
            ALUSrc.setStatus(1);
            jump.setStatus(0);
            regWrite.setStatus(1);
            memToReg.setStatus(0);
            regDst.setStatus(0);
            memRead.setStatus(0);
            memWrite.setStatus(0);
            branch.setStatus(0);
            branchNotEqual.setStatus(0);
            ALUOp.setStatus(0);
        }
        if (input.getStatus() == 12) {
            // andi
            ALUSrc.setStatus(1);
            jump.setStatus(0);
            regWrite.setStatus(1);
            memToReg.setStatus(0);
            regDst.setStatus(0);
            memRead.setStatus(0);
            memWrite.setStatus(0);
            branch.setStatus(0);
            branchNotEqual.setStatus(0);
            ALUOp.setStatus(3);
        }
        if (input.getStatus() == 13) {
            // ori
            ALUSrc.setStatus(1);
            jump.setStatus(0);
            regWrite.setStatus(1);
            memToReg.setStatus(0);
            regDst.setStatus(0);
            memRead.setStatus(0);
            memWrite.setStatus(0);
            branch.setStatus(0);
            branchNotEqual.setStatus(0);
            ALUOp.setStatus(4);
        }
        if (input.getStatus() == 35 ) {
            // load word
            regDst.setStatus(0);
            jump.setStatus(0);
            ALUSrc.setStatus(1);
            memToReg.setStatus(1);
            regWrite.setStatus(1);
            memRead.setStatus(1);
            memWrite.setStatus(0);
            branch.setStatus(0);
            branchNotEqual.setStatus(0);
            ALUOp.setStatus(0);
        }
        if (input.getStatus() == 43 ) {
            // store word
            ALUSrc.setStatus(1);
            jump.setStatus(0);
            regWrite.setStatus(0);
            memRead.setStatus(0);
            memWrite.setStatus(1);
            branch.setStatus(0);
            branchNotEqual.setStatus(0);
            ALUOp.setStatus(0);
        }
    }

    public OutPin getRegDst() {
        return regDst;
    }

    public OutPin getBranchNotEqual() {
        return branchNotEqual;
    }

    public OutPin getBranch() {
        return branch;
    }

    public OutPin getMemRead() {
        return memRead;
    }

    public OutPin getMemToReg() {
        return memToReg;
    }

    public OutPin getALUOp() {
        return ALUOp;
    }

    public OutPin getMemWrite() {
        return memWrite;
    }

    public OutPin getALUSrc() {
        return ALUSrc;
    }

    public OutPin getRegWrite() {
        return regWrite;
    }

    public InPin getInput() {
        return input;
    }
}
