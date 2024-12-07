package nl.tvandijk.aoc.year2024.day7;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayDeque;
import java.util.Arrays;

public class Day7 extends Day {
    boolean findAny(long answer, long[] elements, int i, long a, boolean part2) {
        if (i == elements.length) return answer == a;
        if (findAny(answer, elements, i + 1, a + elements[i], part2)) return true;
        if (findAny(answer, elements, i + 1, a * elements[i], part2)) return true;
        return part2 && findAny(answer, elements, i + 1, Long.parseLong(String.valueOf(a) + elements[i]), true);
    }

    boolean findAny(long answer, long[] elements, boolean part2) {
        return findAny(answer, elements, 1, elements[0], part2);
    }

    private static boolean findAny2(long answer, long[] elements, int n, boolean part2) {
        if (n == 0) return answer == elements[0];
        if (answer % elements[n] == 0 && findAny2(answer / elements[n], elements, n - 1, part2)) return true;
        if (answer > elements[n] && findAny2(answer - elements[n], elements, n - 1, part2)) return true;
        if (!part2) return false;
        var m = elements[n];
        while (m != 0) {
            if (m % 10 != answer % 10) return false;
            m /= 10;
            answer /= 10;
        }
        return findAny2(answer, elements, n - 1, part2);
    }

    private static boolean findAny2(long answer, long[] elements, boolean part2) {
        return findAny2(answer, elements, elements.length - 1, part2);
    }

    private static boolean findAny3(long answer, long[] elements, boolean part2) {
        var deque = new ArrayDeque<Long>();
        deque.push(answer);
        deque.push((long) (elements.length-1));
        while (!deque.isEmpty()) {
            int n = deque.pop().intValue();
            long a = deque.pop();
            if (n == 0) {
                if (a == elements[0]) return true;
            } else if (a >= elements[n]) {
                deque.push(a - elements[n]);
                deque.push((long) n - 1);
                if (a % elements[n] == 0) {
                    deque.push(a / elements[n]);
                    deque.push((long) n - 1);
                }
                if (part2) {
                    var m = elements[n];
                    while (m != 0) {
                        if (m % 10 != a % 10) break;
                        m /= 10;
                        a /= 10;
                    }
                    if (m == 0) {
                        deque.push(a);
                        deque.push((long) n - 1);
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected Object part1() {
        // part 1
        long sum = 0;
        for (var line : lines) {
            var parts = line.split(": ");
            var part2 = parts[1].split("\\s+");
            var ans = Long.parseLong(parts[0]);
            var elements = Arrays.stream(part2).mapToLong(Long::parseLong).toArray();
            if (findAny3(ans, elements, false)) sum += ans;
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        long sum = 0;
        for (var line : lines) {
            var parts = line.split(": ");
            var part2 = parts[1].split("\\s+");
            var ans = Long.parseLong(parts[0]);
            var elements = Arrays.stream(part2).mapToLong(Long::parseLong).toArray();
            if (findAny3(ans, elements, true)) sum += ans;
        }
        return sum;
    }
}
