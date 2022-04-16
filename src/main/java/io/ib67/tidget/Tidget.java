package io.ib67.tidget;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public final class Tidget {
    private final TidgetProperty properties;
    private final Set<TidgetRenderer> renderers = new TreeSet<>(TidgetRenderer::compareTo);
    private final PrintStream out;

    private int outLen;

    public Tidget(TidgetProperty properties, PrintStream output) {
        this.properties = properties;
        this.out = output;
    }

    public Tidget registerRenderer(TidgetRenderer renderer) {
        this.renderers.add(renderer);
        return this;
    }

    public Tidget unregisterRenderer(TidgetRenderer renderer) {
        this.renderers.remove(renderer);
        return this;
    }

    public Tidget clearRenderers() {
        renderers.clear();
        return this;
    }

    public Collection<? extends TidgetRenderer> getRenderers() {
        return Collections.unmodifiableSet(renderers);
    }

    public TidgetCanvas draw() {
        var finalCanvas = createCanvas();
        for (TidgetRenderer renderer : renderers) {
            var canvas = renderer.useSharedCanvas() ? finalCanvas : createCanvas();
            renderer.drawCall(canvas);
            if (finalCanvas != canvas) {
                finalCanvas.merge(canvas);
            }
        }
        return finalCanvas;
    }

    public void render() {
        clearTerminal();
        var len = 0;
        for (String s : draw().draw()) {
            len++;
            out.println(s);
        }
        outLen = len;
    }

    public void clearTerminal(){
        out.printf("\033[%dA",outLen);
        out.print("\033[2K");
    }

    private TidgetCanvas createCanvas() {
        return new TidgetCanvas(properties.populator(), properties.width(), properties.height());
    }
}
