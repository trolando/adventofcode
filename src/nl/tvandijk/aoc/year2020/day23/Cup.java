package nl.tvandijk.aoc.year2020.day23;

class Cup {
    final int value;

    private Cup nextLower;
    private Cup next;
    private Cup prev;

    public Cup(int value) {
        this.value = value;
    }

    public Cup getNextLower() {
        return nextLower;
    }

    public void setNextLower(Cup nextLower) {
        this.nextLower = nextLower;
    }

    public Cup getNext() {
        return next;
    }

    public void setNext(Cup next) {
        this.next = next;
    }

    public Cup getPrev() {
        return prev;
    }

    public void setPrev(Cup prev) {
        this.prev = prev;
    }

    @Override
    public String toString() {
        return "Cup " + value;
    }
}
