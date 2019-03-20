package chrisandbrendanappdev.unhalbedo.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import chrisandbrendanappdev.unhalbedo.R;
import chrisandbrendanappdev.unhalbedo.data.DataEnums;

/**
 *  Patchiness percentage. This question asks the user the describe the patchiness of the snow
 *  on the ground by using a slider in intervals of 10. The user is able to select 0% and 100%,
 *  but their answer to the previous question about Snow State will be changed accordingly.
 *
 */
public class SurveyPatchinessPercentage extends SurveyFragment {

    private SeekBar slider;
    private TextView valueDisplay;
    private Button butNext;

    public SurveyPatchinessPercentage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_patchiness_percentage_fragment, container, false);

        init(v, getString(R.string.title_patchiness_percentage));

        return v;
    }

    @Override
    void getViews(View v) {
        slider = v.findViewById(R.id.survey_patchiness_percentage_slider);
        valueDisplay = v.findViewById(R.id.survey_patchiness_percentage_value);
        butNext = v.findViewById(R.id.survey_patchiness_percentage_next);
    }

    @Override
    void addOnClickListeners() {
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int dispProg = progress + 5;
                dispProg /= 10;
                dispProg *= 10;
                String progText = dispProg + "% Covered";
                valueDisplay.setText(progText);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int dispProg = seekBar.getProgress() + 5;
                dispProg /= 10;
                dispProg *= 10;
                data.setPatchinessPercentage(dispProg);
            }
        });
        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slider.getProgress() == 0) {
                    data.setSnowState(DataEnums.SnowState.SNOW_FREE_GREEN);
                    saveDataAndContinue(new SurveyGroundCover());
                }
                else {
                    if (slider.getProgress() == 100) {
                        data.setSnowState(DataEnums.SnowState.SNOW_COVERED);
                    }
                    saveDataAndContinue(new SurveySnowSurfaceAge());
                }
            }
        });
    }

    @Override
    public void fillInEmptyValues() {
        if (data.getPatchinessPercentage() != -999) {
            slider.setProgress(data.getPatchinessPercentage());
        }
        String coverText = slider.getProgress() + "% Covered";
        valueDisplay.setText(coverText);
    }
}
