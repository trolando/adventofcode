package nl.tvandijk.aoc.year2021.day1;

import nl.tvandijk.aoc.common.Day;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Day1 extends Day {
    private int[] numbers;

    @Override
    protected void processInput(String fileContents) {
        var lexer = new InputLexer(CharStreams.fromString(fileContents));
        var parser = new InputParser(new CommonTokenStream(lexer));
        numbers = parser.root().meter().stream().mapToInt(mctx -> Integer.parseInt(mctx.getText())).toArray();
    }

    @Override
    protected Object part1() {
        int prevNumber = 0;
        int numbercount = 0;
        int howOftenDidDepthIncrease = 0;

        for (var number : numbers) {
            if (numbercount != 0 && number > prevNumber) howOftenDidDepthIncrease++;

            prevNumber = number;
            numbercount++;
        }

        return howOftenDidDepthIncrease;
    }

    @Override
    protected Object part2() {
        int howOftenDidDepthIncrease = 0;

        for (int i = 3; i < numbers.length; i++) {
            if (numbers[i] > numbers[i-3]) howOftenDidDepthIncrease++;
        }

        return howOftenDidDepthIncrease;
    }
}
