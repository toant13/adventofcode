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

    private static final char CHANGE = '+';
    private static final char HORIZONTAL = '-';
    private static final char VERTICAL = '|';

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("PART1 answer for TEST: " + findPath(INPUT));
    }


    public static String findPath(String input) throws IOException, URISyntaxException {
        List<String> diagram = getTubes(input);
        StringBuilder path = new StringBuilder();

        char[][] diagramArray = buildDiagramArray(diagram);
        int index = findStartingPoint(diagramArray);


        return path.toString();
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
