package day9;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day9 {

    private static final String INPUT = "Day9Input";
    private static final String TEST = "Day9Test";


    private static final char IGNORE = '!';
    private static final char GARBAGE_OPEN = '<';
    private static final char GARBAGE_CLOSE = '>';
    private static final char GROUP_OPEN = '{';
    private static final char GROUP_CLOSE = '}';

    private static Function<String, Integer> getNumberOfGroups = s -> {
        int total = 0;
        boolean garbage = false;
        Stack<Character> group = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == IGNORE) {
                i++;
            } else if (s.charAt(i) == GARBAGE_OPEN && !garbage) {
                garbage = true;
            } else if (s.charAt(i) == GARBAGE_CLOSE && garbage) {
                garbage = false;
            } else if (s.charAt(i) == GROUP_OPEN && !garbage) {
                group.push(s.charAt(i));
            } else if (s.charAt(i) == GROUP_CLOSE && !garbage && group.size()>0) {
                total += group.size();
                group.pop();
            }
        }
        return total;
    };

    private static Function<String, Integer> getCharsOfGarbage = s -> {
        int total = 0;
        boolean garbage = false;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == IGNORE) {
                i++;
            } else if (s.charAt(i) == GARBAGE_OPEN && !garbage) {
                garbage = true;
            } else if (s.charAt(i) == GARBAGE_CLOSE) {
                garbage = false;
            } else if(garbage){
                total++;
            }
        }
        return total;
    };

    public static void main(String[] args) throws IOException, URISyntaxException {
        // Let's try something different I normally won't do.
        // Let's pass in a function as a parameter instead so we don't have to write 'findSolution' method twice
        System.out.println("PART1 answer INPUT: " + findSolution(INPUT, getNumberOfGroups));

        System.out.println("PART2 answer TEST: " + findSolution(TEST, getCharsOfGarbage));
        System.out.println("PART2 answer INPUT: " + findSolution(INPUT, getCharsOfGarbage));

    }


    public static int findSolution(String fileName, Function<String,Integer> function) throws IOException, URISyntaxException {
        Path path = Paths.get(Day9.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);
        List<String> list = lines.collect(Collectors.toList());

        int total = 0;
        for (String s : list) {
            total += function.apply(s);
        }

        return total;
    }






}
