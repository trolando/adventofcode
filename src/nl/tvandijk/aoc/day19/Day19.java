package nl.tvandijk.aoc.day19;

import nl.tvandijk.aoc.common.AoCCommon;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day19 extends AoCCommon {
    private final Map<Integer, Matcher> rules = new HashMap<>(); // all the rules

    public void parseRule(String line) {
        var lexer = new MessageRulesLexer(CharStreams.fromString(line));
        var parser = new MessageRulesParser(new CommonTokenStream(lexer));
        var tree = parser.parserule();
        int id = Integer.parseInt(tree.id.getText());
        Matcher m = tree.expr().accept(new MatcherVisitor());
        rules.put(id, m);
    }

    public boolean matches(String s) {
        var res = rules.get(0).match(s, rules);
        return res.contains("");
    }

    @Override
    public void process(InputStream stream) throws IOException {
        var lexer = new MessageRulesLexer(CharStreams.fromStream(stream));
        var parser = new MessageRulesParser(new CommonTokenStream(lexer));
        var tree = parser.start();

        for (var ruleCtx : tree.parserule()) {
            int id = Integer.parseInt(ruleCtx.id.getText());
            Matcher m = ruleCtx.expr().accept(new MatcherVisitor());
            rules.put(id, m);
        }

        List<String> words = tree.word().stream()
                .map(RuleContext::getText)
                .collect(Collectors.toUnmodifiableList());

        var good = words.stream().filter(this::matches).count();
        System.out.printf("Number of matches (pre-patch): %d\n", good);

        // patch rules
        parseRule("8: 42 | 42 8");
        parseRule("11: 42 31 | 42 11 31");

        good = words.stream().filter(this::matches).count();
        System.out.printf("Number of matches (post-patch): %d\n", good);
    }

    public static void main(String[] args) {
        run(Day19::new, "example1.txt", "example2.txt", "input.txt");
    }
}