package nl.tvandijk.aoc.util;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Chinese Remainder Theorem implementation.
 * Given a system of equations of the form cx≡a(mod b), this class can solve for x.
 * @see <a href="https://en.wikipedia.org/wiki/Chinese_remainder_theorem">Wikipedia</a>.
 */
public class ChineseRemainderTheorem {
    private List<Pair<Long,Long>> system = new ArrayList<>();

    /**
     * Create an empty Chinese Remainder Theorem object.
     */
    public ChineseRemainderTheorem() {
    }

    /**
     * Add an equation of the form x≡a(mod b) to the system.
     * @param a
     * @param b
     */
    public void addEquation(long a, long b) {
        system.add(Pair.of(a, b));
    }

    /**
     * Create a Chinese Remainder Theorem object from a list of equations.
     * @param a the remainders
     * @param m the moduli
     * @return a Chinese Remainder Theorem object
     */
    public static ChineseRemainderTheorem of(int[] a, int[] m) {
        if (a.length != m.length) throw new IllegalArgumentException();
        ChineseRemainderTheorem crt = new ChineseRemainderTheorem();
        for (int i = 0; i < a.length; i++) {
            crt.addEquation(a[i], m[i]);
        }
        return crt;
    }

    /**
     * Create a Chinese Remainder Theorem object from a list of equations.
     * @param a the remainders
     * @param m the moduli
     * @return a Chinese Remainder Theorem object
     */
    public static ChineseRemainderTheorem of(long[] a, long[] m) {
        if (a.length != m.length) throw new IllegalArgumentException();
        ChineseRemainderTheorem crt = new ChineseRemainderTheorem();
        for (int i = 0; i < a.length; i++) {
            crt.addEquation(a[i], m[i]);
        }
        return crt;
    }

    /**
     * Add an equation of the form cx≡a(mod m) to the system.
     * @param c the coefficient
     * @param a the remainder
     * @param m the modulus
     */
    public void addEquation(long c, long a, long m) {
        long d = egcd(c, m)[0];
        if (a % d == 0) {
            c /= d;
            a /= d;
            m /= d;

            long inv = egcd(c, m)[1];
            m = Math.abs(m);
            a = (((a * inv) % m) + m) % m;

            system.add(Pair.of(a, m));
        }
    }

    /**
     * Transform the set of equations to one with only pairwise co-prime moduli.
     * @return true if the system is solvable, false otherwise
     */
    public boolean reduce() {
        List<Pair<Long,Long>> split = new ArrayList<>();
        for (var equation : system) {
            List<Long> factors = primeFactorization(equation.b);
            factors.sort(null);
            for (int j = 0; j < factors.size(); j++) {
                long val = factors.get(j);
                long total = val;
                while (j + 1 < factors.size() && factors.get(j + 1) == val) {
                    total *= val;
                    j++;
                }
                split.add(Pair.of(equation.a % total, total));
            }
        }
        // For every PAIR of equations, check if they either repeat or have a conflict
        for (int i = 0; i < split.size(); i++) {
            for (int j = i + 1; j < split.size(); j++) {
                if (split.get(i).b % split.get(j).b == 0 || split.get(j).b % split.get(i).b == 0) {
                    // same prime factor
                    if (split.get(i).b > split.get(j).b) {
                        // same prime factor, i > j, check if equal modulo j
                        if ((split.get(i).a % split.get(j).b) == split.get(j).a) {
                            split.remove(j);
                            j--;
                        } else {
                            // conflict!
                            return false;
                        }
                    } else {
                        // same prime factor, j > i, check if equal modulo i
                        if ((split.get(j).a % split.get(i).b) == split.get(i).a) {
                            split.remove(i);
                            i--;
                            break;
                        } else {
                            // conflict!
                            return false;
                        }
                    }
                }
            }
        }
        system = split;
        return true;
    }

    /**
     * Solve the system of equations.
     * @return an array of two longs: x, M such that x is the solution modulo M
     */
    public long[] solve() {
        if (!reduce()) return null;

        // compute product of all moduli
        long M = 1;
        for (var equation : system) M *= equation.b;

        long x = 0;
        for (var equation : system) {
            long inv = egcd(M / equation.b, equation.b)[1];
            x += (M / equation.b) * equation.a * inv;
            x = ((x % M) + M) % M;
        }

        return new long[] {x, M};
    }

    /**
     * Prime factorization of a number.
     * @param n the number to factor
     * @return a list of prime factors of n
     */
    private static ArrayList<Long> primeFactorization(long n) {
        ArrayList<Long> factors = new ArrayList<>();
        if (n <= 0) throw new IllegalArgumentException();
        else if (n == 1) return factors;
        PriorityQueue<Long> divisorQueue = new PriorityQueue<>();
        divisorQueue.add(n);
        while (!divisorQueue.isEmpty()) {
            long divisor = divisorQueue.remove();
            if (isPrime(divisor)) {
                factors.add(divisor);
                continue;
            }
            long next = rho(divisor);
            if (next == divisor) {
                divisorQueue.add(divisor);
            } else {
                divisorQueue.add(next);
                divisorQueue.add(divisor / next);
            }
        }
        return factors;
    }

    /**
     * Pollard's rho algorithm to find a factor of a number.
     * @param n the number to factor
     * @return a factor of n
     */
    private static long rho(long n) {
        if (n % 2 == 0) return 2;
        // Get a number in the range [2, 10^6]
        long x = 2 + ThreadLocalRandom.current().nextLong(999999);
        long c = 2 + ThreadLocalRandom.current().nextLong(999999);
        long y = x;
        long d = 1;
        while (d == 1) {
            x = (x * x + c) % n;
            y = (y * y + c) % n;
            y = (y * y + c) % n;
            d = gcf(Math.abs(x - y), n);
            if (d == n) break;
        }
        return d;
    }

    /**
     * Greatest common factor.
     * @param a the first number
     * @param b the second number
     * @return the greatest common factor of a and b
     */
    private static long gcf(long a, long b) {
        return b == 0 ? a : gcf(b, a % b);
    }

    /**
     * Checks if a number is prime.
     * @param n the number to check
     * @return true if the number is prime, false otherwise
     */
    private static boolean isPrime(long n) {
        if (n < 2) return false;
        if (n == 2 || n == 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;

        int limit = (int) Math.sqrt(n);

        for (int i = 5; i <= limit; i += 6) if (n % i == 0 || n % (i + 2) == 0) return false;

        return true;
    }

    /**
     * Extended Euclidean algorithm.
     * @param a the first number
     * @param b the second number
     * @return an array of three longs: gcd(a,b), x, y such that ax + by = gcd(a,b)
     */
    private static long[] egcd(long a, long b) {
        if (b == 0) {
            return new long[] {a, 1, 0};
        } else {
            long[] res = egcd(b, a % b);
            long tmp = res[1] - res[2] * (a / b);
            res[1] = res[2];
            res[2] = tmp;
            return res;
        }
    }
}
