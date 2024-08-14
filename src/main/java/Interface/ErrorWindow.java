package Interface;

import Structures.Exceptions.JSONException;

import javax.swing.*;
import java.util.Arrays;

public class ErrorWindow extends JOptionPane {
    public ErrorWindow(String errorMessage, Throwable ex) {
        StringBuilder message = new StringBuilder();
        if (errorMessage != null) {
            message = new StringBuilder(errorMessage + "\n");
        }
        message.append(ex.toString()).append("\n\n");
//        for (StackTraceElement st : ex.getStackTrace()) {
//            message.append(st.toString()).append("\n");
//        }
        showMessageDialog(new JFrame(), message.toString(), "Error", ERROR_MESSAGE);
    }

    public ErrorWindow(Throwable ex) {
        this(null, ex);
    }
}
