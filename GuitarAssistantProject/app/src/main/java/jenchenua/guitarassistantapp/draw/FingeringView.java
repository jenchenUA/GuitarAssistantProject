package jenchenua.guitarassistantapp.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import jenchenua.guitarassistantapp.draw.interfaces.Fingering;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.Cross;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.Dot;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.Fret;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.GuitarString;

public class FingeringView extends View implements Fingering {
    public static final String LOG_TAG = FingeringView.class.getSimpleName();

    private int sWidth;
    private Paint mPaint;
    private DrawManager mDrawManager;
    private Canvas mCanvas;

    public FingeringView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initWidthAndHeight(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mCanvas = canvas;
        if (mDrawManager != null) {
            mDrawManager.onDraw();
        }
    }

    @Override
    public void drawDot(Dot dot) {
        mPaint.setColor(dot.getColor());
        mCanvas.drawCircle(dot.getX(), dot.getY(), dot.getRadius(), mPaint);
    }

    @Override
    public void drawFret(Fret fret) {
        mPaint.setColor(fret.getColor());
        mCanvas.drawRect(
                fret.getStartX(),
                fret.getStartY(),
                fret.getStopX(),
                fret.getStopY(),
                mPaint
        );
    }

    @Override
    public void drawGuitarString(GuitarString string) {
        mPaint.setColor(string.getColor());
        mCanvas.drawRect(
                string.getStartX(),
                string.getStartY(),
                string.getStopX(),
                string.getStopY(),
                mPaint
        );
    }

    @Override
    public void drawCross(Cross cross) {
        mPaint.setColor(cross.getColor());
        mPaint.setStrokeWidth(cross.getStrokeWidth());
        mCanvas.drawLine(
                cross.getFirstLineStartX(),
                cross.getFirstLineStartY(),
                cross.getFirstLineStopX(),
                cross.getFirstLineStopY(),
                mPaint
        );
        mCanvas.drawLine(
                cross.getSecondLineStartX(),
                cross.getSecondLineStartY(),
                cross.getSecondLineStopX(),
                cross.getSecondLineStopY(),
                mPaint
        );
    }

    @Override
    public void redraw() {
        invalidate();
    }

    public int getScreenWidth() {
        return sWidth;
    }

    public void setDrawManager(DrawManager drawManager) {
        mDrawManager = drawManager;
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    private void initWidthAndHeight(Context context) {
        WindowManager windowManager = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        sWidth = point.x;
    }
}
