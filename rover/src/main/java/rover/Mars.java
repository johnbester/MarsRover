package rover;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Main class with entry point for running as jar, while also having a method to simplify running unit tests
 */
public class Mars {
    /**
     * A utility method to allow expecting the empty lines between differnent rover inputs, while at the
     * same time being flexible enough to handle pattern recognition. The most obvious check would be where
     * the last input on a line is received, and for some reason more data is received on the same line.
     * @param actual
     * @param expected
     * @return
     */
    private String expect(String actual, String expected) {
        if (expected == null && actual == null)
            return null;
        if (actual == null || expected == null)
            throw new IllegalArgumentException("Expected value differs from actual: " + expected + " <> " + actual);
        if (expected.isBlank() && actual.isBlank())
            return actual;
        try {
            if (Pattern.compile(expected, Pattern.CASE_INSENSITIVE).matcher(actual).matches())
                return actual;
            throw new IllegalArgumentException("Expected value differs from actual: " + expected + " <> " + actual);
        } catch (Exception e) {
            if (expected.trim().equalsIgnoreCase(actual.trim()))
                return actual;
            throw new IllegalArgumentException("Expected value differs from actual: " + expected + " <> " + actual);
        }
    }

    /**
     * Used for getting N, E, S, W
     * @param actual
     * @return
     */
    private char expectChar(String actual) {
        if (actual == null || actual.isBlank() || actual.trim().length() != 1)
            throw new IllegalArgumentException("Expected a single character");
        return actual.trim().toCharArray()[0];
    }

    /**
     * Used for getting a list of rover instructions as single characters
     */
    private char[] expectChars(String actual) {
        if (actual == null || actual.isBlank() || actual.trim().length() == 0)
            throw new IllegalArgumentException("Expected one or more characters");
        return actual.trim().replace(" ", "").toCharArray();
    }

    /**
     * Read a rover object. It would have been nice to be able to return null, but
     * my decision to use a Scanner object that blocks, should rather be changed
     * to a BufferedReader if this is an important consideration.
     * @param input
     * @return
     */
    private Rover readRover(Scanner input) {
        int x = input.nextInt();
        int y = input.nextInt();
        char heading = expectChar(input.nextLine());
        return new Rover(x, y, heading);
    }

    /**
     * Process data - either from stdin, or from unit tests
     * Do not add an additional empty line at the end of input, since
     * it may cause readRover to block and thereby blocking the
     * entire app. The exception handing in the loop should provide
     * a clean exit.
     * @param input
     * @param out
     */
    public void process(Scanner input, PrintStream out) {
        int w = input.nextInt();
        int h = input.nextInt();
        Surface surface = new Surface(w, h);
        expect(input.nextLine(), "");
        do {
            Rover rover = readRover(input);
            if (rover == null)
                return;
            char[] movements = expectChars(input.nextLine());
            // Process all movements and then decide whether to add a rover that
            // fell over the edge to the surface, or to clear out scent if the
            // rover is in a safe safe location
            for (char movement : movements) {
                rover.go(movement, surface);
            }
            if (rover.isLost())
                surface.addLostRover(rover);
            else
                rover.clearScent();
            out.println(rover.toString());
            try {
                expect(input.nextLine(), "");
            } catch (Exception e) {
                // Ignore error, since last input line may be empty and cause exception in scanner
                return;
            }
        } while (true);
    }

    public static void main(String[] args) {
        new Mars().process(new Scanner(System.in), System.out);
    }
}
