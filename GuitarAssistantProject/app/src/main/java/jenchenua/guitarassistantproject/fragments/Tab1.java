package jenchenua.guitarassistantproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jenchenua.guitarassistantproject.R;
import jenchenua.guitarassistantproject.database.GuitarAssistantDB;
import jenchenua.guitarassistantproject.draw.FingeringDrawing;


public class Tab1 extends Fragment {
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
        View rootView = inflater.inflate(R.layout.tab_1, container, false);
        fingering = (FingeringDrawing) rootView.findViewById(R.id.fingering_drawing_tab_1);

        fingering.setSwitches(switches);

        fingering.invalidate();

        return rootView;
    }
}
