package nl.tvandijk.aoc.day8;

class Acc implements Instruction {
    private final int amount;

    public Acc(int amount) {
        this.amount = amount;
    }

    public void run(ProgramState state) {
        state.setAccumulator(state.getAccumulator() + amount);
        state.setPc(state.getPc() + 1);
    }
}
