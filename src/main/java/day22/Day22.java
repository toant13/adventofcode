package day22;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day22 {

    private static final String TEST = "Day22Part1Test";
    private static final String INPUT = "Day22Input";

    private static final char CLEAN = '.';
    private static final char INFECTED = '#';

    public static void main(String[] args) throws Exception {
        System.out.println("PART1 answer for TEST: " + getNumberOfInfected(TEST, 10000));
//        System.out.println("PART1 answer for INPUT: " + getNumberOfInfected(INPUT, 10000));
    }


    private static int getNumberOfInfected(String input, int numberOfBursts) throws Exception {
        int count = 0;
        Burst burst = getBurst(input);
        for (int i = 0; i < numberOfBursts; i++) {
            if(burst.isInfected()){
                burst.clean();
                burst.moveLeft();
            } else { //is clean
                burst.infect();
                burst.moveRight();
                count++;
            }
        }

        return count;
    }

    private static Burst getBurst(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(Day22.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);

        Set<String> gridMap = new HashSet<>();
        List<String> list = lines.collect(Collectors.toList());
        for (int y = 0; y < list.size(); y++) {
            for (int x = 0; x < list.get(y).length(); x++) {
                if(list.get(y).charAt(x)== INFECTED){
                    gridMap.add(x + "," + y);
                }
            }
        }
        return new Burst(gridMap, list.get(0).length()/2, list.size()/2, list.get(0).length(),list.size());
    }






//    private static int getNumberOfInfected(String input, int numberOfBursts) throws Exception {
//        char[][] grid = getEnhancementRules(input);
//
//        int count = 0;
//        Burst burst = new Burst(grid[0].length / 2, grid.length / 2);
//        for (int i = 0; i < numberOfBursts; i++) {
//            if (grid[burst.y][burst.x] == CLEAN) {
//                grid[burst.y][burst.x] = INFECTED;
//                burst.moveRight();
//                count++;
//            } else if (grid[burst.y][burst.x] == INFECTED) {
//                grid[burst.y][burst.x] = CLEAN;
//                burst.moveLeft();
//            } else {
//                throw new Exception("ohhh no!!!");
//            }
//        }
//
//        return count;
//    }
//
//
//    private static char[][] getEnhancementRules(String fileName) throws IOException, URISyntaxException {
//        Path path = Paths.get(Day22.class.getClassLoader().getResource(fileName).toURI());
//        Stream<String> lines = Files.lines(path);
//        List<String> list = lines.collect(Collectors.toList());
//
//        char[][] grid = new char[list.size()][];
//        for (int i = 0; i < list.size(); i++) {
//            grid[i] = list.get(i).toCharArray();
//        }
//
//        return grid;
//    }
}