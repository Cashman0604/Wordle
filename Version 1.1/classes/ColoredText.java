public class ColoredText {
    private static final String ESC_STR = "\u001B";
    private static final String RESET_ESC_SEQUENCE_STR = "\u001B[0m";

    private String coloredStr;

    private ColoredText(String text, int color) {
        coloredStr = toColoredStr(text, color);
    }

    private ColoredText(String initial, String text, int color) {
        coloredStr = initial + toColoredStr(text, color);
    }

    public static ColoredText with(String text, int color) {
        return new ColoredText(text, color);
    }

    public ColoredText plus(String text, int color) {
        return new ColoredText(coloredStr, text, color);
    }

    public void print() {
        System.out.print(coloredStr);
    }

    public void println() {
        System.out.println(coloredStr);
    }

    @Override
    public String toString() {
        return coloredStr;
    }

    private String toEscSequenceStr(int color) {
        int foreground = 30;
        foreground += color & 0x07;
        color >>= 3;
        foreground += 60 * (color & 0x01);
        color >>= 1;

        int background = 40;
        background += color & 0x07;
        color >>= 3;
        background += 60 * (color & 0x01);

        return String.format("%s[%d;%dm", ESC_STR, foreground, background);
    }

    private String toColoredStr(String text, int color) {
        return String.format("%s%s%s", toEscSequenceStr(color), text, RESET_ESC_SEQUENCE_STR);
    }
}