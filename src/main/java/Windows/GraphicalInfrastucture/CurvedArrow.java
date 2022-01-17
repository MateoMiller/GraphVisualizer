package Windows.GraphicalInfrastucture;

import StateMachineInfrastructure.Edge;

import java.awt.*;
import java.util.stream.Collectors;

public class CurvedArrow implements Arrow{
    private final int initialX;
    private final int initialY;
    private final Edge edge;
    public Polygon polygon;
    private final double angle;
    private final int width;
    private final int length;
    private final int height;

    public CurvedArrow(int initialX, int initialY, int length, int width, int height, double angle, Edge edge){
        this.angle = angle;
        this.width = width;
        this.length = length;
        this.height = height;
        this.initialX = initialX;
        this.initialY = initialY;
        this.edge = edge;
        this.polygon = initPolygon();
    }

    private Polygon initPolygon(){
        var polygon = new Polygon();
        polygon.addPoint(0, 0);
        polygon.addPoint(length / 3, -height);
        polygon.addPoint(length * 2 / 3, -height);
        polygon.addPoint(length * 5 / 6, -height / 2);
        polygon.addPoint(length * 5 / 6, -height);
        polygon.addPoint(length, 0);
        polygon.addPoint(length * 5 / 6, 0);
        polygon.addPoint(length * 5 / 6, -height + width);
        polygon.addPoint(length * 5 / 6, -height + width);
        polygon.addPoint(length * 5 / 6, -height / 2 + width);
        polygon.addPoint(length * 2 / 3, -height + width);
        polygon.addPoint(length / 3, -height + width);
        polygon.addPoint(0, width);
        Rotator.rotatePolygon(polygon, angle);
        polygon.translate(initialX, initialY);
        return polygon;
    }

    @Override
    public Polygon getPolygon() {
        return polygon;
    }

    @Override
    public Edge getParentEdge() {
        return edge;
    }

    @Override
    public String getTransitionChars() {
        if (edge == null)
            return "";
        return edge.transitionChars.stream()
                .map(x -> Character.toString(x.character))
                .collect(Collectors.joining(","));
    }

    @Override
    public double getTransitionCharsAngle() {
        return angle;
    }

    @Override
    public Point getCenterPosition() {
        var position = new Point(length / 2, -height - width / 2);
        Rotator.rotatePoint(position, angle);
        position.translate(initialX, initialY);
        return position;
    }
}
