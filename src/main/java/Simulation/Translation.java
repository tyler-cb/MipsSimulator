package Simulation;

import Structures.Exceptions.TranslationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class Translation {
    public enum InstructionFormat {
        R,
        J,
        I
    }

    // This model has 32 registers, register 0 is reserved for always 0. The rest are labeled r1-r31
    private static HashMap<String, String> registerMap = new HashMap<>();
    static {
        registerMap.put("$zero", "00000");
        for (int i = 1; i < 32; i++) {
            String padded = toPaddedBinaryString(i, 5);
            registerMap.put("$r"+i, padded);
        }
    }

    private static HashMap<String, InstructionFormat> instructionFormatMap = new HashMap<>();
    static {
        instructionFormatMap.put("add", InstructionFormat.R);
        instructionFormatMap.put("sub", InstructionFormat.R);
        instructionFormatMap.put("addi", InstructionFormat.I);
        instructionFormatMap.put("and", InstructionFormat.R);
        instructionFormatMap.put("or", InstructionFormat.R);
        instructionFormatMap.put("andi", InstructionFormat.I);
        instructionFormatMap.put("ori", InstructionFormat.I);
        instructionFormatMap.put("lw", InstructionFormat.I);
        instructionFormatMap.put("sw", InstructionFormat.I);
        instructionFormatMap.put("beq", InstructionFormat.I);
        instructionFormatMap.put("bne", InstructionFormat.I);
        instructionFormatMap.put("j", InstructionFormat.J);
    }

    // Define opcodes for non-R formats, and function field for R-format
    private static HashMap<String, String> opcodeMap = new HashMap<>();
    static {
        // Non-R
        opcodeMap.put("addi", "001000"); // 8
        opcodeMap.put("andi", "001100"); // 12
        opcodeMap.put("ori", "001101"); // 13
        opcodeMap.put("lw", "100011"); // 35
        opcodeMap.put("sw", "101011"); // 43
        opcodeMap.put("beq", "000100"); // 4
        opcodeMap.put("bne", "000101"); // 5
        opcodeMap.put("j", "000010"); // 2

        // R
        opcodeMap.put("add", "100000");
        opcodeMap.put("sub", "100010");
        opcodeMap.put("and", "100100");
        opcodeMap.put("or", "100101");
    }

    public static String translate(String ins) throws TranslationException {
        ArrayList<String> insTokens =  new ArrayList<>(Arrays.asList(ins.split("\\s+")));
        String opcode = insTokens.get(0);
        InstructionFormat format = instructionFormatMap.get(opcode);
        if (format == null) {
            throw new TranslationException("Could not find instruction " + opcode);
        }
        switch (format) {
            case I -> { return translateI(insTokens); }
            case J -> { return translateJ(insTokens); }
            case R -> { return translateR(insTokens); }
        }
        return null;
    }

    private static String translateI(ArrayList<String> ins) throws TranslationException {
        // op (6 bits), source register (5 bits), destination register (5 bits), immediate value (16 bits)
        StringBuilder sb = new StringBuilder();
        if (ins.size() != 4) {
            throw new TranslationException("Incorrect structure for I-format instruction");
        }
        String opcode = opcodeMap.get(ins.get(0));
        if (opcode == null) {
            throw new TranslationException("Could not find translation for opcode " + ins.get(0));
        }
        sb.append(opcode);
        String rt = registerMap.get(ins.get(1));
        String rs = registerMap.get(ins.get(2));
        String errorMessage = rs == null ? "Could not find register " + ins.get(1) :
                              rt == null ? "Could not find register " + ins.get(2) : null;
        if (errorMessage != null) {
            throw new TranslationException(errorMessage);
        }
        sb.append(rs);
        sb.append(rt);
        String imm = ins.get(3);
        if (!isNumbers(imm)) {
            throw new TranslationException("Immediate value must contain valid number");
        }
        sb.append(toPaddedBinaryString(Integer.valueOf(imm), 16));

        return sb.toString();
    }

    private static String translateR(ArrayList<String> ins) throws TranslationException {
        // op (6 bits), first operand (5 bits), second operand (5 bits), destination (5 bits), shift (5 bits), function (6 bits)
        // Opcode is always 000000
        StringBuilder sb = new StringBuilder("000000");
        String rd = registerMap.get(ins.get(1));
        String rs = registerMap.get(ins.get(2));
        String rt = registerMap.get(ins.get(3));
        String errorMessage = rs == null ? "Could not find register " + ins.get(1) :
                        rt == null ? "Could not find register " + ins.get(2) :
                        rd == null ? "Could not find register " + ins.get(3) : null;
        if (errorMessage != null) {
            throw new TranslationException(errorMessage);
        }
        sb.append(rs);
        sb.append(rt);
        sb.append(rd);

        sb.append("00000");

        String func = opcodeMap.get(ins.get(0));
        if (func == null) {
            throw new TranslationException("Could not find instruction " + ins.get(0));
        }
        sb.append(func);

        return sb.toString();
    }

    private static String translateJ(ArrayList<String> ins) throws TranslationException {
        // op (6 bits), address (26 bits)
        StringBuilder sb = new StringBuilder();
        if (ins.size() != 2) {
            throw new TranslationException("Incorrect structure for J-format instruction");
        }
        String opcode = opcodeMap.get(ins.get(0));
        if (opcode == null) {
            throw new TranslationException("Could not find translation for opcode " + ins.get(0));
        }
        sb.append(opcode);
        String address = ins.get(1);
        if (!isNumbers(address) || Integer.parseInt(address) < 0 || Integer.parseInt(address) > 127) {
            throw new TranslationException("Invalid address");
        }
        sb.append(toPaddedBinaryString(Integer.valueOf(address), 26));
        return sb.toString();
    }

    private static boolean isNumbers(String str) {
        return str.matches("-?\\d+");
    }

    public static String toPaddedBinaryString(Integer i, Integer desiredLength) {
        String binary = Integer.toBinaryString(i);
        if (i < 0) {
            binary = binary.substring(16);
        }
        return String.format("%" + desiredLength + "s", binary).replace(" ", "0");
    }

}
