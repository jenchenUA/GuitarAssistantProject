package jenchenua.guitarassistantproject.draw;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import jenchenua.guitarassistantproject.R;

public class FingeringDrawing extends View {

    private static final String LOG_TAG = FingeringDrawing.class.getSimpleName();

    private Paint gridColor = null;
    private Paint dotsColor = null;
    private Paint tonicDotsColor2 = null;

    private int width;
    private int height;

    private int orientation;

    private byte[] switches;

    public FingeringDrawing(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        orientation = getResources().getConfiguration().orientation;

        width = canvas.getWidth();
        height = canvas.getHeight();

        gridColor = new Paint();
        gridColor.setColor(getResources().getColor(R.color.gridColor));

        dotsColor = new Paint();
        dotsColor.setColor(getResources().getColor(R.color.dotsColor));

        tonicDotsColor2 = new Paint();
        tonicDotsColor2.setColor(getResources().getColor(R.color.tonicDotsColor));
        
        drawFingering(canvas);
    }

    public void setSwitches(byte[] switches) {
        this.switches = switches;
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
        return width * factor;
    }

    private float getPercentHeight(float percent) {
        float factor = percent / 100;
        return  height * factor;
    }

    private void drawFingering(Canvas canvas) {

        Log.i(LOG_TAG, "Drawing fingering.");

        float startStringPointX = getPercentWidth(5F);
        float stopStringPointX = getPercentWidth(93.5F);
        float startFretPointX = getPercentWidth(5F);
        float stopFretPointX = getPercentWidth(6.5F);

        float startStringPointY;
        float stopStingPointY;
        float startFretPointY;
        float stopFretPointY;

        float stepX;
        float stepY;

        float[] coordinatesDotsX;
        float[] coordinatesDotsY;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            startStringPointY = getPercentHeight(5F);
            stopStingPointY = getPercentHeight(5.2F);
            startFretPointY = getPercentHeight(5F);

            stepX = getStepX(startStringPointX, stopStringPointX);
            stepY = getPercentHeight(7F);
        } else {
            startStringPointY = getPercentHeight(10F);
            stopStingPointY = getPercentHeight(10.4F);
            startFretPointY = getPercentHeight(10F);

            stepX = getStepX(startStringPointX, stopStringPointX);
            stepY = getPercentHeight(15F);
        }

        coordinatesDotsX = getCoordinatesDotsX(startFretPointX, stopFretPointX, stepX);
        coordinatesDotsY = getCoordinatesDotsY(startStringPointY, stopStingPointY, stepY);

        stopFretPointY = drawStrings(canvas, startStringPointX, stopStringPointX, startStringPointY, stopStingPointY, stepY);
        drawFrets(canvas, startFretPointX, stopFretPointX, startFretPointY, stopFretPointY, stepX);
        drawDots(canvas, coordinatesDotsX, coordinatesDotsY);
    }

    private float drawStrings(Canvas canvas, float startStringPointX, float stopStringPointX, float startStringPointY, float stopStingPointY, float stepY) {

        Log.i(LOG_TAG, "Drawing strings.");

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

        Log.i(LOG_TAG, "Drawing frets.");

        int i = 1;
        while (i++ <= 6) {
            canvas.drawRect(startFretPointX, startFretPointY, stopFretPointX, stopFretPointY, gridColor);

            startFretPointX += stepX;
            stopFretPointX += stepX;
        }
    }

    private void drawDots(Canvas canvas, float[] coordinatesX, float[] coordinatesY) {

        Log.i(LOG_TAG, "Drawing dots.");

        float radius;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            radius = getPercentWidth(4F);
        } else {
            radius = getPercentWidth(2.5F);
        }

        drawDotsOnString(canvas, coordinatesX, coordinatesY, radius);
    }

    private void drawDotsOnString(Canvas canvas, float[] coordinatesX, float[] coordinatesY, float radius) {
        int k = 0;
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 4; j++, k++) {
                if (switches[k] == 1 || switches[k] == 2)
                    if (switches[k] == 2) {
                        canvas.drawCircle(coordinatesX[j], coordinatesY[i], radius, tonicDotsColor2);
                    } else {
                        canvas.drawCircle(coordinatesX[j], coordinatesY[i], radius, dotsColor);
                    }
            }
        }
    }
}
