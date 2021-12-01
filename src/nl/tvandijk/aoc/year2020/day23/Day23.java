package nl.tvandijk.aoc.year2020.day23;

import nl.tvandijk.aoc.common.AoCCommon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Day23 extends AoCCommon {
    private static final boolean VERBOSE = false;

    private Cup buildCups(String line, Cup[] cups) {
        /**
         * Make "n" cups (cupsize = cups.length)
         */
        for (int i = 0; i < cups.length; i++) {
            cups[i] = new Cup(i+1);
            if (i > 0) cups[i].setNextLower(cups[i-1]);
        }
        cups[0].setNextLower(cups[cups.length-1]);

        /**
         * Translate line into array of cups
         */
        var lineArr = line.chars().map(Character::getNumericValue).toArray();

        /**
         * Now set initial state of the cupboard
         */
        Cup first = null;
        Cup cur = null;

        for (int i = 0; i < lineArr.length; i++) {
            if (i == 0) {
                first = cups[lineArr[i]-1];
                cur = first;
            } else {
                var next = cups[lineArr[i]-1];
                next.setPrev(cur);
                cur.setNext(next);
                cur = next;
            }
        }

        assert first != null;

        for (int i = lineArr.length; i < cups.length; i++) {
            var next = cups[i];
            next.setPrev(cur);
            cur.setNext(next);
            cur = next;
        }

        cur.setNext(first);
        first.setPrev(cur);

        return first;
    }

    private Cup move(Cup cur) {
        if (VERBOSE) {
            System.out.print("-- move --\n");
            System.out.printf("cups: (%d)", cur.value);
            for (Cup print=cur.getNext(); print!=cur; print=print.getNext()) {
                System.out.printf(" %d", print.value);
            }
        }

        Cup pick1 = cur.getNext();
        Cup pick2 = pick1.getNext();
        Cup pick3 = pick2.getNext();

        // remove them
        pick1.getPrev().setNext(pick3.getNext());
        pick3.getNext().setPrev(pick1.getPrev());

        Cup lower = cur.getNextLower();
        while (lower == pick1 || lower == pick2 || lower == pick3) lower = lower.getNextLower();

        if (VERBOSE) {
            System.out.printf("\npick up: %d %d %d\n", pick1.value, pick2.value, pick3.value);
            System.out.printf("destination: %d\n", lower.value);
        }

        pick1.setPrev(lower);
        pick3.setNext(lower.getNext());
        lower.setNext(pick1);
        pick1.getPrev().setNext(pick1);
        pick3.getNext().setPrev(pick3);

        return cur.getNext();
    }

    private void part1(String line) {
        Cup[] cups = new Cup[line.length()];
        Cup cur = buildCups(line, cups);
        for (int i=0; i<100; i++) cur = move(cur);

        System.out.print("Result of part 1: ");
        for (Cup print=cups[0].getNext(); print!=cups[0]; print=print.getNext()) {
            System.out.printf("%d", print.value);
        }
        System.out.println();
    }

    private void part2(String line) {
        Cup[] cups = new Cup[1000000];
        Cup cur = buildCups(line, cups);
        for (int i=0; i<10000000; i++) cur = move(cur);

        var a = cups[0].getNext();
        var b = a.getNext();

        long va = a.value;
        long vb = b.value;
        long m = va * vb;

        System.out.printf("Result of part 2: %d %d ==> %d\n", va, vb, m);
    }

    @Override
    public void process(InputStream stream) throws IOException {
        /**
         * In today's puzzle, there is just one line, which is a series of numbers...
         */
        var line = new BufferedReader(new InputStreamReader(stream)).readLine();

        part1(line);
        part2(line);
    }

    public static void main(String[] args) {
        run(Day23::new, "example.txt", "input.txt");
    }
}