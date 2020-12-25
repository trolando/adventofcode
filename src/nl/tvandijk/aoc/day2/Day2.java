package nl.tvandijk.aoc.day2;

import nl.tvandijk.aoc.common.AoCCommon;

import java.io.*;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class Day2 extends AoCCommon {

    @Override
    protected void process(InputStream stream) throws Exception {
        try (var br = new BufferedReader(new InputStreamReader(stream))) {
            Pattern p = Pattern.compile("^(\\d+)-(\\d+) (\\S): (\\S+)$");

            int correct1 = 0;
            int correct2 = 0;

            String line;
            while ((line = br.readLine()) != null) {
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

            System.out.println("Correct part 1: " + correct1);
            System.out.println("Correct part 2: " + correct2);
        }
    }

    public static void main(String[] args) {
        run(Day2::new, "example.txt", "input.txt");
    }
}
