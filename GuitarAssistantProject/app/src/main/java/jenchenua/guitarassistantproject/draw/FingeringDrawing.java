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

        gridColor = new Paint();
        dotsColor = new Paint();
        gridColor.setColor(getResources().getColor(R.color.gridColor));
        dotsColor.setColor(getResources().getColor(R.color.dotsColor));
        
        drawFingeringGrid(canvas);
        drawDots(canvas);
    }

    private void drawFingeringGrid(Canvas canvas) {
        float startStringPointX = getPercentWidth(0.06F);
        float stopStringPointX = getPercentWidth(0.94F);
        float startStringPointY = 50F;
        float stopStingPointY = 52F;
        float stepY = 75F;

        int i = 1;
        while (i++ <= 6) {
            canvas.drawRect(startStringPointX, startStringPointY, stopStringPointX, stopStingPointY, gridColor);

            startStringPointY += stepY;
            stopStingPointY += stepY + 1;
        }

        float startFretPointX = getPercentWidth(0.06F);
        float stopFretPointX = getPercentWidth(0.075F);
        float startFretPointY = 50F;
        float stopFretPointY = stopStingPointY - stepY - 1;
        float stepX = getPercentWidth(0.175F);

        int j = 1;
        while (j++ <= 6) {
            canvas.drawRect(startFretPointX, startFretPointY, stopFretPointX, stopFretPointY, gridColor);

            startFretPointX += stepX;
            stopFretPointX += stepX;
        }
    }

    private void drawDots(Canvas canvas) {
        float radius = 30F;

        float string1 = 51F;
        float string2 = 126.5F;
        float string3 = 202F;
        float string4 = 277.5F;
        float string5 = 353F;
        float string6 = 428.5F;

        float fret1 = getPercentWidth(0.1565F);
        float fret2;
        float fret3;
        float fret4;
        float fret5;

        float step = (canvas.getWidth() - 100) / 5;
        float startX = step / 2 + 60;

        canvas.drawCircle(fret1, string1, radius, dotsColor);
        canvas.drawCircle(startX, string2, radius, dotsColor);
        canvas.drawCircle(startX, string3, radius, dotsColor);
        canvas.drawCircle(startX, string4, radius, dotsColor);
        canvas.drawCircle(startX, string5, radius, dotsColor);
        canvas.drawCircle(startX, string6, radius, dotsColor);
    }

    private float getPercentWidth(float percent) {
        int width = this.width;
        return width * percent;
    }
}
