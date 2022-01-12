package Windows.GraphicalStuff;

import Infrastructure.Edge;
import Infrastructure.Node;

import java.awt.*;

public interface Palette {
    Color getNodeColor(Node node);
    Color getEdgeColor(Edge edge);
    Color getEdgeStrokeColor(Edge edge);
    Color getStartNodeEntryColor();
    Color getFinalNodeExitColor();
    Color getNodeNameColor(Node node);
    Color getEdgeNameColor(Edge edge);
    Color getTransitionCharColor(Edge edge);
}
