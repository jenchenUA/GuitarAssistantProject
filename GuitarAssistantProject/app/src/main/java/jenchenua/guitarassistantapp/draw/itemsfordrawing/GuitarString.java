package jenchenua.guitarassistantapp.draw.itemsfordrawing;

public class GuitarString extends Rect {
    private float width;
    private int color;

    public GuitarString(float startX, float startY, float stopX, float stopY) {
        super(startX, startY, stopX, stopY);
        this.width = stopY - startY;
    }

    public GuitarString(float startX, float startY, float stopX, float stopY, int color) {
        super(startX, startY, stopX, stopY);
        this.width = stopY - startY;
        this.color = color;
    }

    public float getLenght() {
        return stopX - startX;
    }

    public float getWidth() {
        return width;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
