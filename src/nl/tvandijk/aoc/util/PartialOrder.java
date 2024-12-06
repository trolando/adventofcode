package nl.tvandijk.aoc.util;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class PartialOrder<T> {
    private final Map<T, Set<T>> adjacencyMap = new HashMap<>();
    private final BiPredicate<T, T> comparator;

    public PartialOrder() {
        this.comparator = this::mapBasedComparator;
    }

    public PartialOrder(BiPredicate<T, T> comparator) {
        this.comparator = comparator;
    }

    private boolean mapBasedComparator(T a, T b) {
        return adjacencyMap.getOrDefault(a, Collections.emptySet()).contains(b);
    }

    public void add(T before, T after) {
        adjacencyMap.computeIfAbsent(before, k -> new HashSet<>()).add(after);
        adjacencyMap.putIfAbsent(after, new HashSet<>()); // Ensure 'after' is in the map
    }

    /**
     * Checks if a list of elements respects the defined partial order
     * @param elements the elements to check
     * @return true is it respects the order, otherwise false
     */
    public boolean respectsOrder(List<T> elements) {
        for (int i = 0; i < elements.size(); i++) {
            for (int j = i + 1; j < elements.size(); j++) {
                var a = elements.get(i);
                var b = elements.get(j);
                if (comparator.test(b, a)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Topological sorting method: Orders elements or returns null if there's a cycle
     */
    public List<T> order(Collection<T> elements) {
        // Initialize in-degree map
        Map<T, Integer> inDegree = new HashMap<>();
        for (T a : elements) {
            for (T b : elements) {
                if (!a.equals(b) && comparator.test(a, b)) {
                    inDegree.compute(b, (key, value) -> (value == null ? 1 : value + 1));
                }
            }
        }

        Queue<T> queue = new LinkedList<>();
        for (var e : elements) {
            if (inDegree.getOrDefault(e, 0) == 0) {
                queue.offer(e);
            }
        }

        List<T> sorted = new ArrayList<>();
        while (!queue.isEmpty()) {
            var current = queue.poll();
            sorted.add(current);

            for (var neighbor : elements) {
                if (!current.equals(neighbor) && comparator.test(current, neighbor)) {
                    var updatedValue = inDegree.compute(neighbor, (key, value) -> value == null ? 0 : value - 1);
                    if (updatedValue == 0) {
                        queue.offer(neighbor);
                    }
                }
            }
        }

        // If result size is less than the number of elements, there's a cycle
        return sorted.size() == elements.size() ? sorted : null;
    }

    /**
     * Check if we have a cycle in the partial order (only works if we use no custom lambda function)
     */
    public boolean hasCycle() {
        return order(adjacencyMap.keySet()) == null;
    }

    /**
     * Find transitive dependencies of the given element
     */
    public Set<T> getDependencies(T element) {
        Set<T> dependencies = new HashSet<>();
        Deque<T> stack = new ArrayDeque<>();
        stack.push(element);

        while (!stack.isEmpty()) {
            T current = stack.pop();
            for (T neighbor : adjacencyMap.getOrDefault(current, Collections.emptySet())) {
                if (dependencies.add(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }
        return dependencies;
    }

    /**
     * Find all independent elements
     */
    public Set<T> getIndependentElements() {
        return adjacencyMap.keySet().stream()
                .filter(key -> adjacencyMap.getOrDefault(key, Collections.emptySet()).isEmpty())
                .collect(Collectors.toSet());
    }
}

