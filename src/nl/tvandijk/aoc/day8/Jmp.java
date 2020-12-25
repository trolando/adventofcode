package nl.tvandijk.aoc.day8;

class Jmp implements Instruction {
    private final int offset;

    public Jmp(int offset) {
        this.offset = offset;
    }

    public void run(ProgramState state) {
        state.setPc(state.getPc() + offset);
    }

    public Noop asNoop() {
        return new Noop(offset);
    }
}
