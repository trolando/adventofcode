package nl.tvandijk.aoc.year2020.day6;

import nl.tvandijk.aoc.common.Day;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day6 extends Day {
    private InputParser.RootContext tree;

    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);

        var lexer = new InputLexer(CharStreams.fromString(fileContents));
        var parser = new InputParser(new CommonTokenStream(lexer));
        tree = parser.root();
    }

    @Override
    protected Object part1() throws Exception {
        List<Set<Character>> groups = new ArrayList<>();

        for (var groupCtx : tree.group()) {
            var set = new HashSet<Character>();
            for (var personCtx : groupCtx.person()) {
                set.addAll(personCtx.CHAR().stream().map(x -> x.getText().charAt(0)).collect(Collectors.toSet()));
            }
            groups.add(set);
        }

        // Sum of unique letters
        return groups.stream().mapToInt(Set::size).sum();
    }

    @Override
    protected Object part2() throws Exception {
        long count = 0;
        for (var groupCtx : tree.group()) {
            Set<Character> set = null;
            for (var personCtx : groupCtx.person()) {
                var person = personCtx.CHAR().stream().map(x -> x.getText().charAt(0)).collect(Collectors.toSet());
                if (set == null) set = person;
                else set.retainAll(person);
            }
            count += set.size();
        }
        // unique letters part 23
        return count;
    }
}
