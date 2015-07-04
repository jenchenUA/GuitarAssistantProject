package jenchenua.guitarassistantproject.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;
import java.util.List;

import jenchenua.guitarassistantproject.DetailActivity;
import jenchenua.guitarassistantproject.MainActivity;
import jenchenua.guitarassistantproject.R;
import jenchenua.guitarassistantproject.database.DBHelper;
import jenchenua.guitarassistantproject.database.FingeringDatabase.ScaleEntry;

public class ScaleFragment extends android.support.v4.app.Fragment {
    private static final String LOG_TAG = ScaleFragment.class.getSimpleName();
    private static final String SCREEN_NAME = "Scale";

    private Tracker tracker;

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;

    public static final String[] LIST_NAME_COLUMN_FOR_SQL_QUERY = {ScaleEntry.NAME_COLUMN};

    private ArrayAdapter<String> mScaleAdapter;

    private List<String> scaleList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scale, container, false);

        tracker = MainActivity.getTracker();

        Log.i(LOG_TAG, "Set screen name: " + SCREEN_NAME);
        tracker.setScreenName(SCREEN_NAME);

        getScaleListFromDB();

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
        mScaleAdapter = new ArrayAdapter<String>(
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

    private void getScaleListFromDB() {
        dbHelper = new DBHelper(getActivity().getApplicationContext());
        dbHelper.setForcedUpgrade();
        sqLiteDatabase = dbHelper.getReadableDatabase();

        cursor = sqLiteDatabase.query(
                ScaleEntry.TABLE_NAME,
                LIST_NAME_COLUMN_FOR_SQL_QUERY,
                null,
                null,
                null,
                null,
                null
        );

        scaleList = new ArrayList<>();

        cursor.moveToFirst();

        do {
            scaleList.add(cursor.getString(cursor.getColumnIndex(ScaleEntry.NAME_COLUMN)));
        } while (cursor.moveToNext());
    }
}
