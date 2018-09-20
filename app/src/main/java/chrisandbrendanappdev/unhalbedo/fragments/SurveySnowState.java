package chrisandbrendanappdev.unhalbedo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import chrisandbrendanappdev.unhalbedo.R;
import chrisandbrendanappdev.unhalbedo.data.DataEnums;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveySnowState extends SurveyFragment {

    private Button snow, patchy, dormant, green;

    public SurveySnowState() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_snow_state_fragment, container, false);

        init(v, getString(R.string.title_snow_state));

        return v;
    }

    @Override
    void getViews(View v) {
        snow = (Button) v.findViewById(R.id.survey_snow_state_snow_covered);
        patchy = (Button) v.findViewById(R.id.survey_snow_state_patchy_snow);
        dormant = (Button) v.findViewById(R.id.survey_snow_state_snow_free_dormant);
        green = (Button) v.findViewById(R.id.survey_snow_state_snow_free_green);
    }

    @Override
    void addOnClickListeners() {
        snow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setPatchinessPercentage(100);
                continueSurvey(DataEnums.SnowState.SNOW_COVERED, new SurveySnowSurfaceAge());
            }
        });
        patchy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueSurvey(DataEnums.SnowState.PATCHY_SNOW, new SurveyPatchinessPercentage());
            }
        });
        dormant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setPatchinessPercentage(0);
                continueSurvey(DataEnums.SnowState.SNOW_FREE_DORMANT, new SurveyGroundCover());
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setPatchinessPercentage(0);
                continueSurvey(DataEnums.SnowState.SNOW_FREE_GREEN, new SurveyGroundCover());
            }
        });
    }

    private void continueSurvey(DataEnums.SnowState state, SurveyFragment fragment) {
        data.setSnowState(state);
        saveDataAndContinue(fragment);
    }

}
