package nl.tvandijk.aoc.common;

import java.io.InputStream;

public abstract class AoCCommon {
    protected abstract void process(InputStream stream) throws Exception;

    protected void go(String filename) {
        System.out.println("File: " + filename);

        try (var inp = getClass().getResourceAsStream(filename)) {
            if (inp == null) {
                System.out.println("File not found: " + filename);
                return;
            }

            long before = System.nanoTime();
            process(inp);
            long after = System.nanoTime();

            System.out.printf("Elapsed time: %.0f ms\n\n", ((double)(after-before))/1000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void run(AoCDayConstructor thing, String ... inputs) {
        for (var filename : inputs) {
            thing.cons().go(filename);
        }
    }
}
