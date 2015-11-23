package jenchenua.guitarassistantproject.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import jenchenua.guitarassistantproject.database.FingeringDatabase.PentatonicEntry;

public class PentatonicFragment extends Fragment {
    private static final String LOG_TAG = PentatonicFragment.class.getSimpleName();

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;

    public static final String[] LIST_NAME_COLUMN_FOR_SQL_QUERY = {PentatonicEntry.NAME_COLUMN};

    private ArrayAdapter<String> mPentatonicAdapter;

    private List<String> pentatonicList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pentatonic, container, false);

        getPentatonicListFromDB();

        createListView(rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        cursor.close();
        sqLiteDatabase.close();
        dbHelper.close();
    }

    private void createListView(View rootView) {
        mPentatonicAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.fragment_list_item,
                R.id.card_view_textView,
                pentatonicList);

        ListView listView = (ListView) rootView.findViewById(R.id.listView_pentatonic);
        listView.setAdapter(mPentatonicAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fingeringName = mPentatonicAdapter.getItem(position);

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("tableName", PentatonicEntry.TABLE_NAME);
                intent.putExtra("fingeringName", fingeringName);

                startActivity(intent);
            }
        });
    }

    private void getPentatonicListFromDB() {
        dbHelper = new DBHelper(getActivity().getApplicationContext());
        sqLiteDatabase = dbHelper.getReadableDatabase();

        cursor = sqLiteDatabase.query(
                PentatonicEntry.TABLE_NAME,
                LIST_NAME_COLUMN_FOR_SQL_QUERY,
                null,
                null,
                null,
                null,
                null
        );

        pentatonicList = new ArrayList<>();

        cursor.moveToFirst();

        do {
            pentatonicList.add(cursor.getString(cursor.getColumnIndex(PentatonicEntry.NAME_COLUMN)));
        } while (cursor.moveToNext());
    }
}
