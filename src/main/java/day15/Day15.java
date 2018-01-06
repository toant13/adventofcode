package day15;

public class Day15 {


    public static long INPUT_GENERATORA = 783;
    public static long INPUT_GENERATORB = 325;

    public static long TEST_GENERATORA = 65;
    public static long TEST_GENERATORB = 8921;

    public static final int FACTOR_A = 16807;
    public static final int FACTOR_B = 48271;
    public static final int DIVISOR = 2147483647;

    public static int TOTAL_PAIRS = 40000000;

    public static void main(String[] args) {
        System.out.println("PART1 answer for TEST: " + getJudgeCount(TEST_GENERATORA, TEST_GENERATORB));
        System.out.println("PART1 answer for INPUT: " + getJudgeCount(INPUT_GENERATORA, INPUT_GENERATORB));
    }


    public static int getJudgeCount(long inputA, long inputB) {
        int count = 0;

        for (int i = 0; i < TOTAL_PAIRS; i++) {
            long previousInputA = inputA;
            long previousInputB = inputB;

            inputA = (previousInputA * FACTOR_A) % DIVISOR;
            inputB = (previousInputB * FACTOR_B) % DIVISOR;

            if (isPair(inputA, inputB)) {
                count++;
            }
        }

        return count;
    }

    private static boolean isPair(long inputA, long inputB) {
        String binaryA = Long.toBinaryString(inputA);
        String binaryB = Long.toBinaryString(inputB);

        if (binaryA.length() < 16 || binaryB.length() < 16) {
            return false;
        }

        int shiftA = binaryA.length()-16;
        int shiftB = binaryB.length()-16;
        String binary16ofA =  binaryA.substring(shiftA);
        String binary16ofB =  binaryB.substring(shiftB);

        return binary16ofA.equals(binary16ofB);
    }
}
