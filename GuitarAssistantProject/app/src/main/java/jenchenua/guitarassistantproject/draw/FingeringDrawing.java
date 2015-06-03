package jenchenua.guitarassistantproject.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import jenchenua.guitarassistantproject.R;

public class FingeringDrawing extends View {
    private Paint gridColor = null;
    private Paint dotsColor = null;
    private int width;
    private int height;
    private boolean[] switches;

    public void setSwitches(boolean[] switches) {
        this.switches = switches;
    }

    public FingeringDrawing(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = canvas.getWidth();
        height = canvas.getHeight();

        gridColor = new Paint();
        dotsColor = new Paint();
        gridColor.setColor(getResources().getColor(R.color.gridColor));
        dotsColor.setColor(getResources().getColor(R.color.dotsColor));
        
        drawFingering(canvas);
    }

    private void drawFingering(Canvas canvas) {
        float startStringPointX = getPercentWidth(5F);
        float stopStringPointX = getPercentWidth(93.5F);
        /*float startStringPointY = 50F;
        float stopStingPointY = 52F;
        float stepY = 75F;*/
        float startStringPointY = getPercentHeight(5F);
        float stopStingPointY = getPercentHeight(5.2F);
        float stepY = getPercentHeight(7F);


        stopStingPointY = drawStrings(canvas, startStringPointX, stopStringPointX, startStringPointY, stopStingPointY, stepY);

        float startFretPointX = getPercentWidth(5F);
        float stopFretPointX = getPercentWidth(6.5F);
        float startFretPointY = getPercentHeight(5F);
        float stopFretPointY = stopStingPointY - stepY - 1;
        float stepX = getStepX(startStringPointX, stopStringPointX);
        float[] coordinatesDotsX = getCoordinatesDotsX(startFretPointX, stopFretPointX, stepX);

        drawFrets(canvas, startFretPointX, stopFretPointX, startFretPointY, stopFretPointY, stepX);

        drawDots(canvas, coordinatesDotsX);
    }

    private void drawFrets(Canvas canvas, float startFretPointX, float stopFretPointX, float startFretPointY, float stopFretPointY, float stepX) {
        int j = 1;
        while (j++ <= 6) {
            canvas.drawRect(startFretPointX, startFretPointY, stopFretPointX, stopFretPointY, gridColor);

            startFretPointX += stepX;
            stopFretPointX += stepX;
        }
    }

    private float drawStrings(Canvas canvas, float startStringPointX, float stopStringPointX, float startStringPointY, float stopStingPointY, float stepY) {
        int i = 1;
        while (i++ <= 6) {
            canvas.drawRect(startStringPointX, startStringPointY, stopStringPointX, stopStingPointY, gridColor);

            startStringPointY += stepY;
            stopStingPointY += stepY + 1;
        }
        return stopStingPointY;
    }

    private void drawDots(Canvas canvas, float[] coordinatesX) {
        float radius = 30F;

        float string1 = 51F;
        float string2 = 126.5F;
        float string3 = 202F;
        float string4 = 277.5F;
        float string5 = 353F;
        float string6 = 428.5F;

        float fret1 = coordinatesX[0];
        float fret2 = coordinatesX[1];
        float fret3 = coordinatesX[2];
        float fret4 = coordinatesX[3];
        float fret5 = coordinatesX[4];

        canvas.drawCircle(fret1, string1, radius, dotsColor);
        canvas.drawCircle(fret2, string2, radius, dotsColor);
        canvas.drawCircle(fret3, string3, radius, dotsColor);
        canvas.drawCircle(fret4, string4, radius, dotsColor);
        canvas.drawCircle(fret5, string5, radius, dotsColor);
        canvas.drawCircle(fret1, string6, radius, dotsColor);
    }

    private float[] getCoordinatesDotsX(float startFretX, float stopFretX, float stepX){
        float[] coordinates = new float[5];

        float distanceToCentreFret = (startFretX + stepX - stopFretX) / 2;

        coordinates[0] = stopFretX + distanceToCentreFret;
        coordinates[1] = stopFretX + stepX + distanceToCentreFret;
        coordinates[2] = stopFretX + (stepX * 2) + distanceToCentreFret;
        coordinates[3] = stopFretX + (stepX * 3) + distanceToCentreFret;
        coordinates[4] = stopFretX + (stepX * 4) + distanceToCentreFret;

        return coordinates;
    }

    private float getStepX(float startStringPointX, float stopStringPointX) {
        return (stopStringPointX - startStringPointX) / 5;
    }

    private float getPercentWidth(float percent) {
        float factor = percent / 100;
        int width = this.width;
        return width * factor;
    }

    private float getPercentHeight(float percent) {
        float factor = percent / 100;
        int height = this.height;
        return  height * factor;
    }
}
