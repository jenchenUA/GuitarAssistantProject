package jenchenua.guitarassistantapp.draw.itemsfordrawing;

public class Rect {
    protected float startX;
    protected float startY;
    protected float stopX;
    protected float stopY;

    public Rect(float startX, float startY, float stopX, float stopY) {
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getStopX() {
        return stopX;
    }

    public float getStopY() {
        return stopY;
    }
}
