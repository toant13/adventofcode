package day21;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day21 {

    private static final String TEST = "Day21Part1Test";
    private static final String INPUT = "Day21Input";


    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("PART1 answer for TEST: " + getNumberOfOnPixels(TEST));
//        System.out.println("PART1 answer for INPUT: " + getNumberOfOnPixels(INPUT));


    }

    public static int getNumberOfOnPixels(String input) throws IOException, URISyntaxException {
        List<String> enhancementRules = getEnhancementRules(input);
        char[][] grid = getStartGrid();





        
        return getOpenCount(grid);
    }

    private static int getOpenCount(char[][] grid) {
        int count = 0;

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == '#') {
                    count++;
                }
            }
        }

        return 0;
    }

    private static List<String> getEnhancementRules(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(Day21.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);

        return lines.collect(Collectors.toList());
    }

    private static char[][] getStartGrid() {
        char[][] grid = new char[3][];
        grid[0] = new char[]{'.', '#', '.'};
        grid[1] = new char[]{'.', '.', '#'};
        grid[2] = new char[]{'#', '#', '#'};

        return grid;
    }
}
