package Windows.GraphicalStuff;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Spiral {
    private final double radius;
    private final Point center;

    public Spiral(double width, double height, Point center) {
        this.radius = Math.min(width, height);
        this.center = center;
    }

    public List<Point> getPositions(int pointsCount){
        var deltaAngle = 2 * Math.PI / pointsCount;
        var result = new ArrayList<Point>();
        for (int i = 0; i < pointsCount; i++){
            var deltaX = radius * Math.cos(i * deltaAngle);
            var deltaY = radius * Math.sin(i * deltaAngle);
            var position = new Point((int)(center.x + deltaX), (int)(center.y + deltaY));
            result.add(position);
        }
        return result;
    }
}
