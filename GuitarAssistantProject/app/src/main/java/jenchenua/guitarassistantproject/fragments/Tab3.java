package jenchenua.guitarassistantproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jenchenua.guitarassistantproject.R;
import jenchenua.guitarassistantproject.draw.DescriptionDrawing;
import jenchenua.guitarassistantproject.draw.FingeringDrawing;

public class Tab3 extends Fragment {
    private FingeringDrawing fingering = null;
    private DescriptionDrawing description = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_3, container, false);

        fingering = (FingeringDrawing) rootView.findViewById(R.id.fingering_drawing_tab_3);
        fingering.invalidate();


        return rootView;
    }
}
