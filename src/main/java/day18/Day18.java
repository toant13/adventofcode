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
//        System.out.println("PART1 answer for TEST: " + getLastRecoveredFrequency(TEST));
//        System.out.println("PART1 answer for INPUT: " + getLastRecoveredFrequency(INPUT));

//        System.out.println("PART2 answer for TEST: " + getTimesProgramOneSend(TEST2));
//        System.out.println("PART2 answer for INPUT: " + getTimesProgramOneSend(INPUT));
        getTimesProgramOneSend(INPUT);
    }


    private static int sendCount = 0;

    public static void getTimesProgramOneSend(String input) throws IOException, URISyntaxException {
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

        boolean p0 = true;
        do {
            program0Index = program0IndexAfter;
            program1Index = program1IndexAfter;
            if (p0) {
                program0IndexAfter = executeInstructions(instructions, 0, program0Index, registerMapProgram0, queue0, queue1);
                if (program0Index == program0IndexAfter) {
                    p0 = false;
                }
            } else {
                program1IndexAfter = executeInstructions(instructions, 1, program1Index, registerMapProgram1, queue0, queue1);
                if (program1Index == program1IndexAfter) {
                    p0 = true;
                }
            }


//        } while (program0Index != program0IndexAfter || program1Index != program1IndexAfter);
        } while (true);
//        return sendCount;
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
        System.out.println("[program: " + programNum + "] executing: " + instruction + " [map is : " + registerMap + "]");
        System.out.println("queue 0: " + queue0);
        System.out.println("queue 1: " + queue1);
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

    private static void send(String instruction, Map<String, Long> registerMap, int programNum, Queue<Long> queue0, Queue<Long> queue1) {
        String valueString = instruction.split(" ")[1];

        if (Character.isAlphabetic(valueString.charAt(0))) {
            preprocessMap(registerMap, valueString);
            long value = registerMap.get(valueString);
            if (programNum == 0) {
                queue1.add(value);
            } else {
//                sendCount++;
                queue0.add(value);
            }
        } else {
            if (programNum == 0) {
                queue1.add(Long.parseLong(valueString));
            } else {
//                sendCount++;
                queue0.add(Long.parseLong(valueString));
            }
        }
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

    private static int recover(String instruction, int programNum, Map<String, Long> registerMap, Queue<Long> queue0, Queue<Long> queue1) {
        String[] instructionArray = instruction.split(" ");
        String register = instructionArray[1];

        if (programNum == 0) {
            if (queue0.isEmpty()) {
                return 0;
            }
            sendCount++;
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
