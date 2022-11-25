package nl.tvandijk.aoc.year2020.day16;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16 extends Day {
    private List<Field> fields = new ArrayList<>();

    public Day16() {
        super("example1.txt", "example2.txt", "input.txt");
    }

    public Field findField(int value) {
        for (var f : fields) {
            if (f.matches(value)) return f;
        }
        return null;
    }

    public List<Field> findMatching(List<Field> fields, List<Integer> values) {
        return fields.stream().filter(
                f -> values.stream().allMatch(f::matches)).collect(Collectors.toList());
    }

    public void parseField(String name, int min1, int max1, int min2, int max2) {
        fields.add(new Field(name, min1, max1, min2, max2));
    }

    private List<String> validTickets = new ArrayList<>();

    public boolean has(Field[] arr, Field val) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == val) return true;
        }

        return false;
    }

    public boolean done(Field[] arr) {
        return !has(arr, null);
    }

    @Override
    protected Object part1() {
        int phase = 0;
        var pat = Pattern.compile("^([^:]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)$");

        long problems = 0;

        for (var line : lines) {
            if (line.isBlank()) {
                phase++;
            } else if (phase == 0) {
                var m = pat.matcher(line.trim());
                if (!m.matches()) throw new RuntimeException("Cannot match file on line " + line.trim());
                parseField(m.group(1), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)),
                        Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5)));
            } else if (phase == 1) {
            } else {
                if (line.startsWith("nearby tickets")) continue;

                boolean badTicket = false;
                for (var s : line.split(",")) {
                    var i = Integer.valueOf(s);
                    if (findField(i) == null) {
                        problems += i;
                        badTicket = true;
                    }
                }

                if (!badTicket) validTickets.add(line);
            }
        }
        return problems;
    }

    @Override
    protected Object part2() {
        int phase = 0;
        var pat = Pattern.compile("^([^:]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)$");

        String myTicket = null;

        for (var line : lines) {
            if (line.isBlank()) {
                phase++;
            } else if (phase == 0) {
                var m = pat.matcher(line.trim());
                if (!m.matches()) throw new RuntimeException("Cannot match file on line " + line.trim());
                parseField(m.group(1), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)),
                        Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5)));
            } else if (phase == 1) {
                if (line.startsWith("your ticket")) continue;
                myTicket = line.trim();
            } else {
                if (line.startsWith("nearby tickets")) continue;

                boolean badTicket = false;
                for (var s : line.split(",")) {
                    var i = Integer.valueOf(s);
                    if (findField(i) == null) {
                        badTicket = true;
                    }
                }

                if (!badTicket) validTickets.add(line);
            }
        }

        // process
        int numberOfFields = myTicket.split(",").length;
        Field[] knownFields = new Field[numberOfFields];

        while (!done(knownFields)) {
            for (int i = 0; i < numberOfFields; i++) {
                if (knownFields[i] != null) continue;
                List<Field> unknowns = fields.stream().filter(f -> !has(knownFields, f)).collect(Collectors.toList());
                List<Integer> values = new ArrayList<>();
                for (var ticket : validTickets) {
                    values.add(Integer.valueOf(ticket.split(",")[i]));
                }
                var res = findMatching(unknowns, values);
                // System.out.println("res: " + res.toString());
                if (res.size() == 1) {
                    knownFields[i] = res.get(0);
//                    System.out.printf("set %d to %s\n", i, res.get(0));
                }
            }
        }

        long result = 1;
        for (int i = 0; i < numberOfFields; i++) {
            if (knownFields[i].name.startsWith("departure")) {
                result *= Integer.parseInt(myTicket.split(",")[i]);
            }
        }
        return result;
    }

    @Override
    protected boolean resetForPartTwo() {
        return true;
    }
}