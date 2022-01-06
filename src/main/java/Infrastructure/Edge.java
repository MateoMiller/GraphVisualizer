package Infrastructure;

import java.awt.*;

public class Edge {
    public Node from, to;
    public double weight;

    public Edge(Node from, Node to){
        this.from = from;
        this.to = to;
        this.weight = 0;
    }

    public Edge(Node from, Node to, double weight){
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}
