package byow.Core;

import byow.InputDemo.InputSource;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.hw4.AStarGraph;
import byow.hw4.WeirdSolver;
import byow.proj2ab.Point;
import byow.proj2ab.WeirdPointSet;

import java.awt.Font;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Random;
import java.util.List;
import java.util.Comparator;
import java.util.Arrays;

import edu.princeton.cs.introcs.StdDraw;



public class Engine implements Serializable {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static final int ROOM = 20;
    private static final int EDGE = 3;
    public static final int INITIALIZATION = -1232134124;
    public static final int RECIEVESEED = -2134214;
    private static final double CENTERX = (double) (WIDTH - 1) / 2;
    private static final double CENTERY = (double) (HEIGHT - 1) / 2;
    private static final int SIGHTRANGE = 6;
    private static final Font LARGE = new Font("Monaco", Font.BOLD, 40);
    private static final Font SMALL = new Font("Monaco", Font.PLAIN, 20);
    static final Font TINY = new Font("Monaco", Font.PLAIN, 15);
    private Point avatar;
    private WeirdPointSet roomKDT;
    private LinkedList<Point> room;
    private HashMap<Point, TETile> pointType;
    private Point inf;
    private long number;
    private TETile[][] world;
    private TETile[][] sightWorld;
    private TERenderer ter;
    private boolean inputStringQuit = false;
    private boolean chinese = false;

    public Engine() {
        ter = new TERenderer();
        room = new LinkedList<>();
        inf = new Point(1000, 1000);
        room.addLast(inf);
        world = new TETile[WIDTH][HEIGHT];
        roomKDT = new WeirdPointSet(room);
        pointType = new HashMap<>();
        sightWorld = new TETile[WIDTH][HEIGHT];
    }

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT);
        String input = initializeInterface();
        saveOrGenerateNew(input);
        ter.initialize(WIDTH, HEIGHT);
        if (SIGHTRANGE < 100) {
            ter.renderFrame(sightWorld);
        } else {
            ter.renderFrame(world);
        }

        moveAvatar();

    }

    private void saveOrGenerateNew(String input) {
        if (input.equalsIgnoreCase("L")) {
            load();
        } else {
            generateWorld(input);
        }
    }

    private void load() {     /* @Source reference from SaveDemo */
        File f = new File("./save_data.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                Engine engine = (Engine) os.readObject();
                world = engine.world;
                avatar = engine.avatar;
                pointType = engine.pointType;
                room = engine.room;
                roomKDT = engine.roomKDT;
                sightWorld = engine.sightWorld;
                os.close();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        } else {
            System.out.println("fsdfsafsadfasdf");
        }
    }

    private void save() {
        File f = new File("./save_data.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(this);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void moveAvatar() {
        InputSource inputSource = new MoveInputSource(this, ter);
        while (true) {
            char c = inputSource.getNextKey();
            if (c == ':') {
                char isQ = inputSource.getNextKey();
                if (isQ == 'Q') {
                    save();
                    System.exit(0);
                }
            }
            move(c);
            if (SIGHTRANGE < 100) {
                ter.renderFrame(sightWorld);
            } else {
                ter.renderFrame(world);
            }

        }
    }

    private void move(char c) {
        if (c == 'W') {
            int x = avatar.getX();
            int y = avatar.getY();
            if (!world[x][y + 1].equals(Tileset.WALL)) {
                avatar = new Point(x, y + 1);
                world[x][y] = Tileset.NOTHING;
                pointType.put(new Point(x, y), Tileset.NOTHING);
                world[x][y + 1] = Tileset.AVATAR;
                pointType.put(avatar, Tileset.AVATAR);
            }
        }

        if (c == 'S') {
            int x = avatar.getX();
            int y = avatar.getY();
            if (!world[x][y - 1].equals(Tileset.WALL)) {
                avatar = new Point(x, y - 1);
                world[x][y] = Tileset.NOTHING;
                pointType.put(new Point(x, y), Tileset.NOTHING);
                world[x][y - 1] = Tileset.AVATAR;
                pointType.put(avatar, Tileset.AVATAR);
            }
        }

        if (c == 'A') {
            int x = avatar.getX();
            int y = avatar.getY();
            if (!world[x - 1][y].equals(Tileset.WALL)) {
                avatar = new Point(x - 1, y);
                world[x][y] = Tileset.NOTHING;
                pointType.put(new Point(x, y), Tileset.NOTHING);
                world[x - 1][y] = Tileset.AVATAR;
                pointType.put(avatar, Tileset.AVATAR);
            }
        }

        if (c == 'D') {
            int x = avatar.getX();
            int y = avatar.getY();
            if (!world[x + 1][y].equals(Tileset.WALL)) {
                avatar = new Point(x + 1, y);
                world[x][y] = Tileset.NOTHING;
                pointType.put(new Point(x, y), Tileset.NOTHING);
                world[x + 1][y] = Tileset.AVATAR;
                pointType.put(avatar, Tileset.AVATAR);
            }
        }
        updateSight();
    }

    private String initializeInterface() {
        drawInitializationWindow();
        InputSource inputSource = new InitializationInputSource(INITIALIZATION);
        StringBuilder input = new StringBuilder();
        while (true) {
            char current = inputSource.getNextKey();
            if (current == 'C') {
                if (chinese) {
                    chinese = false;
                } else {
                    chinese = true;
                }
                if (chinese) {
                    drawInitializationWindowChinese();
                } else {
                    drawInitializationWindow();
                }
            }
            if (current == 'Q') {
                System.exit(0);
            }
            if (current == 'N') {
                input.append(current);
                if (chinese) {
                    draw(SMALL, CENTERX, CENTERY - 6, "请输入随机数种子");
                } else {
                    draw(SMALL, CENTERX, CENTERY - 6, "Enter seed");
                }
                StdDraw.show();
                System.out.println(current);
                while (true) {
                    InputSource inputSeed = new InitializationInputSource(RECIEVESEED);
                    char c = inputSeed.getNextKey();
                    if (c == 'C') {
                        if (chinese) {
                            chinese = false;
                        } else {
                            chinese = true;
                        }
                        if (chinese) {
                            drawInitializationWindowChinese();
                            draw(SMALL, CENTERX, CENTERY - 6, "请输入随机数种子");
                        } else {
                            drawInitializationWindow();
                            draw(SMALL, CENTERX, CENTERY - 6, "Enter seed");
                        }
                    }
                    if (c == 'Q') {
                        System.exit(0);
                    }
                    System.out.println(c);
                    if (!(c == 'C')) {
                        input.append(c);
                    }

                    if (c == 'S') {
                        return input.toString();
                    }
                    if (chinese) {
                        drawInitializationWindowChinese();
                        draw(SMALL, CENTERX, CENTERY - 6, "请输入随机数种子");
                    } else {
                        drawInitializationWindow();
                        draw(SMALL, CENTERX, CENTERY - 6, "Enter seed");
                    }
                    draw(SMALL, CENTERX, CENTERY - 8, input.toString().substring(1));
                    StdDraw.show();
                }
            }
            if (current == 'L') {
                input.append(current);
                return input.toString();
            }
        }
    }

    private void drawInitializationWindow() {
        setInitializeCanvas();
        draw(LARGE, CENTERX, (double) HEIGHT * 5 / 6, "CS61B: THE GAME");
        draw(SMALL, CENTERX, CENTERY + 2, "New Game(N)");
        draw(SMALL, CENTERX, CENTERY, "Load Game(L)");
        draw(SMALL, CENTERX, CENTERY - 2, "Quit(Q)");
        draw(SMALL, CENTERX, CENTERY - 4, "Set Language To Chinese(C)");
        StdDraw.show();
    }

    private void drawInitializationWindowChinese() {
        setInitializeCanvas();
        draw(LARGE, CENTERX, (double) HEIGHT * 5 / 6, "CS61B: 游戏");
        draw(SMALL, CENTERX, CENTERY + 2, "新游戏(N)");
        draw(SMALL, CENTERX, CENTERY, "载入游戏存档(L)");
        draw(SMALL, CENTERX, CENTERY - 2, "退出(Q)");
        draw(SMALL, CENTERX, CENTERY - 4, "切换回英语(C)");
        StdDraw.show();
    }

    private void setInitializeCanvas() {
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
    }

    private void draw(Font font, double x, double y, String text) {
        StdDraw.setFont(font);
        StdDraw.text(x, y, text);
    }


    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
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
        inputStringGenerateWorld(input);
        if (SIGHTRANGE < 1000) {
            return sightWorld;
        }
        return world;
    }

    private TETile[][] generateWorld(String input) {
        dealWithInput(input);
        generateRoom(this.number);
        sortRoom();
        AStarGraph<Point> graph = new GridWorldGraph(pointType);
        connectAll(graph);
        addAvatar();
        return world;
    }

    private void inputStringGenerateWorld(String input) {
        input = input.toUpperCase();
        String first = input.substring(0, 1);
        if (first.equals("N")) {
            int endOfSeed = input.indexOf('S');
            String nTos = input.substring(0, endOfSeed + 1);
            generateWorld(nTos);
            if (input.length() > endOfSeed + 1) {
                inputStringMove(input, endOfSeed);
            }
        }

        if (first.equals("L")) {
            load();
            if (input.length() > 1) {
                inputStringMove(input, 0);
            }

        }
    }

    private void inputStringMove(String input, int endOfSeed) {
        char[] moves = input.substring(endOfSeed + 1).toCharArray();
        for (int i = 0; i < moves.length; i++) {
            if (moves[i] == ':' && moves[i + 1] == 'Q') {
                save();
                inputStringQuit = true;
                return;
            }
            move(moves[i]);
        }
    }

    private void sortRoom() {
        room.remove(inf);
        room.sort(Comparator.comparingInt(Point::hashCode));
    }

    private void dealWithInput(String input) {
        String first = input.substring(0, 1);
        String last = input.substring(input.length() - 1);
        String num = input.substring(1, input.length() - 1);
        if (!(first.equalsIgnoreCase("n") && last.equalsIgnoreCase("s"))) {
            throw new IllegalArgumentException(
                    "The first and last element of the string must be 'n' and 's'");
        }
        this.number = Long.parseLong(num);
        initializeWorldWithNothing(world);

    }

    private void initializeWorldWithNothing(TETile[][] whichWorld) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                whichWorld[i][j] = Tileset.NOTHING;
                if (Arrays.deepEquals(whichWorld, world)) {
                    pointType.put(new Point(i, j), Tileset.NOTHING);
                }

            }
        }
    }

    private void generateOneRoom(long num) {
        Random r = new Random(num);
        int width = RandomUtils.uniform(r, 2, 7);
        int height = RandomUtils.uniform(r, 2, 3);
        int x = RandomUtils.uniform(r, 1 + EDGE, WIDTH - 2 - EDGE);
        int y = RandomUtils.uniform(r, 1 + EDGE, HEIGHT - 2 - EDGE);
        Point p = new Point(x, y);
        Point nearest = roomKDT.nearest(x, y);
        int distance = Point.distance(nearest, p);
        if (distance < width + height) {
            return;
        }
        room.addLast(p);
        roomKDT = new WeirdPointSet(room);
        NotOutOfWindow notOutOfWindow = new NotOutOfWindow(width, height, x, y).invoke();
        int startX = notOutOfWindow.getStartX();
        int endX = notOutOfWindow.getEndX();
        int startY = notOutOfWindow.getStartY();
        int endY = notOutOfWindow.getEndY();
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

    private void generateRoom(long num) {
        while (room.size() < ROOM) {
            generateOneRoom(num);
            num += 1;
        }
    }

    private List<Point> findShortestPath(Point p1, Point p2, AStarGraph<Point> graph) {
        WeirdSolver<Point> solver = new WeirdSolver<>(graph, p1, p2, 100000.0);
        return solver.solution();
    }

    private void connectTwoRoom(List<Point> solution) {
        for (int i = 0; i < solution.size(); i++) {
            Point current = solution.get(i);
            world[current.getX()][current.getY()] = Tileset.FLOOR;
            pointType.put(current, Tileset.FLOOR);
            List<Point> neighbors = PointUtils.neighbors(current);
            for (Point p : neighbors) {
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
            List<Point> solution;
            solution = findShortestPath(start, end, graph);
            connectTwoRoom(solution);
        }
    }

    private void addAvatar() {
        for (Point p : room) {
            if (pointType.get(p).equals(Tileset.FLOOR)) {
                world[p.getX()][p.getY()] = Tileset.AVATAR;
                avatar = p;
                break;
            }
        }
        initializeWorldWithNothing(sightWorld);
        updateSight();
    }

    public void updateSight() {
        int x = avatar.getX();
        int y = avatar.getY();
        NotOutOfWindow notOutOfWindow = new NotOutOfWindow(SIGHTRANGE, SIGHTRANGE, x, y).invoke();
        int startX = notOutOfWindow.getStartX();
        int endX = notOutOfWindow.getEndX();
        int startY = notOutOfWindow.getStartY();
        int endY = notOutOfWindow.getEndY();
        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                sightWorld[i][j] = world[i][j];
            }
            sightWorld[i][endY + 1] = Tileset.NOTHING;
            sightWorld[i][startY - 1] = Tileset.NOTHING;
        }
        for (int i = startY; i <= endY; i++) {
            sightWorld[endX + 1][i] = Tileset.NOTHING;
            sightWorld[startX - 1][i] = Tileset.NOTHING;
        }
    }


    private class NotOutOfWindow implements Serializable {
        private int width;
        private int height;
        private int x;
        private int y;
        private int startX;
        private int endX;
        private int startY;
        private int endY;

        NotOutOfWindow(int width, int height, int x, int y) {
            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;
        }

        public int getStartX() {
            return startX;
        }

        public int getEndX() {
            return endX;
        }

        public int getStartY() {
            return startY;
        }

        public int getEndY() {
            return endY;
        }

        public NotOutOfWindow invoke() {
            startX = x - width;
            endX = x + width;
            startY = y - height;
            endY = y + height;
            if (startX < EDGE) {
                startX = EDGE;
            }
            if (endX >= WIDTH - EDGE) {
                endX = WIDTH - 1 - EDGE;
            }
            if (startY < EDGE) {
                startY = EDGE;
            }
            if (endY >= HEIGHT - EDGE) {
                endY = HEIGHT - 1 - EDGE;
            }
            return this;
        }
    }

    public boolean getInputStringQuit() {
        return inputStringQuit;
    }

    public TETile[][] getWorld() {
        return world;
    }

    public TETile[][] getSightWorld() {
        return sightWorld;
    }

    public int getSightrange() {
        return SIGHTRANGE;
    }
}
