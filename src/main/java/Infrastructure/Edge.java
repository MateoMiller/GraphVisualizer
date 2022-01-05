package Infrastructure;

import java.awt.*;

public class Edge {
    public Node from, to;
    public Color color;

    public Edge(Node from, Node to){
        this.from = from;
        this.to = to;
        this.color = Color.BLACK;
    }
}
