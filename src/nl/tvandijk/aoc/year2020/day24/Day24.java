package nl.tvandijk.aoc.year2020.day24;

import nl.tvandijk.aoc.common.AoCCommon;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class Day24 extends AoCCommon {
    private Set<HexagonalCoordinate> blackTiles = new HashSet<>();

    private int countAdjacent(HexagonalCoordinate p) {
        int count = 0;
        if (blackTiles.contains(p.w())) count++;
        if (blackTiles.contains(p.nw())) count++;
        if (blackTiles.contains(p.sw())) count++;
        if (blackTiles.contains(p.e())) count++;
        if (blackTiles.contains(p.ne())) count++;
        if (blackTiles.contains(p.se())) count++;
        return count;
    }

    private void day() {
        Set<HexagonalCoordinate> newBlackTiles = new HashSet<>();

        for (var p : blackTiles) {
            var adjacent = countAdjacent(p);
            if (adjacent == 1 || adjacent == 2) newBlackTiles.add(p);

            var p2 = p.ne();
            if (!blackTiles.contains(p2) && countAdjacent(p2) == 2) newBlackTiles.add(p2);
            p2 = p.nw();
            if (!blackTiles.contains(p2) && countAdjacent(p2) == 2) newBlackTiles.add(p2);
            p2 = p.e();
            if (!blackTiles.contains(p2) && countAdjacent(p2) == 2) newBlackTiles.add(p2);
            p2 = p.w();
            if (!blackTiles.contains(p2) && countAdjacent(p2) == 2) newBlackTiles.add(p2);
            p2 = p.se();
            if (!blackTiles.contains(p2) && countAdjacent(p2) == 2) newBlackTiles.add(p2);
            p2 = p.sw();
            if (!blackTiles.contains(p2) && countAdjacent(p2) == 2) newBlackTiles.add(p2);
        }

        blackTiles = newBlackTiles;
    }

    @Override
    public void process(InputStream stream) throws IOException {
        var lexer = new InputLexer(CharStreams.fromStream(stream));
        var parser = new InputParser(new CommonTokenStream(lexer));
        var tree = parser.root();

        for (var lineCtx : tree.line()) {
            HexagonalCoordinate p = new HexagonalCoordinate(0, 0);
            for (var dirCtx : lineCtx.dir()) {
                switch (dirCtx.getText()) {
                    case "ne":
                        p = p.ne();
                        break;
                    case "nw":
                        p = p.nw();
                        break;
                    case "e":
                        p = p.e();
                        break;
                    case "se":
                        p = p.se();
                        break;
                    case "sw":
                        p = p.sw();
                        break;
                    case "w":
                        p = p.w();
                        break;
                    default:
                        throw new RuntimeException("unexpected");
                }
            }

            if (!blackTiles.remove(p)) blackTiles.add(p);
        }

        System.out.println("Solution of part 1: " + blackTiles.size());

        for (int i = 0; i < 100; i++) {
            day();
            // System.out.printf("Day %d: %d\n", i+1, blackTiles.size());
        }

        System.out.println("Solution of part 2: " + blackTiles.size());
    }

    public static void main(String[] args) {
        run(Day24::new, "example.txt", "input.txt");
    }
}