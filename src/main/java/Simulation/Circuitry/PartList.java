package Simulation.Circuitry;

import java.util.HashMap;

public class PartList extends HashMap<String, Part> {

    public PartList() {
        super();
    }

    @Override
    public Part put(String id, Part p) {
        p.setId(id);
        return super.put(id, p);
    }
}
