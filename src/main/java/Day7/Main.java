package Day7;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    /*
     * What a shitty over-engineered program I have built.  There's a much more efficient approach that I'll build
     * later.
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("PART1 answer: " + part1());
    }


    public static String part1() throws IOException, URISyntaxException {
        GraphBuilder graphBuilder = new GraphBuilder();
        graphBuilder.buildGraph("Day7PArt1");
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

    private static String findProgram(Set<String> visited, Set<Node> original) {
        visited.forEach(key -> original.remove(new Node(key)));
        return original.iterator().next().getName();
    }
}
