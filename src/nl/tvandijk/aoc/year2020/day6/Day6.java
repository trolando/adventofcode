package nl.tvandijk.aoc.year2020.day6;

import nl.tvandijk.aoc.common.AoCCommon;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day6 extends AoCCommon {
    @Override
    protected void process(InputStream stream) throws Exception {
        var lexer = new InputLexer(CharStreams.fromStream(stream));
        var parser = new InputParser(new CommonTokenStream(lexer));
        var tree = parser.root();

        List<Set<Character>> groups = new ArrayList<>();

        for (var groupCtx : tree.group()) {
            var set = new HashSet<Character>();
            for (var personCtx : groupCtx.person()) {
                set.addAll(personCtx.CHAR().stream().map(x -> x.getText().charAt(0)).collect(Collectors.toSet()));
            }
            groups.add(set);
        }

        System.out.printf("Sum of unique letters: %d\n", groups.stream().mapToInt(Set::size).sum());

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

        System.out.printf("Unique letters part 2: %d\n", count);
    }

    public static void main(String[] args) {
        run(Day6::new, "example.txt", "input.txt");
    }
}
