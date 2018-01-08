package day18;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day18 {


    private static final String INPUT = "Day18Input";
    private static final String TEST = "Day18Part1Test";
    private static final String TEST2 = "Day18Part2Test";

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("PART1 answer for TEST: " + getLastRecoveredFrequency(TEST));
        System.out.println("PART1 answer for INPUT: " + getLastRecoveredFrequency(INPUT));

        System.out.println("PART2 answer for TEST: " + getTimesProgramOneSend(TEST2));
        System.out.println("PART2 answer for INPUT: " + getTimesProgramOneSend(INPUT));
    }


    private static int sendCount = 0;

    public static int getTimesProgramOneSend(String input) throws IOException, URISyntaxException {
        List<String> instructions = getInstructions(input);

        Map<String, Long> registerMapProgram0 = new HashMap<>();
        registerMapProgram0.put("p", 0L);
        Queue<Long> queue0 = new LinkedList<>();

        Map<String, Long> registerMapProgram1 = new HashMap<>();
        registerMapProgram1.put("p", 1L);
        Queue<Long> queue1 = new LinkedList<>();

        int program0Index;
        int program1Index;
        int program0IndexAfter = 0;
        int program1IndexAfter = 0;
        sendCount = 0;

        do {
            program0Index = program0IndexAfter;
            program1Index = program1IndexAfter;
            program0IndexAfter = executeInstructions(instructions, 0, program0Index, registerMapProgram0, queue0, queue1);
            program1IndexAfter = executeInstructions(instructions, 1, program1Index, registerMapProgram1, queue0, queue1);
        } while (program0Index != program0IndexAfter || program1Index != program1IndexAfter);

        return sendCount;
    }

    public static int executeInstructions(List<String> instructions, int programNum, int index, Map<String, Long> registerMap, Queue<Long> queue0, Queue<Long> queue1) {
        String instruction = instructions.get(index);
        int nextIndex = index;

        switch (instruction.split(" ")[0]) {
            case "snd":
                send(instruction, registerMap, programNum, queue0, queue1);
                nextIndex++;
                break;
            case "set":
                set(instruction, registerMap);
                nextIndex++;
                break;
            case "add":
                arithmetic(instruction, registerMap, (a, b) -> a + b);
                nextIndex++;
                break;
            case "mul":
                arithmetic(instruction, registerMap, (a, b) -> a * b);
                nextIndex++;
                break;
            case "mod":
                arithmetic(instruction, registerMap, (a, b) -> a % b);
                nextIndex++;
                break;
            case "rcv":
                nextIndex = nextIndex + recover(instruction, programNum, registerMap, queue0, queue1);
                break;
            case "jgz":
                nextIndex = jump(instruction, index, registerMap) + 1;
                break;
        }
        return nextIndex;
    }

    public static long getLastRecoveredFrequency(String input) throws IOException, URISyntaxException {
        List<String> instructions = getInstructions(input);
        long lastRecoveredFrequency = 0;
        long lastPlayedFrequency = 0;
        Map<String, Long> registerMap = new HashMap<>();
        for (int i = 0; i < instructions.size(); i++) {
            String instruction = instructions.get(i);
            switch (instruction.split(" ")[0]) {
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

    private static long send(String instruction, Map<String, Long> registerMap) {
        String register = instruction.split(" ")[1];
        registerMap.putIfAbsent(register, 0L);

        return registerMap.get(register);
    }

    private static void send(String instruction, Map<String, Long> registerMap, int programNum, Queue<Long> queue0, Queue<Long> queue1) {
        String valueString = instruction.split(" ")[1];

        if (Character.isAlphabetic(valueString.charAt(0))) {
            registerMap.putIfAbsent(valueString, 0L);
            long value = registerMap.get(valueString);
            if (programNum == 0) {
                queue1.add(value);
            } else {
                sendCount++;
                queue0.add(value);
            }
        } else {
            if (programNum == 0) {
                queue1.add(Long.parseLong(valueString));
            } else {
                sendCount++;
                queue0.add(Long.parseLong(valueString));
            }
        }
    }

    private static void set(String instruction, Map<String, Long> registerMap) {
        String[] instructionArray = instruction.split(" ");
        String register = instructionArray[1];
        String registerValueString = instructionArray[2];
        registerMap.putIfAbsent(register, 0L);
        long registerValue = Character.isAlphabetic(registerValueString.charAt(0)) ? registerMap.get(registerValueString) : Integer.parseInt(registerValueString);

        registerMap.put(register, registerValue);
    }

    private static void arithmetic(String instruction, Map<String, Long> registerMap, BiFunction<Long, Long, Long> operation) {
        String[] instructionArray = instruction.split(" ");
        String register = instructionArray[1];
        String registerValueString = instructionArray[2];
        registerMap.putIfAbsent(register, 0L);
        long registerValue = Character.isAlphabetic(registerValueString.charAt(0)) ? registerMap.get(registerValueString) : Integer.parseInt(registerValueString);
        long number = registerMap.get(register);

        registerMap.put(register, operation.apply(number, registerValue));
    }

    private static long recover(String instruction, long lastPlayedFrequency, long lastRecoveredFrequency, Map<String, Long> registerMap) {
        String[] instructionArray = instruction.split(" ");
        String register = instructionArray[1];
        registerMap.putIfAbsent(register, 0L);
        long registerValue = registerMap.get(register);
        if (registerValue != 0) {
            return lastPlayedFrequency;
        }

        return lastRecoveredFrequency;
    }

    private static int recover(String instruction, int programNum, Map<String, Long> registerMap, Queue<Long> queue0, Queue<Long> queue1) {
        String[] instructionArray = instruction.split(" ");
        String register = instructionArray[1];

        if (programNum == 0) {
            if (queue0.isEmpty()) {
                return 0;
            }
            registerMap.put(register, queue0.poll());
        } else {
            if (queue1.isEmpty()) {
                return 0;
            }
            registerMap.put(register, queue1.poll());
        }

        return 1;
    }

    private static int jump(String instruction, int currentInstructionIndex, Map<String, Long> registerMap) {
        String[] instructionArray = instruction.split(" ");
        String register = instructionArray[1];
        if (Character.isAlphabetic(register.charAt(0))) {
            registerMap.putIfAbsent(register, 0L);
        }

        String registerValueString = instructionArray[2];
        long offset = Character.isAlphabetic(registerValueString.charAt(0)) ? registerMap.get(register) : Integer.parseInt(registerValueString);
        long x = Character.isAlphabetic(register.charAt(0)) ? registerMap.get(register) : Long.parseLong(register); //you sneaky asshole making X a number or character
        if (x > 0) {
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
