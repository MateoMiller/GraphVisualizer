package Windows.GraphicalInfrastucture;

import StateMachineInfrastructure.Node;
import StateMachineInfrastructure.StateMachine;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class NodesLayouter {
    private Map<Node, Point> nodeToPosition = new HashMap<>();

    public void init(StateMachine machine, int width, int height) {
        nodeToPosition.clear();
        Spiral spiral = new Spiral(width/ 3.0d, height / 3.0d, new Point(width/2, height /2));
        var nodes = machine.getNodes();
        var positions = spiral.getPositions(nodes.size());
        for (int i = 0; i < positions.size(); i++) {
            nodeToPosition.put(nodes.get(i), positions.get(i));
        }
    }

    public Point getPosition(Node node) {
        return nodeToPosition.getOrDefault(node, null);
    }

}
