package day10;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Day10 {

    private static int[] INPUT = {147, 37, 249, 1, 31, 2, 226, 0, 161, 71, 254, 243, 183, 255, 30, 70};
    private static int[] TEST = {3, 4, 1, 5};
    private static int[] SUFFIX = {17, 31, 73, 47, 23};
    private static String INPUT_STRING = "147,37,249,1,31,2,226,0,161,71,254,243,183,255,30,70";


    public static void main(String[] args) {
        System.out.println("PART1 answer for TEST: " + getSum(TEST, 5));
        System.out.println("PART1 answer for INPUT: " + getSum(INPUT, 256));

        System.out.println("PART2 answer for INPUT: " + getKnotHash(INPUT_STRING, 256));
    }


    public static String getKnotHash(String input, int length) {
        int[] sparseHash = getSparseHash(input, length);
        int[] denseHash = getDenseHash(sparseHash);

        StringBuilder knotHash = new StringBuilder();
        for (int hash : denseHash) {
            String hex = Integer.toHexString(hash);
            knotHash.append(hex.length() == 1 ? "0" + hex : hex);
        }
        return knotHash.toString();
    }

    private static int[] getDenseHash(int[] list) {
        int[] result = new int[16];
        int current = 0;
        int index = 0;
        while (current < list.length) {
            int block = 0;
            for (int i = current; i < current + 16; i++) {
                block ^= list[i];
            }
            result[index++] = block;
            current = 16 * index;
        }

        return result;
    }

    private static int[] getSparseHash(String input, int length) {
        int[] lengths = transformInput(input);
        int[] sparseHash = getList(length);

        int skipSize = 0;
        int current = 0;
        for (int i = 0; i < 64; i++) {
            for (int lengthValue : lengths) {
                reverseLengthWindow(sparseHash, current, lengthValue);
                current = (current + skipSize + lengthValue) % sparseHash.length;
                skipSize++;
            }
        }

        return sparseHash;
    }


    private static int[] transformInput(String s) {
        int[] array = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            int num = s.charAt(i);
            array[i] = num;
        }
        return IntStream.concat(Arrays.stream(array), Arrays.stream(SUFFIX)).toArray();
    }


    private static int getSum(int[] input, int length) {
        int[] list = getList(length);
        int skipSize = 0;
        int current = 0;

        for (int i : input) {
            reverseLengthWindow(list, current, i);
            current = (current + skipSize + i) % list.length;
            skipSize++;
        }
        return list[0] * list[1];
    }

    private static void reverseLengthWindow(int[] input, int current, int length) {
        int head = current;
        int tail = (current + length - 1) % input.length;

        for (int i = 1; i <= length / 2; i++) {
            swapElements(input, head, tail);
            tail = (tail - 1) < 0 ? input.length - 1 : tail - 1;
            head = (head + 1) % input.length;
        }

    }

    private static void swapElements(int[] input, int a, int b) {
        int temp = input[a];
        input[a] = input[b];
        input[b] = temp;
    }

    private static int[] getList(int length) {
        return IntStream.range(0, length).toArray();
    }
}
