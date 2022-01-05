package Algorithms.Traversing;

import Algorithms.Algorithm;
import Infrastructure.Graph;
import Infrastructure.Node;

public abstract class TraversingAlgorithm implements Algorithm {
    protected Node startNode;
    protected Graph graph;

    public void SetStartNode(Node node) {
        this.startNode = node;
    }

    public void SetGraph(Graph graph){
        this.graph = graph;
        this.startNode = null;
    }
}
