package Simulation;

import Interface.CircuitPanel;
import Structures.Vector2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class Helper {
    public static Vector2 getScreenCoordinates(Vector2 simVector, CircuitPanel panel) {
        float x =  ((panel.getWidth()/2f) + panel.getZoom()*(simVector.x - panel.cameraPos.x));
        float y =  ((panel.getHeight()/2f) + panel.getZoom()*(-simVector.y + panel.cameraPos.y));
        return new Vector2(x, y);
    }

    public static Vector2 getSimCoordinates(Vector2 screenVector, CircuitPanel panel) {
        float x =  panel.cameraPos.x + ((screenVector.x - panel.getWidth()/2f) / panel.getZoom());
        float y =  panel.cameraPos.y - ((screenVector.y - panel.getHeight()/2f) / panel.getZoom());
        return new Vector2(x, y);
    }

    public static int smallestPowerOfTwo(int i) {
        int power = 1;
        while (power <= i) {
            power *= 2;
        }
        return power;
    }

    public static Integer getTwosComplement(Integer value, Integer width) {
        if (value < (Math.pow(2, width-1) - 1)) {
            return value;
        }
        else if ( (Math.pow(2, width-1) - 1) < value && value <= (Math.pow(2, width)) ) {
            double twos = (value - Math.pow(2, width));
            return (int) twos;
        }
        return 0;
    }

    public static List<Integer> jsonToIntList(JSONArray j) {
        List<Integer> newList = new java.util.ArrayList<>(List.of());
        try {
            for (Object o : j) {
                if (o.getClass() == Long.class) {
                    newList.add(((Long) o).intValue());
                } else {
                    throw new Exception("Non numeric value in JSON reading");
                }
            }
        } catch (Exception e) {
            // Log error
            System.out.println(e.getMessage());
        }
        return newList;
    }

    public static float parseJsonFloat(Object num) {
        if (num instanceof Number) {
            return ((Number) num).floatValue();
        } else if (num instanceof String) {
            try {
                return Float.parseFloat((String) num);
            } catch (NumberFormatException e) {
                return 0.0f;
            }
        }
        return 0.0f;
    }

    public static Integer parseJsonInteger(Object num) {
        if (num instanceof Number) {
            return ((Number) num).intValue();
        } else if (num instanceof String) {
            try {
                return Integer.parseInt((String) num);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

}
