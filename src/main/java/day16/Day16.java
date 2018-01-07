package day16;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class Day16 {

    private static final String INPUT = "Day16Input";
    private static final String TEST = "Day16Part1Test";

    private static String PROGRAM = "abcdefghijklmnop";

    private static final BiFunction<String, String, String> SPIN = (move, program) -> {
        String xString = move.substring(1);
        int x = Integer.parseInt(xString);

        int pivotPoint = program.length() - x;
        String end = program.substring(pivotPoint);
        String beginning = program.substring(0, pivotPoint);

        return end + beginning;
    };

    private static final BiFunction<String, String, String> EXCHANGE = (move, program) -> {
        String xString = move.substring(1);
        String[] abArray = xString.split("/");
        int a = Integer.parseInt(abArray[0]);
        int b = Integer.parseInt(abArray[1]);

        char[] newProgram = program.toCharArray();
        swap(newProgram, a, b);

        return String.valueOf(newProgram);
    };

    private static final BiFunction<String, String, String> PARTNER = (move, program) -> {
        String xString = move.substring(1);
        String[] abArray = xString.split("/");
        char a = abArray[0].charAt(0);
        char b = abArray[1].charAt(0);
        int indexA = program.indexOf(a);
        int indexB = program.indexOf(b);

        char[] newProgram = program.toCharArray();
        swap(newProgram, indexA, indexB);

        return String.valueOf(newProgram);
    };

    private static final Map<Character, BiFunction<String, String, String>> functionMap = new HashMap<>();

    static {
        functionMap.put('s', SPIN);
        functionMap.put('x', EXCHANGE);
        functionMap.put('p', PARTNER);
    }


    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("PART1 answer for TEST: " + getProgramAfterDance(TEST, "abcde"));
        System.out.println("PART1 answer for INPUT: " + getProgramAfterDance(INPUT, PROGRAM));

    }


    public static String getProgramAfterDance(String input, String program) throws IOException, URISyntaxException {
        String[] array = getDanceFromInput(input);

        String newProgram = program;
        for (String danceMove : array) {
            char move = danceMove.charAt(0);
            newProgram = functionMap.get(move).apply(danceMove, newProgram);
        }
        return newProgram;
    }

    private static String[] getDanceFromInput(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(Day16.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);
        return lines.findFirst().get().split(",");
    }

    private static void swap(char[] array, int a, int b) {
        char tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }
}
