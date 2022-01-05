package Infrastructure;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final List<Node> nodes = new ArrayList<>();

    public List<Node> getNodes(){
        return nodes;
    }

    public void addNode(String newNodeName){
        if (findNodeByName(newNodeName) != null){
            throw new IllegalArgumentException("Вершина с таким именем уже существует");
        }

        var newNode = new Node(newNodeName);
        nodes.add(newNode);
    }

    public void addDirectedEdge(String from, String to){
        Node nodeFrom = findNodeByName(from);
        Node nodeTo = findNodeByName(to);

        if (nodeFrom == null || nodeTo == null)
            throw new RuntimeException("Вершины с такими именами не существуют");

        Edge edge = new Edge(nodeFrom, nodeTo);
        nodeFrom.addEdge(edge);
    }

    public void addUndirectedEdge(String firstNodeName, String secondNodeName){
        Node first = findNodeByName(firstNodeName);
        Node second = findNodeByName(secondNodeName);

        if (first == null || second == null)
            throw new RuntimeException("Вершины с такими именами не существуют");

        Edge firstEdge = new Edge(first, second);
        Edge secondEdge = new Edge(second, first);
        first.addEdge(firstEdge);
        second.addEdge(secondEdge);
    }

    public void removeNode(String nodeName)
    {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).name.equals(nodeName)){
                nodes.remove(i);
                i--;
            }
        }
    }

    public void clear(){
        nodes.clear();
    }

    private Node findNodeByName(String name){
        return nodes
                .stream()
                .filter(x -> x.name.equals(name))
                .findFirst()
                .orElse(null);
    }
}
