package Day7;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Main {

    static final String INPUT_FILENAME = "Day7Input";
    static final String TEST_FILENAME = "Day7Part2Test";

    /*
     * What a shitty over-engineered program I have built.  There's a much more efficient approach that I'll build
     * later.
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        String rootName = part1(INPUT_FILENAME);
        String testRoot = part1(TEST_FILENAME);
        System.out.println("PART1 answer: " + rootName);
        System.out.println("PART1 test answer: " + testRoot);


        System.out.println("PART2 answer: ");
        part2(rootName, INPUT_FILENAME);
    }


    public static String part1(String fileName) throws IOException, URISyntaxException {
        GraphBuilder graphBuilder = new GraphBuilder();
        graphBuilder.buildGraph(fileName);
        Set<Node> graph = graphBuilder.getGraph();

        Set<String> visited = new HashSet<>();
        for (Node node : graph) {
            if (!node.getList().isEmpty()) {
                List<String> destinations = node.getList();
                for (String destination : destinations) {
                    visited.add(destination);
                }
            }
        }

        return findProgram(visited, graph);
    }

    public static void part2(String root, String fileName) throws IOException, URISyntaxException {
        TreeBuilder treeBuilder = new TreeBuilder();
        Map<String, TreeNode> map = treeBuilder.buildTree(fileName);

        TreeNode rootNode = map.get(root);
        while (rootNode != null) {
            List<Integer> list = new ArrayList<>();
            for (String key : rootNode.list) {
                int weight = getTotalWeight(map, key);
                list.add(weight);
            }
            int index = getUniqueIndex(list);
            if (index == -1) {
                System.out.println("final answer: " + rootNode.getWeight());
                rootNode = null;
            } else {
                System.out.println(list);
                System.out.println("new key: " + rootNode.list.get(index));
                rootNode = map.get(rootNode.list.get(index));
            }
        }

    }

    private static int getUniqueIndex(List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            int diffCount = 0;
            for (int j = i + 1; j < list.size(); j++) {
                System.out.println("i:" + list.get(i));
                System.out.println("j:" + list.get(j));
                if (!list.get(i).equals(list.get(j))) {
                    diffCount++;
                }
            }

            if (diffCount > 1) {
                return i;
            }
        }

        return -1;
    }

    public static int getTotalWeight(Map<String, TreeNode> map, String key) {
        TreeNode node = map.get(key);
        if (node.list.isEmpty()) {
            return node.getWeight();
        }

        int totalWeight = 0;
        for (String s : node.list) {
            totalWeight += getTotalWeight(map, s);
        }
        return totalWeight + node.getWeight();
    }

    private static String findProgram(Set<String> visited, Set<Node> original) {
        visited.forEach(key -> original.remove(new Node(key)));
        return original.iterator().next().getName();
    }
}
