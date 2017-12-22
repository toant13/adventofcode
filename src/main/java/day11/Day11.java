package day11;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11 {

    private static final String INPUT = "Day11Input";
    private static final String TEST = "Day11Part1Test";
    private static final String COMMA_DELIMITER = ",";


    private static final Map<String, Function<Coordinate, Coordinate>> actionMap = new HashMap<>();

    static {
        actionMap.put("nw", coordinate -> new Coordinate(coordinate.x - 1, coordinate.y));
        actionMap.put("n", coordinate -> new Coordinate(coordinate.x, coordinate.y + 1));
        actionMap.put("ne", coordinate -> new Coordinate(coordinate.x + 1, coordinate.y + 1));
        actionMap.put("se", coordinate -> new Coordinate(coordinate.x + 1, coordinate.y));
        actionMap.put("s", coordinate -> new Coordinate(coordinate.x, coordinate.y - 1));
        actionMap.put("sw", coordinate -> new Coordinate(coordinate.x - 1, coordinate.y - 1));
    }


    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("PART1 answer for INPUT: " + getSteps(INPUT));
        System.out.println("PART1 answer for TEST: " + getSteps(TEST));

        System.out.println("PART2 answer for INPUT: " + getMaxDistance(INPUT));
    }

    // part 1
    public static int getSteps(String fileName) throws URISyntaxException, IOException {
        Path path = Paths.get(Day11.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);
        List<String> list = lines.collect(Collectors.toList());

        int totalSteps = 0;
        for (String s : list) {
            String[] steps = s.split(COMMA_DELIMITER);
            Coordinate finalCoordinate = moveSteps(steps);

            totalSteps += getDistanceToStartingPoint(finalCoordinate);
        }

        return totalSteps;
    }

    // part 2
    public static int getMaxDistance(String fileName) throws URISyntaxException, IOException {
        Path path = Paths.get(Day11.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);
        List<String> list = lines.collect(Collectors.toList());

        int maxSteps = 0;
        for (String s : list) {
            String[] steps = s.split(COMMA_DELIMITER);
            maxSteps = getMaxOFMoveSteps(steps);
        }

        return maxSteps;
    }

    private static int getMaxOFMoveSteps(String[] steps){
        Coordinate currentCoordinate = new Coordinate();
        int maxDistance = Integer.MIN_VALUE;
        for (String step : steps) {
            currentCoordinate = actionMap.get(step).apply(currentCoordinate);
            int currentDistance = getDistanceToStartingPoint(currentCoordinate);
            maxDistance = Integer.max(currentDistance, maxDistance);
        }

        return maxDistance;
    }

    private static int getDistanceToStartingPoint(Coordinate coordinate) {
        int steps = 0;
        Coordinate current = new Coordinate(coordinate.x, coordinate.y);
        while (!(current.x == 0 || current.y == 0)) {
            if (current.x < 0 && current.y > 0) { //nw
                current = actionMap.get("se").apply(current);
            } else if (current.x > 0 && current.y > 0) { //ne
                current = actionMap.get("sw").apply(current);
            } else if (current.x > 0 && current.y < 0) { //se
                current = actionMap.get("nw").apply(current);
            } else { //sw
                current = actionMap.get("ne").apply(current);
            }
            steps++;
        }
        int remainingSteps = Integer.max(Math.abs(current.x), Math.abs(current.y));
        return steps + remainingSteps;
    }

    private static Coordinate moveSteps(String[] steps) {
        Coordinate finalCoordinate = new Coordinate();
        for (String step : steps) {
            finalCoordinate = actionMap.get(step).apply(finalCoordinate);
        }

        return finalCoordinate;
    }

}
