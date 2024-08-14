package Structures.Exceptions;

public class JSONException extends Exception {
    public JSONException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }

    public JSONException(String errorMessage) {
        super(errorMessage);
    }
}
