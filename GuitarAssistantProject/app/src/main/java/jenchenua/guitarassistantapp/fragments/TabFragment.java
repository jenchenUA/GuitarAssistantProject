package jenchenua.guitarassistantapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import jenchenua.guitarassistantapp.R;
import jenchenua.guitarassistantapp.activity.DetailActivity;
import jenchenua.guitarassistantapp.draw.DrawManager;
import jenchenua.guitarassistantapp.draw.FingeringView;

public class TabFragment extends Fragment {
    private static final String LOG_TAG = TabFragment.class.getSimpleName();
    private static final String FORMULA_TAG = "Formula";
    private static final String POSITION_TAG = "Position";
    private static String sScreenName;
    private Tracker tracker;
    private String fingeringName;

    public static TabFragment newInstance(String columnName, String formula, String position) {
        TabFragment tab = new TabFragment();

        Bundle args = new Bundle();
        args.putString(FORMULA_TAG, formula);
        args.putString(POSITION_TAG, position);
        tab.setArguments(args);
        sScreenName = columnName;

        return tab;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        String formula = getArguments().getString(FORMULA_TAG);
        String position = getArguments().getString(POSITION_TAG);
        FingeringView fingeringView = (FingeringView) rootView.findViewById(R.id.fingering_view);
        DrawManager drawManager = new DrawManager(
                fingeringView,
                fingeringView.getScreenWidth(),
                formula,
                position
        );
        fingeringView.setDrawManager(drawManager);
        fingeringView.redraw();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        tracker = DetailActivity.getTracker();
        Log.i(LOG_TAG, "Set screen name: " + sScreenName + " - " + fingeringName);
        tracker.setScreenName(fingeringName + ": " + sScreenName);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
