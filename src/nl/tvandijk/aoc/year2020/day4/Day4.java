package nl.tvandijk.aoc.year2020.day4;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4 extends Day {
    public Day4() {
        super(new String[] {"example.txt", "example2.txt", "input.txt"});
    }

    private static boolean validateByr(String s) {
        try {
            int year = Integer.valueOf(s);
            return year >= 1920 && year <= 2002;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean validateIyr(String s) {
        try {
            int year = Integer.valueOf(s);
            return year >= 2010 && year <= 2020;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean validateEyr(String s) {
        try {
            int year = Integer.valueOf(s);
            return year >= 2020 && year <= 2030;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean validateHgt(String s) {
        var pat = Pattern.compile("(\\d+)(\\S+)");
        var mat = pat.matcher(s);
        if (mat.matches()) {
            int amount = Integer.valueOf(mat.group(1));
            switch (mat.group(2)) {
                case "in":
                    return amount >= 59 && amount <= 76;
                case "cm":
                    return amount >= 150 && amount <= 193;
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    private static boolean validateHcl(String s) {
        var pat = Pattern.compile("#([0-9a-f]{6})");
        var mat = pat.matcher(s);
        return mat.matches();
    }

    private static String[] hcls = {"amb", "blu", "brn", "gry", "grn", "hzl", "oth"};

    private static boolean validateEcl(String s) {
        return Arrays.stream(hcls).anyMatch(x -> x.equals(s));
    }

    private static boolean validatePid(String s) {
        var pat = Pattern.compile("[0-9]{9}");
        var mat = pat.matcher(s);
        return mat.matches();
    }


    private static boolean validate1(List<String> lines) {
        var res = lines.stream().collect(Collectors.joining(" "));

        boolean byr=false;
        boolean iyr=false;
        boolean eyr=false;
        boolean hgt=false;
        boolean hcl=false;
        boolean ecl=false;
        boolean pid=false;

        for (var part : res.split("\\s+")) {
            if (part.startsWith("byr:")) byr = true;
            if (part.startsWith("iyr:")) iyr = true;
            if (part.startsWith("eyr:")) eyr = true;
            if (part.startsWith("hgt:")) hgt = true;
            if (part.startsWith("hcl:")) hcl = true;
            if (part.startsWith("ecl:")) ecl = true;
            if (part.startsWith("pid:")) pid = true;
        }

        return (byr && iyr && eyr && hcl && hgt && ecl && pid);
    }

    private static boolean validate2(List<String> lines) {
        var res = lines.stream().collect(Collectors.joining(" "));

        boolean byr=false;
        boolean iyr=false;
        boolean eyr=false;
        boolean hgt=false;
        boolean hcl=false;
        boolean ecl=false;
        boolean pid=false;

        for (var part : res.split("\\s+")) {
            if (part.startsWith("byr:")) byr = validateByr(part.substring(4));
            if (part.startsWith("iyr:")) iyr = validateIyr(part.substring(4));
            if (part.startsWith("eyr:")) eyr = validateEyr(part.substring(4));
            if (part.startsWith("hgt:")) hgt = validateHgt(part.substring(4));
            if (part.startsWith("hcl:")) hcl = validateHcl(part.substring(4));
            if (part.startsWith("ecl:")) ecl = validateEcl(part.substring(4));
            if (part.startsWith("pid:")) pid = validatePid(part.substring(4));
        }

        return (byr && iyr && eyr && hcl && hgt && ecl && pid);
    }

    @Override
    protected Object part1() {
        int count = 0;
        List<String> linesSoFar = new ArrayList<>();
        for (var line : lines) {
            if (line.trim().isBlank()) {
                if (validate1(linesSoFar)) count++;
                linesSoFar.clear();
            } else {
                linesSoFar.add(line);
            }
        }
        if (validate1(linesSoFar)) count++;
        return count;
    }

    @Override
    protected Object part2() {
        int count = 0;
        List<String> linesSoFar = new ArrayList<>();
        for (var line : lines) {
            if (line.trim().isBlank()) {
                if (validate2(linesSoFar)) count++;
                linesSoFar.clear();
            } else {
                linesSoFar.add(line);
            }
        }
        if (validate2(linesSoFar)) count++;
        return count;
    }
}
