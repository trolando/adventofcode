package nl.tvandijk.aoc.year2022.day13;

import java.util.*;
import java.util.stream.Collectors;

import nl.tvandijk.aoc.common.Day;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.RuleNode;

public class Day13 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
    }

    static class Visitor extends PacketsBaseVisitor<Object> {
        @Override
        public Integer visitNumber(PacketsParser.NumberContext ctx) {
            return Integer.parseInt(ctx.getText());
        }

        @Override
        public List<Object> visitList(PacketsParser.ListContext ctx) {
            return ctx.children.stream().map(this::visit).filter(Objects::nonNull).collect(Collectors.toList());
        }

        @Override
        public Object visitRoot(PacketsParser.RootContext ctx) {
            return visitList(ctx.list());
        }
    }

    private final Map<String, Object> parsedStrings = new HashMap<>();

    private Object parse(String s) {
        return parsedStrings.computeIfAbsent(s, k -> {
            PacketsLexer lexer = new PacketsLexer(CharStreams.fromString(k));
            PacketsParser parser = new PacketsParser(new CommonTokenStream(lexer));
            return new Visitor().visit(parser.root());
        });
    }

    private int compareList(List<Object> left, List<Object> right) {
        final int lsize = left.size();
        final int rsize = right.size();
        int i=0;
        while (true) {
            if (i >= lsize) return lsize == rsize ? 0 : -1;
            if (i >= rsize) return 1;
            int cmp = compare(left.get(i), right.get(i));
            if (cmp != 0) return cmp;
            i++;
        }
    }

    private int compare(Object left, Object right) {
        var l = left instanceof List;
        var r = right instanceof List;
        if (!l && !r) {
            if (((Integer)left) < ((Integer)right)) return  -1;
            if (((Integer)left) > ((Integer)right)) return  1;
            return 0;
        }
        List<Object> ll;
        if (l) {
            ll = (List<Object>) left;
        } else {
            ll = new ArrayList<>();
            ll.add(left);
        }
        List<Object> lr;
        if (r) {
            lr = (List<Object>) right;
        } else {
            lr = new ArrayList<>();
            lr.add(right);
        }
        return compareList(ll, lr);
    }

    @Override
    protected Object part1() {
        // part 1
        int result = 0;
        var parts = fileContents.split("\n\n");
        for (int i = 0; i < parts.length; i++) {
            var lines = parts[i].split("\n");
            if (compare(parse(lines[0]), parse(lines[1])) == -1) result += i + 1;
        }
        return result;
    }

    @Override
    protected Object part2() {
        // part 2
        var parts = fileContents.split("\n+");
        List<String> items = new ArrayList<>(Arrays.asList(parts));
        items.add("[[2]]");
        items.add("[[6]]");
        Collections.sort(items, (a,b) -> compare(parse(a), parse(b)));
        return (items.indexOf("[[2]]")+1) * (items.indexOf("[[6]]")+1);
    }
}
