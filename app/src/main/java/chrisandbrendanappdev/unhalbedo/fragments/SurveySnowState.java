package chrisandbrendanappdev.unhalbedo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import chrisandbrendanappdev.unhalbedo.R;
import chrisandbrendanappdev.unhalbedo.data.DataEnums;

/**
 *  Snow State is the amount of snow on the ground. This can range from Snow covered, patchy,
 *  or snow free. The users select these from buttons with images showing what the ground would
 *  look like with the snow on it. After the user selects one of the values, they are
 *  automatically brought to one of three possible survey questions. If the user says the ground
 *  is fully covered in snow, they will be brought to the Snow Surface Age question, and the
 *  patchiness will be set to 100%. If the user says the ground is patchy, they will be brought
 *  to the Patchiness Percentage question to choose the percentage there. If the user says the
 *  ground is snow free, they are brought to the Ground Cover question.
 */
public class SurveySnowState extends SurveyFragment {

    private Button snow, patchy, dormant, green;

    public SurveySnowState() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_snow_state_fragment, container, false);

        init(v, getString(R.string.title_snow_state));

        return v;
    }

    @Override
    void getViews(View v) {
        snow = v.findViewById(R.id.survey_snow_state_snow_covered);
        patchy = v.findViewById(R.id.survey_snow_state_patchy_snow);
        dormant = v.findViewById(R.id.survey_snow_state_snow_free_dormant);
        green = v.findViewById(R.id.survey_snow_state_snow_free_green);
    }

    @Override
    void addOnClickListeners() {
        snow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set patchiness to 100% and go to Snow Surface Age question
                data.setPatchinessPercentage(100);
                continueSurvey(DataEnums.SnowState.SNOW_COVERED, new SurveySnowSurfaceAge());
            }
        });
        patchy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Patchiness Percentage question
                continueSurvey(DataEnums.SnowState.PATCHY_SNOW, new SurveyPatchinessPercentage());
            }
        });
        dormant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set patchiness to 0% and go to Snow Surface Age question
                data.setPatchinessPercentage(0);
                data.setSnowMelt(true);
                data.setSnowSurfaceAge(DataEnums.SnowSurfaceAge.FOUR_PLUS_WEEKS);
                continueSurvey(DataEnums.SnowState.SNOW_FREE_DORMANT, new SurveySnowSurfaceAge());
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set patchiness to 0% and go to Snow Surface Age question
                data.setPatchinessPercentage(0);
                data.setSnowMelt(true);
                data.setSnowSurfaceAge(DataEnums.SnowSurfaceAge.FOUR_PLUS_WEEKS);
                continueSurvey(DataEnums.SnowState.SNOW_FREE_GREEN, new SurveySnowSurfaceAge());
            }
        });
    }

    private void continueSurvey(DataEnums.SnowState state, SurveyFragment fragment) {
        data.setSnowState(state);
        saveDataAndContinue(fragment);
    }

}
