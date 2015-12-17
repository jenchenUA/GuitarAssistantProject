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

public class TabFragment extends Fragment {
    private static final String LOG_TAG = TabFragment.class.getSimpleName();
    private static final String COLUMN_NAME_TAG = "columnName";
    private static String sScreenName;
    private TabsAsyncTask tabsAsyncTask = null;
    private Tracker tracker = null;
    private String fingeringName = null;
    private byte[] switches = null;

    public static TabFragment newInstance(String columnName) {
        TabFragment tab = new TabFragment();
        Bundle args = new Bundle();
        args.putString(COLUMN_NAME_TAG, columnName);
        tab.setArguments(args);
        sScreenName = columnName;

        return tab;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fingeringName = getActivity().getIntent().getStringExtra("fingeringName");

        final String TABLE_NAME = getActivity().getIntent().getStringExtra("tableName");
        final String COLUMN_NAME = getArguments().getString(COLUMN_NAME_TAG);
        final String WHERE = FingeringDatabase.NAME_COLUMN + " = " + "\"" + fingeringName + "\"";

        tabsAsyncTask = new TabsAsyncTask(getActivity().getApplicationContext());
        tabsAsyncTask.execute(LOG_TAG, sScreenName, TABLE_NAME, COLUMN_NAME, WHERE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        try {
            switches = tabsAsyncTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tracker = DetailActivity.getTracker();

        Log.i(LOG_TAG, "Set screen name: " + sScreenName + " - " + fingeringName);
        tracker.setScreenName(sScreenName + ": " + fingeringName);

        draw(rootView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void draw(View rootView) {
        FingeringDrawing fingering = (FingeringDrawing) rootView.findViewById(R.id.fingering_drawing);
        fingering.setSwitches(switches);
        fingering.setClassName(getActivity().getIntent().getStringExtra("className"));
        fingering.invalidate();
    }
}
