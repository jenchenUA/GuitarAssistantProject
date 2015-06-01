package jenchenua.guitarassistantproject.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import jenchenua.guitarassistantproject.R;

public class FingeringDrawing extends View {
    private Paint mPaint = null;

    public FingeringDrawing(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.stringsColor));

        drawStrings(canvas);
    }

    private void drawStrings(Canvas canvas) {
        int startStringPointX = 50;
        int stopStringPointX = canvas.getWidth() - 50;
        int startStringPointY = 50;
        int stopStingPointY = 52;

        int i = 1;
        while (i++ <= 6) {
            canvas.drawRect(startStringPointX, startStringPointY, stopStringPointX, stopStingPointY, mPaint);

            startStringPointY += 75;
            stopStingPointY += 76;
        }

        int startFretPointX = 50;
        int stopFretPointX = 60;
        int startFretPointY = 50;
        int stopFretPointY = stopStingPointY - 76;

        int j = 1;
        while (j++ <= 6) {
            canvas.drawRect(startFretPointX, startFretPointY, stopFretPointX, stopFretPointY, mPaint);

            startFretPointX += (canvas.getWidth() - 100)/5;
            stopFretPointX += (canvas.getWidth() - 100)/5;
        }
    }
}
