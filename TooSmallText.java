public class TooSmallText extends Exception {
    public TooSmallText() {
        super();
    }

    public TooSmallText(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "TooSmallText: " + getMessage();
    }
}
