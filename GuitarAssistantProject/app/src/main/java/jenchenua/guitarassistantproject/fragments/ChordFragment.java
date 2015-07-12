package jenchenua.guitarassistantproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import jenchenua.guitarassistantproject.database.FingeringDatabase.ChordsEntry;

public class ChordFragment extends Fragment {
    private static final String LOG_TAG = ChordFragment.class.getSimpleName();
    private static final String SCREEN_NAME = "Chords";

    private Tracker tracker = null;

    private ArrayAdapter<String> mPentatonicAdapter = null;

    private List<String> chordsList = null;

    private MainActivityAsyncTask mainActivityAsyncTask = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String TABLE_NAME = ChordsEntry.TABLE_NAME;
        final String COLUMN_NAME = ChordsEntry.NAME_COLUMN;

        mainActivityAsyncTask = new MainActivityAsyncTask(getActivity().getApplicationContext());
        mainActivityAsyncTask.execute(LOG_TAG, SCREEN_NAME, TABLE_NAME, COLUMN_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pentatonic, container, false);

        tracker = MainActivity.getTracker();

        Log.i(LOG_TAG, "Set screen name: " + SCREEN_NAME);
        tracker.setScreenName(SCREEN_NAME);

        try {
            chordsList = mainActivityAsyncTask.get();
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
        mPentatonicAdapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.fragment_list_item,
                R.id.card_view_textView,
                chordsList);

        ListView listView = (ListView) rootView.findViewById(R.id.listView_pentatonic);
        listView.setAdapter(mPentatonicAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fingeringName = mPentatonicAdapter.getItem(position);

                tracker.send(new HitBuilders.EventBuilder()
                                .setCategory("Action")
                                .setAction("Click")
                                .setLabel(fingeringName)
                                .build()
                );

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("className", LOG_TAG);
                intent.putExtra("tableName", ChordsEntry.TABLE_NAME);
                intent.putExtra("fingeringName", fingeringName);

                startActivity(intent);
            }
        });
    }
}
