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
    private static int sWidth;
    private static int sStartFret;
    private static int sStopFret;
    private static float sFretInterval;
    private boolean[] mDrawSwitches;
    private boolean[] mRootSwitches;
    private String[] standardTune = {"E", "B", "G", "D", "A", "E"};
    private String[] keys = {"A", "A#/Bb", "B", "C", "C#/Db", "D", "D#/Eb", "E", "F", "F#/Gb", "G", "G#/Ab"};
    private FingeringView mFingeringView;
    private ArrayList<GuitarString> mGuitarStrings;
    private ArrayList<Fret> mFrets;
    private ArrayList<Dot> mDots;
    private ArrayList<Cross> mCrosses;

    public DrawManager(FingeringView fingeringView, int width, String formula, String position) {
        this.mFingeringView = fingeringView;
        sWidth = width;
        parsePosition(position);
        initGuitarStrings();
        initFrets(sStopFret - sStartFret + 1);
        initSwitches(formula);
        initDotsAndCrosses();
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
        /*for (Cross cross: mCrosses) {
            mFingeringView.drawCross(cross);
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
        final float stringInterval = getDimension(R.dimen.stings_interval);
        mGuitarStrings = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_STRING; i++) {
            mGuitarStrings.add(new GuitarString(startStringX, startStringY, stopStringX, stopStringY, color));
            startStringY += stringInterval;
            stopStringY += stringInterval + 1;
        }
    }

    @SuppressWarnings("deprecation")
    private void initFrets(int numberOfFrets) {
        final float fretWidth = getDimension(R.dimen.fret_width);
        float startFretX = getDimension(R.dimen.activity_horizontal_margin);
        float stopFretX = startFretX + fretWidth;
        final float startFretY = getDimension(R.dimen.activity_vertical_margin) +
                getDimension(R.dimen.dot_radius);
        final float stopFretY = mGuitarStrings.get(NUMBER_OF_STRING - 1).getStopY();
        final int color = mFingeringView.getResources().getColor(R.color.gridColor);
        sFretInterval = (mGuitarStrings.get(0).getLenght() - fretWidth) / numberOfFrets;
        mFrets = new ArrayList<>();
        for (int i = 0; i <= numberOfFrets; i++) {
            mFrets.add(new Fret(startFretX, startFretY, stopFretX, stopFretY, color));
            startFretX += sFretInterval;
            stopFretX += sFretInterval;
        }
    }

    @SuppressWarnings("deprecation")
    private void initDotsAndCrosses() {
        mDots = new ArrayList<>();
        mCrosses = new ArrayList<>();
        float radius = getDimension(R.dimen.dot_radius);
        float strokeWidth = getDimension(R.dimen.stroke_width);
        int colorDot = mFingeringView.getResources().getColor(R.color.dotColor);
        int colorDotRoot = mFingeringView.getResources().getColor(R.color.rootDotsColor);
        int colorCross = mFingeringView.getResources().getColor(R.color.crossColor);
        int i = 0;
        int j = 0;
        for (GuitarString string: mGuitarStrings) {
            for (Fret fret: mFrets) {
                float x = fret.getStopX() + ((sFretInterval - fret.getWidth()) / 2);
                float y = string.getStartY() + (string.getWidth() / 2);
                if (x < string.getStopX()) {
                    if (mDrawSwitches[i++]) {
                        if (mRootSwitches[j]) {
                            mDots.add(new Dot(x, y, radius, colorDotRoot));
                        } else {
                            mDots.add(new Dot(x, y, radius, colorDot));
                        }
                    }
                    j++;
                    mCrosses.add(new Cross(x, y, radius / 2, strokeWidth, colorCross));
                }
            }
        }
    }

    private void initSwitches(String formula) {
        ArrayList<String[]> strings = new ArrayList<>();
        for (String key: standardTune) {
            strings.add(createStringAccordingKey(key));
        }
        String[] fingeringKeys = formula.split(" ");
        mDrawSwitches = createDrawSwitches(strings, fingeringKeys);
        mRootSwitches = createRootNoteSwitches(strings, fingeringKeys[0]);
    }

    private void parsePosition(String position) {
        String[] positions = position.split(":");
        sStartFret = Integer.parseInt(positions[0]);
        sStopFret = Integer.parseInt(positions[1]);
    }

    private boolean[] createDrawSwitches(ArrayList<String[]> strings, String[] fingeringKeys) {
        String rootKey = fingeringKeys[0];
        boolean[] drawSwitches = new boolean[NUMBER_OF_STRING * (mFrets.size() - 1)];
        int j = 0;
        for (String[] string: strings) {
            for (int i = sStartFret; i <= sStopFret; i++, j++) {
                for (String key: fingeringKeys) {
                    if (key.length() > 1) {
                        if (string[i].contains(key)) {
                            drawSwitches[j] = true;
                            break;
                        }
                    } else {
                        if (string[i].equals(key)) {
                            drawSwitches[j] = true;
                            break;
                        }
                    }
                }
            }
        }
        return drawSwitches;
    }
    
    private boolean[] createRootNoteSwitches(ArrayList<String[]> strings, String tonicNote) {
        boolean[] tonicSwitches = new boolean[NUMBER_OF_STRING * (mFrets.size() - 1)];
        int j = 0;
        for (String[] string: strings) {
            for (int i = sStartFret; i <= sStopFret; i++, j++) {
                if (tonicNote.length() > 1) {
                    if (string[i].contains(tonicNote)) {
                        tonicSwitches[j] = true;
                    }
                } else {
                    if (string[i].equals(tonicNote)) {
                        tonicSwitches[j] = true;
                    }
                }
            }
        }
        return tonicSwitches;
    }

    private String[] createStringAccordingKey(String key) {
        int index = 0;
        for (int i = 0; i < keys.length; i++) {
            if (key.equals(keys[i])) {
                index = i;
                break;
            }
        }
        String[] string = new String[23];
        for (int i = 0; i < string.length; i++) {
            string[i] = keys[index++];
            if (index == keys.length) {
                index = 0;
            }
        }
        return string;
    }
}

