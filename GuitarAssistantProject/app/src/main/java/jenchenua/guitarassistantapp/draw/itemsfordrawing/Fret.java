package jenchenua.guitarassistantapp.draw.itemsfordrawing;

public class Fret extends Rect {
    private float width;
    private int color;

    public Fret(float startX, float startY, float stopX, float stopY) {
        super(startX, startY, stopX, stopY);
        width = stopX - startX;
    }

    public Fret(float startX, float startY, float stopX, float stopY, int color) {
        super(startX, startY, stopX, stopY);
        this.width = stopX - startX;
        this.color = color;
    }

    public float getWidth() {
        return width;
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
