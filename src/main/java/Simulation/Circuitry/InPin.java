package Simulation.Circuitry;

import Structures.Vector2;

public class InPin extends Pin {
    // Is this pin used for a control signal
    private boolean isControl;

    // Has the pin's value changed in the current cycle
    private boolean changedInCurrentCycle;
    public InPin(Part parent, Vector2 pos, int bits, boolean isControl) {
        super(parent.getInPins().size(), parent, pos, bits);
        this.isControl = isControl;
        changedInCurrentCycle = false;
    }

    public InPin(Part parent, Vector2 pos, int bits) {
        this(parent, pos, bits, false);
    }

    @Override
    public void setStatus(int status) {
        changedInCurrentCycle = true;
        super.setStatus(status);
        if (!isControl) {
            // If the pin is not used for a control signal, add the part to the run queue.
            getParent().getSimController().appendRunQueue(getParent());
        }
    }

    public boolean isControl() {
        return isControl;
    }

    public void setControl(boolean control) {
        isControl = control;
    }

    public boolean changedInCurrentCycle() {
        return changedInCurrentCycle;
    }

    public void setChangedInCurrentCycle(boolean changedInCurrentCycle) {
        this.changedInCurrentCycle = changedInCurrentCycle;
    }
}
