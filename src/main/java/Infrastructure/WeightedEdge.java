package Infrastructure;

public class WeightedEdge extends Edge {
    public double weight;

    public WeightedEdge(Node from, Node to, double weight){
        super(from, to);
        this.weight = weight;
    }
}
