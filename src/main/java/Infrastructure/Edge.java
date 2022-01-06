package Infrastructure;

import java.awt.*;

public class Edge {
    public Node from, to;
    public String name;
    public double weight;

    public Edge(Node from, Node to, String name){
        this.name = name;
        this.from = from;
        this.to = to;
        this.weight = 0;
    }

    public Edge(Node from, Node to, String name, double weight){
        this.from = from;
        this.to = to;
        this.name = name;
        this.weight = weight;
    }
}
