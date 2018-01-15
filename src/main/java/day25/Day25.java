package day25;

public class Day25 {


    public static void main(String[] args) {
        System.out.println("PART1 answer for TEST: " + getCheckSum(6, new TestFunctions()));
        System.out.println("PART1 answer for INPUT: " + getCheckSum(12919244, new InputFunctions()));
    }

    public static int getCheckSum(int iterations, Functions function) {
        Tape tape = new Tape(0, function);
        for (int i = 0; i < iterations; i++) {
            tape.process();
        }

        return tape.positionValues.size();
    }
}
