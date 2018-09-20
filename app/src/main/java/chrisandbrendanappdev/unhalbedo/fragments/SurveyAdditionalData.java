package chrisandbrendanappdev.unhalbedo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.ToggleButton;

import chrisandbrendanappdev.unhalbedo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyAdditionalData extends SurveyFragment {

    private Spinner depthSpin, tempSpin, weightSpin1, weightSpin2;
    private Button butNext;

    public SurveyAdditionalData() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_additional_data_fragment, container, false);

        init(v, getString(R.string.title_additional_data));

        return v;
    }

    @Override
    void getViews(View v) {
        depthSpin = (Spinner) v.findViewById(R.id.survey_additional_data_spinner_depth);
        tempSpin = (Spinner) v.findViewById(R.id.survey_additional_data_spinner_temp);
        weightSpin1 = (Spinner) v.findViewById(R.id.survey_additional_data_spinner_weight1);
        weightSpin2 = (Spinner) v.findViewById(R.id.survey_additional_data_spinner_weight2);
        butNext = (Button) v.findViewById(R.id.survey_additional_data_next);

        setupAll();
    }

    private void setupAll() {
        String[] depths = {getString(R.string.units_in), getString(R.string.units_cm)};
        String[] temps = {getString(R.string.units_F), getString(R.string.units_C)};
        String[] weights = {getString(R.string.units_lbs), getString(R.string.units_g)};
        ArrayAdapter<String> depthAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                depths
        );
        ArrayAdapter<String> tempAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                temps
        );
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                weights
        );
        depthSpin.setAdapter(depthAdapter);
        tempSpin.setAdapter(tempAdapter);
        weightSpin1.setAdapter(weightAdapter);
        weightSpin2.setAdapter(weightAdapter);
    }

    @Override
    void addOnClickListeners() {
        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataAndContinue(new SurveyNotes());
            }
        });
    }
}
