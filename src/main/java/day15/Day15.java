package day15;

public class Day15 {

    public static long INPUT_GENERATORA = 783;
    public static long INPUT_GENERATORB = 325;

    public static long TEST_GENERATORA = 65;
    public static long TEST_GENERATORB = 8921;

    public static final int FACTOR_A = 16807;
    public static final int FACTOR_B = 48271;
    public static final int MULTIPLE_A = 4;
    public static final int MULTIPLE_B = 8;
    public static final int DIVISOR = 2147483647;


    public static int BITS_TO_COMPARE = 16;
    public static int PART1_TOTAL_PAIRS = 40000000;
    public static int PART2_TOTAL_PAIRS = 5000000;

    public static void main(String[] args) {
        System.out.println("PART1 answer for TEST: " + getJudgeCount(TEST_GENERATORA, TEST_GENERATORB, PART1_TOTAL_PAIRS, 1, 1));
        System.out.println("PART1 answer for INPUT: " + getJudgeCount(INPUT_GENERATORA, INPUT_GENERATORB, PART1_TOTAL_PAIRS, 1, 1));

        System.out.println("PART2 answer for TEST: " + getJudgeCount(TEST_GENERATORA, TEST_GENERATORB, PART2_TOTAL_PAIRS, MULTIPLE_A, MULTIPLE_B));
        System.out.println("PART2 answer for INPUT: " + getJudgeCount(INPUT_GENERATORA, INPUT_GENERATORB, PART2_TOTAL_PAIRS, MULTIPLE_A, MULTIPLE_B));
    }

    public static int getJudgeCount(long inputA, long inputB, int rounds, int multipleA, int multipleB) {
        int count = 0;

        for (int i = 0; i < rounds; i++) {
            inputA = getJudgeNumber(inputA, multipleA, FACTOR_A);
            inputB = getJudgeNumber(inputB, multipleB, FACTOR_B);

            if (isPair(inputA, inputB)) {
                count++;
            }
        }

        return count;
    }

    private static long getJudgeNumber(long input, int multiple, int factor) {
        do {
            long previousInput = input;
            input = (previousInput * factor) % DIVISOR;
        } while (input % multiple != 0);

        return input;
    }

    private static boolean isPair(long inputA, long inputB) {
        String binaryA = Long.toBinaryString(inputA);
        String binaryB = Long.toBinaryString(inputB);

        if (binaryA.length() < BITS_TO_COMPARE || binaryB.length() < BITS_TO_COMPARE) {
            return false;
        }

        int shiftA = binaryA.length() - BITS_TO_COMPARE;
        int shiftB = binaryB.length() - BITS_TO_COMPARE;
        String binaryBITS_TO_COMPAREofA = binaryA.substring(shiftA);
        String binaryBITS_TO_COMPAREofB = binaryB.substring(shiftB);

        return binaryBITS_TO_COMPAREofA.equals(binaryBITS_TO_COMPAREofB);
    }
}
