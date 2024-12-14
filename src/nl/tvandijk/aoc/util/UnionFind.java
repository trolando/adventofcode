package nl.tvandijk.aoc.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Union find data structure
 */
public class UnionFind<N> {
    private final Map<N, N> parent = new HashMap<>();
    private final Map<N, Integer> rank = new HashMap<>();

    /**
     * Find the parent of a node
     *
     * @param n the node
     * @return the parent of the node
     */
    public N find(N n) {
        while (true) {
            if (!parent.containsKey(n)) {
                parent.put(n, n);
                rank.put(n, 0);
                return n;
            }
            if (parent.get(n) == n) return n;
            n = parent.get(n);
        }
    }

    /**
     * Union two nodes
     *
     * @param n1 the first node
     * @param n2 the second node
     */
    public void union(N n1, N n2) {
        var p1 = find(n1);
        var p2 = find(n2);
        if (p1 == p2) return;
        if (rank.get(p1) < rank.get(p2)) {
            parent.put(p1, p2);
        } else if (rank.get(p1) > rank.get(p2)) {
            parent.put(p2, p1);
        } else {
            parent.put(p2, p1);
            rank.put(p1, rank.get(p1) + 1);
        }
    }

    /**
     * Get the number of disjoint sets
     *
     * @return the number of disjoint sets
     */
    public int getNumberOfSets() {
        var sets = new HashSet<N>();
        for (var n : parent.keySet()) {
            sets.add(find(n));
        }
        return sets.size();
    }

    /**
     * Get the number of elements in the set containing n
     *
     * @param n the element
     * @return the number of elements in the set containing n
     */
    public int getSetSize(N n) {
        var p = find(n);
        var size = 0;
        for (var n2 : parent.keySet()) {
            if (find(n2) == p) size++;
        }
        return size;
    }

    public boolean connected(N n1, N n2) {
        return find(n1) == find(n2);
    }

    public Set<N> getConnected(N n) {
        var p = find(n);
        var res = new HashSet<N>();
        for (var n2 : parent.keySet()) {
            if (find(n2) == p) res.add(n2);
        }
        return res;
    }

    /**
     * Get the largest connected set in the UnionFind structure
     *
     * @return the largest set of connected elements
     */
    public Set<N> getLargestSet() {
        Map<N, Set<N>> components = new HashMap<>();

        // Group all elements by their root parent
        for (var n : parent.keySet()) {
            var root = find(n); // Find the root parent
            components.computeIfAbsent(root, k -> new HashSet<>()).add(n);
        }

        // Find the largest component
        Set<N> largestSet = new HashSet<>();
        for (var component : components.values()) {
            if (component.size() > largestSet.size()) {
                largestSet = component;
            }
        }

        return largestSet;
    }
}
