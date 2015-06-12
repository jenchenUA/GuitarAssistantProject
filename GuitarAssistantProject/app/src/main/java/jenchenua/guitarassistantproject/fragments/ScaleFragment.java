package jenchenua.guitarassistantproject.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import jenchenua.guitarassistantproject.DetailActivity;
import jenchenua.guitarassistantproject.R;
import jenchenua.guitarassistantproject.database.DBHelper;
import jenchenua.guitarassistantproject.database.FingeringDatabase.ScaleEntry;

public class ScaleFragment extends android.support.v4.app.Fragment {
    public static final String[] LIST_NAME_COLUMN_FOR_SQL_QUERY = {ScaleEntry.NAME_COLUMN};

    private List<String> scaleList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext());
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
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

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.fragment_list_item,
                R.id.card_view_textView,
                scaleList);

        View rootView = inflater.inflate(R.layout.fragment_scale, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listView_scale);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
