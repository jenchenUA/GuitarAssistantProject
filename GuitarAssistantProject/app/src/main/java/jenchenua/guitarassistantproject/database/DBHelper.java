package jenchenua.guitarassistantproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import jenchenua.guitarassistantproject.database.GuitarAssistantDB.ScaleEntry;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABSE_NAME = "GuitarAssistant.db";

    public DBHelper(Context context) {
        super(context, DATABSE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SCALE_TABLE = "CREATE TABLE " + ScaleEntry.TABLE_NAME + " (" +
                ScaleEntry._ID + " INTEGER PRIMARY KEY, " +
                ScaleEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                ScaleEntry.COLUMN_POSITION_1 + " BLOB NOT NULL, " +
                ScaleEntry.COLUMN_POSITION_2 + " BLOB NOT NULL, " +
                ScaleEntry.COLUMN_POSITION_3 + " BLOB NOT NULL, " +
                ScaleEntry.COLUMN_POSITION_4 + " BLOB NOT NULL, " +
                ScaleEntry.COLUMN_POSITION_5 + " BLOB NOT NULL " +
                ");";
        db.execSQL(SQL_CREATE_SCALE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ScaleEntry.TABLE_NAME);
        onCreate(db);
    }
}
