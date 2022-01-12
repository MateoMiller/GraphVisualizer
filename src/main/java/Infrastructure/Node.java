package Infrastructure;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public String name;
    public List<Edge> edges = new ArrayList<>();

    public Node(String name) {
        this.name = name;
    }

    public void addTransition(Node to, TransitionChar transitionChar){
        var edge = getEdge(to);
        if (edge == null){
            edge = new Edge(this, to);
            edges.add(edge);
        }
        edge.addTransitionChar(transitionChar);
    }

    public Edge getEdge(Node to){
       return edges.stream()
                .filter(x -> x.to.name.equals(to.name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Такого ребра не существует"));
    }

    public void addEdge(Edge edge){
        edges.add(edge);
    }

    public boolean removeTransition(String to, TransitionChar transitionChar) {
        return edges
                .stream()
                .filter(x -> x.to.name.equals(to))
                .findFirst()
                .map(x -> x.transitionChars.remove(transitionChar))
                .orElse(false);
    }

    @Override
    public String toString() {
        return "name:= " + name + ", edgesCount:= " + edges.size();
    }
}
