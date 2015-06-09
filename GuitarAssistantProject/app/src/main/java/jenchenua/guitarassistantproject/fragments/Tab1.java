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
import jenchenua.guitarassistantproject.database.GuitarAssistantDB;
import jenchenua.guitarassistantproject.draw.FingeringDrawing;


public class Tab1 extends Fragment {
    private FingeringDrawing fingering = null;
    private byte[] switches/* = {
            0, 2, 0, 1, 1,
            0, 1, 1, 0, 1,
            1, 1, 0, 1, 0,
            0, 1, 0, 2, 0,
            0, 1, 0, 1, 1,
            0, 2, 0, 1, 1
    }*/;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_1, container, false);

        DBHelper dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(GuitarAssistantDB.ScaleEntry.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int index = cursor.getColumnIndex("position_1");
            do {
               switches = cursor.getBlob(index);
            } while (cursor.moveToNext());
        }


        fingering = (FingeringDrawing) rootView.findViewById(R.id.fingering_drawing_tab_1);

        fingering.setSwitches(switches);

        fingering.invalidate();

        return rootView;
    }
}
