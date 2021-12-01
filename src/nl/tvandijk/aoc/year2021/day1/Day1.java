package nl.tvandijk.aoc.year2021.day1;

import nl.tvandijk.aoc.common.AoCCommon;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Day1 extends AoCCommon {
    private void doFirstPart(int[] numbers) {
        int prevNumber = 0;
        int numbercount = 0;
        int howOftenDidDepthIncrease = 0;

        for (var number : numbers) {
            if (numbercount != 0 && number > prevNumber) howOftenDidDepthIncrease++;

            prevNumber = number;
            numbercount++;
        }

        System.out.println("Puzzle 1 output: " + howOftenDidDepthIncrease);
    }

    private void doSecondPart(int[] numbers) {
        List<Integer> listOfWindows = new ArrayList<>();

        for (int i = 2; i < numbers.length; i++) {
            int window = numbers[i-2] + numbers[i-1] + numbers[i];
            listOfWindows.add(window);
        }

        int prevNumber = 0;
        int numbercount = 0;
        int howOftenDidDepthIncrease = 0;

        for (var number : listOfWindows) {
            if (numbercount != 0 && number > prevNumber) howOftenDidDepthIncrease++;

            prevNumber = number;
            numbercount++;
        }

        System.out.println("Puzzle 2 output: " + howOftenDidDepthIncrease);
    }

    private void doSecondPartAnotherWay(int[] numbers) {
        int howOftenDidDepthIncrease = 0;

        for (int i = 3; i < numbers.length; i++) {
            if (numbers[i] > numbers[i-3]) howOftenDidDepthIncrease++;
        }

        System.out.println("Puzzle 2 output: " + howOftenDidDepthIncrease);
    }

    @Override
    protected void process(InputStream stream) throws Exception {
        var lexer = new InputLexer(CharStreams.fromStream(stream));
        var parser = new InputParser(new CommonTokenStream(lexer));

        var theNumbersFromTheFile = parser.root().meter().stream().mapToInt(mctx -> Integer.parseInt(mctx.getText())).toArray();

        doFirstPart(theNumbersFromTheFile);
        doSecondPart(theNumbersFromTheFile);
        doSecondPartAnotherWay(theNumbersFromTheFile);
    }

    public static void main(String[] args) {
        run(Day1::new, "example.txt", "input.txt");
    }
}
