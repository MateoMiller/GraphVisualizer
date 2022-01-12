package Windows.GraphicalStuff;

import Infrastructure.Edge;

import java.awt.*;

public interface Arrow {
    public Polygon getPolygon();
    public Edge getParentEdge();
    public String getTransitionChars();
    public double getTransitionCharsAngle();
    public Point getCenterPosition();
}
