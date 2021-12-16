package nl.tvandijk.aoc.year2021.day16;

import nl.tvandijk.aoc.common.Day;

import java.util.*;

public class Day16 extends Day {

    private static class Packet {
        int version;
        int type;
        long value;
        List<Packet> subPackets = new ArrayList<>();

        @Override
        public String toString() {
            var sb = new StringBuilder();
            sb.append("v");
            sb.append(version);
            sb.append(" type ");
            sb.append(type);
            if (type == 4) {
                sb.append(" value=");
                sb.append(value);
            } else {
                sb.append(" {");
                for (int i = 0; i < subPackets.size(); i++) {
                    sb.append(subPackets.get(0).toString());
                    sb.append(", ");
                }
                sb.append("}");
            }
            return sb.toString();
        }

        public long versionSum() {
            long res = version;
            for (int i = 0; i < subPackets.size(); i++) {
                res += subPackets.get(i).versionSum();
            }
            return res;
        }

        public long eval() {
            switch (type) {
                case 0 -> {
                    long res = 0;
                    for (int i = 0; i < subPackets.size(); i++) {
                        res += subPackets.get(i).eval();
                    }
                    return res;
                }
                case 1 -> {
                    long res = 1;
                    for (int i = 0; i < subPackets.size(); i++) {
                        res *= subPackets.get(i).eval();
                    }
                    return res;
                }
                case 2 -> {
                    long res = Long.MAX_VALUE;
                    for (int i = 0; i < subPackets.size(); i++) {
                        res = Math.min(res, subPackets.get(i).eval());
                    }
                    return res;
                }
                case 3 -> {
                    long res = Long.MIN_VALUE;
                    for (int i = 0; i < subPackets.size(); i++) {
                        res = Math.max(res, subPackets.get(i).eval());
                    }
                    return res;
                }
                case 4 -> {
                    return value;
                }
                case 5 -> {
                    return subPackets.get(0).eval() > subPackets.get(1).eval() ? 1 : 0;
                }
                case 6 -> {
                    return subPackets.get(0).eval() < subPackets.get(1).eval() ? 1 : 0;
                }
                case 7 -> {
                    return subPackets.get(0).eval() == subPackets.get(1).eval() ? 1 : 0;
                }
                default -> {
                    return -1;
                }
            }
        }
    }

    private static class Parser {
        String bits;
        int bitsPos = 0;
        Packet root;

        public Parser(String bits) {
            this.bits = bits;
            root = readPacket();
        }

        private boolean nextBit() {
            return bits.charAt(bitsPos++) == '1';
        }

        private long nextInt(int bits) {
            long r = 0;
            for (int i = 0; i < bits; i++) {
                r = r * 2L + (nextBit() ? 1 : 0);
            }
            return r;
        }

        public Packet getRootPacket() {
            return root;
        }

        private Packet readPacket() {
            var packet = new Packet();
            int startPos = bitsPos;
            packet.version = (int)nextInt(3);
            packet.type = (int)nextInt(3);

            if (packet.type == 4) {
                // literal value
                long res = 0;
                while (true) {
                    boolean c = nextBit();
                    res = res * 16 + nextInt(4);
                    if (!c) break;
                }
                packet.value = res;
            } else {
                // operator packet
                boolean lengthTypeID = nextBit();
                if (lengthTypeID) {
                    // is one
                    var numPackets = nextInt(11);
                    for (int i = 0; i < numPackets; i++) {
                        packet.subPackets.add(readPacket());
                    }
                } else {
                    var length = nextInt(15);
                    var posEnd = bitsPos + length;
                    while (bitsPos < posEnd) {
                        packet.subPackets.add(readPacket());
                    }
                }
            }
            return packet;
        }
    }

    private Parser parse(String fileContents) {
        var bitsBuilder = new StringBuilder();
        fileContents = fileContents.trim();
        for (int i = 0; i < fileContents.length(); i++) {
            switch (fileContents.charAt(i)) {
                case '0' -> bitsBuilder.append("0000");
                case '1' -> bitsBuilder.append("0001");
                case '2' -> bitsBuilder.append("0010");
                case '3' -> bitsBuilder.append("0011");
                case '4' -> bitsBuilder.append("0100");
                case '5' -> bitsBuilder.append("0101");
                case '6' -> bitsBuilder.append("0110");
                case '7' -> bitsBuilder.append("0111");
                case '8' -> bitsBuilder.append("1000");
                case '9' -> bitsBuilder.append("1001");
                case 'A' -> bitsBuilder.append("1010");
                case 'B' -> bitsBuilder.append("1011");
                case 'C' -> bitsBuilder.append("1100");
                case 'D' -> bitsBuilder.append("1101");
                case 'E' -> bitsBuilder.append("1110");
                case 'F' -> bitsBuilder.append("1111");
            }
        }
        return new Parser(bitsBuilder.toString());
    }

    @Override
    protected void part1(String fileContents) {
        System.out.println("Part 1: " + parse(fileContents).getRootPacket().versionSum());
    }

    private void eval(String s) {
        var sb = new StringBuilder();
        sb.append("Evaluation of ");
        sb.append(s);
        sb.append(": ");
        sb.append(parse(s).getRootPacket().eval());
        System.out.println(sb);
    }

    @Override
    protected void part2(String fileContents) {
//        eval("C200B40A82");
//        eval("04005AC33890");
//        eval("880086C3E88112");
//        eval("CE00C43D881120");
//        eval("D8005AC2A8F0");
//        eval("F600BC2D8F");
//        eval("9C005AC2F8F0");
//        eval("9C0141080250320F1802104A08");

        System.out.println("Part 2: " + parse(fileContents).getRootPacket().eval());
    }

    public static void main(String[] args) {
        run(Day16::new, "example.txt", "input.txt");
    }
}
