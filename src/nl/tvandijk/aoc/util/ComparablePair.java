package nl.tvandijk.aoc.util;

public class ComparablePair<A extends Comparable<A>, B extends Comparable<B>> extends Pair<A, B> implements Comparable<ComparablePair<A, B>> {
    public ComparablePair(A a, B b) {
        super(a, b);
    }

    @Override
    public int compareTo(ComparablePair<A, B> o) {
        if (a.equals(o.a)) return b.compareTo(o.b);
        else return a.compareTo(o.a);
    }
}
