package jenchenua.guitarassistantapp.draw;

import android.util.TypedValue;

import java.util.ArrayList;

import jenchenua.guitarassistantapp.R;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.Cross;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.Dot;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.Fret;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.GuitarString;

public class DrawManager {
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

    private void initGuitarStrings() {
        final float startXString = dipToPixels(R.dimen.activity_horizontal_margin);
        final float stopXSting = sWidth - dipToPixels(R.dimen.activity_horizontal_margin);
        float startYString = dipToPixels(R.dimen.activity_vertical_margin);
        float stopYString = startYString + 2;
        final int color = mFingeringView.getResources().getColor(R.color.gridColor);
        final float stringInterval = dipToPixels(R.dimen.stings_interval);
        mGuitarStrings = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_STRING; i++) {
            mGuitarStrings.add(new GuitarString(startXString, startYString, stopXSting, stopYString, color));
            startYString += stringInterval;
            stopYString++;
        }
    }

    private void initFrets() {
        //TODO
    }

    private void initDots() {
        //TODO
    }

    private void initCrosses() {
        //TODO
    }

    public void onDraw() {
        for (GuitarString string: mGuitarStrings) {
            mFingeringView.drawGuitarString(string);
        }
        //TODO
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
