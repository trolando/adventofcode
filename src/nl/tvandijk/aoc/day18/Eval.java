package nl.tvandijk.aoc.day18;

public class Eval extends ExprBaseVisitor<Long> {
    @Override
    public Long visitOpExpr(ExprParser.OpExprContext ctx) {
        var left = visit(ctx.left);
        var right = visit(ctx.right);
        switch (ctx.op.getText().charAt(0)) {
            case '+':
                return left + right;
            case '*':
                return left * right;
            default:
                return 0L;
        }
    }

    @Override
    public Long visitAtomExpr(ExprParser.AtomExprContext ctx) {
        return Long.valueOf(ctx.getText());
    }

    @Override
    public Long visitParenExpr(ExprParser.ParenExprContext ctx) {
        return this.visit(ctx.expr());
    }
}
