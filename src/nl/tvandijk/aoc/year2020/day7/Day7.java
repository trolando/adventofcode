package nl.tvandijk.aoc.year2020.day7;

import nl.tvandijk.aoc.common.Day;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.*;

public class Day7 extends Day {
    public Day7() {
        super("example.txt", "example2.txt", "input.txt");
    }

    private Map<String, List<String>> heldBy = new HashMap<>();
    private Map<String, List<String>> holds = new HashMap<>();

    public int counter(String what) {
        int res = 0;
        for (var s : holds.get(what)) {
            res += 1 + counter(s);
        }
        return res;
    }

    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);

        var lexer = new InputLexer(CharStreams.fromString(fileContents));
        var parser = new InputParser(new CommonTokenStream(lexer));

        for (var line : parser.root().line()) {
            if (line instanceof InputParser.ContainsBagsContext) {
                var ctx = (InputParser.ContainsBagsContext) line;

                String bag = ctx.bag().CHARS(0) + " " + ctx.bag().CHARS(1);
                holds.putIfAbsent(bag, new LinkedList<>());
                heldBy.putIfAbsent(bag, new LinkedList<>());

                for (var nbagsCtx : ctx.nbags()) {
                    int amount = Integer.parseInt(nbagsCtx.INT().getText());
                    String bagType = nbagsCtx.bag().CHARS(0) + " " + nbagsCtx.bag().CHARS(1);

                    for (int i = 0; i < amount; i++) holds.get(bag).add(bagType);

                    heldBy.putIfAbsent(bagType, new LinkedList<>());
                    heldBy.get(bagType).add(bag);
                }
            } else {
                var ctx = (InputParser.ContainsNoBagsContext) line;
                String bag = ctx.bag().CHARS(0) + " " + ctx.bag().CHARS(1);
                holds.putIfAbsent(bag, new LinkedList<>());
                heldBy.putIfAbsent(bag, new LinkedList<>());
            }
        }
    }

    @Override
    protected Object part1() throws Exception {
        Set<String> result = new HashSet<>();
        Queue<String> front = new LinkedList<>();
        result.add("shiny gold");
        front.add("shiny gold");
        while (!front.isEmpty()) {
            for (var next : heldBy.get(front.remove())) {
                if (!result.contains(next)) {
                    result.add(next);
                    front.add(next);
                }
            }
        }

        // Total bags that could contain shiny gold
        // Arrays.deepToString(result.toArray());
        return result.size() - 1;
    }

    @Override
    protected Object part2() throws Exception {
        // Total that contained in shiny gold
        return counter("shiny gold");
    }
}
