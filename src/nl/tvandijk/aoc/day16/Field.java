package nl.tvandijk.aoc.day16;

class Field {
    String name;
    int min1;
    int max1;
    int min2;
    int max2;

    public Field(String name, int min1, int max1, int min2, int max2) {
        this.name = name;
        this.min1 = min1;
        this.max1 = max1;
        this.min2 = min2;
        this.max2 = max2;
    }

    public boolean matches(int value) {
        return (value >= min1 && value <= max1) || (value >= min2 && value <= max2);
    }

    @Override
    public String toString() {
        return name;
    }
}
