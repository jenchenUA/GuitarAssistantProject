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

    private int[] switches;

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

    public void setSwitches(int[] switches) {
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

        float string1 = coordinatesY[0];
        float string2 = coordinatesY[1];
        float string3 = coordinatesY[2];
        float string4 = coordinatesY[3];
        float string5 = coordinatesY[4];
        float string6 = coordinatesY[5];

        drawDotsOnString6(canvas, coordinatesX, string6, radius);
        drawDotsOnString5(canvas, coordinatesX, string5, radius);
        drawDotsOnString4(canvas, coordinatesX, string4, radius);
        drawDotsOnString3(canvas, coordinatesX, string3, radius);
        drawDotsOnString2(canvas, coordinatesX, string2, radius);
        drawDotsOnString1(canvas, coordinatesX, string1, radius);
    }

    private void drawDotsOnString1(Canvas canvas, float[] coordinatesX, float string1, float radius) {
        Log.i(LOG_TAG, "Drawing dots on the first string.");

        float fret1 = coordinatesX[0];
        float fret2 = coordinatesX[1];
        float fret3 = coordinatesX[2];
        float fret4 = coordinatesX[3];
        float fret5 = coordinatesX[4];

        if (switches[25] == 1 || switches[25] == 2)
            if (switches[25] == 2) {
                canvas.drawCircle(fret1, string1, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret1, string1, radius, dotsColor);
            }
        if (switches[26] == 1 || switches[26] == 2)
            if (switches[26] == 2) {
                canvas.drawCircle(fret2, string1, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret2, string1, radius, dotsColor);
            }
        if (switches[27] == 1 || switches[27] == 2)
            if (switches[27] == 2) {
                canvas.drawCircle(fret3, string1, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret3, string1, radius, dotsColor);
            }
        if (switches[28] == 1 || switches[28] == 2)
            if (switches[28] == 2) {
                canvas.drawCircle(fret4, string1, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret4, string1, radius, dotsColor);
            }
        if (switches[29] == 1 || switches[29] == 2)
            if (switches[29] == 2) {
                canvas.drawCircle(fret5, string1, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret5, string1, radius, dotsColor);
            }
    }

    private void drawDotsOnString2(Canvas canvas, float[] coordinatesX, float string2, float radius) {
        Log.i(LOG_TAG, "Drawing dots on the second string.");

        float fret1 = coordinatesX[0];
        float fret2 = coordinatesX[1];
        float fret3 = coordinatesX[2];
        float fret4 = coordinatesX[3];
        float fret5 = coordinatesX[4];

        if (switches[20] == 1 || switches[20] == 2)
            if (switches[20] == 2) {
                canvas.drawCircle(fret1, string2, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret1, string2, radius, dotsColor);
            }
        if (switches[21] == 1 || switches[21] == 2)
            if (switches[21] == 2) {
                canvas.drawCircle(fret2, string2, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret2, string2, radius, dotsColor);
            }
        if (switches[22] == 1 || switches[22] == 2)
            if (switches[22] == 2) {
                canvas.drawCircle(fret3, string2, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret3, string2, radius, dotsColor);
            }
        if (switches[23] == 1 || switches[23] == 2)
            if (switches[23] == 2) {
                canvas.drawCircle(fret4, string2, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret4, string2, radius, dotsColor);
            }
        if (switches[24] == 1 || switches[24] == 2)
            if (switches[24] == 2) {
                canvas.drawCircle(fret5, string2, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret5, string2, radius, dotsColor);
            }
    }

    private void drawDotsOnString3(Canvas canvas, float[] coordinatesX, float string3, float radius) {
        Log.i(LOG_TAG, "Drawing dots on the third string.");

        float fret1 = coordinatesX[0];
        float fret2 = coordinatesX[1];
        float fret3 = coordinatesX[2];
        float fret4 = coordinatesX[3];
        float fret5 = coordinatesX[4];

        if (switches[15] == 1 || switches[15] == 2)
            if (switches[15] == 2) {
                canvas.drawCircle(fret1, string3, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret1, string3, radius, dotsColor);
            }
        if (switches[16] == 1 || switches[16] == 2)
            if (switches[16] == 2) {
                canvas.drawCircle(fret2, string3, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret2, string3, radius, dotsColor);
            }
        if (switches[17] == 1 || switches[17] == 2)
            if (switches[17] == 2) {
                canvas.drawCircle(fret3, string3, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret3, string3, radius, dotsColor);
            }
        if (switches[18] == 1 || switches[18] == 2)
            if (switches[18] == 2) {
                canvas.drawCircle(fret4, string3, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret4, string3, radius, dotsColor);
            }
        if (switches[19] == 1 || switches[19] == 2)
            if (switches[19] == 2) {
                canvas.drawCircle(fret5, string3, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret5, string3, radius, dotsColor);
            }
    }

    private void drawDotsOnString4(Canvas canvas, float[] coordinatesX, float string4, float radius) {
        Log.i(LOG_TAG, "Drawing dots on the fourth string.");

        float fret1 = coordinatesX[0];
        float fret2 = coordinatesX[1];
        float fret3 = coordinatesX[2];
        float fret4 = coordinatesX[3];
        float fret5 = coordinatesX[4];

        if (switches[10] == 1 || switches[10] == 2)
            if (switches[10] == 2) {
                canvas.drawCircle(fret1, string4, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret1, string4, radius, dotsColor);
            }
        if (switches[11] == 1 || switches[11] == 2)
            if (switches[11] == 2) {
                canvas.drawCircle(fret2, string4, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret2, string4, radius, dotsColor);
            }
        if (switches[12] == 1 || switches[12] == 2)
            if (switches[12] == 2) {
                canvas.drawCircle(fret3, string4, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret3, string4, radius, dotsColor);
            }
        if (switches[13] == 1 || switches[13] == 2)
            if (switches[13] == 2) {
                canvas.drawCircle(fret4, string4, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret4, string4, radius, dotsColor);
            }
        if (switches[14] == 1 || switches[14] == 2)
            if (switches[14] == 2) {
                canvas.drawCircle(fret5, string4, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret5, string4, radius, dotsColor);
            }
    }

    private void drawDotsOnString5(Canvas canvas, float[] coordinatesX, float string5, float radius) {
        Log.i(LOG_TAG, "Drawing dots on the fifth string.");

        float fret1 = coordinatesX[0];
        float fret2 = coordinatesX[1];
        float fret3 = coordinatesX[2];
        float fret4 = coordinatesX[3];
        float fret5 = coordinatesX[4];

        if (switches[5] == 1 || switches[5] == 2)
            if (switches[5] == 2) {
                canvas.drawCircle(fret1, string5, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret1, string5, radius, dotsColor);
            }
        if (switches[6] == 1 || switches[6] == 2)
            if (switches[6] == 2) {
                canvas.drawCircle(fret2, string5, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret2, string5, radius, dotsColor);
            }
        if (switches[7] == 1 || switches[7] == 2)
            if (switches[7] == 2) {
                canvas.drawCircle(fret3, string5, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret3, string5, radius, dotsColor);
            }
        if (switches[8] == 1 || switches[8] == 2)
            if (switches[8] == 2) {
                canvas.drawCircle(fret4, string5, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret4, string5, radius, dotsColor);
            }
        if (switches[9] == 1 || switches[9] == 2)
            if (switches[9] == 2) {
                canvas.drawCircle(fret5, string5, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret5, string5, radius, dotsColor);
            }
    }

    private void drawDotsOnString6(Canvas canvas, float[] coordinatesX, float string6, float radius) {
        Log.i(LOG_TAG, "Drawing dots on the sixth string.");

        float fret1 = coordinatesX[0];
        float fret2 = coordinatesX[1];
        float fret3 = coordinatesX[2];
        float fret4 = coordinatesX[3];
        float fret5 = coordinatesX[4];

        if (switches[0] == 1 || switches[0] == 2)
            if (switches[0] == 2) {
                canvas.drawCircle(fret1, string6, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret1, string6, radius, dotsColor);
            }
        if (switches[1] == 1 || switches[1] == 2)
            if (switches[1] == 2) {
                canvas.drawCircle(fret2, string6, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret2, string6, radius, dotsColor);
            }
        if (switches[2] == 1 || switches[2] == 2)
            if (switches[2] == 2) {
                canvas.drawCircle(fret3, string6, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret3, string6, radius, dotsColor);
            }
        if (switches[3] == 1 || switches[3] == 2)
            if (switches[3] == 2) {
                canvas.drawCircle(fret4, string6, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret4, string6, radius, dotsColor);
            }
        if (switches[4] == 1 || switches[4] == 2)
            if (switches[4] == 2) {
                canvas.drawCircle(fret5, string6, radius, tonicDotsColor2);
            } else {
                canvas.drawCircle(fret5, string6, radius, dotsColor);
            }
    }
}
