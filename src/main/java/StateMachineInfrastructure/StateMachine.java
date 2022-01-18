package StateMachineInfrastructure;

import java.util.*;

public class StateMachine {
    private final List<Node> nodes = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();
    private Node startNode;
    private List<Node> finalNodes = new ArrayList<>();
    private Map<TransitionChar, Integer> charToCount = new HashMap<>();

    public List<Node> getNodes() {
        return nodes;
    }

    public void addNode(String newNodeName) {
        var exists = false;
        try {
            findNodeByName(newNodeName);
            exists = true;

        } catch (Exception e) {
            var newNode = new Node(newNodeName);
            nodes.add(newNode);
        }
        if (exists)
            throw new RuntimeException("Такая вершина уже существует: " + newNodeName);
    }

    public void setStartNode(String nodeName) {
        var node = findNodeByName(nodeName);
        setStartNode(node);
    }

    public void setStartNode(Node node) {
        startNode = node;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void addFinalNode(String nodeName) {
        var node = findNodeByName(nodeName);
        addFinalNode(node);
    }

    public void addFinalNode(Node node) {
        finalNodes.add(node);
    }

    public List<Node> getFinalNodes() {
        return finalNodes;
    }

    public void addTransition(String from, String to, TransitionChar transitionChar) {
        Node fromNode = findNodeByName(from);
        Node toNode = findNodeByName(to);

        Edge edge;
        try {
            edge = fromNode.getEdge(toNode);
        } catch (Exception e) {
            edge = new Edge(fromNode, toNode);
            fromNode.edges.add(edge);
            edges.add(edge);
        }
        edge.addTransitionChar(transitionChar);
        charToCount.put(transitionChar, charToCount.getOrDefault(transitionChar, 0) + 1);
    }

    public void removeNode(String nodeName) {
        var nodeToRemove = findNodeByName(nodeName);

        if (nodeToRemove == startNode)
            startNode = null;

        finalNodes.remove(nodeToRemove);
        
        nodes.remove(nodeToRemove);
        for (var i = 0; i < nodeToRemove.edges.size(); i++){
            removeEdge(nodeToRemove, nodeToRemove.edges.get(i));
            i--;
        }

        for (Node otherNode : nodes) {
            for (var i = 0; i < otherNode.edges.size(); i++) {
                var edge = otherNode.edges.get(i);
                if (edge.to.equals(nodeToRemove)) {
                    removeEdge(otherNode, edge);
                    i--;
                }
            }
        }
    }

    private void removeEdge(Node node, Edge edge) {
        node.edges.remove(edge);
        edges.remove(edge);
        for (var transition : edge.transitionChars) {
            var newCharsCount = charToCount.get(transition) - 1;
            if (newCharsCount == 0) {
                charToCount.remove(transition);
            } else {
                charToCount.put(transition, newCharsCount);
            }
        }
    }

    public void removeFinalNode(String nodeName){
        var node = findNodeByName(nodeName);
        removeFinalNode(node);
    }

    private void removeFinalNode(Node node){
        if (!finalNodes.remove(node))
            throw new RuntimeException("Данная вершина не была финальной");
    }

    public void removeTransition(String from, String to, TransitionChar transition) {
        var fromNode = findNodeByName(from);
        var toNode = findNodeByName(to);
        var edge = fromNode.getEdge(toNode);

        edge.removeTransitionChar(transition);
        if (edge.transitionChars.isEmpty())
        {
            edges.remove(edge);
            fromNode.edges.remove(edge);
        }
        var newCharsCount = charToCount.get(transition) - 1;
        if (newCharsCount == 0) {
            charToCount.remove(transition);
        } else {
            charToCount.put(transition, newCharsCount);
        }
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Set<TransitionChar> getAlphabet() {
        return charToCount.keySet();
    }

    public void clear() {
        nodes.clear();
        edges.clear();
        charToCount.clear();
        finalNodes.clear();
        startNode = null;
    }

    private Node findNodeByName(String name) {
        return nodes
                .stream()
                .filter(x -> x.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Такой вершины не существует: " + name));
    }
}
