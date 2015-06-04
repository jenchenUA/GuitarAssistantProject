package jenchenua.guitarassistantproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jenchenua.guitarassistantproject.R;
import jenchenua.guitarassistantproject.draw.FingeringDrawing;

public class Tab4 extends Fragment {
    private FingeringDrawing fingering = null;
    private boolean[] switches = {
            false, true, true, false, true,
            false, true, false, true, true,
            false, true, false, true, true,
            false, true, false, true, false,
            false, true, true, false, true,
            false, true, true, false, true
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_4, container, false);

        fingering = (FingeringDrawing) rootView.findViewById(R.id.fingering_drawing_tab_4);

        fingering.setSwitches(switches);

        fingering.invalidate();

        return rootView;
    }
}
