package jenchenua.guitarassistantapp.draw.itemsfordrawing;

public class Cross {
    private float x0;
    private float x1;
    private float y0;
    private float y1;
    private float strokeWidth;
    private int color;

    public Cross(float x, float y, float radius, float strokeWidth) {
        this.x0 = x - radius;
        this.x1 = x + radius;
        this.y0 = y - radius;
        this.y1 = y + radius;
        this.strokeWidth = strokeWidth;
    }

    public Cross(float x, float y, float radius, float strokeWidth, int color) {
        this.x0 = x - radius;
        this.x1 = x + radius;
        this.y0 = y - radius;
        this.y1 = y + radius;
        this.strokeWidth = strokeWidth;
        this.color = color;
    }

    public float getFisthLineStartX() {
        return x0;
    }

    public float getSecondLineStartX() {
        return x1;
    }

    public float getFirstLineStopX() {
        return x1;
    }

    public float getSecondLineStopX() {
        return x0;
    }

    public float getFisthLineStartY() {
        return y0;
    }

    public float getSecondLineStartY() {
        return y1;
    }

    public float getFirstLineStopY() {
        return y1;
    }

    public float getSecondLineStopY() {
        return y0;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
