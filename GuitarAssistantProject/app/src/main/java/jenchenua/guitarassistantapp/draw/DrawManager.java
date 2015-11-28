package jenchenua.guitarassistantapp.draw;

import android.util.DisplayMetrics;

public class DrawManager {
    private FingeringView mFingeringView;
    private static int sWidth;
    private static int sHeight;

    public DrawManager(FingeringView fingeringView, int width, int height) {
        this.mFingeringView = fingeringView;
        sWidth = width;
        sHeight = height;
    }

    public void onDraw() {
        //TODO
    }

    private int dipToPixels(float dipValue) {
        final float scale = mFingeringView.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
