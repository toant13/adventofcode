package day24;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day24 {

    private static final String INPUT = "Day24Input";
    private static final String TEST = "Day24Part1Test";


    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("PART1 answer for TEST: " + getStrengthOfStrongestBridge(TEST));
        System.out.println("PART1 answer for INPUT: " + getStrengthOfStrongestBridge(INPUT));
    }

    public static int getStrengthOfStrongestBridge(String input) throws IOException, URISyntaxException {
        int highestStrength = Integer.MIN_VALUE;
        List<String> components = getComponentsList(input);

        Map<Integer, List<String>> map = getMap(components);

        List<String> startList = map.get(0);
        for (String component : startList) {
            int currentStrength = helper(map, new HashSet<>(components), 0, 0, component);
            highestStrength = currentStrength > highestStrength ? currentStrength : highestStrength;
        }

        return highestStrength;
    }

    private static int helper(Map<Integer, List<String>> map, Set<String> nonVisited, int currentStrength, int currentPort, String currentComponent) {
        if (!map.containsKey(currentPort)) {
            return currentStrength;
        }

        if (!nonVisited.contains(currentComponent)) {
            return currentStrength;
        }

        int nextPort = getNextPort(currentComponent, currentPort);
        List<String> nextComponents = map.get(nextPort);
        int total = currentStrength;
        nonVisited.remove(currentComponent);
        for (String nextComponent : nextComponents) {
            int value = helper(map, nonVisited, currentStrength + nextPort + currentPort, nextPort, nextComponent);
            total = value > total ? value : total;
        }
        nonVisited.add(currentComponent);

        return total;
    }

    private static int getNextPort(String currentComponent, int currentPort) {
        String[] array = currentComponent.split("/");
        int port1 = Integer.parseInt(array[0]);
        int port2 = Integer.parseInt(array[1]);

        if (currentPort == port1) {
            return port2;
        }
        return port1;
    }

    public static Map<Integer, List<String>> getMap(List<String> components) {
        Map<Integer, List<String>> map = new HashMap<>();

        for (String component : components) {
            String[] componentArray = component.split("/");
            int port1 = Integer.parseInt(componentArray[0]);
            addToMap(map, port1, component);
            int port2 = Integer.parseInt(componentArray[1]);
            addToMap(map, port2, component);
        }

        return map;
    }

    private static void addToMap(Map<Integer, List<String>> map, int port, String component) {
        if (map.containsKey(port)) {
            map.get(port).add(component);
        } else {
            List<String> list = new ArrayList<>();
            list.add(component);
            map.put(port, list);
        }
    }

    private static List<String> getComponentsList(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(Day24.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);

        return lines.collect(Collectors.toList());
    }
}
