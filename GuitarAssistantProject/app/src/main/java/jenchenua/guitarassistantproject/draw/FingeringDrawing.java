package jenchenua.guitarassistantproject.draw;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import jenchenua.guitarassistantproject.R;
import jenchenua.guitarassistantproject.fragments.ChordFragment;
import jenchenua.guitarassistantproject.fragments.PatternFragment;
import jenchenua.guitarassistantproject.fragments.ScaleFragment;

public class FingeringDrawing extends View {

    private static final String LOG_TAG = FingeringDrawing.class.getSimpleName();

    private Typeface typeface;

    private Paint gridColor = null;
    private Paint dotsColor = null;
    private Paint tonicDotsColor = null;
    private Paint textColor = null;
    private Paint crossColor = null;

    private int width;
    private int height;

    private int orientation;

    private byte[] switches;

    private String className;

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

        tonicDotsColor = new Paint();
        tonicDotsColor.setColor(getResources().getColor(R.color.tonicDotsColor));

        typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Roboto-MediumItalic.ttf");
        textColor = new Paint();
        textColor.setColor(getResources().getColor(R.color.colorTextColor));
        textColor.setTypeface(typeface);
        textColor.setTextSize(getPercentHeight(4F));

        crossColor = new Paint();
        crossColor.setColor(getResources().getColor(R.color.crossColor));
        crossColor.setStrokeWidth(getPercentWidth(1F));
        
        drawFingering(canvas);
    }

    public void setSwitches(byte[] switches) {
        this.switches = switches;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    private float[] getCoordinatesDotsX(float startFretX, float stopFretX, float stepX){
        float[] coordinates = new float[switches[0]];

        float distanceToCentreFret = (startFretX + stepX - stopFretX) / 2;

        for (int i = 0; i < switches[0]; i++) {
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
        return (stopStringPointX - startStringPointX) / switches[0];
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

        Log.i(LOG_TAG, "Drawing fingering finished.");
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

        Log.i(LOG_TAG, "Drawing strings finished.");

        return stopStingPointY;
    }

    private void drawFrets(Canvas canvas, float startFretPointX, float stopFretPointX, float startFretPointY, float stopFretPointY, float stepX) {
        int i = 0;
        while (i++ <= switches[0]) {
            canvas.drawRect(startFretPointX, startFretPointY, stopFretPointX, stopFretPointY, gridColor);

            startFretPointX += stepX;
            stopFretPointX += stepX;
        }

        Log.i(LOG_TAG, "Drawing frets finished.");
    }

    private void drawDots(Canvas canvas, float[] coordinatesX, float[] coordinatesY) {
        float radius;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            radius = getPercentWidth(4F);
        } else {
            radius = getPercentWidth(2.5F);
        }

        if (className.equals(ScaleFragment.class.getSimpleName())) {
            drawScales(canvas, coordinatesX, coordinatesY, radius);
        }

        if (className.equals(ChordFragment.class.getSimpleName())) {
            drawChords(canvas, coordinatesX, coordinatesY, radius);
        }

        if (className.equals(PatternFragment.class.getSimpleName())) {
            drawScales(canvas, coordinatesX, coordinatesY, radius);
        }

        Log.i(LOG_TAG, "Drawing dots finished.");
    }

    private void drawScales(Canvas canvas, float[] coordinatesX, float[] coordinatesY, float radius) {
        int k = 1;
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j < switches[0]; j++, k++) {
                if (switches[k] != 0) {
                    if (switches[k] == 2)
                        canvas.drawCircle(coordinatesX[j], coordinatesY[i], radius, tonicDotsColor);
                    else
                        canvas.drawCircle(coordinatesX[j], coordinatesY[i], radius, dotsColor);
                }
            }
        }
    }

    private void drawChords(Canvas canvas, float[] coordinatesX, float[] coordinatesY, float radius) {
        float crossRadius = radius / 2;

        if (switches[1] == 0) {
            canvas.drawText(switches[1] + " fr", getPercentWidth(5F), getPercentHeight(47.5F), textColor);
        } else {
            textColor.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(switches[1] + " fr", coordinatesX[0], getPercentHeight(47.5F), textColor);
        }

        int k = 2;
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j < switches[0]; j++, k++) {
                if (switches[k] != 0) {
                    if (switches[k] == 2)
                        canvas.drawCircle(coordinatesX[j], coordinatesY[i], radius, tonicDotsColor);
                    else if (switches[k] == 3) {
                        canvas.drawLine(coordinatesX[j] - crossRadius, coordinatesY[i] - crossRadius,
                                coordinatesX[j] + crossRadius, coordinatesY[i] + crossRadius, crossColor);
                        canvas.drawLine(coordinatesX[j] - crossRadius, coordinatesY[i] + crossRadius,
                                coordinatesX[j] + crossRadius, coordinatesY[i] - crossRadius, crossColor);
                    }
                    else
                        canvas.drawCircle(coordinatesX[j], coordinatesY[i], radius, dotsColor);
                }
            }
        }
    }
}
