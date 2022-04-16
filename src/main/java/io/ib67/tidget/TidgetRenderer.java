package io.ib67.tidget;

public abstract class TidgetRenderer implements Comparable<TidgetRenderer> {
    protected final int priority;
    protected final TidgetRenderer parent;

    protected TidgetRenderer(int priority, TidgetRenderer parent) {
        this.priority = priority;
        this.parent = parent;
    }

    @Override
    public int compareTo(TidgetRenderer o) {
        return o.priority - priority;
    }

    public boolean useSharedCanvas(){
        return false;
    }
    public final void drawCall(TidgetCanvas canvas){
        if(parent != null) parent.drawCall(canvas);
        onDraw(canvas);
    }

    protected abstract void onDraw(TidgetCanvas canvas);
}
