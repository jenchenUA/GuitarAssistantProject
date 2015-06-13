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

public class Tab4 extends Fragment {
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;

    private static final String[] TAB_NAME = {FingeringDatabase.BOX_4_COLUMN};

    private String fingeringName;

    private FingeringDrawing fingering = null;

    private byte[] switches;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_4, container, false);

        getSwitchesFromDB();
        draw(rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cursor.close();
        sqLiteDatabase.close();
        dbHelper.close();
    }

    private void draw(View rootView) {
        fingering = (FingeringDrawing) rootView.findViewById(R.id.fingering_drawing_tab_4);

        fingering.setSwitches(switches);

        fingering.invalidate();
    }

    private void getSwitchesFromDB() {
        dbHelper = new DBHelper(getActivity().getApplicationContext());
        sqLiteDatabase = dbHelper.getReadableDatabase();

        fingeringName = getActivity().getIntent().getStringExtra("fingeringName");
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
        switches = cursor.getBlob(cursor.getColumnIndex(FingeringDatabase.BOX_4_COLUMN));
    }
}
