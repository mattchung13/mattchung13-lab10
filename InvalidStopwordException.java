public class InvalidStopwordException extends Exception {
    public InvalidStopwordException() {
        super();
    }

    public InvalidStopwordException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "InvalidStopwordException: " + getMessage();
    }
}
