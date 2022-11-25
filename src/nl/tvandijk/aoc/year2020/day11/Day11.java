package nl.tvandijk.aoc.year2020.day11;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11 extends Day {
    int[] board;
    int width, height;

    int pos(int x, int y) {
        return y*width + x;
    }

    int occ(int x, int y) {
        if (x>=0 && x<width && y>=0 && y<height) return board[pos(x,y)] == 1 ? 1 : 0;
        else return 0;
    }

    int occ2(int x, int y, int cx, int cy) {
        if (x>=0 && x<width && y>=0 && y<height) {
            if (board[pos(x,y)] != -1) return board[pos(x,y)];
            else return occ2(x+cx, y+cy, cx, cy);
        } else {
            return 0;
        }
    }

    int adj2(int x, int y) {
        return occ2(x-1,y, -1, 0) +
                occ2(x-1,y-1, -1, -1) +
                occ2(x-1,y+1, -1, 1) +
                occ2(x,y-1, 0, -1) +
                occ2(x,y+1, 0, +1) +
                occ2(x+1,y, 1, 0) +
                occ2(x+1,y-1, 1, -1) +
                occ2(x+1,y+1, 1, 1);
    }

    int adj(int x, int y) {
        return occ(x-1,y) + occ(x-1,y-1) + occ(x-1,y+1) + occ(x,y-1) +
                occ(x,y+1)+ occ(x+1,y) + occ(x+1,y-1) + occ(x+1,y+1);
    }

    boolean apply() {
        boolean change = false;
        int[] newBoard = board.clone();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (board[pos(x,y)] == -1) {
                    newBoard[pos(x,y)] = -1;
                } else if (board[pos(x,y)] == 0) {
                    if (adj(x,y) == 0) {
                        newBoard[pos(x,y)] = 1;
                        change=true;
                    }
                    else newBoard[pos(x,y)] = 0;
                } else {
                    if (adj(x,y) >= 4) {
                        newBoard[pos(x,y)] = 0;
                        change=true;
                    }
                    else newBoard[pos(x,y)] = 1;
                }
            }
        }
        board = newBoard;
        return change;
    }

    boolean apply2() {
        boolean change = false;
        int[] newBoard = board.clone();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (board[pos(x,y)] == -1) {
                    newBoard[pos(x,y)] = -1;
                } else if (board[pos(x,y)] == 0) {
                    if (adj2(x,y) == 0) {
                        newBoard[pos(x,y)] = 1;
                        change=true;
                    }
                    else newBoard[pos(x,y)] = 0;
                } else {
                    if (adj2(x,y) >= 5) {
                        newBoard[pos(x,y)] = 0;
                        change=true;
                    }
                    else newBoard[pos(x,y)] = 1;
                }
            }
        }
        board = newBoard;
        return change;
    }

    @Override
    protected Object part1() throws Exception {
        List<Integer> arr = new ArrayList<>();

        for (var line : lines) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == 'L') arr.add(0);
                else if (line.charAt(i) == '.') arr.add(-1);
                else if (line.charAt(i) == '#') arr.add(1);
            }
            height++;
        }

        board = arr.stream().mapToInt(x -> x).toArray();
        width = board.length/height;
        while (apply()) ;
        // count occupied
        return Arrays.stream(board).filter(x-> x == 1).count();
    }

    @Override
    protected Object part2() throws Exception {
        List<Integer> arr = new ArrayList<>();

        for (var line : lines) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == 'L') arr.add(0);
                else if (line.charAt(i) == '.') arr.add(-1);
                else if (line.charAt(i) == '#') arr.add(1);
            }
            height++;
        }

        board = arr.stream().mapToInt(x -> x).toArray();
        width = board.length/height;
        while (apply2()) ;
        // count occupied
        return (int) Arrays.stream(board).filter(x-> x == 1).count();
    }

    @Override
    protected boolean resetForPartTwo() {
        return true;
    }
}
