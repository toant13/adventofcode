package Day7;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class GraphBuilder {

    private static final String DEST_DELIMITER = "->";
    private static final String SPACE_DELIMITER = " ";
    private static final int NAME_INDEX = 0;
    private static final int NAMEANDWEIGHT_INDEX = 0;
    private static final int WEIGHT_INDEX = 1;
    private static final int DESTINATION_INDEX = 1;

    private Set<Node> graph;

    public GraphBuilder() {
        this.graph = new HashSet<>();
    }

    public Set<Node> getGraph() {
        return graph;
    }

    public void buildGraph(String fileName) throws URISyntaxException, IOException {
        Path path = Paths.get(GraphBuilder.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);

        lines.forEach(s -> {
            String[] splitByDestination = s.split(DEST_DELIMITER);
            String[] nameAndWeight = splitByDestination[NAMEANDWEIGHT_INDEX].split(SPACE_DELIMITER);

            String name = nameAndWeight[NAME_INDEX];
            int weight = getWeightFromWeightString(nameAndWeight[WEIGHT_INDEX]);
            Node node = new Node(weight, name);
            addDestinations(splitByDestination, node);

            graph.add(node);
        });

    }

    private void addDestinations(String[] splitByDestination, Node node) {
        if (splitByDestination.length > 1) {
            String[] destinations = splitByDestination[DESTINATION_INDEX].trim().split(SPACE_DELIMITER);
            Arrays.stream(destinations).forEach(destination -> node.addNode(destination.replaceAll(",","")));
        }
    }

    private int getWeightFromWeightString(String weightString) {
        String result = weightString.replaceAll("[()]", "");
        return Integer.parseInt(result);
    }
}
