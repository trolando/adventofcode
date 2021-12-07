package nl.tvandijk.aoc.year2021.day6;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.year2021.day5.LinesLexer;
import nl.tvandijk.aoc.year2021.day5.LinesParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Day6 extends Day {

    private Map<Integer, Long> fishPerAge = new TreeMap<>();

    private void addFishesForDays(Map<Integer, Long> targetMap, int days, long fishes) {
        targetMap.compute(days, (key, value) -> {
            if (value == null) return fishes;
            else return value + fishes;
        });
    }

    private void simulateADay() {
        Map<Integer, Long> nextDay = new TreeMap<>();

        for (var entry : fishPerAge.entrySet()) {
            var key = entry.getKey();     // number of days left
            var value = entry.getValue(); // number of fishes

            if (key == 0) {
                addFishesForDays(nextDay, 8, value);
                addFishesForDays(nextDay, 6, value);
            } else {
                addFishesForDays(nextDay, key-1, value);
            }
        }

        fishPerAge = nextDay;
    }

    private long countFishes() {
        long result = 0;
        for (var n : fishPerAge.values()) {
            result += n;
        }
        return result;
    }

    private void printCurrentState() {
        for (var entry : fishPerAge.entrySet()) {
            var key = entry.getKey();     // number of days left
            var value = entry.getValue(); // number of fishes

            System.out.printf("%d fishes have %d days left%n", value, key);
        }
    }

    @Override
    protected void part1(String fileContents) {
        fishPerAge = new TreeMap<>();

        var numbers = fileContents.trim().split(",");

        for (var numberOfDays : numbers) {
            fishPerAge.compute(Integer.parseInt(numberOfDays), (key, value) -> {
                if (value == null) return 1L;
                else return value + 1L;
            });
        }

        for (int i = 0; i < 80; i++) {
            simulateADay();
        }

//        System.out.println("After " + (i+1) + " days...");
        System.out.println("Number of fish: " + countFishes());
        printCurrentState();
    }

    @Override
    protected void part2(String fileContents) {
        fishPerAge = new TreeMap<>();
        var numbers = fileContents.trim().split(",");

        for (var numberOfDays : numbers) {
            fishPerAge.compute(Integer.parseInt(numberOfDays), (key, value) -> {
                if (value == null) return 1L;
                else return value + 1L;
            });
        }

        for (int i = 0; i < 256; i++) {
            simulateADay();
            System.out.println("After " + (i+1) + " days...");
            System.out.println("Number of fish: " + countFishes());
            printCurrentState();
        }

        // example: 26984457539
        // got: 28698815940960

//        System.out.println("After " + (i+1) + " days...");
        System.out.println("Number of fish: " + countFishes());
        printCurrentState();
    }

    public static void main(String[] args) {
        run(Day6::new, "example.txt", "input.txt");
    }
}
