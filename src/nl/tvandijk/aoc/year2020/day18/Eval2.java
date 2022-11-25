package nl.tvandijk.aoc.year2020.day18;

public class Eval2 extends Expr2BaseVisitor<Long> {
    @Override
    public Long visitOpExpr(Expr2Parser.OpExprContext ctx) {
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
    public Long visitAtomExpr(Expr2Parser.AtomExprContext ctx) {
        return Long.valueOf(ctx.getText());
    }

    @Override
    public Long visitParenExpr(Expr2Parser.ParenExprContext ctx) {
        return this.visit(ctx.expr());
    }
}
