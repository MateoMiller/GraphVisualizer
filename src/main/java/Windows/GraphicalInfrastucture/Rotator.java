package Windows.GraphicalInfrastucture;

import java.awt.*;

public class Rotator {

    public static void rotatePolygon(Polygon poly, double angle) {
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        for (int i = 0; i < poly.npoints; i++) {
            double x = poly.xpoints[i];
            double y = poly.ypoints[i];
            poly.xpoints[i] = (int) (x * cos - y * sin);
            poly.ypoints[i] = (int) (x * sin + y * cos);
        }
    }

    public static void rotatePoint(Point point, double angle){
        var x = point.x;
        var y = point.y;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        point.x = (int)(x * cos - y * sin);
        point.y = (int)(x * sin + y * cos);
    }


    public static Point polygonCenterOfMass(Polygon pg) {

        if (pg == null) {
            return null;
        }

        int N = pg.npoints;
        Point[] polygon = new Point[N];

        for (int q = 0; q < N; q++) {
            polygon[q] = new Point(pg.xpoints[q], pg.ypoints[q]);
        }

        double cx = 0, cy = 0;
        double A = PolygonArea(polygon, N);
        int i, j;

        double factor = 0;
        for (i = 0; i < N; i++) {
            j = (i + 1) % N;
            factor = (polygon[i].x * polygon[j].y - polygon[j].x * polygon[i].y);
            cx += (polygon[i].x + polygon[j].x) * factor;
            cy += (polygon[i].y + polygon[j].y) * factor;
        }
        factor = 1.0 / (6.0 * A);
        cx *= factor;
        cy *= factor;
        return new Point((int) Math.abs(Math.round(cx)), (int) Math.abs(Math.round(cy)));
    }

    public static double PolygonArea(Point[] polygon, int N) {

        int i, j;
        double area = 0;

        for (i = 0; i < N; i++) {
            j = (i + 1) % N;
            area += polygon[i].x * polygon[j].y;
            area -= polygon[i].y * polygon[j].x;
        }

        area /= 2.0;
        return (Math.abs(area));
    }
}
