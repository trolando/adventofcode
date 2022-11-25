package nl.tvandijk.aoc.year2021.day16;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayList;
import java.util.List;

public class Day16 extends Day {
    private static class Packet {
        int version;
        int type;
        long value;
        List<Packet> subPackets = new ArrayList<>();

        public long versionSum() {
            return version + subPackets.stream().mapToLong(Packet::versionSum).sum();
        }

        public long eval() {
            return switch (type) {
                case 0 -> subPackets.stream().mapToLong(Packet::eval).sum();
                case 1 -> subPackets.stream().mapToLong(Packet::eval).reduce(1L, (x,y) -> x*y);
                case 2 -> subPackets.stream().mapToLong(Packet::eval).min().orElseThrow();
                case 3 -> subPackets.stream().mapToLong(Packet::eval).max().orElseThrow();
                case 4 -> value;
                case 5 -> subPackets.get(0).eval() > subPackets.get(1).eval() ? 1 : 0;
                case 6 -> subPackets.get(0).eval() < subPackets.get(1).eval() ? 1 : 0;
                case 7 -> subPackets.get(0).eval() == subPackets.get(1).eval() ? 1 : 0;
                default -> -1;
            };
        }
    }

    private static class Parser {
        String bits;
        int bitsPos = 0;

        private Parser(String bits) {
            this.bits = bits;
        }

        public static Packet parseBits(String bits) {
            return new Parser(bits).readPacket();
        }

        public static Packet parseHex(String hex) {
            var bitsBuilder = new StringBuilder();
            hex = hex.trim();
            for (int i = 0; i < hex.length(); i++) {
                bitsBuilder.append(switch (hex.charAt(i)) {
                    case '0' -> "0000";
                    case '1' -> "0001";
                    case '2' -> "0010";
                    case '3' -> "0011";
                    case '4' -> "0100";
                    case '5' -> "0101";
                    case '6' -> "0110";
                    case '7' -> "0111";
                    case '8' -> "1000";
                    case '9' -> "1001";
                    case 'A' -> "1010";
                    case 'B' -> "1011";
                    case 'C' -> "1100";
                    case 'D' -> "1101";
                    case 'E' -> "1110";
                    case 'F' -> "1111";
                    default -> "----";
                });
            }
            return parseBits(bitsBuilder.toString());
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

        private Packet readPacket() {
            var packet = new Packet();
            packet.version = (int)nextInt(3);
            packet.type = (int)nextInt(3);

            if (packet.type == 4) {
                // literal value
                var res = 0L;
                var more = true;
                while (more) {
                    more = nextBit();
                    res = res * 16 + nextInt(4);
                }
                packet.value = res;
            } else {
                // operator packet
                if (nextBit()) { // length type
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

    @Override
    protected Object part1() {
        return Parser.parseHex(fileContents.trim()).versionSum();
    }

    private void eval(String s) {
        System.out.printf("Evaluation of %s: %d%n", s.trim(), Parser.parseHex(s.trim()).eval());
    }

    @Override
    protected Object part2() {
        if (false) {
            eval("C200B40A82");
            eval("04005AC33890");
            eval("880086C3E88112");
            eval("CE00C43D881120");
            eval("D8005AC2A8F0");
            eval("F600BC2D8F");
            eval("9C005AC2F8F0");
            eval("9C0141080250320F1802104A08");
        }

        return Parser.parseHex(fileContents.trim()).eval();
    }
}
