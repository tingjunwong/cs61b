package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This is the main entry point for the program. This class simply parses
 * the command line inputs, and lets the byow.Core.Engine class take over
 * in either keyboard or input string mode.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            Engine engine = new Engine();
            TETile[][] randomTiles = engine.interactWithInputString("n25sdwwwww");
            TERenderer ter = new TERenderer();
            ter.initialize(Engine.WIDTH, Engine.HEIGHT);
            ter.renderFrame(randomTiles);
            MoveInputSource inputSource = new MoveInputSource(engine, ter);
            while (true) {
                inputSource.mouseHover();
                if (engine.getInputStringQuit()) {
                    System.exit(0);
                }
            }

        } else {
            Engine engine = new Engine();
            engine.interactWithKeyboard();
        }
    }

    @Test
    public void twice() {
        Engine engine = new Engine();
        TETile[][] world1 = engine.interactWithInputString("N999SDWDWAWDDDwwwwww");
        Engine engine1 = new Engine();
        TETile[][] world2;
        world2 = engine1.interactWithInputString("lAWDDDwwwwww");
        for (int i = 0; i < world1.length; i++) {
            for (int j = 0; j < world1[0].length; j++) {
                assertEquals(world1[i][j], world2[i][j]);

            }
        }
    }

}
