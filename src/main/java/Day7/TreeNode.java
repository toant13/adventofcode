package Day7;

import java.util.LinkedList;

public class TreeNode {
    private int weight;
    LinkedList<String> list;

    public TreeNode(int weight) {
        this.weight = weight;
        this.list = new LinkedList<>();
    }

    public int getWeight() {
        return weight;
    }
}
