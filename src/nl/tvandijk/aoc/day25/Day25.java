package nl.tvandijk.aoc.day25;

import nl.tvandijk.aoc.common.AoCCommon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Day25 extends AoCCommon {
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
    public void process(InputStream stream) throws IOException {
        long cardPublicKey, doorPublicKey;

        try (var br = new BufferedReader(new InputStreamReader(stream))) {
            cardPublicKey = Long.parseLong(br.readLine());
            doorPublicKey = Long.parseLong(br.readLine());
        }

        // long cardPublicKey = transform(7, cardSecret);
        // long doorPublicKey = transform(7, doorSecret);

        var cardSecret = reverse(7, cardPublicKey);
        var doorSecret = reverse(7, doorPublicKey);

        var encryptKey = transform(cardPublicKey, doorSecret);
        var encryptKey2 = transform(doorPublicKey, cardSecret);

        System.out.printf("secret: %d or %d\n", encryptKey, encryptKey2);
    }

    public static void main(String[] args) {
        run(Day25::new, "example.txt", "input.txt");
    }
}