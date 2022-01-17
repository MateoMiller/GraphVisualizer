package Windows.GraphicalInfrastucture;

import StateMachineInfrastructure.Edge;

import java.awt.*;
import java.util.stream.Collectors;

public class LoopArrow implements Arrow {

    private Polygon polygon;
    private final int initialX;
    private final int initialY;
    private final Edge edge;
    private final int width;
    private final int diameter;

    public LoopArrow(int initialX, int initialY, int diameter, int width, Edge edge){
        this.width = width;
        this.initialX = initialX;
        this.initialY = initialY;
        this.edge = edge;
        this.diameter = diameter;
        this.polygon = initPolygon();
    }

    private Polygon initPolygon(){
        var polygon = new Polygon();
        polygon.addPoint(0, 0);
        polygon.addPoint(-diameter / 2, -diameter / 3);
        polygon.addPoint(-diameter / 2, -diameter * 2 / 3);
        polygon.addPoint(-diameter/ 6, -diameter);
        polygon.addPoint(diameter/ 6, -diameter);
        polygon.addPoint(diameter/ 2, -diameter * 2 / 3);
        polygon.addPoint(diameter/ 2, -diameter / 3);
        polygon.addPoint(0, 0);
        polygon.addPoint(0, -width);
        polygon.addPoint(-diameter / 2 + width, -diameter / 3);
        polygon.addPoint(-diameter / 2 + width, -diameter * 2 / 3);
        polygon.addPoint(-diameter/ 6, -diameter + width);
        polygon.addPoint(diameter/ 6, -diameter + width);
        polygon.addPoint(diameter/ 2 - width, -diameter * 2 / 3);
        polygon.addPoint(diameter/ 2 - width, -diameter / 3);
        polygon.addPoint(0, -width);
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
        return 0;
    }

    @Override
    public Point getCenterPosition() {
        return new Point(
                initialX,
                initialY - diameter + width / 2
        );
    }
}
