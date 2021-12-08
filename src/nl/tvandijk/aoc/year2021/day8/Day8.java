package nl.tvandijk.aoc.year2021.day8;

import nl.tvandijk.aoc.common.Day;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class Day8 extends Day {

    @Override
    protected void part1(String fileContents) {
        var lexer = new DigitsLexer(CharStreams.fromString(fileContents));
        var parser = new DigitsParser(new CommonTokenStream(lexer));
        var tree = parser.root();

        int result = 0;

        for (var line : tree.line()) {
            for (var c : line.output().code()) {
                var theCode = c.CODE().getText();
                if (theCode.length() == 2 || theCode.length() == 3 ||
                        theCode.length() == 4 || theCode.length() == 7) {
                    result++;
                }
            }
        }

        System.out.printf("Puzzle part 1: %d%n", result);
    }

    /*

  0:      1:      2:      3:      4:
 aaaa    ....    aaaa    aaaa    ....
b    c  .    c  .    c  .    c  b    c
b    c  .    c  .    c  .    c  b    c
 ....    ....    dddd    dddd    dddd
e    f  .    f  e    .  .    f  .    f
e    f  .    f  e    .  .    f  .    f
 gggg    ....    gggg    gggg    ....

  5:      6:      7:      8:      9:
 aaaa    aaaa    aaaa    aaaa    aaaa
b    .  b    .  .    c  b    c  b    c
b    .  b    .  .    c  b    c  b    c
 dddd    dddd    ....    dddd    dddd
.    f  e    f  .    f  e    f  .    f
.    f  e    f  .    f  e    f  .    f
 gggg    gggg    ....    gggg    gggg

LENGTHS:
- 2: 1
- 3: 7
- 4: 4
- 5: 2 3 5
- 6: 0 6 9
- 7: 8

in common with "2 length" (1):
- "1" intersects with 3
- "4 minus 1" intersects with 5
- the remaining one is 2
the 6 lengths:
- the one that does not intersect with 1 is 6
- the one that does not intersect with 4 is 0
     */

    /**
     * compute the amount of overlap
     */
    private int overlap(char[] ac, char[] bc) {
        int result = 0;
        for (char ch : bc) {
            for (char ch2 : ac) {
                if (ch == ch2) result++;
            }
        }
        return result;
    }

    private int decodeLine(DigitsParser.LineContext line) {
        var codes = line.input().code().stream().map(ctx -> {
            var ca = ctx.getText().toCharArray();
            Arrays.sort(ca);
            return new String(ca);
        }).collect(Collectors.toList());

        String[] encodings = new String[10];

        // determine one, four, seven, eight

        for (String code : codes) {
            if (code.length() == 2) {
                encodings[1] = code;
            } else if (code.length() == 3) {
                encodings[7] = code;
            } else if (code.length() == 4) {
                encodings[4] = code;
            } else if (code.length() == 7) {
                encodings[8] = code;
            }
        }

        for (String code : codes) {
            if (code.length() == 5) {
                if (overlap(code.toCharArray(), encodings[1].toCharArray()) == 2) {
                    encodings[3] = code;
                } else if (overlap(code.toCharArray(), encodings[4].toCharArray()) == 3) {
                    encodings[5] = code;
                } else {
                    encodings[2] = code;
                }
            } else if (code.length() == 6) {
                if (overlap(code.toCharArray(), encodings[1].toCharArray()) == 1) {
                    encodings[6] = code;
                } else if (overlap(code.toCharArray(), encodings[4].toCharArray()) == 3) {
                    encodings[0] = code;
                } else {
                    encodings[9] = code;
                }
            }
        }

        var outputs = line.output().code().stream().map(ctx -> {
            var ca = ctx.getText().toCharArray();
            Arrays.sort(ca);
            return new String(ca);
        }).collect(Collectors.toList());

        int result = 0;

        for (var code : outputs) {
            result *= 10;
            for (int i = 0; i < 10; i++) {
                if (code.equals(encodings[i])) result += i;
            }
        }

        return result;
    }

    @Override
    protected void part2(String fileContents) {
        var lexer = new DigitsLexer(CharStreams.fromString(fileContents));
        var parser = new DigitsParser(new CommonTokenStream(lexer));
        var tree = parser.root();

        long numberSum = 0;

        for (var line : tree.line()) {
            numberSum += decodeLine(line);
            System.out.println("Line is " + decodeLine(line));
        }

        System.out.printf("Puzzle part 2: %d%n", numberSum);
    }

    public static void main(String[] args) {
        run(Day8::new, "example.txt", "input.txt");
    }
}
