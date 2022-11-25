package nl.tvandijk.aoc.year2020.day14;

import nl.tvandijk.aoc.common.Day;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.HashMap;
import java.util.Map;

public class Day14 extends Day {
    public Day14() {
        super("example1.txt", "example2.txt", "input.txt");
    }

    private static long applyMask(long value, String mask) {
        long M = 1L;
        for (int i = 0; i < mask.length(); i++) {
            switch (mask.charAt(mask.length()-1-i)) {
                case '1':
                    value |= M;
                    break;
                case '0':
                    value &= ~M;
                    break;
                default:
                    break;
            }
            M <<= 1;
        }
        return value;
    }

    @Override
    protected Object part1() throws Exception {
        var lexer = new InputLexer(CharStreams.fromString(fileContents));
        var parser = new InputParser(new CommonTokenStream(lexer));
        var tree = parser.start();

        String mask = null;
        Map<Long, Long> memory = new HashMap<>();

        for (var xx : tree.instruction()) {
            if (xx instanceof InputParser.MaskInstructionContext) {
                var maskInstr = (InputParser.MaskInstructionContext) xx;
                mask = maskInstr.XSTR().getText();
            } else if (xx instanceof InputParser.MemInstructionContext) {
                var memInstr = (InputParser.MemInstructionContext) xx;
                long addr = Long.parseLong(memInstr.loc.getText());
                long val = Long.parseLong(memInstr.val.getText());
                val = applyMask(val, mask);
                memory.put(addr, val);
            }
        }

        return memory.values().stream().mapToLong(l->l).sum();
    }

    @Override
    protected Object part2() throws Exception {
        var lexer = new InputLexer(CharStreams.fromString(fileContents));
        var parser = new InputParser(new CommonTokenStream(lexer));
        var tree = parser.start();

        String mask = null;
        TreeNode currentMemory = null;

        for (var xx : tree.instruction()) {
            if (xx instanceof InputParser.MaskInstructionContext) {
                var maskInstr = (InputParser.MaskInstructionContext) xx;
                mask = maskInstr.XSTR().getText();
            } else if (xx instanceof InputParser.MemInstructionContext) {
                var memInstr = (InputParser.MemInstructionContext) xx;
                long addr = Long.parseLong(memInstr.loc.getText());
                long val = Long.parseLong(memInstr.val.getText());
                currentMemory = TreeNode.set(currentMemory, addr, mask, val);
            }
        }

        return currentMemory.computeValue();
    }
}
