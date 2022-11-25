package nl.tvandijk.aoc.year2020.day12;

import nl.tvandijk.aoc.common.Day;

public class Day12 extends Day {
    @Override
    protected Object part1() {
        double posx = 0;
        double posy = 0;
        int dir = 90;

        for (var line : lines) {
            char s = line.charAt(0);
            int a = Integer.parseInt(line.substring(1));
            switch (s) {
                case 'F' -> {
                    posx += ((double) a) * Math.sin(Math.toRadians(dir));
                    posy -= ((double) a) * Math.cos(Math.toRadians(dir));
                }
                case 'N' -> posy -= a;
                case 'E' -> posx += a;
                case 'W' -> posx -= a;
                case 'S' -> posy += a;
                case 'L' -> dir -= a;
                case 'R' -> dir += a;
            }
        }

        //System.out.printf("coords x=%f y=%f manhattan distance=%.0f\n", posx, posy, Math.abs(posx)+Math.abs(posy));
        return Math.abs(posx)+Math.abs(posy);
    }

    @Override
    protected Object part2() {
        double shipx = 0;
        double shipy = 0;
        double posx = 10;
        double posy = -1;

        for (var line : lines) {
            char s = line.charAt(0);
            int a = Integer.parseInt(line.substring(1));
            switch (s) {
                case 'F' -> {
                    shipx += a * posx;
                    shipy += a * posy;
                }
                case 'N' -> posy -= a;
                case 'E' -> posx += a;
                case 'W' -> posx -= a;
                case 'S' -> posy += a;
                case 'L' -> {
                    double dist = Math.hypot(posx, posy);
                    double deg = 90 + Math.toDegrees(Math.atan2(posy, posx));
                    deg -= a;
                    posx = dist * Math.sin(Math.toRadians(deg));
                    posy = 0 - dist * Math.cos(Math.toRadians(deg));
                }
                case 'R' -> {
                    double dist = Math.hypot(posx, posy);
                    double deg = 90 + Math.toDegrees(Math.atan2(posy, posx));
                    deg += a;
                    posx = dist * Math.sin(Math.toRadians(deg));
                    posy = 0 - dist * Math.cos(Math.toRadians(deg));
                }
            }
        }
        // System.out.printf("coords ship=(%f %f) wp=(%f %f) manhattan distance %.0f\n", shipx, shipy, posx, posy, Math.abs(shipx)+Math.abs(shipy));
        return Math.abs(shipx)+Math.abs(shipy);
    }
}
