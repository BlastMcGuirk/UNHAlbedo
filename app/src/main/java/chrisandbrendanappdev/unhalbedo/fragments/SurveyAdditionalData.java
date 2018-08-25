package chrisandbrendanappdev.unhalbedo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chrisandbrendanappdev.unhalbedo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyAdditionalData extends SurveyFragment {


    public SurveyAdditionalData() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_additional_data_fragment, container, false);

        init(v);

        return v;
    }

    @Override
    void getViews(View v) {

    }

    @Override
    void addOnClickListeners() {

    }
}
