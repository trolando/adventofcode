package nl.tvandijk.aoc.year2021.day4;

import nl.tvandijk.aoc.common.Day;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.stream.Collectors;

public class Day4 extends Day {

    @Override
    protected void part1(String fileContents) {
        var lexer = new InputLexer(CharStreams.fromString(fileContents));
        var parser = new InputParser(new CommonTokenStream(lexer));

        var rootCtx = parser.root();

        var drawnNumbers = rootCtx.drawn().NUMBER().stream().mapToInt(node -> Integer.parseInt(node.getText())).toArray();

        var boards = rootCtx.board().stream().map(Board::new).collect(Collectors.toList());

        for (var number : drawnNumbers) {

            for (var board : boards) {
                board.seeANumber(number);
                if (board.bingo()) {
                    int result = board.countUnseen() * number;
                    System.out.println("Result of part 1 = " + result);
                    return;
                }
            }
        }
    }

    private class Board {
        int[] fields;
        boolean[] seen = new boolean[25];
        boolean mayHaveBingo = true;

        public Board(InputParser.BoardContext ctx) {
            fields = ctx.NUMBER().stream().mapToInt(token -> Integer.parseInt(token.getText())).toArray();
        }

        public int countUnseen() {
            int result = 0;
            for (int i = 0; i < 25; i++) {
                if (!seen[i]) result += fields[i];
            }
            return result;
        }

        public void seeANumber(int number) {
            for (int i = 0; i < 25; i++) {
                if (fields[i] == number) seen[i] = true;
            }
        }

        public boolean bingo() {
            // check each row
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    if (!seen[index(x, y)]) break;

                    if (y == 4) {
                        if (mayHaveBingo) {
                            mayHaveBingo = false;
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }

            // check each column
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 5; x++) {
                    if (!seen[index(x, y)]) break;

                    if (x == 4) {
                        if (mayHaveBingo) {
                            mayHaveBingo = false;
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }

            return false;
        }

        public int index(int x, int y) {
            return y*5+x;
        }
    }


    @Override
    protected void part2(String fileContents) {
        var lexer = new InputLexer(CharStreams.fromString(fileContents));
        var parser = new InputParser(new CommonTokenStream(lexer));

        var rootCtx = parser.root();

        var drawnNumbers = rootCtx.drawn().NUMBER().stream().mapToInt(node -> Integer.parseInt(node.getText())).toArray();

        var boards = rootCtx.board().stream().map(Board::new).collect(Collectors.toList());

        int lastResult = 0;

        for (var number : drawnNumbers) {
            for (var board : boards) {
                board.seeANumber(number);
                if (board.bingo()) {
                    lastResult = board.countUnseen() * number;
                }
            }
        }

        System.out.println("Result of part 2 = " + lastResult);
    }

    public static void main(String[] args) {
        run(Day4::new, "example.txt", "input.txt");
    }
}
