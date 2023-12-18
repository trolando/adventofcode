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

    /**
     * Compute area under a polygon of points
     * @param points the points (consecutive order)
     * @return the area
     */
    public static long shoelace(List<Point> points) {
        // Calculate with shoelace formula
        // x1y2 - x2y1 + ... + xny1 - x1yn
        long result = 0;
        var size = points.size();
        for (int i = 0; i < size; i++) {
            var pt = points.get(i);
            var pt2 = points.get((i+1)%size);
            result += pt.x * pt2.y - pt2.x * pt.y;
        }
        return result / 2;
    }

    /**
     * Compute digital area using shoelace and Pick's theorem
     * @param points the points (consecutive order)
     * @return the area
     */
    public static long area(List<Point> points, boolean includeBorder) {
        // Pick's theorem: Area = i + b/2 - 1
        // where i is number of interior points and b is number of boundary points
        // thus the interior i = Area + 1 - b/2
        // however we want to include the boundary! so
        // points = Area + 1 + b/2
        var area = 0L;
        var boundary = 0L;
        var size = points.size();
        for (int i = 0; i < size; i++) {
            var pt = points.get(i);
            var pt2 = points.get((i+1)%size);
            boundary += pt.manhattan(pt2);
            area += pt.x * pt2.y - pt2.x * pt.y;
        }
        if (includeBorder) return area/2 + 1 + boundary/2;
        else return area/2 + 1 - boundary/2;
    }
}
