import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day6 {

    private static final int[] INPUT = new int[]{10, 3, 15, 10, 5, 15, 5, 15, 9, 2, 5, 8, 5, 2, 3, 6};
    private static final int[] TEST1 = new int[]{0, 2, 7, 0};

    public static void main(String[] args) {
        System.out.println("PART 1 Test 1: " + memoryReallocationPart1(TEST1));
        System.out.println("PART 1 INPUT: " + memoryReallocationPart1(INPUT));

        System.out.println("PART 2 Test 1: " + memoryReallocationPart2(TEST1));
        System.out.println("PART 2 INPUT: " + memoryReallocationPart2(INPUT));
    }

    public static int memoryReallocationPart1(int[] array) {
        int rounds = 0;
        Set<String> set = new HashSet<>();
        String hash;
        do {
            int index = getMaxIndex(array);
            redistribute(array, index);
            hash = getHash(array);
            rounds++;
        } while (set.add(hash));

        return rounds;
    }

    public static int memoryReallocationPart2(int[] array) {
        int rounds = 0;
        Map<String, Integer> map = new HashMap<>();
        while (true) {
            int index = getMaxIndex(array);
            redistribute(array, index);
            String hash = getHash(array);
            rounds++;

            if (map.containsKey(hash)) {
                rounds = rounds - map.get(hash);
                break;
            } else {
                map.put(hash, rounds);
            }
        }

        return rounds;
    }

    private static void redistribute(int[] array, int index) {
        int number = array[index];
        array[index] = 0;
        int i = index;
        while (number > 0) {
            i = (i + 1) % array.length;
            array[i] = array[i] + 1;
            number--;
        }
    }


    private static int getMaxIndex(int[] array) {
        int index = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                index = i;
            }
        }

        return index;
    }


    private static String getHash(int[] array) {
        StringBuilder str = new StringBuilder();
        for (int i : array) {
            str.append("-" + i);
        }

        return str.toString();
    }
}
