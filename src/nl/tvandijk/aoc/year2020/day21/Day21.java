package nl.tvandijk.aoc.year2020.day21;

import nl.tvandijk.aoc.common.Day;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 extends Day {
    private final Map<String, List<String>> allergens = new HashMap<>();
    private final List<List<String>> products = new LinkedList<>();
    private List<String> dangerous;

    public void propagate() {
        Queue<String> toDo = new LinkedList<>();
        Set<String> seen = new HashSet<>();

        for (var entry : allergens.entrySet()) {
            if (entry.getValue().size() == 1) {
                seen.add(entry.getKey());
                toDo.add(entry.getKey());
            }
        }

        while (!toDo.isEmpty()) {
            var nextAllergen = toDo.poll();
            var ingredient = allergens.get(nextAllergen).get(0);
            for (var entry : allergens.entrySet()) {
                if (seen.contains(entry.getKey())) continue;

                if (entry.getValue().remove(ingredient)) {
                    if (entry.getValue().size() == 1) {
                        seen.add(entry.getKey());
                        toDo.add(entry.getKey());
                    }
                }
            }
        }
    }

    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);

        var lexer = new IngredientsLexer(CharStreams.fromString(fileContents));
        var parser = new IngredientsParser(new CommonTokenStream(lexer));

        for (var productCtx : parser.products().product()) {
            var ingredients = productCtx.ingredient().stream()
                    .map(RuleContext::getText)
                    .collect(Collectors.toList());

            products.add(ingredients);

            for (var allergenCtx : productCtx.allergen()) {
                String allergen = allergenCtx.getText();

                allergens.compute(allergen, (key, value) -> {
                    if (value == null) {
                        value = new LinkedList<>(ingredients);
                    } else {
                        value.retainAll(ingredients);
                    }
                    return value;
                });
            }
        }

        propagate();

        dangerous = allergens.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    protected Object part1() {
        // Safe count
        return products.stream()
                .flatMap(Collection::stream)
                .filter(x -> !dangerous.contains(x))
                .count();
     }

    @Override
    protected Object part2() throws Exception {
        return String.join(",", dangerous);
    }
}