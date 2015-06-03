package jenchenua.guitarassistantproject.draw;

import android.content.Context;
import android.content.res.Configuration;
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
        int orientation = getResources().getConfiguration().orientation;

        float startStringPointX = getPercentWidth(5F);
        float stopStringPointX = getPercentWidth(93.5F);
        float startFretPointX = getPercentWidth(5F);
        float stopFretPointX = getPercentWidth(6.5F);

        float startStringPointY;
        float stopStingPointY;
        float startFretPointY;
        float stopFretPointY;

        float stepY;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            startStringPointY = getPercentHeight(5F);
            stopStingPointY = getPercentHeight(5.2F);
            stepY = getPercentHeight(7F);
            startFretPointY = getPercentHeight(5F);
        } else {
            startStringPointY = getPercentHeight(10F);
            stopStingPointY = getPercentHeight(10.4F);
            stepY = getPercentHeight(15F);
            startFretPointY = getPercentHeight(10F);
        }

        float stepX = getStepX(startStringPointX, stopStringPointX);

        float[] coordinatesDotsX = getCoordinatesDotsX(startFretPointX, stopFretPointX, stepX);
        float[] coordinatesDotsY = getCoordinatesDotsY(startStringPointY, stopStingPointY, stepY);

        stopFretPointY = drawStrings(canvas, startStringPointX, stopStringPointX, startStringPointY, stopStingPointY, stepY);
        drawFrets(canvas, startFretPointX, stopFretPointX, startFretPointY, stopFretPointY, stepX);
        drawDots(canvas, coordinatesDotsX, coordinatesDotsY);
    }

    private float drawStrings(Canvas canvas, float startStringPointX, float stopStringPointX, float startStringPointY, float stopStingPointY, float stepY) {
        int i = 1;
        while (i++ <= 6) {
            canvas.drawRect(startStringPointX, startStringPointY, stopStringPointX, stopStingPointY, gridColor);

            startStringPointY += stepY;
            if (i <= 6) {
                stopStingPointY += stepY + 1;
            }
        }

        return stopStingPointY;
    }

    private void drawFrets(Canvas canvas, float startFretPointX, float stopFretPointX, float startFretPointY, float stopFretPointY, float stepX) {
        int i = 1;
        while (i++ <= 6) {
            canvas.drawRect(startFretPointX, startFretPointY, stopFretPointX, stopFretPointY, gridColor);

            startFretPointX += stepX;
            stopFretPointX += stepX;
        }
    }

    private void drawDots(Canvas canvas, float[] coordinatesX, float[] coordinatesY) {
        float radius = 30F;

        float fret1 = coordinatesX[0];
        float fret2 = coordinatesX[1];
        float fret3 = coordinatesX[2];
        float fret4 = coordinatesX[3];
        float fret5 = coordinatesX[4];

        float string1 = coordinatesY[0];
        float string2 = coordinatesY[1];
        float string3 = coordinatesY[2];
        float string4 = coordinatesY[3];
        float string5 = coordinatesY[4];
        float string6 = coordinatesY[5];

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

        for (int i = 0; i < 5; i++) {
            if (i == 0)
                coordinates[i] = stopFretX + distanceToCentreFret;
            else if (i == 1)
                coordinates[i] = stopFretX + stepX + distanceToCentreFret;
            else
                coordinates[i] = stopFretX + (stepX * i) + distanceToCentreFret;
        }

        return coordinates;
    }

    private float[] getCoordinatesDotsY(float startStringY, float stopFretY, float stepY) {
        float[] coordinates = new float[6];
        for (int i = 0; i < 6; i++) {
            float distanceToCenterString = (stopFretY - startStringY) / 2;
            if (i != 0) {
                distanceToCenterString = (stopFretY - startStringY + i) / 2;
            }
            if (i == 0)
                coordinates[i] = startStringY + distanceToCenterString;
            else if (i == 1)
                    coordinates[i] = startStringY + stepY + distanceToCenterString;
            else
                coordinates[i] = startStringY + (stepY * i) + distanceToCenterString;
        }
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
