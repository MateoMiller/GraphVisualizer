package TransitionTableStuff;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class TransitionTable {
    private Map<Pair<String, String>, String> transitions = new HashMap<>();
    private Set<String> nodes = new HashSet<>();
    private Set<String> edges = new HashSet<>();

    public Set<String> getNodes() {
        return nodes;
    }

    public void addNode(String nodeName) {
        nodes.add(nodeName);
    }

    public void addTransition(String startNode, String edge, String nextPosition) {
        if (nodes.contains(startNode) && nodes.contains(nextPosition) && edges.contains(edge)) {
            Pair<String, String> pair = new ImmutablePair<>(startNode, edge);
            transitions.put(pair, nextPosition);
        } else {
            throw new RuntimeException("Таких вершин или ребер не существует");
        }
    }

    public Set<String> getEdges() {
        return edges;
    }

    public void addEdge(String edgeName){
        edges.add(edgeName);
    }

    public String getNextCondition(String startNode, String edge) {
        if (!nodes.contains(startNode) || !edges.contains(edge))
            throw new RuntimeException("Таких вершин/ребер не существует");
        Pair<String, String> pair = new ImmutablePair<>(startNode, edge);
        return transitions.getOrDefault(pair, null);
    }
}
