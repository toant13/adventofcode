package day10;

public class Day10 {

    private static int[] INPUT = {147, 37, 249, 1, 31, 2, 226, 0, 161, 71, 254, 243, 183, 255, 30, 70};
    private static int[] TEST = {3, 4, 1, 5};


    public static void main(String[] args) {
        System.out.println("PART1 answer for TEST: " + getSum(TEST, 5));
        System.out.println("PART1 answer for INPUT: " + getSum(INPUT, 256));
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
        int[] list = new int[length];
        for (int i = 0; i < list.length; i++) {
            list[i] = i;
        }
        return list;
    }
}
