package nl.tvandijk.aoc.util;

import java.util.Objects;

public class Triple<A, B, C> {
    public A a;
    public B b;
    public C c;

    public Triple(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public static <A, B, C> Triple<A, B, C> of(A a, B b, C c) {
        return new Triple<>(a, b, c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triple<?, ?, ?> pair = (Triple<?, ?, ?>) o;
        return Objects.equals(a, pair.a) && Objects.equals(b, pair.b) && Objects.equals(c, pair.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }

    @Override
    public String toString() {
        return "<" + a + ", " + b + ", " + c + ">";
    }
}
