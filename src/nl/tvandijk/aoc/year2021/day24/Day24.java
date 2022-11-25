package nl.tvandijk.aoc.year2021.day24;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Day24 extends Day {
    interface Filter {
        boolean keep(DDleaf leaf);
    }

    private static class DDnode  {
        int x;
        int v;
        Object then, otherwise;

        public DDnode(int x, int v, Object then, Object otherwise) {
            this.x = x;
            this.v = v;
            this.then = then;
            this.otherwise = otherwise;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DDnode dDnode = (DDnode) o;
            return x == dDnode.x && v == dDnode.v && then.equals(dDnode.then) && otherwise.equals(dDnode.otherwise);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, v, then, otherwise);
        }
    }

    private static class DDten  {
        Object[] out;

        public DDten(Object[] out) {
            this.out = out;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DDten dDten = (DDten) o;
            return Arrays.equals(out, dDten.out);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(out);
        }
    }

    private static class DDleaf {
        int[] state;

        public DDleaf(int[] state) {
            this.state = state;
        }

        public DDleaf(DDleaf other) {
            this.state = Arrays.copyOf(other.state, 4);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DDleaf dDleaf = (DDleaf) o;
            return Arrays.equals(state, dDleaf.state);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(state);
        }
    }

    interface Updater {
        Object run(DDleaf state);
    }

    private static class Runner {
        Object states = new DDleaf(new int[4]);
        int x = 0;

        Map<DDleaf, DDleaf> leafs = new HashMap<>();
        Map<DDnode, DDnode> nodes = new HashMap<>();
        Map<DDten, DDten> tens = new HashMap<>();

        Map<Object, Object> cache = new HashMap<>();

        public DDleaf norm(DDleaf state) {
            var l = leafs.getOrDefault(state, null);
            if (l != null) {
                return l;
            }
            leafs.put(state, state);
            return state;
        }

        public DDnode norm(DDnode node) {
            var n = nodes.getOrDefault(node, null);
            if (n != null) return n;
            nodes.put(node, node);
            return node;
        }

        public DDten norm(DDten node) {
            var n = tens.getOrDefault(node, null);
            if (n != null) return n;
            tens.put(node, node);
            return node;
        }

        public Object norm(Object o) {
            if (o instanceof DDnode) return norm((DDnode) o);
            else if (o instanceof DDten) return norm((DDten) o);
            else return norm((DDleaf) o);
        }

        public void runProgram(String[] lines) {
            int lineno=0;
            for (var line : lines) {
                System.out.println("Running line " + lineno + " " + line);
                lineno++;
                runLine(line);
            }
        }

        public void runLine(String line) {
            cache = new HashMap<>();
            var l = line.split(" ");

            int a = switch (l[1]) {
                case "w" -> 0;
                case "x" -> 1;
                case "y" -> 2;
                case "z" -> 3;
                default -> -1;
            };
            int b = 0;
            boolean bv = true;
            if (l.length > 2) {
                switch (l[2]) {
                    case "w" -> b = 0;
                    case "x" -> b = 1;
                    case "y" -> b = 2;
                    case "z" -> b = 3;
                    default -> {
                        bv = false;
                        b = Integer.parseInt(l[2]);
                    }
                }
            }
            final int _b = b;
            final boolean _bv = bv;
            switch (l[0]) {
                case "inp" -> {
                    final int _x = x;
                    states = runLine(states, (Updater) state -> {
                        var cc = cache.getOrDefault(state, null);
                        if (cc != null) return cc;
                        Object[] n = new Object[10];
                        for (int i = 0; i < 10; i++) {
                            DDleaf news = new DDleaf(state);
                            news.state[a] = i;
                            n[i] = norm(news);
                        }
                        var res = new DDten(n);
                        cache.put(state, res);
                        return res;
                    });
                    x++;
                }
                case "add" -> {
                    states = runLine(states, (Updater) state -> {
                        var cc = cache.getOrDefault(state, null);
                        if (cc != null) return cc;
                        DDleaf news = new DDleaf(state);
                        news.state[a] += _bv ? news.state[_b] : _b;
                        var res = norm(news);
                        cache.put(state, res);
                        return res;
                    });
                }
                case "mul" -> {
                    states = runLine(states, (Updater) state -> {
                        var cc = cache.getOrDefault(state, null);
                        if (cc != null) return cc;
                        DDleaf news = new DDleaf(state);
                        news.state[a] *= _bv ? news.state[_b] : _b;
                        var res = norm(news);
                        cache.put(state, res);
                        return res;
                    });
                }
                case "div" -> {
                    states = runLine(states, (Updater) state -> {
                        var cc = cache.getOrDefault(state, null);
                        if (cc != null) return cc;
                        DDleaf news = new DDleaf(state);
                        news.state[a] /= _bv ? news.state[_b] : _b;
                        var res = norm(news);
                        cache.put(state, res);
                        return res;
                    });
                }
                case "mod" -> {
                    states = runLine(states, (Updater) state -> {
                        var cc = cache.getOrDefault(state, null);
                        if (cc != null) return cc;
                        DDleaf news = new DDleaf(state);
                        news.state[a] %= _bv ? news.state[_b] : _b;
                        var res = norm(news);
                        cache.put(state, res);
                        return res;
                    });
                }
                case "eql" -> {
                    states = runLine(states, (Updater) state -> {
                        var cc = cache.getOrDefault(state, null);
                        if (cc != null) return cc;
                        DDleaf news = new DDleaf(state);
                        news.state[a] = news.state[a] == (_bv ? news.state[_b] : _b) ? 1 : 0;
                        var res = norm(news);
                        cache.put(state, res);
                        return res;
                    });
                }
            }
        }


        public void runLineBackwards(String line) {
            cache = new HashMap<>();
            var l = line.split(" ");

            int a = switch (l[1]) {
                case "w" -> 0;
                case "x" -> 1;
                case "y" -> 2;
                case "z" -> 3;
                default -> -1;
            };
            int b = 0;
            boolean bv = true;
            if (l.length > 2) {
                switch (l[2]) {
                    case "w" -> b = 0;
                    case "x" -> b = 1;
                    case "y" -> b = 2;
                    case "z" -> b = 3;
                    default -> {
                        bv = false;
                        b = Integer.parseInt(l[2]);
                    }
                }
            }
            final int _b = b;
            final boolean _bv = bv;
            switch (l[0]) {
                case "inp" -> {
                    final int _x = x;
                    states = runLine(states, (Updater) state -> {
                        var cc = cache.getOrDefault(state, null);
                        if (cc != null) return cc;
                        Object[] n = new Object[10];
                        for (int i = 0; i < 10; i++) {
                            DDleaf news = new DDleaf(state);
                            news.state[a] = i;
                            n[i] = norm(news);
                        }
                        var res = new DDten(n);
                        cache.put(state, res);
                        return res;
                    });
                    x++;
                }
                case "add" -> {
                    states = runLine(states, (Updater) state -> {
                        var cc = cache.getOrDefault(state, null);
                        if (cc != null) return cc;
                        DDleaf news = new DDleaf(state);
                        news.state[a] += _bv ? news.state[_b] : _b;
                        var res = norm(news);
                        cache.put(state, res);
                        return res;
                    });
                }
                case "mul" -> {
                    states = runLine(states, (Updater) state -> {
                        var cc = cache.getOrDefault(state, null);
                        if (cc != null) return cc;
                        DDleaf news = new DDleaf(state);
                        news.state[a] *= _bv ? news.state[_b] : _b;
                        var res = norm(news);
                        cache.put(state, res);
                        return res;
                    });
                }
                case "div" -> {
                    states = runLine(states, (Updater) state -> {
                        var cc = cache.getOrDefault(state, null);
                        if (cc != null) return cc;
                        DDleaf news = new DDleaf(state);
                        news.state[a] /= _bv ? news.state[_b] : _b;
                        var res = norm(news);
                        cache.put(state, res);
                        return res;
                    });
                }
                case "mod" -> {
                    states = runLine(states, (Updater) state -> {
                        var cc = cache.getOrDefault(state, null);
                        if (cc != null) return cc;
                        DDleaf news = new DDleaf(state);
                        news.state[a] %= _bv ? news.state[_b] : _b;
                        var res = norm(news);
                        cache.put(state, res);
                        return res;
                    });
                }
                case "eql" -> {
                    states = runLine(states, (Updater) state -> {
                        var cc = cache.getOrDefault(state, null);
                        if (cc != null) return cc;
                        DDleaf news = new DDleaf(state);
                        news.state[a] = news.state[a] == (_bv ? news.state[_b] : _b) ? 1 : 0;
                        var res = norm(news);
                        cache.put(state, res);
                        return res;
                    });
                }
            }
        }

        public Object runLine(Object state, Updater updater) {
            if (state == null) return null;
            var cc = cache.getOrDefault(state, null);
            if (cc != null) return cc;
            if (state instanceof DDnode) {
                var s = (DDnode) state;
                s.then = norm(runLine(s.then, updater));
                s.otherwise = norm(runLine(s.otherwise, updater));
                var res = state;
                cache.put(state, res);
                return res;
            } else if (state instanceof DDten) {
                var s = (DDten) state;
                var n = new Object[s.out.length];
                for (int i = 0; i < s.out.length; i++) {
                    n[i] = norm(runLine(s.out[i], updater));
                }
                var res = norm(new DDten(n));
                cache.put(state, res);
                return res;
            } else {
                var res = updater.run((DDleaf) state);
                cache.put(state, res);
                return res;
            }
        }

        public void filter(Filter f) {
            states = filter(states, f);
        }

        public Object filter(Object state, Filter f) {
            if (state == null) return null;
            var cc = cache.getOrDefault(state, null);
            if (cc != null) return cc;
            if (state instanceof DDnode) {
                var s = (DDnode) state;
                s.then = norm(filter(s.then, f));
                s.otherwise = norm(filter(s.otherwise, f));
                var res = state;
                cache.put(state, res);
                return res;
            } else if (state instanceof DDten) {
                var s = (DDten) state;
                var n = new Object[s.out.length];
                for (int i = 0; i < s.out.length; i++) {
                    n[i] = norm(filter(s.out[i], f));
                }
                var res = norm(new DDten(n));
                cache.put(state, res);
                return res;
            } else {
                var res = f.keep((DDleaf) state) ? state : null;
                cache.put(state, res);
                return res;
            }
        }
    }

    private interface Expr {
        default Expr simp() { return this; }
        Pair<Long,Long> range();
        Expr eval(int[] inputs);
        Expr lift();
        long size();
    }

    abstract class Binary implements Expr {
        Expr left, right;

        public Binary(Expr left, Expr right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public long size() {
            return left.size() + right.size() + 1;
        }
    }

    private HashMap<Expr, Expr> normal = new HashMap<>();

    Expr norm(Expr e) {
        Expr ee = normal.getOrDefault(e, null);
        if (ee != null) return ee;
        normal.put(e, e);
        return e;
    }

    private class Add extends Binary {
        public Add(Expr left, Expr right) {
            super(left, right);
        }

        @Override
        public Expr simp() {
            if (left instanceof Lit && ((Lit)left).value == 0) return right;
            if (right instanceof Lit && ((Lit)right).value == 0) return left;
            if (left instanceof Lit && right instanceof Lit) {
                return new Lit(((Lit)left).value + ((Lit)right).value);
            }
            return this;
        }

        @Override
        public String toString() {
            return "(" + left + " + " + right + ")";
        }

        @Override
        public Pair<Long, Long> range() {
            var rl = left.range();
            var rr = right.range();
            return Pair.of(rl.a+rr.a, rl.b+rr.b);
        }

        @Override
        public Expr eval(int[] inputs) {
            return norm(new Add(left.eval(inputs), right.eval(inputs)).simp());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Add eq = (Add) o;
            return left.equals(eq.left) && right.equals(eq.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }

        @Override
        public Expr lift() {
            if (left instanceof Digit) {
                var d = (Digit) left;
                Expr[] res = new Expr[d.pos.length];
                for (int i = 0; i < d.pos.length; i++) res[i] = norm(new Add(d.pos[i], right).simp());
                return norm(new Digit(d.index, res).simp());
            } else if (right instanceof Digit) {
                var d = (Digit) right;
                Expr[] res = new Expr[d.pos.length];
                for (int i = 0; i < d.pos.length; i++) res[i] = norm(new Add(left, d.pos[i]).simp());
                return norm(new Digit(d.index, res).simp());
            } else {
                return norm(new Add(left.lift(), right.lift()).simp());
            }
        }
    }

    private class Mult extends Binary {
        public Mult(Expr left, Expr right) {
            super(left, right);
        }

        @Override
        public Expr simp() {
            if (left instanceof Lit && ((Lit)left).value == 0) return left;
            if (right instanceof Lit && ((Lit)right).value == 0) return right;
            if (left instanceof Lit && ((Lit)left).value == 1) return right;
            if (right instanceof Lit && ((Lit)right).value == 1) return left;
            if (left instanceof Lit && right instanceof Lit) {
                return new Lit(((Lit)left).value * ((Lit)right).value);
            }
            if (left instanceof Add && right instanceof Lit) {
                var al = (Add) left;
                return norm(new Add(new Mult(al.left, right).simp(), new Mult(al.right, right).simp()).simp());
            }
            if (left instanceof Digit && right instanceof Lit) {
                return lift(); // always move this inwards
            }
            return this;
        }

        @Override
        public String toString() {
            return "(" + left + " * " + right + ")";
        }

        @Override
        public Pair<Long, Long> range() {
            var rl = left.range();
            var rr = right.range();
            return Pair.of(rl.a*rr.a, rl.b*rr.b);
        }

        @Override
        public Expr eval(int[] inputs) {
            return norm(new Mult(left.eval(inputs), right.eval(inputs)).simp());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Mult eq = (Mult) o;
            return left.equals(eq.left) && right.equals(eq.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }

        @Override
        public Expr lift() {
            if (left instanceof Digit) {
                var d = (Digit) left;
                Expr[] res = new Expr[d.pos.length];
                for (int i = 0; i < d.pos.length; i++) res[i] = norm(new Mult(d.pos[i], right).simp());
                return norm(new Digit(d.index, res).simp());
            } else if (right instanceof Digit) {
                var d = (Digit) right;
                Expr[] res = new Expr[d.pos.length];
                for (int i = 0; i < d.pos.length; i++) res[i] = norm(new Mult(left, d.pos[i]).simp());
                return norm(new Digit(d.index, res).simp());
            } else {
                return norm(new Mult(left.lift(), right.lift()).simp());
            }
        }
    }

    private class Div extends Binary {
        public Div(Expr left, Expr right) {
            super(left, right);
        }

        @Override
        public Expr simp() {
            if (left instanceof Lit && ((Lit)left).value == 0) return left;
            if (right instanceof Lit && ((Lit)right).value == 1) return left;
            if (left == right) return new Lit(1);
            if (left instanceof Lit && right instanceof Lit) {
                return new Lit(((Lit)left).value / ((Lit)right).value);
            }
            if (left instanceof Add && right instanceof Lit) {
                var al = (Add) left;
                return norm(new Add(norm(new Div(al.left, right).simp()), norm(new Div(al.right, right).simp())).simp());
            }
            if (left instanceof Digit && right instanceof Lit) {
                return lift(); // always move this inwards
            }
            return this;
        }

        @Override
        public String toString() {
            return "(" + left + " / " + right + ")";
        }

        @Override
        public Pair<Long, Long> range() {
            var rl = left.range();
            var rr = right.range();
            return Pair.of(rl.a/rr.a, rl.b/rr.b);
        }

        @Override
        public Expr eval(int[] inputs) {
            return norm(new Div(left.eval(inputs), right.eval(inputs)).simp());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Div eq = (Div) o;
            return left.equals(eq.left) && right.equals(eq.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }

        @Override
        public Expr lift() {
            if (left instanceof Digit) {
                var d = (Digit) left;
                Expr[] res = new Expr[d.pos.length];
                for (int i = 0; i < d.pos.length; i++) res[i] = norm(new Div(d.pos[i], right).simp());
                return norm(new Digit(d.index, res).simp());
            } else if (right instanceof Digit) {
                var d = (Digit) right;
                Expr[] res = new Expr[d.pos.length];
                for (int i = 0; i < d.pos.length; i++) res[i] = norm(new Div(left, d.pos[i]).simp());
                return norm(new Digit(d.index, res).simp());
            } else {
                return norm(new Mult(left.lift(), right.lift()).simp());
            }
        }
    }

    private class Mod extends Binary {
        public Mod(Expr left, Expr right) {
            super(left, right);
        }

        @Override
        public Expr simp() {
            if (left instanceof Lit && ((Lit)left).value == 0) return left;
            if (left instanceof Lit && right instanceof Lit) {
                return new Lit(((Lit)left).value % ((Lit)right).value);
            }
            return this;
        }

        @Override
        public String toString() {
            return "(" + left + " % " + right + ")";
        }

        @Override
        public Pair<Long, Long> range() {
            var rl = left.range();
            var rr = right.range();
            return Pair.of(0L, Math.min(rl.b, rr.b));
        }

        @Override
        public Expr eval(int[] inputs) {
            return norm(new Mod(left.eval(inputs), right.eval(inputs)).simp());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Mod eq = (Mod) o;
            return left.equals(eq.left) && right.equals(eq.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }

        @Override
        public Expr lift() {
            if (left instanceof Digit) {
                var d = (Digit) left;
                Expr[] res = new Expr[d.pos.length];
                for (int i = 0; i < d.pos.length; i++) res[i] = norm(new Mod(d.pos[i], right).simp());
                return norm(new Digit(d.index, res).simp());
            } else if (right instanceof Digit) {
                var d = (Digit) right;
                Expr[] res = new Expr[d.pos.length];
                for (int i = 0; i < d.pos.length; i++) res[i] = norm(new Mod(left, d.pos[i]).simp());
                return norm(new Digit(d.index, res).simp());
            } else {
                return norm(new Mod(left.lift(), right.lift()).simp());
            }
        }
    }

    private class Eq extends Binary {
        public Eq(Expr left, Expr right) {
            super(left, right);
        }

        @Override
        public Expr simp() {
            if (right instanceof Inp) {
                var r = (Inp) right;
                if (left instanceof Lit) {
                    var l = (Lit) left;
                    if (l.value >= 10) return new Lit(0);
                }
            }
            if (left instanceof Inp) {
                var r = (Inp) left;
                if (right instanceof Lit) {
                    var l = (Lit) right;
                    if (l.value >= 10) return new Lit(0);
                }
            }
            if (left instanceof Lit && right instanceof Lit) {
                return new Lit(((Lit)left).value == ((Lit)right).value ? 1L : 0L);
            }
            var rl = left.range();
            var rr = right.range();
            if (rl.b < rr.a || rr.b < rl.a) return new Lit(0);
            return this;
        }

        @Override
        public String toString() {
            return "(" + left + " == " + right + ")";
        }

        @Override
        public Pair<Long, Long> range() {
            return Pair.of(0L, 1L);
        }

        @Override
        public Expr eval(int[] inputs) {
            return norm(new Eq(left.eval(inputs), right.eval(inputs)).simp());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Eq eq = (Eq) o;
            return left.equals(eq.left) && right.equals(eq.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }

        @Override
        public Expr lift() {
            if (left instanceof Digit) {
                var d = (Digit) left;
                Expr[] res = new Expr[d.pos.length];
                for (int i = 0; i < d.pos.length; i++) res[i] = norm(new Eq(d.pos[i], right).simp());
                return norm(new Digit(d.index, res).simp());
            } else if (right instanceof Digit) {
                var d = (Digit) right;
                Expr[] res = new Expr[d.pos.length];
                for (int i = 0; i < d.pos.length; i++) res[i] = norm(new Eq(left, d.pos[i]).simp());
                return norm(new Digit(d.index, res).simp());
            } else {
                return norm(new Eq(left.lift(), right.lift()).simp());
            }
        }
    }

    private class Inp implements Expr {
        int index;

        public Inp(int index) {
            this.index = index;
        }

        @Override
        public Expr simp() {
            return this;
        }

        @Override
        public String toString() {
            return "I_" + index;
        }

        @Override
        public Pair<Long, Long> range() {
            return Pair.of(0L, 9L);
        }

        @Override
        public Expr eval(int[] inputs) {
            return norm(inputs[index] == -1 ? this : new Lit(inputs[index]));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Inp inp = (Inp) o;
            return index == inp.index;
        }

        @Override
        public int hashCode() {
            return Objects.hash(index);
        }

        @Override
        public Expr lift() {
            return null;
        }

        @Override
        public long size() {
            return 1;
        }
    }

    private class Digit implements Expr {
        int index;
        Expr[] pos;

        public Digit(int index) {
            this.index = index;
            this.pos = new Expr[9]; // values 1 through 9
            for (int i = 0; i < 9; i++) {
                this.pos[i] = norm(new Lit(i+1));
            }
        }

        public Digit(int index, Expr[] pos) {
            this.index = index;
            this.pos = pos;
        }

        @Override
        public Expr simp() {
            var e = pos[0];
            for (int i = 0; i < pos.length; i++) {
                if (e != pos[i]) return this;
            }
            return e;
        }

        @Override
        public String toString() {
            return "Digit{" +
                    "index=" + index +
                    ", pos=" + Arrays.toString(pos) +
                    '}';
        }

        @Override
        public Pair<Long, Long> range() {
            long min = Long.MAX_VALUE;
            long max = Long.MIN_VALUE;
            for (int i = 0; i < pos.length; i++) {
                var r = pos[i].range();
                min = Long.min(r.a, min);
                max = Long.max(r.b, max);
            }
            return Pair.of(min, max);
        }

        @Override
        public Expr eval(int[] inputs) {
            return norm(inputs[index] == -1 ? this : new Lit(inputs[index]));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Digit digit = (Digit) o;
            return index == digit.index && Arrays.equals(pos, digit.pos);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(index);
            result = 31 * result + Arrays.hashCode(pos);
            return result;
        }

        @Override
        public Expr lift() {
            Expr[] res = new Expr[pos.length];
            for (int i = 0; i < pos.length; i++) {
                res[i] = pos[i].lift();
            }
            return norm(new Digit(index, res).simp());
        }

        @Override
        public long size() {
            long size = 1;
            for (int i = 0; i < pos.length; i++) {
                size += pos[i].size();
            }
            return size;
        }
    }

    private class Lit implements Expr {
        long value;

        public Lit(long value) {
            this.value = value;
        }

        @Override
        public Expr simp() {
            return this;
        }

        @Override
        public String toString() {
            return Long.toString(value);
        }

        @Override
        public Pair<Long, Long> range() {
            return Pair.of(value, value);
        }

        @Override
        public Expr eval(int[] inputs) {
            return norm(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Lit lit = (Lit) o;
            return value == lit.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public Expr lift() {
            return this;
        }

        @Override
        public long size() {
            return 0;
        }
    }

    Expr lift(Expr e) {
        // TODO cache
        return e.lift();
    }

    @Override
    protected Object part1() {
        var lines = fileContents.split(System.lineSeparator());

        Expr[] state = new Expr[4];
        state[0] = new Lit(0L);
        state[1] = new Lit(0L);
        state[2] = new Lit(0L);
        state[3] = new Lit(0L);
        int x = 0;

        int lineno=0;
        for (var line : lines) {
            var l = line.split(" ");
            if (l[0].equals("inp")) {
                System.out.println();
            }
            int a = switch (l[1]) {
                case "w" -> 0;
                case "x" -> 1;
                case "y" -> 2;
                case "z" -> 3;
                default -> -1;
            };
            int b = 0;
            boolean bv = true;
            if (l.length > 2) {
                switch (l[2]) {
                    case "w" -> b = 0;
                    case "x" -> b = 1;
                    case "y" -> b = 2;
                    case "z" -> b = 3;
                    default -> {
                        bv = false;
                        b = Integer.parseInt(l[2]);
                    }
                }
            }
            switch (l[0]) {
                case "inp" -> state[a] = norm(new Digit(x++).simp());
                case "add" -> state[a] = norm(new Add(state[a], bv ? state[b] : new Lit(b)).simp());
                case "mul" -> state[a] = norm(new Mult(state[a], bv ? state[b] : new Lit(b)).simp());
                case "div" -> state[a] = norm(new Div(state[a], bv ? state[b] : new Lit(b)).simp());
                case "mod" -> state[a] = norm(new Mod(state[a], bv ? state[b] : new Lit(b)).simp());
                case "eql" -> state[a] = norm(new Eq(state[a], bv ? state[b] : new Lit(b)).simp());
            }

            System.out.println();
            System.out.println("Line " + lineno++ + " " + line);
            for (int i = 0; i < 4; i++) {
                var t = state[i];
                Expr nt = null;
                while (t != nt) {
                    nt = t;
                    t = lift(t);
                    if (t.size() > nt.size()) break;
                }
                state[i] = nt;
            }
            System.out.println("w = " + state[0]);
            System.out.println("x = " + state[1]);
            System.out.println("y = " + state[2]);
            System.out.println("z = " + state[3]);
        }

        var e = state[3];
        e = lift(e);
        e = lift(e);
        e = lift(e);
        e = lift(e);
        e = lift(e);
        e = lift(e);
        e = lift(e);
        e = lift(e);
        e = lift(e);

        dfs(state[3]);

//        var r = new Runner();
//        r.runProgram(lines);

//        r.filter(state -> state.state[3] == 0);

        return null;
    }

    void dfs(Expr e) {
        dfs(e, 0, new int[] {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
    }

    void dfs(Expr e, int index, int[] vals) {
        for (int i = 0; i < 9; i++) {
            vals[index] = 9-i;
            Expr ee = e.eval(vals);
            if (ee instanceof Lit) {
                if (((Lit)ee).value == 0) {
                    System.out.println("ex " + Arrays.toString(vals) + ": " + ((Lit) ee).value);
                }
            } else {
                dfs(ee, index + 1, vals);
            }
        }
        vals[index] = -1;
    }

    @Override
    protected Object part2() {
        return null;
    }
}
