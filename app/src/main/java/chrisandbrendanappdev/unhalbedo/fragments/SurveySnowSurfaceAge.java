package chrisandbrendanappdev.unhalbedo.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.Objects;

import chrisandbrendanappdev.unhalbedo.R;
import chrisandbrendanappdev.unhalbedo.data.DataEnums;

/**
 *  Snow Surface Age asks how fresh the snow is. The user can choose from a Spinner of multiple
 *  options, ranging from <24 hours to 4+ weeks. After, the user is brought to the Ground Cover
 *  question.
 */
public class SurveySnowSurfaceAge extends SurveyFragment {

    private Spinner age;
    ArrayAdapter<DataEnums.SnowSurfaceAge> spinnerAdapter;

    private CheckBox snowMelt;

    private Button butNext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_snow_surface_age_fragment, container, false);

        init(v, getString(R.string.title_snow_surface_age));

        return v;
    }

    @Override
    void getViews(View v) {
        age = v.findViewById(R.id.survey_snow_surface_age_spinner);
        snowMelt = v.findViewById(R.id.survey_snow_surface_age_melt);
        butNext = v.findViewById(R.id.survey_snow_surface_age_next);

        setupSpinner();
    }

    @Override
    void addOnClickListeners() {
        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setSnowSurfaceAge((DataEnums.SnowSurfaceAge) age.getSelectedItem());
                data.setSnowMelt(snowMelt.isActivated());
                saveDataAndContinue(new SurveyGroundCover());
            }
        });
    }

    private void setupSpinner() {
        spinnerAdapter = new ArrayAdapter<>(
                Objects.requireNonNull(getActivity()),
                android.R.layout.simple_list_item_1,
                DataEnums.SnowSurfaceAge.values()
        );
        age.setAdapter(spinnerAdapter);
    }

    @Override
    void fillInEmptyValues() {
        if (data.getSnowSurfaceAge() != null) {
            age.setSelection(spinnerAdapter.getPosition(data.getSnowSurfaceAge()));
        }
        if (data.isSnowMelt()) {
            snowMelt.setChecked(true);
        }
    }
}
