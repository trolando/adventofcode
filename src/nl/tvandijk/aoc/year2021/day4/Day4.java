package nl.tvandijk.aoc.year2021.day4;

import nl.tvandijk.aoc.common.Day;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day4 extends Day {
    private static class Board {
        int[] fields;
        boolean[] seen;
        boolean mayHaveBingo = true;

        public Board(InputParser.BoardContext ctx) {
            fields = ctx.NUMBER().stream().mapToInt(token -> Integer.parseInt(token.getText())).toArray();
            seen = new boolean[fields.length];
        }

        /**
         * Compute the sum of all unseen numbers
         */
        public int countUnseen() {
            int result = 0;
            for (int i = 0; i < 25; i++) {
                if (!seen[i]) result += fields[i];
            }
            return result;
        }

        /**
         * Update the bingo card with the given number (if on the card)
         */
        //@ ensures (\forall int i; 0 <= i && i < 25; fields[i] == number ==> seen[i]);
        public void seeANumber(int number) {
            for (int i = 0; i < 25; i++) {
                if (fields[i] == number) seen[i] = true;
            }
        }

        /**
         * This checks for bingo but also ensures that we only return true once
         */
        public boolean bingoOnce() {
            if (mayHaveBingo && bingo()) {
                mayHaveBingo = false;
                return true;
            } else {
                return false;
            }
        }

        private boolean bingo() {
            // check each row and column
            for (int i = 0; i < 2; i++) {
                // check columns if i equals 0, or rows if i equals 1
                for (int x = 0; x < 5; x++) {
                    for (int y = 0; y < 5; y++) {
                        int idx = i == 0 ? index(x, y) : index(y, x);
                        // if not seen, then it's no bingo on this row/column
                        if (!seen[idx]) break;
                        // if we are here, then all 5 on this row//column were seen
                        if (y == 4) return true;
                    }
                }
            }
            // we did not see a row/column with bingo
            return false;
        }

        public int index(int x, int y) {
            return y*5+x;
        }
    }

    private List<Integer> computeBingoResults(String fileContents) {
        // get all data
        var lexer = new InputLexer(CharStreams.fromString(fileContents));
        var parser = new InputParser(new CommonTokenStream(lexer));
        var rootCtx = parser.root();
        var drawnNumbers = rootCtx.drawn().NUMBER().stream().mapToInt(node -> Integer.parseInt(node.getText())).toArray();
        var boards = rootCtx.board().stream().map(Board::new).collect(Collectors.toList());
        // compute the results
        var results = new ArrayList<Integer>();
        for (var number : drawnNumbers) {
            for (var board : boards) {
                board.seeANumber(number);
                if (board.bingoOnce()) results.add(board.countUnseen() * number);
            }
        }
        return results;
    }

    @Override
    protected Object part1() {
        var results = computeBingoResults(fileContents);
        return results.get(0);
    }

    @Override
    protected Object part2() {
        var results = computeBingoResults(fileContents);
        return results.get(results.size()-1);
    }
}
