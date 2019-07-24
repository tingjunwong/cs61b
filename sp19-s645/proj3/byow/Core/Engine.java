package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.hw4.AStarGraph;
import byow.hw4.WeirdSolver;
import byow.proj2ab.Point;
import byow.proj2ab.WeirdPointSet;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;



import java.util.LinkedList;
import java.util.HashMap;
import java.util.Random;
import java.util.List;
import java.util.Comparator;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static final int ROOM = 30;
    private WeirdPointSet roomKDT;
    private LinkedList<Point> room;
    private HashMap<Point, TETile> pointType;
    private Point inf;
    private WeightedQuickUnionUF connection;
    private long number;
    TETile[][] world;
    public Engine() {
        room = new LinkedList<>();
        inf = new Point(1000, 1000);
        room.addLast(inf);
        world = new TETile[WIDTH][HEIGHT];
        roomKDT = new WeirdPointSet(room);
        pointType = new HashMap<>();

    }
    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        String first = input.substring(0, 1);
        String last = input.substring(input.length() - 1, input.length());
        String num = input.substring(1, input.length() - 1);
        if (!(first.equalsIgnoreCase("n") && last.equalsIgnoreCase("s"))) {
            throw new IllegalArgumentException(
                    "The first and last element of the string must be 'n' and 's'");
        }
        this.number = Long.parseLong(num);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
                pointType.put(new Point(i, j), Tileset.NOTHING);
            }
        }

        while (room.size() < ROOM) {
            generateRoom(this.number);
            this.number += 1;
        }

        room.remove(inf);
        room.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return o1.hashCode() - o2.hashCode();
            }
        });

        AStarGraph<Point> graph = new GridWorldGraph(pointType);
        connectAll(graph);
        return world;
    }

    private void generateRoom(long num) {
        Random r = new Random(num);
        int width = RandomUtils.uniform(r, 2, WIDTH / 10);
        int height = RandomUtils.uniform(r, 2, HEIGHT / 10);
        int x = RandomUtils.uniform(r, 1, WIDTH - 2);
        int y = RandomUtils.uniform(r, 1, HEIGHT - 2);
        Point p = new Point(x, y);
        Point nearest = roomKDT.nearest(x, y);
        int distance = Point.distance(nearest, p);
        if (distance < width + height) {
            return;
        }
        room.addLast(p);
        roomKDT = new WeirdPointSet(room);
        int startX = x - width;
        int endX = x + width;
        int startY = y - height;
        int endY = y + height;
        if (startX < 0) {
            startX = 0;
        }
        if (endX >= WIDTH) {
            endX = WIDTH - 1;
        }
        if (startY < 0) {
            startY = 0;
        }
        if (endY >= HEIGHT) {
            endY = HEIGHT - 1;
        }
        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                if (i == startX || i == endX || j == startY || j == endY) {
                    world[i][j] = Tileset.WALL;
                    pointType.put(new Point(i, j), Tileset.WALL);
                } else {
                    world[i][j] = Tileset.FLOOR;
                    pointType.put(new Point(i, j), Tileset.FLOOR);
                }
            }
        }
    }
    private List<Point> findShortestPath(Point p1, Point p2, AStarGraph<Point> graph) {
        WeirdSolver<Point> solver = new WeirdSolver<>(graph, p1, p2, 10.0);
        return solver.solution();
    }
    private void connectTwoRoom(List<Point> solution) {
        for (int i = 0; i < solution.size(); i++) {
            Point current = solution.get(i);
            world[current.getX()][current.getY()] = Tileset.FLOOR;
            pointType.put(current, Tileset.FLOOR);
            List<Point> neighbors = PointUtils.neighbors(current);
            for (Point p:neighbors) {
                if (pointType.get(p).equals(Tileset.NOTHING)) {
                    world[p.getX()][p.getY()] = Tileset.WALL;
                    pointType.put(p, Tileset.WALL);
                }
            }

        }
    }

    private void connectAll(AStarGraph<Point> graph) {
        for (int i = 0; i < room.size() - 1; i++) {
            Point start = room.get(i);
            Point end = room.get(i + 1);
            WeirdSolver<Point> solver = new WeirdSolver<>(graph, start, end, 10000000000.0);
            List<Point> solution = solver.solution();
            connectTwoRoom(solution);
        }
    }
    public HashMap<Point, TETile> getPointType() {
        return pointType;
    }

}
