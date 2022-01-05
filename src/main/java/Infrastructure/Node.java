package Infrastructure;

import java.awt.geom.Point2D;
import java.util.List;

public class Node {
    public Point2D position;
    public String name;
    public List<Edge> edges;

    public Node(String name) {
        this.name = name;
    }

    public void addEdge(Edge edge){
        edges.add(edge);
    }
}
