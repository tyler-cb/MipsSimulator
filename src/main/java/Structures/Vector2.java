package Structures;

import Simulation.Helper;
import org.json.simple.JSONObject;

public class Vector2 {
    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(JSONObject json) throws ClassCastException {
        try {
            this.x = Helper.parseJsonFloat(json.get("x"));
            this.y = Helper.parseJsonFloat(json.get("y"));
        } catch (ClassCastException e) {
            throw new ClassCastException("Invalid JSON coordinates");
        }
    }

    // Add another vector to this one
    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    // Checks if this vector is within the rectangle at bottomLeft with width and height.
    public boolean within(Vector2 bottomLeft, float width, float height) {
        return bottomLeft.x <= x && x <= bottomLeft.x + width && bottomLeft.y <= y && y <= bottomLeft.y + height;
    }


    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", x);
        json.put("y", y);
        return json;
    }
}
