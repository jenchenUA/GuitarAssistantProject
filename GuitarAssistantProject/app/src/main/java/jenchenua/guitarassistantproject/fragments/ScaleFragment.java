package jenchenua.guitarassistantproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.List;

import jenchenua.guitarassistantproject.DetailActivity;
import jenchenua.guitarassistantproject.MainActivity;
import jenchenua.guitarassistantproject.R;
import jenchenua.guitarassistantproject.asynctasks.MainActivityAsyncTask;
import jenchenua.guitarassistantproject.database.FingeringDatabase;
import jenchenua.guitarassistantproject.database.FingeringDatabase.ScaleEntry;

public class ScaleFragment extends android.support.v4.app.Fragment {
    private static final String LOG_TAG = ScaleFragment.class.getSimpleName();
    private static final String SCREEN_NAME = "Scale";

    private Tracker tracker = null;

    private ArrayAdapter<String> mScaleAdapter = null;

    private MainActivityAsyncTask mainActivityAsyncTask = null;

    private List<String> scaleList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String TABLE_NAME = ScaleEntry.TABLE_NAME;
        final String COLUMN_NAME = FingeringDatabase.NAME_COLUMN;

        mainActivityAsyncTask = new MainActivityAsyncTask(getActivity().getApplicationContext());
        mainActivityAsyncTask.execute(LOG_TAG, SCREEN_NAME, TABLE_NAME, COLUMN_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scale, container, false);

        tracking();

        try {
            scaleList = mainActivityAsyncTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createListView(rootView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void createListView(View rootView) {
        mScaleAdapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.fragment_list_item,
                R.id.card_view_textView,
                scaleList);

        ListView listView = (ListView) rootView.findViewById(R.id.listView_scale);
        listView.setAdapter(mScaleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fingeringName = mScaleAdapter.getItem(position);

                tracker.send(new HitBuilders.EventBuilder()
                                .setCategory("Action")
                                .setAction("Click")
                                .setLabel(fingeringName)
                                .build()
                );

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("className", LOG_TAG);
                intent.putExtra("tableName", ScaleEntry.TABLE_NAME);
                intent.putExtra("fingeringName", fingeringName);

                startActivity(intent);
            }
        });
    }

    private void tracking() {
        tracker = MainActivity.getTracker();

        Log.i(LOG_TAG, "Set screen name: " + SCREEN_NAME);
        tracker.setScreenName(SCREEN_NAME);
    }
}
