package Infrastructure;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Node {
    public String name;
    public List<Edge> edges = new ArrayList<>();

    public Node(String name) {
        this.name = name;
    }

    public void addEdge(Edge edge){
        edges.add(edge);
    }
}
