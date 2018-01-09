package day19;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day19 {

    private static final String INPUT = "Day19Input";
    private static final String TEST = "Day19TestInput";

    private static final char CHANGE = '+';

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("PART1 answer for TEST: " + findPath(TEST));
        System.out.println("PART2 answer for TEST: " + (totalSteps) + "\n");

        System.out.println("PART1 answer for INPUT: " + findPath(INPUT));
        System.out.println("PART2 answer for INPUT: " + (totalSteps));
    }

    private static int totalSteps = 0;
    public static String findPath(String input) throws IOException, URISyntaxException {
        List<String> diagram = getTubes(input);
        char[][] diagramArray = buildDiagramArray(diagram);
        StringBuilder path = new StringBuilder();
        int x = findStartingPoint(diagramArray);
        int y = 0;
        Direction currentDirection = Direction.DOWN;
        totalSteps = 0;

        while (diagramArray[y][x] != ' ') {
            x = currentDirection.getNextX(x);
            y = currentDirection.getNextY(y);
            totalSteps++;

            if (diagramArray[y][x] == CHANGE) {
                currentDirection = getChangeDirection(x, y, diagramArray, currentDirection);
            } else if (Character.isAlphabetic(diagramArray[y][x])) {
                path.append(diagramArray[y][x]);
            }
        }

        return path.toString();
    }

    private static Direction getChangeDirection(int x, int y, char[][] diagramArray, Direction currentDirection) {
        if (currentDirection == Direction.DOWN || currentDirection == Direction.UP) {
            return diagramArray[y][x - 1] == ' ' ? Direction.RIGHT : Direction.LEFT;
        }

        return diagramArray[y + 1][x] == ' '? Direction.UP : Direction.DOWN;
    }

    private static char[][] buildDiagramArray(List<String> diagram) {
        char[][] diagramArray = new char[diagram.size()][diagram.get(0).length()];

        for (int y = 0; y < diagramArray.length; y++) {
            for (int x = 0; x < diagramArray[y].length; x++) {
                diagramArray[y][x] = diagram.get(y).charAt(x);
            }
        }

        return diagramArray;
    }

    private static int findStartingPoint(char[][] diagramArray) {
        for (int i = 0; i < diagramArray[0].length; i++) {
            if (diagramArray[0][i] == '|') {
                return i;
            }
        }

        return -1;
    }

    private static List<String> getTubes(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(Day19.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);

        return lines.collect(Collectors.toList());
    }
}
