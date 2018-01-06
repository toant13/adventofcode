package day14;

import day10.Day10;

public class Day14 {

    private static final String INPUT = "oundnydw";
    private static final String TEST = "flqrgnkx";

    private static final int SIZE = 128;
    private static final int KNOT_HASH_LENGTH = 256;

    public static void main(String[] args) {
        System.out.println("PART1 answer for TEST: " + findOpenSquares(TEST));
        System.out.println("PART1 answer for INPUT: " + findOpenSquares(INPUT));

    }


    public static int findOpenSquares(String input) {
        String[] knotHashArray = getKnotHashArray(input);
        int count = 0;

        for (String s : knotHashArray) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '1') {
                    count++;
                }
            }
        }

        return count;
    }

    private static String[] getKnotHashArray(String input) {
        String[] result = new String[SIZE];

        for (int i = 0; i < SIZE; i++) {
            String newInput = input + "-" + i;
            String knotHash = Day10.getKnotHash(newInput, KNOT_HASH_LENGTH);

            String binary = getBinaryFromHex(knotHash);
            result[i] = binary;
        }

        return result;
    }

    private static String getBinaryFromHex(String hex) {
        StringBuilder fullBinaryString = new StringBuilder();

        for (int i = 0; i < hex.length(); i++) {
            int decimal = Integer.parseInt(hex.substring(i, i + 1), 16);
            String binary = Integer.toBinaryString(decimal);
            String bufferedBinary = getBufferedBinary(binary);
            fullBinaryString.append(bufferedBinary);
        }
        return fullBinaryString.toString();
    }

    private static String getBufferedBinary(String binary) {
        StringBuilder bufferedBinary = new StringBuilder();
        for (int i = 0; i < 4 - binary.length(); i++) {
            bufferedBinary.append("0");
        }
        bufferedBinary.append(binary);
        return bufferedBinary.toString();
    }


}
