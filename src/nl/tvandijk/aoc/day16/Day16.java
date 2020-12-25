package nl.tvandijk.aoc.day16;

import nl.tvandijk.aoc.common.AoCCommon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16 extends AoCCommon {
    List<Field> fields = new ArrayList<>();

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

    List<String> validTickets = new ArrayList<>();

    @Override
    public void process(InputStream stream) {
        int phase = 0;
        var pat = Pattern.compile("^([^:]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)$");

        long problems = 0;
        String myTicket = null;

        try (var br = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = br.readLine()) != null) {
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
                    System.out.println("ticket: " + line);
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
            System.out.println("problems: " + problems);
        } catch (IOException e) {
            e.printStackTrace();
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
                    System.out.printf("set %d to %s\n", i, res.get(0));
                }
            }
        }

        long result = 1;
        for (int i = 0; i < numberOfFields; i++) {
            if (knownFields[i].name.startsWith("departure")) {
                result *= Integer.parseInt(myTicket.split(",")[i]);
            }
        }

        System.out.printf("Result: %d\n", result);
    }

    public boolean has(Field[] arr, Field val) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == val) return true;
        }

        return false;
    }

    public boolean done(Field[] arr) {
        return !has(arr, null);
    }

    public static void main(String[] args) {
        run(Day16::new, "example1.txt", "example2.txt", "input.txt");
    }
}