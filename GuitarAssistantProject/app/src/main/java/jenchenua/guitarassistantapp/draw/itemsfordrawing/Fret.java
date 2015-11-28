package jenchenua.guitarassistantapp.draw.itemsfordrawing;

public class Fret {
    private float startX;
    private float stopX;
    private float startY;
    private float stopY;
    private int color;

    public Fret(float startX, float stopX, float startY, float stopY) {
        this.startX = startX;
        this.stopX = stopX;
        this.startY = startY;
        this.stopY = stopY;
    }

    public Fret(float startX, float stopX, float startY, float stopY, int color) {
        this.startX = startX;
        this.stopX = stopX;
        this.startY = startY;
        this.stopY = stopY;
        this.color = color;
    }

    public float getStartX() {
        return startX;
    }

    public float getStopX() {
        return stopX;
    }

    public float getStartY() {
        return startY;
    }

    public float getStopY() {
        return stopY;
    }

    public float getWidth() {
        return stopX - startX;
    }

    public float getHeight() {
        return stopY - startY;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
