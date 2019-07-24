package byow.Core;

/**
 * Created by hug.
 */

import byow.InputDemo.InputSource;
import edu.princeton.cs.introcs.StdDraw;

public class InitializationInputSource implements InputSource {
    private char[] CHARACTERS;

    public InitializationInputSource(int direction) {
        if (direction == Engine.INITIALIZATION) {
            CHARACTERS = "NQLC".toCharArray();
        } else if (direction == Engine.RECIEVESEED) {
            CHARACTERS = "S1234567890QC".toCharArray();
        }
    }

    public char getNextKey() {
        while (true) {
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

    public boolean possibleNextInput() {
        return true;
    }
}
