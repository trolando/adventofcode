package nl.tvandijk.aoc.year2021.day23;

import nl.tvandijk.aoc.common.Day;

import java.util.*;
import java.util.stream.Stream;

public class Day23 extends Day {

    // #############
    // #01234567890#  0 to 11
    // ###A#B#C#D### 10 12 14 16
    //   #A#B#C#D#   11 13 15 17
    //   #########

    private static class State {
        int[] places;

        public State(int[] places) {
            this.places = Arrays.copyOf(places, places.length);
        }

        public State(State other, int from, int to) {
            this.places = Arrays.copyOf(other.places, other.places.length);
            this.places[to] = this.places[from];
            this.places[from] = 0;
        }

        /**
         * Check if the way is clear
         */
        private boolean wayClear(int from, int to) {
            /*
             #############
             #01234567890#  0 to 11
             ###A#B#C#D### 12 14 16 18
               #A#B#C#D#   13 15 17 19
               #########
            */

            if (from == to) return true;

            switch(from) {
                case 12 -> {
                    return places[2] == 0 && wayClear(2, to);
                }
                case 13 -> {
                    return places[12] == 0 && places[2] == 0 && wayClear(2, to);
                }
                case 14 -> {
                    return places[4] == 0 && wayClear(4, to);
                }
                case 15 -> {
                    return places[14] == 0 && places[4] == 0 && wayClear(4, to);
                }
                case 16 -> {
                    return places[6] == 0 && wayClear(6, to);
                }
                case 17 -> {
                    return places[16] == 0 && places[6] == 0 && wayClear(6, to);
                }
                case 18 -> {
                    return places[8] == 0 && wayClear(8, to);
                }
                case 19 -> {
                    return places[18] == 0 && places[8] == 0 && wayClear(8, to);
                }
            }

            // from is 0-11
            // to = 0-11

            if (from < to) {
                for (int i = from + 1; i <= to; i++) {
                    if (places[i] != 0) return false;
                }
                return true;
            } else {
                for (int i = from - 1; i >= to; i--) {
                    if (places[i] != 0) return false;
                }
                return true;
            }
        }

        static long[] basePrice = {0, 1, 10, 100, 1000};

        public long price(int token, int from, int to) {
            var b = basePrice[token];
            return switch (from) {
                case 12 -> b + price(token, 2, to);
                case 13 -> 2 * b + price(token, 2, to);
                case 14 -> b + price(token, 4, to);
                case 15 -> 2 * b + price(token, 4, to);
                case 16 -> b + price(token, 6, to);
                case 17 -> 2 * b + price(token, 6, to);
                case 18 -> b + price(token, 8, to);
                case 19 -> 2 * b + price(token, 8, to);
                default -> switch (to) {
                    case 12 -> b + price(token, from, 2);
                    case 13 -> 2 * b + price(token, from, 2);
                    case 14 -> b + price(token, from, 4);
                    case 15 -> 2 * b + price(token, from, 4);
                    case 16 -> b + price(token, from, 6);
                    case 17 -> 2 * b + price(token, from, 6);
                    case 18 -> b + price(token, from, 8);
                    case 19 -> 2 * b + price(token, from, 8);
                    default -> b * Math.abs(from - to);
                };
            };
        }

        public List<Pair<State, Long>> successors() {
            var res = new ArrayList<Pair<State, Long>>();

            // never move to 2/4/6/8
            // only move from 0-11 to appropriate 12/13/14/15/16/17/18/19
            // only move from 12-19 to 0-11

            for (int i = 0; i < 11; i++) {
                if (places[i] == 0) {
                    // continue
                } else if (places[i] == 1) {
                    // move A
                    if (places[12] == 0) {
                        if (places[13] == 1) {
                            if (wayClear(i, 2)) {
                                res.add(Pair.of(new State(this, i, 12), price(1, i, 12)));
                            }
                        } else if (places[13] == 0 && wayClear(i, 2)) {
                            res.add(Pair.of(new State(this, i, 13), price(1, i, 13)));
                        }
                    }
                } else if (places[i] == 2) {
                    // move B
                    if (places[14] == 0) {
                        if (places[15] == 2) {
                            if (wayClear(i, 4)) {
                                res.add(Pair.of(new State(this, i, 14), price(2, i, 14)));
                            }
                        } else if (places[15] == 0 && wayClear(i, 4)) {
                            res.add(Pair.of(new State(this, i, 15), price(2, i, 15)));
                        }
                    }
                } else if (places[i] == 3) {
                    // move B
                    if (places[16] == 0) {
                        if (places[17] == 3) {
                            if (wayClear(i, 6)) {
                                res.add(Pair.of(new State(this, i, 16), price(3, i, 16)));
                            }
                        } else if (places[17] == 0 && wayClear(i, 6)) {
                            res.add(Pair.of(new State(this, i, 17), price(3, i, 17)));
                        }
                    }
                } else if (places[i] == 4) {
                    // move B
                    if (places[18] == 0) {
                        if (places[19] == 4) {
                            if (wayClear(i, 8)) {
                                res.add(Pair.of(new State(this, i, 18), price(4, i, 18)));
                            }
                        } else if (places[19] == 0 && wayClear(i, 8)) {
                            res.add(Pair.of(new State(this, i, 19), price(4, i, 19)));
                        }
                    }
                }
            }

            for (int j = 0; j < 11; j++) {
                if (j == 2) continue;
                if (j == 4) continue;
                if (j == 6) continue;
                if (j == 8) continue;
                if (places[j] != 0) continue; // unoccupied?

                if (places[12] != 0 && places[2] == 0 && wayClear(2, j)) {
                    res.add(Pair.of(new State(this, 12, j), price(places[12], 12, j)));
                }
                if (places[13] != 0 && places[12] == 0 && places[2] == 0 && wayClear(2, j)) {
                    res.add(Pair.of(new State(this, 13, j), price(places[13], 13, j)));
                }
                if (places[14] != 0 && places[4] == 0 && wayClear(4, j)) {
                    res.add(Pair.of(new State(this, 14, j), price(places[14], 14, j)));
                }
                if (places[15] != 0 && places[14] == 0 && places[4] == 0 && wayClear(4, j)) {
                    res.add(Pair.of(new State(this, 15, j), price(places[15], 15, j)));
                }
                if (places[16] != 0 && places[6] == 0 && wayClear(6, j)) {
                    res.add(Pair.of(new State(this, 16, j), price(places[16], 16, j)));
                }
                if (places[17] != 0 && places[16] == 0 && places[6] == 0 && wayClear(6, j)) {
                    res.add(Pair.of(new State(this, 17, j), price(places[17], 17, j)));
                }
                if (places[18] != 0 && places[8] == 0 && wayClear(8, j)) {
                    res.add(Pair.of(new State(this, 18, j), price(places[18], 18, j)));
                }
                if (places[19] != 0 && places[18] == 0 && places[8] == 0 && wayClear(8, j)) {
                    res.add(Pair.of(new State(this, 19, j), price(places[19], 19, j)));
                }
            }
            return res;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return Arrays.equals(places, state.places);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(places);
        }

        @Override
        public String toString() {
            return Arrays.toString(places);
        }

        public boolean isFinal() {
            return places[12] == 1 && places[13] == 1 &&
                    places[14] == 2 && places[15] == 2 &&
                    places[16] == 3 && places[17] == 3 &&
                    places[18] == 4 && places[19] == 4;
        }

        public String draw() {
            // #############
            // #01234567890#  0 to 11
            // ###A#B#C#D### 10 12 14 16
            //   #A#B#C#D#   11 13 15 17
            //   #########

            var sb = new StringBuilder();
            sb.append("#############\n");
            sb.append("#");
            for (int i = 0; i < 11; i++) sb.append(" ABCD".charAt(places[i]));
            sb.append("#\n");
            sb.append("###");
            sb.append(" ABCD".charAt(places[12]));
            sb.append("#");
            sb.append(" ABCD".charAt(places[14]));
            sb.append("#");
            sb.append(" ABCD".charAt(places[16]));
            sb.append("#");
            sb.append(" ABCD".charAt(places[18]));
            sb.append("###\n  #");
            sb.append(" ABCD".charAt(places[13]));
            sb.append("#");
            sb.append(" ABCD".charAt(places[15]));
            sb.append("#");
            sb.append(" ABCD".charAt(places[17]));
            sb.append("#");
            sb.append(" ABCD".charAt(places[19]));
            sb.append("###\n  #########\n");
            return sb.toString();
        }
    } // class

    public int read(char ch) {
        if (ch == 'A') return 1;
        if (ch == 'B') return 2;
        if (ch == 'C') return 3;
        if (ch == 'D') return 4;
        return 0;
    }

    @Override
    protected void part1(String fileContents) {
        var lines = fileContents.split(System.lineSeparator());

        var pos = new int[20];
        pos[12] = read(lines[2].charAt(3));
        pos[13] = read(lines[3].charAt(3));
        pos[14] = read(lines[2].charAt(5));
        pos[15] = read(lines[3].charAt(5));
        pos[16] = read(lines[2].charAt(7));
        pos[17] = read(lines[3].charAt(7));
        pos[18] = read(lines[2].charAt(9));
        pos[19] = read(lines[3].charAt(9));

        Map<State, Long> distance = new HashMap<>();
        Map<State, State> pred = new HashMap<>();
        PriorityQueue<State> unvisited = new PriorityQueue<>(Comparator.comparingLong(distance::get));

        var initial = new State(pos);
        distance.put(initial, 0L);
        unvisited.add(initial);

        State finalState = null;

        while (!unvisited.isEmpty()) {
            var v = unvisited.poll();
            var dv = distance.get(v);
            if (v.isFinal()) {
                finalState = v;
                break;
            }

            for (var s : v.successors()) {
                var nv = s.a;
                var dnv = dv + s.b;

                if (distance.containsKey(nv)) {
                    var cur = distance.get(nv);
                    if (cur > dnv) {
                        unvisited.remove(nv);
                        distance.put(nv, dnv);
                        unvisited.add(nv);
                        pred.put(nv, v);
                    }
                } else {
                    distance.put(nv, dnv);
                    unvisited.add(nv);
                    pred.put(nv, v);
                }
            }
        }

//        test(distance, new State(new int[]{0,0,0,2,0,0,0,0,0,0,0,0,2,1,3,4,0,3,4,1}));
//        test(distance, new State(new int[]{0,0,0,2,0,0,0,0,0,0,0,0,2,1,0,4,3,3,4,1}));
//        test(distance, new State(new int[]{0,0,0,2,0,4,0,0,0,0,0,0,2,1,0,0,3,3,4,1}));
//        test(distance, new State(new int[]{0,0,0,0,0,4,0,0,0,0,0,0,2,1,0,2,3,3,4,1}));
//        test(distance, new State(new int[]{0,0,0,0,0,4,0,0,0,0,0,0,0,1,2,2,3,3,4,1}));
//        test(distance, new State(new int[]{0,0,0,0,0,4,0,4,0,1,0,0,0,1,2,2,3,3,0,0}));
//        test(distance, new State(new int[]{0,0,0,0,0,0,0,0,0,1,0,0,0,1,2,2,3,3,4,4}));
//        test(distance, new State(new int[]{0,0,0,0,0,0,0,0,0,0,0,0,1,1,2,2,3,3,4,4}));

        var states = new ArrayList<>(distance.entrySet());
        states.sort(Comparator.comparingLong(Map.Entry::getValue));

        List<State> trace = new ArrayList<>();
        {
            var s = finalState;
            while (s != null) {
                trace.add(0, s);
                s = pred.get(s);
            }
        }
        for (var state : trace) {
            System.out.printf("At distance %d:%n", distance.get(state));
            System.out.println(state.draw());
        }

        System.out.println("Part 1: " + distance.get(finalState));
    }

    private void test(Map<State, Long> distance, State state) {
        var d = distance.get(state);
        if (d != null) {
            System.out.printf("%s distance %d%n", state, d);
        } else {
            System.out.printf("%s unreached%n", state);
        }
    }

    private static class State2 {
        int[] places;

        public State2(int[] places) {
            this.places = Arrays.copyOf(places, places.length);
        }

        public State2(State2 other, int from, int to) {
            this.places = Arrays.copyOf(other.places, other.places.length);
            this.places[to] = this.places[from];
            this.places[from] = 0;
        }

        /**
         * Check if the way is clear
         */
        private boolean wayClear(int from, int to) {
            /*
             #############
             #01234567890# 0 .. 10
             ###A#B#C#D### 12 16 20 24
               #A#B#C#D#   13 17 21 25
               #A#B#C#D#   14 18 22 26
               #A#B#C#D#   15 19 23 27
               #########
            */

            if (from == to) return true;

            switch(from) {
                case 12 -> {
                    return places[2] == 0 && wayClear(2, to);
                }
                case 13 -> {
                    return places[12] == 0 && wayClear(12, to);
                }
                case 14 -> {
                    return places[13] == 0 && wayClear(13, to);
                }
                case 15 -> {
                    return places[14] == 0 && wayClear(14, to);
                }
                case 16 -> {
                    return places[4] == 0 && wayClear(4, to);
                }
                case 17 -> {
                    return places[16] == 0 && wayClear(16, to);
                }
                case 18 -> {
                    return places[17] == 0 && wayClear(17, to);
                }
                case 19 -> {
                    return places[18] == 0 && wayClear(18, to);
                }
                case 20 -> {
                    return places[6] == 0 && wayClear(6, to);
                }
                case 21 -> {
                    return places[20] == 0 && wayClear(20, to);
                }
                case 22 -> {
                    return places[21] == 0 && wayClear(21, to);
                }
                case 23 -> {
                    return places[22] == 0 && wayClear(22, to);
                }
                case 24 -> {
                    return places[8] == 0 && wayClear(8, to);
                }
                case 25 -> {
                    return places[24] == 0 && wayClear(24, to);
                }
                case 26 -> {
                    return places[25] == 0 && wayClear(25, to);
                }
                case 27 -> {
                    return places[26] == 0 && wayClear(26, to);
                }
            }

            // from is 0-11
            // to = 0-11

            if (from < to) {
                for (int i = from + 1; i <= to; i++) {
                    if (places[i] != 0) return false;
                }
                return true;
            } else {
                for (int i = from - 1; i >= to; i--) {
                    if (places[i] != 0) return false;
                }
                return true;
            }
        }

        static long[] basePrice = {0, 1, 10, 100, 1000};

        public long price(int token, int from, int to) {
            var b = basePrice[token];
            return switch (from) {
                case 12 -> b + price(token, 2, to);
                case 13 -> 2 * b + price(token, 2, to);
                case 14 -> 3 * b + price(token, 2, to);
                case 15 -> 4 * b + price(token, 2, to);
                case 16 -> b + price(token, 4, to);
                case 17 -> 2 * b + price(token, 4, to);
                case 18 -> 3 * b + price(token, 4, to);
                case 19 -> 4 * b + price(token, 4, to);
                case 20 -> b + price(token, 6, to);
                case 21 -> 2 * b + price(token, 6, to);
                case 22 -> 3 * b + price(token, 6, to);
                case 23 -> 4 * b + price(token, 6, to);
                case 24 -> b + price(token, 8, to);
                case 25 -> 2 * b + price(token, 8, to);
                case 26 -> 3 * b + price(token, 8, to);
                case 27 -> 4 * b + price(token, 8, to);
                default -> switch (to) {
                    case 12 -> b + price(token, from, 2);
                    case 13 -> 2 * b + price(token, from, 2);
                    case 14 -> 3 * b + price(token, from, 2);
                    case 15 -> 4 * b + price(token, from, 2);
                    case 16 -> b + price(token, from, 4);
                    case 17 -> 2 * b + price(token, from, 4);
                    case 18 -> 3 * b + price(token, from, 4);
                    case 19 -> 4 * b + price(token, from, 4);
                    case 20 -> b + price(token, from, 6);
                    case 21 -> 2 * b + price(token, from, 6);
                    case 22 -> 3 * b + price(token, from, 6);
                    case 23 -> 4 * b + price(token, from, 6);
                    case 24 -> b + price(token, from, 8);
                    case 25 -> 2 * b + price(token, from, 8);
                    case 26 -> 3 * b + price(token, from, 8);
                    case 27 -> 4 * b + price(token, from, 8);
                    default -> b * Math.abs(from - to);
                };
            };
        }

        public List<Pair<State2, Long>> successors() {
            var res = new ArrayList<Pair<State2, Long>>();

            // never move to 2/4/6/8
            // only move from 0-11 to appropriate 12/13/14/15/16/17/18/19
            // only move from 12-19 to 0-11

            for (int i = 0; i < 11; i++) {
                if (places[i] == 0) {
                    // continue
                } else if (places[i] == 1) {
                    // move A
                    if (places[12] == 0 && places[13] == 1 && places[14] == 1 && places[15] == 1 && wayClear(i, 2)) {
                        res.add(Pair.of(new State2(this, i, 12), price(1, i, 12)));
                    }
                    if (places[12] == 0 && places[13] == 0 && places[14] == 1 && places[15] == 1 && wayClear(i, 2)) {
                        res.add(Pair.of(new State2(this, i, 13), price(1, i, 13)));
                    }
                    if (places[12] == 0 && places[13] == 0 && places[14] == 0 && places[15] == 1 && wayClear(i, 2)) {
                        res.add(Pair.of(new State2(this, i, 14), price(1, i, 14)));
                    }
                    if (places[12] == 0 && places[13] == 0 && places[14] == 0 && places[15] == 0 && wayClear(i, 2)) {
                        res.add(Pair.of(new State2(this, i, 15), price(1, i, 15)));
                    }
                } else if (places[i] == 2) {
                    // move B
                    if (places[16] == 0 && places[17] == 2 && places[18] == 2 && places[19] == 2 && wayClear(i, 4)) {
                        res.add(Pair.of(new State2(this, i, 16), price(2, i, 16)));
                    }
                    if (places[16] == 0 && places[17] == 0 && places[18] == 2 && places[19] == 2 && wayClear(i, 4)) {
                        res.add(Pair.of(new State2(this, i, 17), price(2, i, 17)));
                    }
                    if (places[16] == 0 && places[17] == 0 && places[18] == 0 && places[19] == 2 && wayClear(i, 4)) {
                        res.add(Pair.of(new State2(this, i, 18), price(2, i, 18)));
                    }
                    if (places[16] == 0 && places[17] == 0 && places[18] == 0 && places[19] == 0 && wayClear(i, 4)) {
                        res.add(Pair.of(new State2(this, i, 19), price(2, i, 19)));
                    }
                } else if (places[i] == 3) {
                    // move C
                    if (places[20] == 0 && places[21] == 3 && places[22] == 3 && places[23] == 3 && wayClear(i, 6)) {
                        res.add(Pair.of(new State2(this, i, 20), price(3, i, 20)));
                    }
                    if (places[20] == 0 && places[21] == 0 && places[22] == 3 && places[23] == 3 && wayClear(i, 6)) {
                        res.add(Pair.of(new State2(this, i, 21), price(3, i, 21)));
                    }
                    if (places[20] == 0 && places[21] == 0 && places[22] == 0 && places[23] == 3 && wayClear(i, 6)) {
                        res.add(Pair.of(new State2(this, i, 22), price(3, i, 22)));
                    }
                    if (places[20] == 0 && places[21] == 0 && places[22] == 0 && places[23] == 0 && wayClear(i, 6)) {
                        res.add(Pair.of(new State2(this, i, 23), price(3, i, 23)));
                    }
                } else if (places[i] == 4) {
                    // move D
                    if (places[24] == 0 && places[25] == 4 && places[26] == 4 && places[27] == 4 && wayClear(i, 8)) {
                        res.add(Pair.of(new State2(this, i, 24), price(4, i, 24)));
                    }
                    if (places[24] == 0 && places[25] == 0 && places[26] == 4 && places[27] == 4 && wayClear(i, 8)) {
                        res.add(Pair.of(new State2(this, i, 25), price(4, i, 25)));
                    }
                    if (places[24] == 0 && places[25] == 0 && places[26] == 0 && places[27] == 4 && wayClear(i, 8)) {
                        res.add(Pair.of(new State2(this, i, 26), price(4, i, 26)));
                    }
                    if (places[24] == 0 && places[25] == 0 && places[26] == 0 && places[27] == 0 && wayClear(i, 8)) {
                        res.add(Pair.of(new State2(this, i, 27), price(4, i, 27)));
                    }
                }
            }

            for (int j = 0; j < 11; j++) {
                if (j == 2) continue;
                if (j == 4) continue;
                if (j == 6) continue;
                if (j == 8) continue;
                if (places[j] != 0) continue; // unoccupied?

                if (places[12] != 0 && places[2] == 0 && wayClear(2, j)) {
                    res.add(Pair.of(new State2(this, 12, j), price(places[12], 12, j)));
                }
                if (places[13] != 0 && places[12] == 0 && places[2] == 0 && wayClear(2, j)) {
                    res.add(Pair.of(new State2(this, 13, j), price(places[13], 13, j)));
                }
                if (places[14] != 0 && places[13] == 0 && places[12] == 0 && places[2] == 0 && wayClear(2, j)) {
                    res.add(Pair.of(new State2(this, 14, j), price(places[14], 14, j)));
                }
                if (places[15] != 0 && places[14] == 0 && places[13] == 0 && places[12] == 0 && places[2] == 0 && wayClear(2, j)) {
                    res.add(Pair.of(new State2(this, 15, j), price(places[15], 15, j)));
                }

                if (places[16] != 0 && places[4] == 0 && wayClear(4, j)) {
                    res.add(Pair.of(new State2(this, 16, j), price(places[16], 16, j)));
                }
                if (places[17] != 0 && places[16] == 0 && places[4] == 0 && wayClear(4, j)) {
                    res.add(Pair.of(new State2(this, 17, j), price(places[17], 17, j)));
                }
                if (places[18] != 0 && places[17] == 0 && places[16] == 0 && places[4] == 0 && wayClear(4, j)) {
                    res.add(Pair.of(new State2(this, 18, j), price(places[18], 18, j)));
                }
                if (places[19] != 0 && places[18] == 0 && places[17] == 0 && places[16] == 0 && places[4] == 0 && wayClear(4, j)) {
                    res.add(Pair.of(new State2(this, 19, j), price(places[19], 19, j)));
                }

                if (places[20] != 0 && places[6] == 0 && wayClear(6, j)) {
                    res.add(Pair.of(new State2(this, 20, j), price(places[20], 20, j)));
                }
                if (places[21] != 0 && places[20] == 0 && places[6] == 0 && wayClear(6, j)) {
                    res.add(Pair.of(new State2(this, 21, j), price(places[21], 21, j)));
                }
                if (places[22] != 0 && places[21] == 0 && places[20] == 0 && places[6] == 0 && wayClear(6, j)) {
                    res.add(Pair.of(new State2(this, 22, j), price(places[22], 22, j)));
                }
                if (places[23] != 0 && places[22] == 0 && places[21] == 0 && places[20] == 0 && places[6] == 0 && wayClear(6, j)) {
                    res.add(Pair.of(new State2(this, 23, j), price(places[23], 23, j)));
                }

                if (places[24] != 0 && places[8] == 0 && wayClear(8, j)) {
                    res.add(Pair.of(new State2(this, 24, j), price(places[24], 24, j)));
                }
                if (places[25] != 0 && places[24] == 0 && places[8] == 0 && wayClear(8, j)) {
                    res.add(Pair.of(new State2(this, 25, j), price(places[25], 25, j)));
                }
                if (places[26] != 0 && places[25] == 0 && places[24] == 0 && places[8] == 0 && wayClear(8, j)) {
                    res.add(Pair.of(new State2(this, 26, j), price(places[26], 26, j)));
                }
                if (places[27] != 0 && places[26] == 0 && places[25] == 0 && places[24] == 0 && places[8] == 0 && wayClear(8, j)) {
                    res.add(Pair.of(new State2(this, 27, j), price(places[27], 27, j)));
                }
            }
            return res;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State2 state = (State2) o;
            return Arrays.equals(places, state.places);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(places);
        }

        @Override
        public String toString() {
            return Arrays.toString(places);
        }

        public boolean isFinal() {
            return places[12] == 1 && places[13] == 1 &&
                    places[14] == 1 && places[15] == 1 &&
                    places[16] == 2 && places[17] == 2 &&
                    places[18] == 2 && places[19] == 2 &&
                    places[20] == 3 && places[21] == 3 &&
                    places[22] == 3 && places[23] == 3 &&
                    places[24] == 4 && places[25] == 4 &&
                    places[26] == 4 && places[27] == 4;
        }
    } // class

    @Override
    protected void part2(String fileContents) {
        var lines = fileContents.split(System.lineSeparator());

        var pos = new int[28];
        pos[12] = read(lines[2].charAt(3));
        pos[13] = 4;
        pos[14] = 4;
        pos[15] = read(lines[3].charAt(3));
        pos[16] = read(lines[2].charAt(5));
        pos[17] = 3;
        pos[18] = 2;
        pos[19] = read(lines[3].charAt(5));
        pos[20] = read(lines[2].charAt(7));
        pos[21] = 2;
        pos[22] = 1;
        pos[23] = read(lines[3].charAt(7));
        pos[24] = read(lines[2].charAt(9));
        pos[25] = 1;
        pos[26] = 3;
        pos[27] = read(lines[3].charAt(9));

        Map<State2, Long> distance = new HashMap<>();
        Map<State2, State2> pred = new HashMap<>();
        PriorityQueue<State2> unvisited = new PriorityQueue<>(Comparator.comparingLong(distance::get));

        var initial = new State2(pos);
        distance.put(initial, 0L);
        unvisited.add(initial);

        State2 finalState = null;

        while (!unvisited.isEmpty()) {
            var v = unvisited.poll();
            var dv = distance.get(v);
            if (v.isFinal()) {
                finalState = v;
                break;
            }

            for (var s : v.successors()) {
                var nv = s.a;
                var dnv = dv + s.b;

                if (distance.containsKey(nv)) {
                    var cur = distance.get(nv);
                    if (cur > dnv) {
                        unvisited.remove(nv);
                        distance.put(nv, dnv);
                        unvisited.add(nv);
                        pred.put(nv, v);
                    }
                } else {
                    distance.put(nv, dnv);
                    unvisited.add(nv);
                    pred.put(nv, v);
                }
            }
        }

        var states = new ArrayList<>(distance.entrySet());
        states.sort(Comparator.comparingLong(Map.Entry::getValue));

//        var s = finalState;
//        while (s != null) {
//            System.out.printf("%s distance %d%n", s, distance.get(s));
//            s = pred.get(s);
//        }

        System.out.println("Part 2: " + distance.get(finalState));    }

    public static void main(String[] args) {
        run(Day23::new, "example.txt", "input.txt");
    }
}
