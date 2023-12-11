package nl.tvandijk.aoc.util;

import java.util.*;
import java.util.function.Predicate;

public class Util {
    public static <T> Set<Pair<T,T>> getPairs(Collection<T> collection) {
        var res = new HashSet<Pair<T,T>>();
        var list = new ArrayList<>(collection);
        for (int i = 0; i < list.size(); i++) {
            var a = list.get(i);
            for (int j = i+1; j < list.size(); j++) {
                var b = list.get(j);
                res.add(Pair.of(a, b));
            }
        }
        return res;
    }

    public static <T> List<List<T>> splitList(List<T> list, Predicate<T> condition) {
        List<List<T>> result = new ArrayList<>();
        List<T> currentSubList = new ArrayList<>();

        for (T element : list) {
            if (condition.test(element)) {
                if (!currentSubList.isEmpty()) {
                    result.add(currentSubList);
                    currentSubList = new ArrayList<>();
                }
            } else {
                currentSubList.add(element);
            }
        }

        if (!currentSubList.isEmpty()) {
            result.add(currentSubList);
        }

        return result;
    }

    public static <T> List<List<T>> splitArray(T[] list, Predicate<T> condition) {
        List<List<T>> result = new ArrayList<>();
        List<T> currentSubList = new ArrayList<>();

        for (T element : list) {
            if (condition.test(element)) {
                if (!currentSubList.isEmpty()) {
                    result.add(currentSubList);
                    currentSubList = new ArrayList<>();
                }
            } else {
                currentSubList.add(element);
            }
        }

        if (!currentSubList.isEmpty()) {
            result.add(currentSubList);
        }

        return result;
    }

    public static <T> List<List<T>> splitListAfter(List<T> list, Predicate<T> condition) {
        List<List<T>> result = new ArrayList<>();
        List<T> currentSubList = new ArrayList<>();

        for (T element : list) {
            currentSubList.add(element);
            if (condition.test(element)) {
                result.add(currentSubList);
                currentSubList = new ArrayList<>();
            }
        }

        if (!currentSubList.isEmpty()) {
            result.add(currentSubList);
        }

        return result;
    }

    public static <T> List<List<T>> splitArrayAfter(T[] list, Predicate<T> condition) {
        List<List<T>> result = new ArrayList<>();
        List<T> currentSubList = new ArrayList<>();

        for (T element : list) {
            currentSubList.add(element);
            if (condition.test(element)) {
                result.add(currentSubList);
                currentSubList = new ArrayList<>();
            }
        }

        if (!currentSubList.isEmpty()) {
            result.add(currentSubList);
        }

        return result;
    }

    public static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }

    public static long gcd(long[] input) {
        long result = input[0];
        for (int i = 1; i < input.length; i++) result = gcd(result, input[i]);
        return result;
    }

    public static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    public static long lcm(long[] input) {
        long result = input[0];
        for (int i = 1; i < input.length; i++) result = lcm(result, input[i]);
        return result;
    }
}
