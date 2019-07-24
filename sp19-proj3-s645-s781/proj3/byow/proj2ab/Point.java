package byow.proj2ab;

import java.io.Serializable;
import java.util.Objects;

public class Point implements Serializable {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Returns the great-circle (haversine) distance between geographic coordinates
     * (LATV, LONV) and (LATW, LONW).
     *
     * @source Kevin Lowe & Antares Chen, and https://www.movable-type.co.uk/scripts/latlong.html
     **/
//    private static int distance(int lonV, int lonW, int latV, int latW) {
//        int phi1 = Math.toRadians(latV);
//        int phi2 = Math.toRadians(latW);
//        int dphi = Math.toRadians(latW - latV);
//        int dlambda = Math.toRadians(lonW - lonV);
//
//        int a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
//        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
//        int c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        return 3963 * c;
//    }

    /**
     * Returns the haversine distance squared between two points, assuming
     * x represents the longitude and y represents the latitude.
     */
    public static int distance(Point p1, Point p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return Integer.compare(point.x, x) == 0
                && Integer.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("Point x: %d, y: %d", x, y);
    }
}
