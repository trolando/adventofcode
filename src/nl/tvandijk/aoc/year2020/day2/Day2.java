package nl.tvandijk.aoc.year2020.day2;

import nl.tvandijk.aoc.common.Day;

import java.util.regex.Pattern;

public class Day2 extends Day {
    private int correct1;
    private int correct2;

    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);

        Pattern p = Pattern.compile("^(\\d+)-(\\d+) (\\S): (\\S+)$");
        correct1 = 0;
        correct2 = 0;

        for (var line : lines) {
            var m = p.matcher(line);
            if (m.matches()) {
                int a = Integer.parseInt(m.group(1));
                int b = Integer.parseInt(m.group(2));
                String c = m.group(3);
                String d = m.group(4);
                {
                    int cnt = 0;
                    for (int i = 0; i < d.length(); i++) {
                        if (d.substring(i).startsWith(c)) cnt++;
                    }
                    if (a <= cnt && cnt <= b) correct1++;
                }
                {
                    int cnt = 0;
                    if (d.substring(a - 1).startsWith(c)) cnt++;
                    if (d.substring(b - 1).startsWith(c)) cnt++;
                    if (cnt == 1) correct2++;
                }
            } else {
                System.out.println("Error matching " + line);
            }
        }
    }

    @Override
    protected Object part1() throws Exception {
        return correct1;
    }

    @Override
    protected Object part2() throws Exception {
        return correct2;
    }
}
