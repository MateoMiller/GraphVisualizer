package Algorithms.Traversing;

import Algorithms.AlgorithmStep;
import Infrastructure.Edge;

import java.util.ArrayList;
import java.util.List;

public class TraversingStep implements AlgorithmStep {
    public List<Edge> visitedEdges;

    public TraversingStep(){
        this.visitedEdges = new ArrayList<>();
    }

    public TraversingStep(List<Edge> visitedEdges){
        this.visitedEdges = visitedEdges;
    }
}
