package io.ib67.tidget;

import static java.lang.Math.max;

public class TidgetCanvas {
    private final String emptyElement;
    private final char[][] canvas;

    private final int width;
    private final int height;

    public TidgetCanvas(String emptyElement, int width, int height) {
        this.emptyElement = emptyElement;
        this.canvas = new char[height][width];
        this.width = width;
        this.height = height;
    }

    private TidgetCanvas(String emptyElement, char[][] canvas, int width, int height) {
        this.emptyElement = emptyElement;
        this.canvas = canvas;
        this.width = width;
        this.height = height;
    }

    public TidgetCanvas draw(int x, int y, char element) {
        if (x >= width || y >= height || x < 0 || y < 0) {
            throw new IllegalArgumentException("x and y must be between 0 and " + width + " and " + height+", but was " + x + " and " + y);
        }
        canvas[y][x] = element;
        return this;
    }

    public TidgetCanvas draw(float xRatio, float yRatio, char element) {
        if (xRatio > 1F || yRatio > 1F || xRatio < 0F || yRatio < 0F) {
            throw new IllegalArgumentException("x and y must be between 0 and 1");
        }
        draw((int) (xRatio * (width - 1)), (int) (yRatio * (height - 1)), element);
        return this;
    }

    public TidgetCanvas drawString(float xRatio, float yRatio, String element) {
        var startX = Math.min(max(0,(int) (xRatio * (width - 1))),width-1);
        var startY = Math.min(max((int) (yRatio * (height - 1)),0),height-1);
        return drawString(startX, startY, element);
    }

    public TidgetCanvas drawString(int startX, int startY, String element) {
        var lines = element.split("\n");
        if (lines.length == 0) {
            lines = new String[]{element};
        }
        for (int i = 0; i < lines.length; i++) {
            if (startY + i >= height) {
                break;
            }
            var chars = lines[i].toCharArray();
            for (int j = 0; j < chars.length; j++) {
                if (startX + j >= width) {
                    break;
                }
                draw(startX + j, startY + i, chars[j]);
            }
        }
        return this;
    }

    public TidgetCanvas merge(TidgetCanvas anotherCanvas) {
        var newCanvas = new char[height][width];
        // copy this canvas
        for (int i = 0; i < canvas.length; i++) {
            for (int j = 0; j < canvas[i].length; j++) {
                newCanvas[i][j] = canvas[i][j];
            }
        }
        // copy another canvas
        for (int i = 0; i < Math.min(height, anotherCanvas.height); i++) {
            for (int j = 0; j < Math.min(width, anotherCanvas.width); j++) {
                newCanvas[i][j] = anotherCanvas.canvas[i][j];
            }
        }
        return new TidgetCanvas(emptyElement, newCanvas, width, height);
    }

    public String[] draw() {
        var result = new String[height];
        for (int y = 0; y < canvas.length; y++) {
            var collector = new StringBuilder();
            for (int x = 0; x < canvas[y].length; x++) {
                var element = canvas[y][x];
                if (element == '\u0000') {
                    collector.append(emptyElement);
                } else {
                    collector.append(element);
                }
            }
            result[y] = collector.toString();
        }
        return result;
    }
}
