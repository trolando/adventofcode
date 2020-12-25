package nl.tvandijk.aoc.day12;

import nl.tvandijk.aoc.common.AoCCommon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day12 extends AoCCommon {
    public void solve1(List<String> lines) {
        double posx = 0;
        double posy = 0;
        int dir = 90;

        for (var line : lines) {
            char s = line.charAt(0);
            int a = Integer.parseInt(line.substring(1));
            switch (s) {
                case 'F':
                    posx += ((double) a) * Math.sin(Math.toRadians(dir));
                    posy -= ((double) a) * Math.cos(Math.toRadians(dir));
                    break;
                case 'N':
                    posy -= a;
                    break;
                case 'E':
                    posx += a;
                    break;
                case 'W':
                    posx -= a;
                    break;
                case 'S':
                    posy += a;
                    break;
                case 'L':
                    dir -= a;
                    break;
                case 'R':
                    dir += a;
                    break;
            }

            // System.out.printf("coords x=%f y=%f manhattan distance=%f\n", posx, posy, Math.abs(posx)+Math.abs(posy));

        }

        System.out.printf("coords x=%f y=%f manhattan distance=%.0f\n", posx, posy, Math.abs(posx)+Math.abs(posy));
    }

    public void solve2(List<String> lines) {
        double shipx = 0;
        double shipy = 0;
        double posx = 10;
        double posy = -1;

        // System.out.printf("coords ship=(%f %f) wp=(%f %f) md %f\n", shipx, shipy, posx, posy, Math.abs(shipx)+Math.abs(shipy));

        for (var line : lines) {
            char s = line.charAt(0);
            int a = Integer.parseInt(line.substring(1));
            switch (s) {
                case 'F': {
                    shipx += a * posx;
                    shipy += a * posy;
                    break;
                }
                case 'N':
                    posy -= a;
                    break;
                case 'E':
                    posx += a;
                    break;
                case 'W':
                    posx -= a;
                    break;
                case 'S':
                    posy += a;
                    break;
                case 'L': {
                    double dist = Math.hypot(posx, posy);
                    double deg = 90 + Math.toDegrees(Math.atan2(posy, posx));
                    deg -= a;
                    posx = dist * Math.sin(Math.toRadians(deg));
                    posy = 0 - dist * Math.cos(Math.toRadians(deg));
                    break;
                }
                case 'R': {
                    double dist = Math.hypot(posx, posy);
                    double deg = 90 + Math.toDegrees(Math.atan2(posy, posx));
                    deg += a;
                    posx = dist * Math.sin(Math.toRadians(deg));
                    posy = 0 - dist * Math.cos(Math.toRadians(deg));
                    break;
                }
            }
            // System.out.printf("coords ship=(%f %f) wp=(%f %f) md %f\n", shipx, shipy, posx, posy, Math.abs(shipx)+Math.abs(shipy));
        }
        System.out.printf("coords ship=(%f %f) wp=(%f %f) manhattan distance %.0f\n", shipx, shipy, posx, posy, Math.abs(shipx)+Math.abs(shipy));
    }


    @Override
    protected void process(InputStream stream) throws Exception {
        List<String> lines = null;

        try (var br = new BufferedReader(new InputStreamReader(stream))) {
            lines = br.lines().collect(Collectors.toList());
        }

        solve1(lines);
        solve2(lines);
    }

    public static void main(String[] args) {
        run(Day12::new, "example.txt", "input.txt");
    }
}
