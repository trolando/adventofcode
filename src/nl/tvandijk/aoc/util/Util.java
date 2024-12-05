package nl.tvandijk.aoc.util;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    public static <T> Map<T, Long> frequency(Collection<T> items) {
        Map<T, Long> res = new HashMap<>();
        for (var it : items) {
            res.compute(it, (key, v) -> v == null ? 1 : 1+v);
        }
        return res;
    }

    public static Stream<int[]> permute(int[] nums) {
        // Create an iterator for lazy generation
        Iterator<int[]> iterator = new RecursivePermutationIterator(nums);
        // Convert the iterator into a Stream
        return StreamSupport.stream(((Iterable<int[]>) () -> iterator).spliterator(), false);
    }

    private static class RecursivePermutationIterator implements Iterator<int[]> {
        private final int[] nums;
        private final int[] currentPermutation;
        private final int[] indices;
        private boolean hasMore;

        public RecursivePermutationIterator(int[] nums) {
            this.nums = nums.clone(); // Clone to avoid modifying the input
            this.currentPermutation = nums.clone();
            this.indices = new int[nums.length];
            for (int i = 0; i < nums.length; i++) {
                indices[i] = i;
            }
            this.hasMore = nums.length > 0;
        }

        @Override
        public boolean hasNext() {
            return hasMore;
        }

        @Override
        public int[] next() {
            if (!hasMore) throw new IllegalStateException("No more permutations");

            // Clone the current permutation to return
            int[] result = currentPermutation.clone();

            // Generate the next permutation in-place
            hasMore = generateNextPermutation();

            return result;
        }

        private boolean generateNextPermutation() {
            int i = indices.length - 1;
            while (i > 0 && indices[i - 1] >= indices[i]) {
                i--;
            }

            if (i <= 0) return false;

            int j = indices.length - 1;
            while (indices[j] <= indices[i - 1]) {
                j--;
            }

            // Swap the indices
            swap(indices, i - 1, j);

            // Reverse the tail
            reverse(indices, i, indices.length - 1);

            // Rebuild the current permutation based on indices
            for (int k = 0; k < indices.length; k++) {
                currentPermutation[k] = nums[indices[k]];
            }

            return true;
        }

        private void swap(int[] array, int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        private void reverse(int[] array, int start, int end) {
            while (start < end) {
                swap(array, start, end);
                start++;
                end--;
            }
        }
    }
}
