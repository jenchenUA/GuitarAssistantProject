package jenchenua.guitarassistantapp.fragments;

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
import android.widget.Spinner;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.List;

import jenchenua.guitarassistantapp.activity.DetailActivity;
import jenchenua.guitarassistantapp.activity.MainActivity;
import jenchenua.guitarassistantapp.R;
import jenchenua.guitarassistantapp.database.DBHelper;
import jenchenua.guitarassistantapp.database.FingeringDatabase;
import jenchenua.guitarassistantapp.database.FingeringDatabase.AEntry;
import jenchenua.guitarassistantapp.database.FingeringDatabase.BEntry;

public class ChordFragment extends Fragment {
    private static final String LOG_TAG = ChordFragment.class.getSimpleName();
    private static final String SCREEN_NAME = "Chords";

    private final String COLUMN_NAME = FingeringDatabase.NAME_COLUMN;
    private String tableName = null;

    private String[] keys = {
            AEntry.TABLE_NAME,
            BEntry.TABLE_NAME
    };

    private Tracker tracker = null;

    private ArrayAdapter<String> mChordsAdapter = null;
    private ArrayAdapter<String> spinnerAdapter = null;

    private List<String> chordsList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chords, container, false);

        tracking();

        ListView listView = (ListView) rootView.findViewById(R.id.listView_chords);
        Spinner spinner = (Spinner) rootView.findViewById(R.id.chords_spinner);

        spinnerAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                keys
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mChordsAdapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.list_item_fingering,
                R.id.listView_item_textView);

        createSpinner(spinner);

        createListView(listView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void createListView(ListView listView) {
        listView.setAdapter(mChordsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fingeringName = mChordsAdapter.getItem(position);

                tracker.send(new HitBuilders.EventBuilder()
                                .setCategory("Action")
                                .setAction("Click")
                                .setLabel(fingeringName)
                                .build()
                );

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("className", LOG_TAG);
                intent.putExtra(MainActivity.TABLE_NAME, tableName);
                intent.putExtra(MainActivity.FINGERING_NAME, fingeringName);

                startActivity(intent);
            }
        });
    }

    private void createSpinner(Spinner spinner) {
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tableName = parent.getSelectedItem().toString();

                chordsList = getChrodsListFromDB(tableName, COLUMN_NAME);

                mChordsAdapter.clear();
                mChordsAdapter.addAll(chordsList);
                mChordsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private List<String> getChrodsListFromDB(String tableName, String column) {
        DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext());
        dbHelper.setForcedUpgrade();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String[] columns = {column};

        Cursor cursor = sqLiteDatabase.query(
                tableName,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        List<String> chordsList = new ArrayList<>();

        do {
            chordsList.add(cursor.getString(cursor.getColumnIndex(column)));
        } while (cursor.moveToNext());

        cursor.close();
        sqLiteDatabase.close();
        dbHelper.close();

        return chordsList;
    }

    private void tracking() {
        tracker = MainActivity.getTracker();

        Log.i(LOG_TAG, "Set screen name: " + SCREEN_NAME);
        tracker.setScreenName(SCREEN_NAME);
    }
}
