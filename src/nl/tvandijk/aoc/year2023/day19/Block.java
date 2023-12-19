package nl.tvandijk.aoc.year2023.day19;

record Block(
        String flow,
        int xmin, int xmax,
        int mmin, int mmax,
        int amin, int amax,
        int smin, int smax) {
    Block withFlow(String flow) {
        return new Block(flow, xmin, xmax, mmin, mmax, amin, amax, smin, smax);
    }

    Block withXmin(int xmin) {
        return new Block(flow, xmin, xmax, mmin, mmax, amin, amax, smin, smax);
    }

    Block withXmax(int xmax) {
        return new Block(flow, xmin, xmax, mmin, mmax, amin, amax, smin, smax);
    }

    Block withMmin(int mmin) {
        return new Block(flow, xmin, xmax, mmin, mmax, amin, amax, smin, smax);
    }

    Block withMmax(int mmax) {
        return new Block(flow, xmin, xmax, mmin, mmax, amin, amax, smin, smax);
    }

    Block withAmin(int amin) {
        return new Block(flow, xmin, xmax, mmin, mmax, amin, amax, smin, smax);
    }

    Block withAmax(int amax) {
        return new Block(flow, xmin, xmax, mmin, mmax, amin, amax, smin, smax);
    }

    Block withSmin(int smin) {
        return new Block(flow, xmin, xmax, mmin, mmax, amin, amax, smin, smax);
    }

    Block withSmax(int smax) {
        return new Block(flow, xmin, xmax, mmin, mmax, amin, amax, smin, smax);
    }
}
