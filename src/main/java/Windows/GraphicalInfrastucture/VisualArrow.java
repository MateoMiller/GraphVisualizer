package Windows.GraphicalInfrastucture;

import StateMachineInfrastructure.Edge;

import java.awt.*;
import java.util.stream.Collectors;

public class VisualArrow implements Arrow{
    private final Polygon polygon;
    private final double angle;
    private final int width;
    private final int length;
    private final int initialX;
    private final int initialY;
    private final Edge edge;

    public VisualArrow(int initialX, int initialY, int length, int width, double angle, Edge edge){
        this.angle = angle;
        this.width = width;
        this.length = length;
        this.initialX = initialX;
        this.initialY = initialY;
        this.edge = edge;
        this.polygon = InitPolygon();
    }

    private Polygon InitPolygon() {
        var polygon = new Polygon();

        var arrowBodyEnd = length * 2 / 3;
        polygon.addPoint(0, 0);
        polygon.addPoint(arrowBodyEnd, 0);
        polygon.addPoint(arrowBodyEnd, -width);
        polygon.addPoint(length, width / 2);
        polygon.addPoint(arrowBodyEnd, 2 * width);
        polygon.addPoint(arrowBodyEnd, width);
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
        var point = new Point(length/2, -width / 2);
        Rotator.rotatePoint(point, angle);
        point.translate(initialX, initialY);
        return point;
    }


}
