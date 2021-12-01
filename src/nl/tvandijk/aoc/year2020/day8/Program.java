package nl.tvandijk.aoc.year2020.day8;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Program {
    private List<Instruction> instructions;
    private boolean quit = false;

    public Program(InputStream stream) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            instructions = br.lines().map(this::parseInstruction).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int runUntilRepeat() {
        boolean[] seen = new boolean[instructions.size()];

        var state = new ProgramState();

        while (!seen[state.getPc()]) {
            seen[state.getPc()] = true;
            instructions.get(state.getPc()).run(state);

            if (state.getPc() >= instructions.size()) {
                System.out.printf("Reached end of program: %d\n", state.getAccumulator());
                quit = true;
                break;
            }
        }

        return state.getAccumulator();
    }

    public void attemptToFix() {
        for (int i = 0; i < instructions.size(); i++) {
            Instruction in = instructions.get(i);
            if (in instanceof Noop) {
                instructions.set(i, ((Noop) in).asJmp());
                runUntilRepeat();
                if (quit) return;
                instructions.set(i, in);
            } else if (in instanceof Jmp) {
                instructions.set(i, ((Jmp) in).asNoop());
                runUntilRepeat();
                if (quit) return;
                instructions.set(i, in);
            }
        }
    }

    private Instruction parseInstruction(String line) {
        Pattern p = Pattern.compile("^\\s*(\\w+)\\s+([+-][0-9]+)\\s*$");
        Matcher m = p.matcher(line);
        if (!m.matches()) {
            System.out.println("Error matching instruction" + line);
            return null;
        }

        switch (m.group(1)) {
            case "jmp":
                return new Jmp(Integer.parseInt(m.group(2)));
            case "acc":
                return new Acc(Integer.parseInt(m.group(2)));
            case "nop":
                return new Noop(Integer.parseInt(m.group(2)));
        }

        return null;
    }
}
