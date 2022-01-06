package Infrastructure;

import java.util.ArrayList;
import java.util.List;

public class StateMachine {
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

    public void addEdge(String from, String to, String name){
        addEdge(from, to, name, 0d);
    }

    public void addEdge(String from, String to, String name, double weight){
        Node fromNode = findNodeByName(from);
        Node toNode = findNodeByName(to);

        if (fromNode == null || toNode == null)
            throw new RuntimeException("Вершины с такими именами не существуют");

        Edge firstEdge = new Edge(fromNode, toNode, name, weight);

        fromNode.addEdge(firstEdge);
    }

    public void removeNode(String nodeName)
    {
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i)
                    .edges
                    .removeIf(x -> x.to.name.equals(nodeName));
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
