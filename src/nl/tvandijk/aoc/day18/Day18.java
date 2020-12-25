package nl.tvandijk.aoc.day18;

import nl.tvandijk.aoc.common.AoCCommon;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.io.InputStream;

public class Day18 extends AoCCommon {
    @Override
    public void process(InputStream stream) throws IOException {
        var lexer = new ExprLexer(CharStreams.fromStream(stream));
        var parser = new ExprParser(new CommonTokenStream(lexer));
        var tree = parser.start();

        long sum = tree.expr().stream()
                .mapToLong(ctx -> {
                    Long answer = new Eval().visit(ctx);
                    System.out.printf("%s = %d\n", ctx.getText(), answer);
                    return answer;
                }).sum();

        System.out.printf("total: %d\n", sum);
    }

    public static void main(String[] args) {
        run(Day18::new, "example.txt", "input.txt");
    }
}