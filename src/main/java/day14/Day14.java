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

        System.out.println("PART2 answer for TEST: " + findNumberOfRegions(TEST));
        System.out.println("PART2 answer for INPUT: " + findNumberOfRegions(INPUT));
    }

    public static int findNumberOfRegions(String input) {
        String[] array = getKnotHashArray(input);
        boolean[][] visited = new boolean[array.length][array[0].length()];
        int regionCount = 0;

        for (int y = 0; y < array.length; y++) {
            for (int x = 0; x < array[0].length(); x++) {
                if (visited[y][x] == false && array[y].charAt(x) == '1') {
                    markVisited(array, visited, x, y);
                    regionCount++;
                }
            }
        }

        return regionCount;
    }

    private static void markVisited(String[] array, boolean[][] visited, int currentX, int currentY) {
        if (currentX < 0 || currentX >= array[0].length() || currentY < 0 || currentY >= array.length || visited[currentY][currentX] || array[currentY].charAt(currentX) == '0') {
            return;
        }

        visited[currentY][currentX] = true;
        markVisited(array, visited, currentX, currentY - 1); //up
        markVisited(array, visited, currentX + 1, currentY); //right
        markVisited(array, visited, currentX, currentY + 1); //down
        markVisited(array, visited, currentX - 1, currentY); //left
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
