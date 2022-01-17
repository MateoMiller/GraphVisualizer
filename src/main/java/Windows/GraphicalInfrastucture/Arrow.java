package Windows.GraphicalInfrastucture;

import StateMachineInfrastructure.Edge;

import java.awt.*;

public interface Arrow {
    public Polygon getPolygon();
    public Edge getParentEdge();
    public String getTransitionChars();
    public double getTransitionCharsAngle();
    public Point getCenterPosition();
}
