package Windows.GraphicalStuff;

import Infrastructure.Edge;
import Infrastructure.Node;

import java.awt.*;

public class ConstantPalette implements Palette {

    @Override
    public Color getNodeColor(Node node) {
        return Color.BLUE;
    }

    @Override
    public Color getEdgeColor(Edge edge) {
        return Color.ORANGE;
    }

    @Override
    public Color getEdgeStrokeColor(Edge edge) {
        return Color.BLACK;
    }

    @Override
    public Color getStartNodeEntryColor() {
        return Color.GREEN;
    }

    @Override
    public Color getFinalNodeExitColor() {
        return Color.PINK;
    }

    @Override
    public Color getNodeNameColor(Node node) {
        return Color.RED;
    }

    @Override
    public Color getEdgeNameColor(Edge edge) {
        return Color.BLACK;
    }

    @Override
    public Color getTransitionCharColor(Edge edge) {
        return Color.MAGENTA;
    }
}
