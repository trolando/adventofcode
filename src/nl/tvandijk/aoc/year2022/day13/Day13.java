package nl.tvandijk.aoc.year2022.day13;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Day13 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
    }

    private Object convert(PacketsParser.ListContext context) {
        var r = new ArrayList<>();
        for (var c : context.children) {
            if (c instanceof PacketsParser.ListContext cl) r.add(convert(cl));
            else if (c instanceof PacketsParser.NumberContext cn) r.add(Integer.parseInt(cn.getText()));
        }
        return r;
    }

    private Object convert(String s) {
        PacketsLexer lexer = new PacketsLexer(CharStreams.fromString(s));
        PacketsParser parser = new PacketsParser(new CommonTokenStream(lexer));
        return convert(parser.root().list());
    }

    private int compareList(List<Object> left, List<Object> right) {
        for (int i = 0; i < left.size(); i++) {
            if (i >= right.size()) return 1;
            switch (compare(left.get(i), right.get(i))) {
                case -1 -> {
                    return -1;
                }
                case 1 -> {
                    return 1;
                }
            }
        }
        if (left.size() < right.size()) return -1;
        else return 0;
    }

    private int compare(Object left, Object right) {
        var l = left instanceof List;
        var r = right instanceof List;
        if (!l && !r) {
            if (((Integer)left) < ((Integer)right)) return  -1;
            if (((Integer)left) > ((Integer)right)) return  1;
            return 0;
        } else if (l && r) {
            var ll = (List<Object>) left;
            var lr = (List<Object>) right;
            return compareList(ll, lr);
        } else if (l) {
            var ll = (List<Object>) left;
            var lr = new ArrayList<Object>();
            lr.add(right);
            return compareList(ll, lr);
        } else {
            var ll = new ArrayList<Object>();
            ll.add(left);
            var lr = (List<Object>) right;
            return compareList(ll, lr);
        }
    }

    @Override
    protected Object part1() {
        // part 1
        int result = 0;
        var parts = fileContents.split("\n\n");
        for (int i = 0; i < parts.length; i++) {
            var lines = parts[i].split("\n");
            if (compare(convert(lines[0]), convert(lines[1])) == -1) result += i + 1;
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
        Collections.sort(items, (a,b) -> compare(convert(a), convert(b)));
        return (items.indexOf("[[2]]")+1) * (items.indexOf("[[6]]")+1);
    }
}
