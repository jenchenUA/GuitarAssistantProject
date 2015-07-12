package jenchenua.guitarassistantproject.asynctasks;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import jenchenua.guitarassistantproject.database.DBHelper;

public class TabsAsyncTask extends AsyncTask<String, Void, byte[]> {
    private DBHelper dbHelper = null;
    private SQLiteDatabase sqLiteDatabase = null;
    private Cursor cursor = null;

    private byte[] switches = null;

    private Context context = null;

    public TabsAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getReadableDatabase();
    }

    @Override
    protected byte[] doInBackground(String... params) {
        Log.i(params[0], params[1] + ": AsyncTask started.");

        final String[] TAB_NAME = {params[3]};

        cursor = sqLiteDatabase.query(
                params[2],
                TAB_NAME,
                params[4],
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        switches = cursor.getBlob(cursor.getColumnIndex(params[3]));

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
