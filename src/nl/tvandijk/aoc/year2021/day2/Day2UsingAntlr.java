package nl.tvandijk.aoc.year2021.day2;

import nl.tvandijk.aoc.common.Day;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Day2UsingAntlr extends Day {
    @Override
    protected void processInput(String fileContents) {
    }

    @Override
    protected Integer part1() {
        int x = 0;
        int depth = 0;

        var lexer = new InputLexer(CharStreams.fromString(fileContents));
        var parser = new InputParser(new CommonTokenStream(lexer));

        for (var commandCtx : parser.root().command()) {
            var command = commandCtx.getChild(0).getChild(0).getText();
            var amount = Integer.parseInt(commandCtx.getChild(0).getChild(1).getText());

            switch (command) {
                case "down":
                    depth += amount;
                    break;
                case "up":
                    depth -= amount;
                    break;
                case "forward":
                    x += amount;
                    break;
                default:
                    break;
            }
        }

        return x * depth;
    }

    @Override
    protected Integer part2() {
        int x = 0;
        int depth = 0;
        int aim = 0;

        var lexer = new InputLexer(CharStreams.fromString(fileContents));
        var parser = new InputParser(new CommonTokenStream(lexer));

        for (var commandCtx : parser.root().command()) {
            var command = commandCtx.getChild(0).getChild(0).getText();
            var amount = Integer.parseInt(commandCtx.getChild(0).getChild(1).getText());

            switch (command) {
                case "down":
                    aim += amount;
                    break;
                case "up":
                    aim -= amount;
                    break;
                case "forward":
                    x += amount;
                    depth += amount * aim;
                    break;
                default:
                    break;
            }

        }

        return x * depth;
    }
}
