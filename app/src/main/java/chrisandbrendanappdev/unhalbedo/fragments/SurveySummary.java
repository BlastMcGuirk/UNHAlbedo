package chrisandbrendanappdev.unhalbedo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import chrisandbrendanappdev.unhalbedo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveySummary extends SurveyFragment {

    private Button subButton;

    public SurveySummary() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_summary_fragment, container, false);

        init(v);

        getActivity().setTitle("Summary");

        return v;
    }

    @Override
    void getViews(View v) {
        subButton = (Button) v.findViewById(R.id.survey_summary_submit);
    }

    @Override
    void addOnClickListeners() {
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Send data to server
                getActivity().finish();
            }
        });
    }
}
