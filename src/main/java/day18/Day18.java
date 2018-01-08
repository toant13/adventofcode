package day18;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day18 {


    private static final String INPUT = "Day18Input";
    private static final String TEST = "Day18Part1Test";

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("PART1 answer for TEST: " + getLastRecoveredFrequency(TEST));
        System.out.println("PART1 answer for INPUT: " + getLastRecoveredFrequency(INPUT));
    }

    public static long getLastRecoveredFrequency(String input) throws IOException, URISyntaxException {
        List<String> instructions = getInstructions(input);
        long lastRecoveredFrequency = 0;
        long lastPlayedFrequency = 0;
        Map<String, Long> registerMap = new HashMap<>();
        for (int i = 0; i < instructions.size(); i++) {
            String instruction = instructions.get(i);
            switch (instructions.get(i).split(" ")[0]) {
                case "snd":
                    lastPlayedFrequency = send(instruction, registerMap);
                    break;
                case "set":
                    set(instruction, registerMap);
                    break;
                case "add":
                    arithmetic(instruction, registerMap, (a, b) -> a + b);
                    break;
                case "mul":
                    arithmetic(instruction, registerMap, (a, b) -> a * b);
                    break;
                case "mod":
                    arithmetic(instruction, registerMap, (a, b) -> a % b);
                    break;
                case "rcv":
                    lastRecoveredFrequency = recover(instruction, lastPlayedFrequency, lastRecoveredFrequency, registerMap);
                    if (lastRecoveredFrequency > 0) {
                        return lastRecoveredFrequency;
                    }
                    break;
                case "jgz":
                    i = jump(instruction, i, registerMap);
                    break;
            }

        }

        return lastRecoveredFrequency;
    }

    private static void preprocessMap(Map<String, Long> registerMap, String register) {
        if (!registerMap.containsKey(register)) {
            registerMap.put(register, 0L);
        }
    }

    private static long send(String instruction, Map<String, Long> registerMap) {
        String register = instruction.split(" ")[1];
        preprocessMap(registerMap, register);

        return registerMap.get(register);
    }

    private static void set(String instruction, Map<String, Long> registerMap) {
        String[] instructionArray = instruction.split(" ");
        String register = instructionArray[1];
        String registerValueString = instructionArray[2];
        preprocessMap(registerMap, register);
        long registerValue = Character.isAlphabetic(registerValueString.charAt(0)) ? registerMap.get(registerValueString) : Integer.parseInt(registerValueString);
        registerMap.put(register, registerValue);
    }

    private static void arithmetic(String instruction, Map<String, Long> registerMap, BiFunction<Long, Long, Long> operation) {
        String[] instructionArray = instruction.split(" ");
        String register = instructionArray[1];
        String registerValueString = instructionArray[2];
        preprocessMap(registerMap, register);
        long registerValue = Character.isAlphabetic(registerValueString.charAt(0)) ? registerMap.get(registerValueString) : Integer.parseInt(registerValueString);

        long number = registerMap.get(register);
        registerMap.put(register, operation.apply(number, registerValue));
    }

    private static long recover(String instruction, long lastPlayedFrequency, long lastRecoveredFrequency, Map<String, Long> registerMap) {
        String[] instructionArray = instruction.split(" ");
        String register = instructionArray[1];
        preprocessMap(registerMap, register);
        long registerValue = registerMap.get(register);
        if (registerValue != 0) {
            return lastPlayedFrequency;
        }

        return lastRecoveredFrequency;
    }

    private static int jump(String instruction, int currentInstructionIndex, Map<String, Long> registerMap) {
        String[] instructionArray = instruction.split(" ");
        String register = instructionArray[1];
        String registerValueString = instructionArray[2];
        preprocessMap(registerMap, register);
        long offset = Character.isAlphabetic(registerValueString.charAt(0)) ? registerMap.get(register) : Integer.parseInt(registerValueString);

        if (registerMap.get(register) > 0) {
            return (int) (currentInstructionIndex + offset - 1);
        }

        return currentInstructionIndex;
    }

    private static List<String> getInstructions(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(Day18.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);
        return lines.collect(Collectors.toList());
    }
}
