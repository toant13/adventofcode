package day21;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day21 {

    private static final String TEST = "Day21Part1Test";
    private static final String INPUT = "Day21Input";

    private static final int NUMBER_ITERATIONS = 5;

    public static void main(String[] args) throws Exception {
        System.out.println("PART1 answer for TEST: " + getNumberOfOnPixels(TEST, 2));
        System.out.println("PART1 answer for INPUT: " + getNumberOfOnPixels(INPUT, NUMBER_ITERATIONS));

    }

    public static int getNumberOfOnPixels(String input, int numberOfIterations) throws Exception {
        Map<String, String> enhancementRulesMap = getEnhancementRules(input);
        char[][] grid = getStartGrid();

//        printCurrentGrid(grid);
        for (int i = 0; i < numberOfIterations; i++) {
            grid = getNewGrid(grid, enhancementRulesMap);
//            printCurrentGrid(grid);

        }
        return getOpenCount(grid);
    }

    private static void printCurrentGrid(char[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                System.out.print(grid[y][x]);
            }
            System.out.println("");
        }
        System.out.println("");
    }


    private static char[][] getNewGrid(char[][] oldGrid, Map<String, String> enhancementRulesMap) throws Exception {
        int newSize = oldGrid.length % 2 == 0 ? oldGrid.length / 2 * 3 : oldGrid.length / 3 * 4;
        char[][] newGrid = new char[newSize][newSize];

        if (oldGrid.length % 2 == 0) {
            populateGrid(oldGrid, newGrid, enhancementRulesMap, 2);
        } else if (oldGrid.length % 3 == 0) {
            populateGrid(oldGrid, newGrid, enhancementRulesMap, 3);
        } else {
            throw new Exception("how is that possible?");
        }

        return newGrid;
    }

    private static void populateGrid(char[][] oldGrid, char[][] newGrid, Map<String, String> enhancementRulesMap, int multiple) {
        int newY = 0;
        int newX = 0;
        for (int oldY = 0; oldY < oldGrid.length; oldY = oldY + multiple) {
            for (int oldX = 0; oldX < oldGrid.length; oldX = oldX + multiple) {
                StringBuilder keyStringBuilder = new StringBuilder();
                for (int y = oldY; y < oldY + multiple; y++) {
                    for (int x = oldX; x < oldX + multiple; x++) {
                        keyStringBuilder.append(oldGrid[y][x]);
                    }
                    keyStringBuilder.append("/");
                }
                String key = keyStringBuilder.toString().substring(0, keyStringBuilder.toString().length() - 1);
                String outputValue = getOutputValue(key, enhancementRulesMap);

                String[] outputArray = outputValue.split("/");
                for (int outY = 0; outY < outputArray.length; outY++) {
                    for (int outX = 0; outX < outputArray[0].length(); outX++) {
                        newGrid[newY + outY][newX + outX] = outputArray[outY].charAt(outX);
                    }
                }
                if (newX + outputArray[0].length() < newGrid[0].length) {
                    newX = newX + outputArray[0].length();
                } else {
                    newX = 0;
                    newY = newY + outputArray.length;
                }
            }
        }


    }

    private static String getOutputValue(String key, Map<String, String> enhancementRulesMap) {
        int i = 0;
        while (!enhancementRulesMap.containsKey(key) && i < 4) {
            key = getRotatedKey(key);
            String flippedKey = getFlippedKey(key);
            if (enhancementRulesMap.containsKey(flippedKey)) {
                key = flippedKey;
                break;
            }
            i++;
        }

        return enhancementRulesMap.get(key);
    }

    private static String getRotatedKey(String oldKey) {
        String[] splitArray = oldKey.split("/");
        int length = splitArray[0].length();
        StringBuilder newKey = new StringBuilder();

        for (int x = 0; x < length; x++) {
            for (int y = length - 1; y >= 0; y--) {
                newKey.append(splitArray[y].charAt(x));
            }
            newKey.append("/");
        }

        return newKey.toString().substring(0, newKey.toString().length() - 1);
    }

    private static String getFlippedKey(String oldKey) {
        String[] splitArray = oldKey.split("/");

        for (int i = 0; i < splitArray.length; i++) {
            char[] charArray = splitArray[i].toCharArray();
            int start = 0;
            int end = charArray.length - 1;
            while (end > start) {
                char tmp = charArray[end];
                charArray[end--] = charArray[start];
                charArray[start++] = tmp;
            }
            splitArray[i] = String.valueOf(charArray);
        }

        StringBuilder newKey = new StringBuilder();
        for (String s : splitArray) {
            newKey.append(s);
            newKey.append("/");
        }

        return newKey.toString().substring(0, newKey.length() - 1);
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
        return count;
    }

    private static Map<String, String> getEnhancementRules(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(Day21.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);
        Map<String, String> rulesMap = new HashMap<>();
        lines.map(rule -> rule.split("=>")).forEach(inputOutput -> rulesMap.put(inputOutput[0].trim(), inputOutput[1].trim()));

        return rulesMap;
    }

    private static char[][] getStartGrid() {
        char[][] grid = new char[3][];
        grid[0] = new char[]{'.', '#', '.'};
        grid[1] = new char[]{'.', '.', '#'};
        grid[2] = new char[]{'#', '#', '#'};

        return grid;
    }
}
