package nl.tvandijk.aoc.year2020.day25;

import nl.tvandijk.aoc.common.Day;

public class Day25 extends Day {
    public long transform(long subjectNumber, long loopSize) {
        long value = 1;
        for (int i = 0; i < loopSize; i++) {
            value = (value * subjectNumber) % 20201227;
        }
        return value;
    }

    public int reverse(long subjectNumber, long target) {
        long value = 1;
        int loopSize = 0;
        while (value != target) {
            value = (value * subjectNumber) % 20201227;
            loopSize++;
        }
        return loopSize;
    }

    @Override
    protected Object part1() throws Exception {
        long cardPublicKey = Long.parseLong(lines[0]);
        long doorPublicKey = Long.parseLong(lines[1]);

        // long cardPublicKey = transform(7, cardSecret);
        // long doorPublicKey = transform(7, doorSecret);

        var cardSecret = reverse(7, cardPublicKey);
        var doorSecret = reverse(7, doorPublicKey);

        var encryptKey = transform(cardPublicKey, doorSecret);
        var encryptKey2 = transform(doorPublicKey, cardSecret);

//        System.out.printf("secret: %d or %d\n", encryptKey, encryptKey2);
        return encryptKey;
    }

    @Override
    protected Object part2() throws Exception {
        return null;
    }
}