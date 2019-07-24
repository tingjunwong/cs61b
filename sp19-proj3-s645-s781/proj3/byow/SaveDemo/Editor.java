package byow.SaveDemo;

import byow.lab13.MemoryGame;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.awt.Color;
import java.io.Serializable;

public class Editor implements Serializable {
    private String words;
    private Shepherd m;
    private int width = 50;
    private int height = 50;

    public Editor() {
        words = "";
        m = new Shepherd();
    }

    public void initialize() {
        MemoryGame.draw(this.width, this.height);
    }
    public void addChar(char s) {
        words = words + s;
    }

    public void tend() {
        m.baa();
    }

    public void show() {
        int midWidth = width / 2;
        int midHeight = height / 2;

        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(midWidth, midHeight, words);
        StdDraw.text(midHeight, height - 1, m.getSheep());
        StdDraw.show();

    }
}
