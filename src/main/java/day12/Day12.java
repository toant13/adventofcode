package day12;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12 {


    private static final String INPUT = "Day12Input";
    private static final String TEST = "Day12Part1Test";

    private static final String KEY_LIST_SPLITTER = "<->";
    private static final String COMMA = ",";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;


    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("PART1 answer for TEST: " + findProgramId0Group(TEST));
        System.out.println("PART1 answer for INPUT: " + findProgramId0Group(INPUT));

        System.out.println("PART2 answer for TEST: " + findNumberOfGroups(TEST));
        System.out.println("PART2 answer for INPUT: " + findNumberOfGroups(INPUT));
    }

    public static int findNumberOfGroups(String fileName) throws URISyntaxException, IOException {
        Path path = Paths.get(Day12.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);
        List<String> list = lines.collect(Collectors.toList());

        List<Integer>[] adjList = buildAdjacencyList(list);
        int count = 0;
        int visitedNumber = 0;
        boolean[] visited = new boolean[adjList.length];
        for (int i = 0; i < adjList.length; i++) {
            int current = visitedNumber;
            int newVisited = countVisited(visited, adjList, i, 0) + current;

            if(newVisited > current){
                count++;
            }
            visitedNumber = newVisited;
        }

        return count;
    }

    public static int countVisited(boolean[] visited, List<Integer>[] adjList, int current, int count){
        if(visited[current]){
            return 0;
        }

        visited[current] = true;
        List<Integer> maps = adjList[current];
        count++;
        for (Integer key : maps) {
            count += countVisited(visited, adjList, key, 0);
        }

        return count;
    }



    public static int findProgramId0Group(String fileName) throws URISyntaxException, IOException {
        Path path = Paths.get(Day12.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);
        List<String> list = lines.collect(Collectors.toList());

        List<Integer>[] adjList = buildAdjacencyList(list);
        int count = 0;
        for (int i = 0; i < adjList.length; i++) {
            boolean[] visited = new boolean[adjList.length];
            if (pathToZero(visited, adjList, i)) {
                count++;
            }
        }

        return count;
    }


    private static boolean pathToZero(boolean[] visited, List<Integer>[] adjList, int current) {
        if (visited[current]) {
            return false;
        }
        if (current == 0) {
            return true;
        }
        visited[current] = true;
        List<Integer> maps = adjList[current];
        for (Integer key : maps) {
            if (pathToZero(visited, adjList, key)) {
                return true;
            }
        }
        return false;
    }

    private static List<Integer>[] buildAdjacencyList(List<String> lines) {
        List<Integer>[] adjList = new ArrayList[lines.size()];

        for (String line : lines) {
            String[] keyAndValues = line.replaceAll(" ", "").split(KEY_LIST_SPLITTER);
            int key = Integer.parseInt(keyAndValues[KEY_INDEX]);
            String[] valuesString = keyAndValues[VALUE_INDEX].split(COMMA);
            List<Integer> valuesArray = Arrays.stream(valuesString).map(Integer::parseInt).collect(Collectors.toList());
            adjList[key] = valuesArray;
        }
        return adjList;
    }
}
