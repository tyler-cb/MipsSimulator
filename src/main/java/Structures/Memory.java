package Structures;

import java.util.ArrayList;
import java.util.List;

public class Memory {
    private List<Integer> memory;
    private int capacity;

    public Memory(int capacity) {
        this.capacity = capacity;
        memory = new ArrayList<Integer>();
        for (int i = 0; i < capacity; i++) {
            memory.add(i, 0);
        }
    }

    public int getByte(int index) {
        return memory.get(index);
    }

    public void setByte(int index, Integer value) {
        if (value > Math.pow(2, 8) || value < 0) {
            throw new IllegalArgumentException("Memory received illegal value");
        }
        memory.set(index, value);
    }

    public List<Integer> getWord(int index) {
        List<Integer> word = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            word.add(getByte(index + i));
        }
        return word;
    }

    public void setWord(int index, List<Integer> word) {
        if (word.size() != 4) {
            throw new IllegalArgumentException("Word not 4 bytes long.");
        }
        // Big Endian
        // Most significant byte stored first
        for (int i = 0; i < 4; i++) {
            setByte(index + i, word.get(i));
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
