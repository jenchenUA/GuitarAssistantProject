package jenchenua.guitarassistantproject.fragments;



import android.content.Intent;
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

public class ScaleFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
}
