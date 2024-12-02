package nl.tvandijk.aoc.util;

/**
 * Utility class extending a Pair<A, B> so that can be compared to another pair.
 * By default, element a is compared first, followed by element b if the a's are equal
 */
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
