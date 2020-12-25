package nl.tvandijk.aoc.day8;

class Noop implements Instruction {
    private final int param;

    public Noop(int param) {
        this.param = param;
    }

    public void run(ProgramState state) {
        state.setPc(state.getPc() + 1);
    }

    public Jmp asJmp() {
        return new Jmp(param);
    }
}
