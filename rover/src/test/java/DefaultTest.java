import org.junit.jupiter.api.Test;
import rover.Mars;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultTest {
    public static String sampleInput = """
            5 3
            1 1 E
            RFRFRFRF
            
            3 2 N
            FRRFLLFFRRFLL
            
            0 3 W
            LLFFFLFLFL
            """;
    public static String sampleOutput = """
            1 1 E
            3 3 N LOST
            2 3 S
            """;

    public void runTest(String input, String expectedOutput) throws IOException {
        try (InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
             ByteArrayOutputStream data = new ByteArrayOutputStream();
             PrintStream out = new PrintStream(data)) {
            Scanner scanner = new Scanner(in);
            new Mars().process(scanner, out);
            out.flush();
            String txt = data.toString(StandardCharsets.UTF_8);
            assertEquals(expectedOutput, txt);
        }
    }

    @Test
    public void runTest() throws IOException {
        runTest(sampleInput, sampleOutput);
    }
}
