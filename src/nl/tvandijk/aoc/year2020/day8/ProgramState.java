package nl.tvandijk.aoc.year2020.day8;

class ProgramState {
    private int pc = 0;
    private int accumulator = 0;

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getAccumulator() {
        return accumulator;
    }

    public void setAccumulator(int accumulator) {
        this.accumulator = accumulator;
    }
}
