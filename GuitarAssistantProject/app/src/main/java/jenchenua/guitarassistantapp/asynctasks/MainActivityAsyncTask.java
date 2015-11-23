package jenchenua.guitarassistantapp.asynctasks;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import jenchenua.guitarassistantapp.database.DBHelper;
import jenchenua.guitarassistantapp.database.FingeringDatabase;

public class MainActivityAsyncTask extends AsyncTask<String, Void, List<String>> {
    private DBHelper dbHelper = null;
    private SQLiteDatabase sqLiteDatabase = null;
    private Cursor cursor = null;

    private List<String> list = null;

    private Context context = null;

    public MainActivityAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dbHelper = new DBHelper(context);
        dbHelper.setForcedUpgrade();
        sqLiteDatabase = dbHelper.getReadableDatabase();
    }

    @Override
    protected List<String> doInBackground(String... params) {
        Log.i(params[0], params[1] + ": AsyncTask started.");

        final String[] COLUMN_NAMES = {params[3]};

        cursor = sqLiteDatabase.query(
                params[2],
                COLUMN_NAMES,
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        list = new ArrayList<>();

        do {
            list.add(cursor.getString(cursor.getColumnIndex(FingeringDatabase.ScaleEntry.NAME_COLUMN)));
        } while (cursor.moveToNext());

        return list;
    }

    @Override
    protected void onPostExecute(List<String> list) {
        super.onPostExecute(list);

        cursor.close();
        sqLiteDatabase.close();
        dbHelper.close();
    }
}
