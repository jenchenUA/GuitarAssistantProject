package jenchenua.guitarassistantproject.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jenchenua.guitarassistantproject.R;
import jenchenua.guitarassistantproject.database.DBHelper;
import jenchenua.guitarassistantproject.database.FingeringDatabase;
import jenchenua.guitarassistantproject.draw.FingeringDrawing;

public class Tab3 extends Fragment {
    private static final String[] TAB_NAME = {FingeringDatabase.BOX_3_COLUMN};

    private String fingeringName;

    private FingeringDrawing fingering = null;

    private byte[] switches;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_3, container, false);

        DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext());
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        fingeringName = getActivity().getIntent().getStringExtra("fingeringName");
        final String TABLE_NAME = getActivity().getIntent().getStringExtra("tableName");
        final String WHERE = FingeringDatabase.NAME_COLUMN + " = " + "\"" + fingeringName + "\"";

        Cursor cursor = sqLiteDatabase.query(
                TABLE_NAME,
                TAB_NAME,
                WHERE,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        switches = cursor.getBlob(cursor.getColumnIndex(FingeringDatabase.BOX_3_COLUMN));

        sqLiteDatabase.close();
        cursor.close();

        fingering = (FingeringDrawing) rootView.findViewById(R.id.fingering_drawing_tab_3);

        fingering.setSwitches(switches);

        fingering.invalidate();

        return rootView;
    }
}
