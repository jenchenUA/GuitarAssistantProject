package jenchenua.guitarassistantapp.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import jenchenua.guitarassistantapp.draw.itemsfordrawing.Cross;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.Dot;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.Fret;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.GuitarString;

public class FingeringView extends View implements Fingering {
    public static final String LOG_TAG = FingeringView.class.getSimpleName();

    private static int sWidth;
    private static int sHeight;
    private Paint mPaint;
    private DrawManager mDrawManager;
    private Canvas mCanvas;

    public FingeringView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initWidthAndHeight(context);
        mDrawManager = new DrawManager(this, sWidth, sHeight);
    }

    private void initWidthAndHeight(Context context) {
        WindowManager windowManager = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        sWidth = point.x;
        sHeight = point.y;
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mCanvas = canvas;
        mDrawManager.onDraw();
    }

    @Override
    public void drawDot(Dot dot) {
        mPaint.setColor(dot.getColor());
        mCanvas.drawCircle(dot.getX(), dot.getY(), dot.getRadius(), mPaint);
    }

    @Override
    public void drawFret(Fret fret) {
        mPaint.setColor(fret.getColor());
        mCanvas.drawRect(fret.getStartX(), fret.getStartY(), fret.getStopX(), fret.getStopY(), mPaint);
    }

    @Override
    public void drawGuitarString(GuitarString string) {
        //TODO
    }

    @Override
    public void drawCross(Cross cross) {
        //TODO
    }
}
