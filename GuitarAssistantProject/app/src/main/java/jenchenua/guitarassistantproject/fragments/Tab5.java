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
import jenchenua.guitarassistantproject.draw.FingeringDrawing;

public class Tab5 extends Fragment {
    private static final String TABLE_NAME = "Scale";
    private static final String[] MINOR = {"box_5"};
    private static final String WHERE = "name = \"Aeolian (Minor)\"";

    private FingeringDrawing fingering = null;
    private byte[] switches = {
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_5, container, false);

        DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext());
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, MINOR, WHERE, null, null, null, null);


        fingering = (FingeringDrawing) rootView.findViewById(R.id.fingering_drawing_tab_5);

        fingering.setSwitches(switches);

        fingering.invalidate();

        return rootView;
    }
}
