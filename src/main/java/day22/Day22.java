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

    public static void main(String[] args) throws Exception {
        System.out.println("PART1 answer for TEST: " + getNumberOfInfected(TEST, 10000));
        System.out.println("PART1 answer for INPUT: " + getNumberOfInfected(INPUT, 10000));

        System.out.println("PART2 answer for TEST: " + getNumberOfEnhancedInfected(TEST, 10000000));
        System.out.println("PART2 answer for INPUT: " + getNumberOfEnhancedInfected(INPUT, 10000000));
    }

    private static int getNumberOfInfected(String input, int numberOfBursts) throws Exception {
        int count = 0;
        Burst burst = getBurst(input);
        for (int i = 0; i < numberOfBursts; i++) {
            if (burst.isInfected()) {
                burst.clean();
                burst.moveRight();
            } else { //is clean
                burst.infect();
                burst.moveLeft();
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
                if (list.get(y).charAt(x) == Burst.INFECTED) {
                    gridMap.add(x + "," + y);
                }
            }
        }
        return new Burst(gridMap, list.get(0).length() / 2, list.size() / 2);
    }

    private static int getNumberOfEnhancedInfected(String input, int numberOfBursts) throws Exception {
        int count = 0;
        EnhancedBurst burst = getEnhancedBurst(input);
        for (int i = 0; i < numberOfBursts; i++) {
            if (burst.isClean()) {
                burst.weaken();
                burst.moveLeft();
            } else if (burst.isWeakened()) {
                burst.infect();
                burst.moveForward();
                count++;
            } else if (burst.isInfected()) {
                burst.flag();
                burst.moveRight();
            } else if (burst.isFlagged()) {
                burst.clean();
                burst.moveReverse();
            } else {
                throw new Exception("Unknown state");
            }
        }

        return count;
    }

    private static EnhancedBurst getEnhancedBurst(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(Day22.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);

        Map<String, Character> gridMap = new HashMap<>();
        List<String> list = lines.collect(Collectors.toList());
        for (int y = 0; y < list.size(); y++) {
            for (int x = 0; x < list.get(y).length(); x++) {
                if (list.get(y).charAt(x) != EnhancedBurst.CLEANED) {
                    gridMap.put(x + "," + y, list.get(y).charAt(x));
                }
            }
        }
        return new EnhancedBurst(gridMap, list.get(0).length() / 2, list.size() / 2);
    }

}
