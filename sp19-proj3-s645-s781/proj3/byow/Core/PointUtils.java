package byow.Core;

import byow.proj2ab.Point;

import java.util.ArrayList;
import java.util.List;

public class PointUtils {
    public static Point upperPoint(Point p) {
        return new Point(p.getX(), p.getY() + 1);
    }

    public static Point lowerPoint(Point p) {
        return new Point(p.getX(), p.getY() - 1);
    }

    public static Point leftPoint(Point p) {
        return new Point(p.getX() - 1, p.getY());
    }

    public static Point rightPoint(Point p) {
        return new Point(p.getX() + 1, p.getY());
    }

    public static Point upperLeft(Point p) {
        return new Point(p.getX() - 1, p.getY() + 1);
    }

    public static Point upperRight(Point p) {
        return new Point(p.getX() + 1, p.getY() + 1);
    }

    public static Point lowerLeft(Point p) {
        return new Point(p.getX() - 1, p.getY() - 1);
    }

    public static Point lowerRight(Point p) {
        return new Point(p.getX() + 1, p.getY() - 1);
    }

    public static boolean insideWindow(Point p) {
        return p.getX() >= 0 && p.getX() < Engine.WIDTH
                && p.getY() >= 0 && p.getY() < Engine.HEIGHT;
    }

    public static List<Point> neighbors(Point p) {
        List<Point> list = new ArrayList<>();
        list.add(PointUtils.upperPoint(p));
        list.add(PointUtils.leftPoint(p));
        list.add(PointUtils.lowerPoint(p));
        list.add(PointUtils.rightPoint(p));
        list.add(PointUtils.upperLeft(p));
        list.add(PointUtils.upperRight(p));
        list.add(PointUtils.lowerLeft(p));
        list.add(PointUtils.lowerRight(p));
        return list;
    }
}
