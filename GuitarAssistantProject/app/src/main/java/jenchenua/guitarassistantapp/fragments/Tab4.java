package jenchenua.guitarassistantapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import jenchenua.guitarassistantapp.activity.DetailActivity;
import jenchenua.guitarassistantapp.R;
import jenchenua.guitarassistantapp.asynctasks.TabsAsyncTask;
import jenchenua.guitarassistantapp.database.FingeringDatabase;
import jenchenua.guitarassistantapp.draw.FingeringDrawing;

public class Tab4 extends Fragment {
    private static final String LOG_TAG = Tab4.class.getSimpleName();
    private static final String SCREEN_NAME = "Box 4";

    private Tracker tracker = null;

    private String fingeringName = null;

    private byte[] switches = null;

    private TabsAsyncTask tabsAsyncTask = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fingeringName = getActivity().getIntent().getStringExtra("fingeringName");

        final String TABLE_NAME = getActivity().getIntent().getStringExtra("tableName");
        final String TAB_NAME = FingeringDatabase.BOX_4_COLUMN;
        final String WHERE = FingeringDatabase.NAME_COLUMN + " = " + "\"" + fingeringName + "\"";

        tabsAsyncTask = new TabsAsyncTask(getActivity().getApplicationContext());
        tabsAsyncTask.execute(LOG_TAG, SCREEN_NAME, TABLE_NAME, TAB_NAME, WHERE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_4, container, false);

        try {
            switches = tabsAsyncTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        tracker = DetailActivity.getTracker();

        Log.i(LOG_TAG, "Set screen name: " + SCREEN_NAME + " - " + fingeringName);
        tracker.setScreenName(SCREEN_NAME + ": " + fingeringName);

        draw(rootView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void draw(View rootView) {
        FingeringDrawing fingering = (FingeringDrawing) rootView.findViewById(R.id.fingering_drawing_tab_4);

        fingering.setSwitches(switches);
        fingering.setClassName(getActivity().getIntent().getStringExtra("className"));

        fingering.invalidate();
    }
}