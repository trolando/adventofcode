package nl.tvandijk.aoc.year2020.day14;

import nl.tvandijk.aoc.common.AoCCommon;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Day14 extends AoCCommon {

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

    private long executePart1(InputParser.StartContext tree) {
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

    private long executePart2(InputParser.StartContext tree) {
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

    @Override
    protected void process(InputStream stream) throws Exception {
        var lexer = new InputLexer(CharStreams.fromStream(stream));
        var parser = new InputParser(new CommonTokenStream(lexer));
        var tree = parser.start();

        System.out.printf("Result of part 1: %d\n", executePart1(tree));
        System.out.printf("Result of part 2: %d\n", executePart2(tree));
    }

    public static void main(String[] args) {
        run(Day14::new, "example1.txt", "example2.txt", "input.txt");
    }
}
