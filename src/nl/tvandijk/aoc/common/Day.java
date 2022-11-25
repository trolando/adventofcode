package nl.tvandijk.aoc.common;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

public abstract class Day {
    protected String[] inputs;
    protected String fileContents;
    protected String[] tokens;
    protected String[] lines;

    public Day() {
        this("example.txt", "input.txt");
    }

    public Day(String ... inputs) {
        this.inputs = inputs;
    }

    protected void processInput(String fileContents) {
        this.fileContents = fileContents;
        this.lines = fileContents.split(System.lineSeparator());
        this.tokens = fileContents.split("\\s+");
    }

    protected abstract Object part1() throws Exception;

    protected abstract Object part2() throws Exception;

    protected boolean resetForPartTwo() {
        return false;
    }

    public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // Get constructor of the class
        String c = System.getProperty("sun.java.command");
        var C = ClassLoader.getSystemClassLoader().loadClass(c);
        var constructor = C.getConstructor();

        // Get input files
        var inputs = ((Day)constructor.newInstance()).inputs;

        for (var filename : inputs) {
            var day = (Day)constructor.newInstance();

            try (var inp = day.getClass().getResourceAsStream(filename)) {
                if (inp == null) {
                    System.out.println("File not found: " + filename);
                    continue;
                }

                String fileContents = new String(inp.readAllBytes(), StandardCharsets.UTF_8);
                System.out.printf("File: %s%n", filename);

                long before1, before2, after1, after2;
                Object result1, result2;

                before1 = System.nanoTime();
                day.fileContents = fileContents;
                day.processInput(fileContents);
                result1 = day.part1();
                after1 = System.nanoTime();

                System.out.println("Solution to part 1: " + result1);

                if (day.resetForPartTwo()) {
                    before2 = System.nanoTime();
                    day = (Day)constructor.newInstance();
                    day.fileContents = fileContents;
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
