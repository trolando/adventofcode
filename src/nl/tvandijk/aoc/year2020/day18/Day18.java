package nl.tvandijk.aoc.year2020.day18;

import nl.tvandijk.aoc.common.Day;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Day18 extends Day {
    @Override
    protected Object part1() {
        var lexer = new Expr1Lexer(CharStreams.fromString(fileContents));
        var parser = new Expr1Parser(new CommonTokenStream(lexer));
        var tree = parser.start();

        return tree.expr().stream().mapToLong(ctx -> new Eval1().visit(ctx)).sum();
    }

    @Override
    protected Object part2() throws Exception {
        var lexer = new Expr2Lexer(CharStreams.fromString(fileContents));
        var parser = new Expr2Parser(new CommonTokenStream(lexer));
        var tree = parser.start();

        return tree.expr().stream().mapToLong(ctx -> new Eval2().visit(ctx)).sum();
    }
}