package jenchenua.guitarassistantproject.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import jenchenua.guitarassistantproject.DetailActivity;
import jenchenua.guitarassistantproject.R;
import jenchenua.guitarassistantproject.database.DBHelper;
import jenchenua.guitarassistantproject.database.FingeringDatabase;
import jenchenua.guitarassistantproject.draw.FingeringDrawing;

public class Tab2 extends Fragment {
    private static final String LOG_TAG = Tab2.class.getSimpleName();
    private static final String SCREEN_NAME = "Box 2";

    private Tracker tracker = null;

    private static final String[] TAB_NAME = {FingeringDatabase.BOX_2_COLUMN};

    private String fingeringName = null;

    private FingeringDrawing fingering = null;

    private byte[] switches = null;

    private View rootView = null;

    private SQLiteAsyncTask SQLiteAsyncTask = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fingeringName = getActivity().getIntent().getStringExtra("fingeringName");

        SQLiteAsyncTask = new SQLiteAsyncTask();
        SQLiteAsyncTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab_2, container, false);

        try {
            switches = SQLiteAsyncTask.get();
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
        fingering = (FingeringDrawing) rootView.findViewById(R.id.fingering_drawing_tab_2);

        fingering.setSwitches(switches);
        fingering.setClassName(getActivity().getIntent().getStringExtra("className"));

        fingering.invalidate();
    }

    private class SQLiteAsyncTask extends AsyncTask<Void, Void, byte[]> {
        private DBHelper dbHelper;
        private SQLiteDatabase sqLiteDatabase;
        private Cursor cursor;

        private byte[] switches;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dbHelper = new DBHelper(getActivity().getApplicationContext());
            sqLiteDatabase = dbHelper.getReadableDatabase();
        }

        @Override
        protected byte[] doInBackground(Void... params) {
            Log.i(LOG_TAG, SCREEN_NAME + ": AsyncTask started.");

            final String TABLE_NAME = getActivity().getIntent().getStringExtra("tableName");
            final String WHERE = FingeringDatabase.NAME_COLUMN + " = " + "\"" + fingeringName + "\"";

            cursor = sqLiteDatabase.query(
                    TABLE_NAME,
                    TAB_NAME,
                    WHERE,
                    null,
                    null,
                    null,
                    null
            );

            cursor.moveToFirst();
            switches = cursor.getBlob(cursor.getColumnIndex(FingeringDatabase.BOX_2_COLUMN));

            return switches;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);

            cursor.close();
            sqLiteDatabase.close();
            dbHelper.close();
        }
    }
}
