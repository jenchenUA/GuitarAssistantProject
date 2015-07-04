package jenchenua.guitarassistantproject.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;
import java.util.List;

import jenchenua.guitarassistantproject.DetailActivity;
import jenchenua.guitarassistantproject.MainActivity;
import jenchenua.guitarassistantproject.R;
import jenchenua.guitarassistantproject.database.DBHelper;
import jenchenua.guitarassistantproject.database.FingeringDatabase.ChordsEntry;

public class ChordFragment extends Fragment {
    private static final String LOG_TAG = ChordFragment.class.getSimpleName();
    private static final String SCREEN_NAME = "Chords";

    private Tracker tracker;

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;

    public static final String[] LIST_NAME_COLUMN_FOR_SQL_QUERY = {ChordsEntry.NAME_COLUMN};

    private ArrayAdapter<String> mPentatonicAdapter;

    private List<String> chordsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pentatonic, container, false);

        tracker = MainActivity.getTracker();

        Log.i(LOG_TAG, "Set screen name: " + SCREEN_NAME);
        tracker.setScreenName(SCREEN_NAME);

        getPentatonicListFromDB();

        createListView(rootView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        cursor.close();
        sqLiteDatabase.close();
        dbHelper.close();
    }

    private void createListView(View rootView) {
        mPentatonicAdapter = new ArrayAdapter<String>(
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

    private void getPentatonicListFromDB() {
        dbHelper = new DBHelper(getActivity().getApplicationContext());
        sqLiteDatabase = dbHelper.getReadableDatabase();

        cursor = sqLiteDatabase.query(
                ChordsEntry.TABLE_NAME,
                LIST_NAME_COLUMN_FOR_SQL_QUERY,
                null,
                null,
                null,
                null,
                null
        );

        chordsList = new ArrayList<>();

        cursor.moveToFirst();

        do {
            chordsList.add(cursor.getString(cursor.getColumnIndex(ChordsEntry.NAME_COLUMN)));
        } while (cursor.moveToNext());
    }
}
