package jenchenua.guitarassistantapp.draw;

import android.util.TypedValue;

import java.util.ArrayList;

import jenchenua.guitarassistantapp.R;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.Cross;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.Dot;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.Fret;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.GuitarString;

public class DrawManager {
    public static final String LOG_TAG = DrawManager.class.getSimpleName();

    private static final int NUMBER_OF_STRING = 6;
    private static int sWidth;
    private static int sHeight;
    private FingeringView mFingeringView;
    private ArrayList<GuitarString> mGuitarStrings;
    private ArrayList<Fret> mFrets;
    private ArrayList<Dot> mDots;
    private ArrayList<Cross> mCrosses;

    public DrawManager(FingeringView fingeringView, int width, int height) {
        this.mFingeringView = fingeringView;
        sWidth = width;
        sHeight = height;
        initGuitarStrings();
        initFrets();
        initDots();
        initCrosses();
    }

    @SuppressWarnings("deprecation")
    private void initGuitarStrings() {
        final float startStringX = dipToPixels(R.dimen.activity_horizontal_margin);
        final float stopStringX = sWidth - dipToPixels(R.dimen.activity_horizontal_margin);
        float startStringY = dipToPixels(R.dimen.activity_vertical_margin);
        float stopStringY = startStringY + dipToPixels(R.dimen.stings_width);
        final int color = mFingeringView.getResources().getColor(R.color.gridColor);
        final float stringInterval = dipToPixels(R.dimen.stings_interval);
        mGuitarStrings = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_STRING; i++) {
            mGuitarStrings.add(new GuitarString(startStringX, startStringY, stopStringX, stopStringY, color));
            startStringY += stringInterval;
            stopStringY++;
        }
    }

    @SuppressWarnings("deprecation")
    private void initFrets() {
        float startFretX = dipToPixels(R.dimen.activity_horizontal_margin);
        float stopFretX = startFretX + dipToPixels(R.dimen.fret_width);
        final float startFretY = dipToPixels(R.dimen.activity_vertical_margin);
        final float stopFretY = mGuitarStrings.get(NUMBER_OF_STRING - 1).getStopX();
        final int color = mFingeringView.getResources().getColor(R.color.gridColor);
        //TODO: Implement this method
    }

    @SuppressWarnings("deprecation")
    private void initDots() {
        //TODO: Implement this method
    }

    @SuppressWarnings("deprecation")
    private void initCrosses() {
        //TODO: Implement this method
    }

    public void onDraw() {
        for (GuitarString string: mGuitarStrings) {
            mFingeringView.drawGuitarString(string);
        }
        for (Fret fret: mFrets) {
            mFingeringView.drawFret(fret);
        }
        for (Dot dot: mDots) {
            mFingeringView.drawDot(dot);
        }
        if (!mCrosses.isEmpty()) {
            for (Cross cross: mCrosses) {
                mFingeringView.drawCross(cross);
            }
        }
    }

    private float dipToPixels(float dipValue) {
        final float scale = mFingeringView.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private float dipToPixels(int resource) {
        TypedValue value = new TypedValue();
        mFingeringView.getResources().getValue(resource, value, true);
        float dipValue = value.getFloat();
        final float scale = mFingeringView.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5F);
    }
}
