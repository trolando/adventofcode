package nl.tvandijk.aoc.common;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public abstract class Day {
    public static class Pair <A, B> {
        public A a;
        public B b;

        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }

        public static <A, B> Pair<A, B> of(A a, B b){
            return new Pair<>(a, b);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(a, pair.a) && Objects.equals(b, pair.b);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }

    protected void part1(String fileContents) throws Exception {

    }

    protected void part2(String fileContents) throws Exception {

    }

    protected void process(String fileContents) throws Exception {
        part1(fileContents);
        part2(fileContents);
    }

    protected void go(String filename) {
        try (var inp = getClass().getResourceAsStream(filename)) {
            if (inp == null) {
                System.out.println("File not found: " + filename);
                return;
            }

            String fileContents = new String(inp.readAllBytes(), StandardCharsets.UTF_8);

            System.out.printf("File: %s%n", filename);

            long before = System.nanoTime();
            process(fileContents);
            long after = System.nanoTime();

            System.out.printf("Elapsed time: %.0f ms%n%n", ((double)(after-before))/1000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void run(DayConstructor factory, String ... inputs) {
        for (var filename : inputs) {
            factory.build().go(filename);
        }
    }
}
