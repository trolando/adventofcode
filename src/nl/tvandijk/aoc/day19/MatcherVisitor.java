package nl.tvandijk.aoc.day19;

public class MatcherVisitor extends MessageRulesBaseVisitor<Matcher> {
    @Override
    public Matcher visitSerialExpr(MessageRulesParser.SerialExprContext ctx) {
        return new SerialMatcher(visit(ctx.left), visit(ctx.right));
    }

    @Override
    public Matcher visitOrExpr(MessageRulesParser.OrExprContext ctx) {
        return new OrMatcher(visit(ctx.left), visit(ctx.right));
    }

    @Override
    public Matcher visitSubruleExpr(MessageRulesParser.SubruleExprContext ctx) {
        return new RuleMatcher(Integer.parseInt(ctx.id.getText()));
    }

    @Override
    public Matcher visitLetterExpr(MessageRulesParser.LetterExprContext ctx) {
        return new AtomMatcher(ctx.ch.getText().charAt(0));
    }
}
