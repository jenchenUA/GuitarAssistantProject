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

        gridColor = new Paint();
        dotsColor = new Paint();
        gridColor.setColor(getResources().getColor(R.color.gridColor));
        dotsColor.setColor(getResources().getColor(R.color.dotsColor));
        
        drawFingeringGrid(canvas);
        drawDots(canvas);
    }

    private void drawFingeringGrid(Canvas canvas) {
        int startStringPointX = 50;
        int stopStringPointX = canvas.getWidth() - 50;
        int startStringPointY = 50;
        int stopStingPointY = 52;
        int stepY = 75;

        int i = 1;
        while (i++ <= 6) {
            canvas.drawRect(startStringPointX, startStringPointY, stopStringPointX, stopStingPointY, gridColor);

            startStringPointY += stepY;
            stopStingPointY += stepY + 1;
        }

        int startFretPointX = 50;
        int stopFretPointX = 60;
        int startFretPointY = 50;
        int stopFretPointY = stopStingPointY - stepY - 1;
        int stepX = (canvas.getWidth() - 100) / 5;

        int j = 1;
        while (j++ <= 6) {
            canvas.drawRect(startFretPointX, startFretPointY, stopFretPointX, stopFretPointY, gridColor);

            startFretPointX += stepX;
            stopFretPointX += stepX;
        }
    }

    private void drawDots(Canvas canvas) {
        float string1 = 51F;
        float string2 = 126.5F;
        float string3 = 202F;
        float string4 = 277.5F;
        float string5 = 353F;
        float string6 = 428.5F;

        float step = (canvas.getWidth() - 100) / 5;
        float startX = step / 2 + 60;

        canvas.drawCircle(startX, string1, 25, dotsColor);
        canvas.drawCircle(startX, string2, 25, dotsColor);
        canvas.drawCircle(startX, string3, 25, dotsColor);
        canvas.drawCircle(startX, string4, 25, dotsColor);
        canvas.drawCircle(startX, string5, 25, dotsColor);
        canvas.drawCircle(startX, string6, 25, dotsColor);
    }
}
