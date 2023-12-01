package nl.tvandijk.aoc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Util {
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
}
