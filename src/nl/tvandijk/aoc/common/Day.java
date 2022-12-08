package nl.tvandijk.aoc.common;

import java.nio.charset.StandardCharsets;

/**
 * Base class for the Advent of Code puzzles
 */
public abstract class Day {
    /**
     * Files that will be read as inputs, usually example.txt and input.txt
     */
    protected String[] inputs;

    /**
     * Contents of current input file
     */
    protected String fileContents;

    /**
     * Tokens of current input file (fileContents split into tokens)
     */
    protected String[] tokens;

    /**
     * Lines of current input file (fileContents split into lines)
     */
    protected String[] lines;

    /**
     * Default construct initializes inputs to example.txt and input.txt
     */
    public Day() {
        this("example.txt", "input.txt");
    }

    /**
     * Construct a Day for the given input files
     * @param inputs the input files, typically example.txt, input.txt
     */
    public Day(String ... inputs) {
        this.inputs = inputs;
    }

    /**
     * Process the file contents, base class behavior is to compute lines and tokens
     * @param fileContents the contents of the input file
     */
    protected void processInput(String fileContents) {
        this.fileContents = fileContents.replaceAll("\r\n","\n");
        this.lines = this.fileContents.split("\n"); // supports linux/windows, not older mac
        this.tokens = this.fileContents.split("\\s+");
    }

    /**
     * Do part 1 of the puzzle
     * @return the result of the puzzle
     * @throws Exception if something went wrong, any exception may be thrown and will be caught
     */
    protected abstract Object part1() throws Exception;

    /**
     * Do part 2 of the puzzle
     * @return the result of the puzzle
     * @throws Exception if something went wrong, any exception may be thrown and will be caught
     */
    protected abstract Object part2() throws Exception;

    /**
     * Whether a new Day instance needs to be constructed for part 2
     */
    protected boolean resetForPartTwo() {
        return false;
    }

    /**
     * Main method, run a Day.
     * @param args unused command line arguments
     */
    public static void main(String[] args) throws Exception {
        // Get the constructor of the actual class
        String c = System.getProperty("sun.java.command");
        var C = ClassLoader.getSystemClassLoader().loadClass(c);
        var constructor = C.getConstructor();

        // Get the input files
        var inputs = ((Day)constructor.newInstance()).inputs;

        // For every input file...
        for (var filename : inputs) {
            // Create a new Day object for the input file
            var day = (Day)constructor.newInstance();

            // Read the file contents
            String fileContents;
            try (var inp = day.getClass().getResourceAsStream(filename)) {
                if (inp == null) {
                    System.out.println("File not found: " + filename);
                    continue;
                }

                System.out.printf("File: %s%n", filename);
                fileContents = new String(inp.readAllBytes(), StandardCharsets.UTF_8);
            }

            try {
                long before1, before2, after1, after2;
                Object result1, result2;

                // Compute part 1

                before1 = System.nanoTime();
                day.processInput(fileContents);
                result1 = day.part1();
                after1 = System.nanoTime();

                System.out.println("Solution to part 1: " + result1);

                // Compute part 2 with optional reset

                if (day.resetForPartTwo()) {
                    before2 = System.nanoTime();
                    day = (Day)constructor.newInstance();
                    day.processInput(fileContents);
                    result2 = day.part2();
                    after2 = System.nanoTime();
                } else {
                    before2 = System.nanoTime();
                    result2 = day.part2();
                    after2 = System.nanoTime();
                }

                System.out.println("Solution to part 2: " + result2);
                System.out.printf("Elapsed times: %.0f ms, %.0f ms%n%n", ((double) (after1 - before1)) / 1000000, ((double) (after2 - before2)) / 1000000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
