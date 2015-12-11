package jenchenua.guitarassistantapp.draw;

import java.util.ArrayList;

import jenchenua.guitarassistantapp.R;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.Cross;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.Dot;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.Fret;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.GuitarString;

public class DrawManager {
    public static final String LOG_TAG = DrawManager.class.getSimpleName();

    private static final int NUMBER_OF_STRING = 6;
    private static final int NUMBER_OF_FRET = 4;
    private static int sWidth;
    private static int sHeight;
    private static float sStringInterval;
    private static float sFretInterval;
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
        /*if (!mCrosses.isEmpty()) {
            for (Cross cross: mCrosses) {
                mFingeringView.drawCross(cross);
            }
        }*/
    }

    private float getDimension(int resource) {
        return mFingeringView.getResources().getDimension(resource);
    }

    @SuppressWarnings("deprecation")
    private void initGuitarStrings() {
        final float startStringX = getDimension(R.dimen.activity_horizontal_margin);
        final float stopStringX = sWidth - getDimension(R.dimen.activity_horizontal_margin);
        float startStringY = getDimension(R.dimen.activity_vertical_margin) +
                getDimension(R.dimen.dot_radius);
        float stopStringY = startStringY + getDimension(R.dimen.stings_width);
        final int color = mFingeringView.getResources().getColor(R.color.gridColor);
        sStringInterval = getDimension(R.dimen.stings_interval);
        mGuitarStrings = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_STRING; i++) {
            mGuitarStrings.add(new GuitarString(startStringX, startStringY, stopStringX, stopStringY, color));
            startStringY += sStringInterval;
            stopStringY += sStringInterval + 1;
        }
    }

    @SuppressWarnings("deprecation")
    private void initFrets() {
        final float fretWidth = getDimension(R.dimen.fret_width);
        float startFretX = getDimension(R.dimen.activity_horizontal_margin);
        float stopFretX = startFretX + fretWidth;
        final float startFretY = getDimension(R.dimen.activity_vertical_margin) +
                getDimension(R.dimen.dot_radius);
        final float stopFretY = mGuitarStrings.get(NUMBER_OF_STRING - 1).getStopY();
        final int color = mFingeringView.getResources().getColor(R.color.gridColor);
        sFretInterval = (mGuitarStrings.get(0).getLenght() - fretWidth) / NUMBER_OF_FRET;
        mFrets = new ArrayList<>();
        for (int i = 0; i <= NUMBER_OF_FRET; i++) {
            mFrets.add(new Fret(startFretX, startFretY, stopFretX, stopFretY, color));
            startFretX += sFretInterval;
            stopFretX += sFretInterval;
        }
    }

    @SuppressWarnings("deprecation")
    private void initDots() {
        mDots = new ArrayList<>();
        float radius = getDimension(R.dimen.dot_radius);
        int color = mFingeringView.getResources().getColor(R.color.dotColor);
        for (GuitarString string: mGuitarStrings) {
            for (Fret fret: mFrets) {
                float x = fret.getStopX() + ((sFretInterval - fret.getWidth()) / 2);
                float y = string.getStartY() + (string.getWidth() / 2);
                if (x < string.getStopX()) {
                    mDots.add(new Dot(x, y, radius, color));
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void initCrosses() {
        //TODO: Implement this method
    }
}
