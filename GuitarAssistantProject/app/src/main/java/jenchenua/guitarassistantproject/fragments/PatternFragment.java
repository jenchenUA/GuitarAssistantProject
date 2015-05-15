package jenchenua.guitarassistantproject.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jenchenua.guitarassistantproject.R;


public class PatternFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String[] fakeData = {"Don't touch this", "You do mistake", "Wow, aliens???", "Don't trust goat"};

        List<String> fakeList = new ArrayList<>(Arrays.asList(fakeData));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.fragment_list_item,
                R.id.card_view_textView,
                fakeList);

        View rootView = inflater.inflate(R.layout.fragment_pattern, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listView_pattern);
        listView.setAdapter(arrayAdapter);

        return rootView;
    }
}
