package byow.Core;

/**
 * Created by hug.
 */

import byow.InputDemo.InputSource;
import byow.TileEngine.TERenderer;

import edu.princeton.cs.introcs.StdDraw;


public class MoveInputSource implements InputSource {
    private char[] CHARACTERS;
    private Engine engine;
    private TERenderer ter;
    private int x;
    private int y;
    public static final int PROMPTX = 3;
    public static final int PROMPTY = Engine.HEIGHT - 2;

    public MoveInputSource(Engine engine, TERenderer ter) {
        CHARACTERS = "WSADQ:".toCharArray();
        this.engine = engine;
        this.ter = ter;
    }

    public char getNextKey() {

        while (true) {
            mouseHover();
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                for (char ch : CHARACTERS) {
                    if (c == ch) {
                        return c;
                    }
                }
            }
        }
    }

    public void mouseHover() {
        x = (int) Math.floor(StdDraw.mouseX());
        y = (int) Math.floor(StdDraw.mouseY());
        notOutOfRange();
        String descirption = engine.getWorld()[x][y].description();
        if (engine.getSightrange() < 1000) {
            ter.renderFrame(engine.getSightWorld());
        } else {
            ter.renderFrame(engine.getWorld());
        }
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(Engine.TINY);
        StdDraw.text(PROMPTX, PROMPTY, descirption);
        StdDraw.show();

    }

    public void notOutOfRange() {
        if (x >= Engine.WIDTH) {
            x = Engine.WIDTH - 1;
        }
        if (x < 0) {
            x = 0;
        }
        if (y >= Engine.HEIGHT) {
            y = Engine.HEIGHT - 1;
        }
        if (y < 0) {
            y = 0;
        }
    }

    public boolean possibleNextInput() {
        return true;
    }
}
