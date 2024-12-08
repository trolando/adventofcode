package nl.tvandijk.aoc.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Permutations {
    public static <T> Stream<List<T>> of(Collection<T> list, int n, boolean repetition) {
        return of(List.copyOf(list), n, repetition);
    }

    public static <T> Stream<List<T>> of(List<T> list, int n, boolean repetition) {
        return StreamSupport.stream(asIterable(list, n, repetition).spliterator(), false);
    }

    public static <T> Iterable<List<T>> asIterable(Collection<T> list, int n, boolean repetition) {
        return asIterable(List.copyOf(list), n, repetition);
    }

    public static <T> Iterable<List<T>> asIterable(List<T> list, int n, boolean repetition) {
        return () -> new PermutationIterator<>(list, n, repetition);
    }

    // Iterator for lazy permutation generation
    private static class PermutationIterator<T> implements Iterator<List<T>> {
        private final List<T> originalList;
        private final List<T> currentCombination;
        private final boolean[] used; // Tracks used elements when repetition is false
        private final int n;
        private final boolean repetition;
        private boolean hasNext;

        public PermutationIterator(List<T> list, int n, boolean repetition) {
            if (n > list.size() && !repetition) {
                throw new IllegalArgumentException("n must not be greater than the size of the list without repetition");
            }
            this.originalList = new ArrayList<>(list); // Clone the input list
            this.currentCombination = new ArrayList<>(n);
            this.used = new boolean[list.size()];
            this.n = n;
            this.repetition = repetition;
            this.hasNext = list.size() > 0 && n > 0;
        }

        @Override
        public boolean hasNext() {
            return hasNext;
        }

        @Override
        public List<T> next() {
            if (!hasNext) throw new IllegalStateException("No more permutations");

            // Generate the next permutation
            if (currentCombination.size() == n) {
                List<T> result = new ArrayList<>(currentCombination);
                hasNext = generateNext();
                return result;
            } else {
                // Build the first valid permutation
                buildNext();
                return next();
            }
        }

        private void buildNext() {
            if (currentCombination.size() < n) {
                for (int i = 0; i < originalList.size(); i++) {
                    if (repetition || !used[i]) {
                        currentCombination.add(originalList.get(i));
                        if (!repetition) used[i] = true;
                        buildNext();
                        if (currentCombination.size() == n) {
                            return;
                        }
                        if (!repetition) used[i] = false;
                        currentCombination.remove(currentCombination.size() - 1);
                    }
                }
            }
        }

        private boolean generateNext() {
            for (int i = currentCombination.size() - 1; i >= 0; i--) {
                int index = originalList.indexOf(currentCombination.get(i));
                if (!repetition) used[index] = false;
                currentCombination.remove(i);
                for (int j = index + 1; j < originalList.size(); j++) {
                    if (repetition || !used[j]) {
                        currentCombination.add(originalList.get(j));
                        if (!repetition) used[j] = true;
                        buildNext();
                        if (currentCombination.size() == n) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }
}
