package day17;

import java.util.LinkedList;
import java.util.List;

public class Day17 {

    private static final int TEST = 3;
    private static final int INPUT = 343;

    public static void main(String[] args) {
        System.out.println("PART1 answer for TEST: " + getSpinLockValue(TEST));
        System.out.println("PART2 answer for INPUT: " + getSpinLockValue(INPUT));
    }


    public static int getSpinLockValue(int input) {
        List<Integer> list = new LinkedList<>();
        list.add(0);
        int currentValue = 1;
        int currentPosition = 0;

        while (currentValue <= 2017) {
            currentPosition = (input + currentPosition + 1) % list.size();
            list.add(currentPosition + 1, currentValue++);
        }

        return list.get(currentPosition+ 2);
    }
}
