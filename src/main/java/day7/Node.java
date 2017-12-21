package day7;

import java.util.LinkedList;

public class Node {
    private int weight;
    private String name;
    private LinkedList<String> list;

    public Node(String name) {
        this.name = name;
    }

    public Node(int weight, String name) {
        this.weight = weight;
        this.name = name;
        this.list = new LinkedList<>();
    }

    public void addNode(String name){
        list.add(name);
    }

    public LinkedList<String> getList() {
        return list;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return name != null ? name.equals(node.name) : node.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
