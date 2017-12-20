package Day7;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TreeBuilder {

    private static final String DEST_DELIMITER = "->";
    private static final String SPACE_DELIMITER = " ";
    private static final int NAME_INDEX = 0;
    private static final int NAMEANDWEIGHT_INDEX = 0;
    private static final int WEIGHT_INDEX = 1;
    private static final int DESTINATION_INDEX = 1;
    
    Map<String, TreeNode> map;

    public TreeBuilder() {
        this.map = new HashMap<>();
    }
    
    public Map<String, TreeNode> buildTree(String fileName) throws URISyntaxException, IOException {
        Path path = Paths.get(GraphBuilder.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);

        lines.forEach(s -> {
            String[] splitByDestination = s.split(DEST_DELIMITER);
            String[] nameAndWeight = splitByDestination[NAMEANDWEIGHT_INDEX].split(SPACE_DELIMITER);

            String name = nameAndWeight[NAME_INDEX];
            int weight = getWeightFromWeightString(nameAndWeight[WEIGHT_INDEX]);

            TreeNode node = new TreeNode(weight);
            addDestinationsToNode(splitByDestination, node);

            map.put(name, node);
        });

        return map;
    }

    private void addDestinationsToNode(String[] splitByDestination, TreeNode node) {
        if (splitByDestination.length > 1) {
            String[] destinations = splitByDestination[DESTINATION_INDEX].trim().split(SPACE_DELIMITER);
            Arrays.stream(destinations).forEach(destination -> node.list.add(destination.replaceAll(",","")));
        }
    }

    private int getWeightFromWeightString(String weightString) {
        String result = weightString.replaceAll("[()]", "");
        return Integer.parseInt(result);
    }
}
