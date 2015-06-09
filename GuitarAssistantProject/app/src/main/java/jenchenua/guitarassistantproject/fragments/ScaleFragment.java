package jenchenua.guitarassistantproject.fragments;



import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jenchenua.guitarassistantproject.DetailActivity;
import jenchenua.guitarassistantproject.R;
import jenchenua.guitarassistantproject.database.DBHelper;
import jenchenua.guitarassistantproject.database.GuitarAssistantDB;

public class ScaleFragment extends android.support.v4.app.Fragment {
    private String name = "Minor";

    private byte[] position_1 = {
            0, 2, 0, 1, 1,
            0, 1, 1, 0, 1,
            1, 1, 0, 1, 0,
            0, 1, 0, 2, 0,
            0, 1, 0, 1, 1,
            0, 2, 0, 1, 1
    };

    private byte[] position_2 = {
            0, 1, 1, 0, 1,
            0, 0, 1, 0, 2,
            0, 1, 0, 1, 1,
            0, 2, 0, 1, 1,
            0, 1, 1, 0, 1,
            0, 1, 1, 0, 1
    };

    private byte[] position_3 = {
            0, 1, 0, 1, 1,
            0, 2, 0, 1, 1,
            1, 1, 0, 1, 0,
            1, 1, 0, 1, 0,
            0, 1, 0, 2, 0,
            0, 1, 0, 1, 1
    };

    private byte[] position_4 = {
            0, 1, 1, 0, 1,
            0, 1, 1, 0, 1,
            0, 1, 0, 2, 0,
            0, 1, 0, 1, 1,
            0, 2, 0, 1, 1,
            0, 1, 1, 0, 1
    };

    private byte[] position_5 = {
            0, 1, 0, 2, 0,
            0, 1, 0, 1, 1,
            2, 0, 1, 1, 0,
            1, 1, 0, 1, 0,
            1, 1, 0, 1, 0,
            0, 1, 0, 2, 0
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        testDB(getActivity());

        String[] fakeData = {"Major", "Minor", "Something", "Don't trust goat"};

        List<String> fakeList = new ArrayList<>(Arrays.asList(fakeData));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.fragment_list_item,
                R.id.card_view_textView,
                fakeList);

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

    private void testDB(Context context) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("position_1", position_1);
        cv.put("position_2", position_2);
        cv.put("position_3", position_3);
        cv.put("position_4", position_4);
        cv.put("position_5", position_5);

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.insert(GuitarAssistantDB.ScaleEntry.TABLE_NAME, null, cv);

        db.close();
    }
}
