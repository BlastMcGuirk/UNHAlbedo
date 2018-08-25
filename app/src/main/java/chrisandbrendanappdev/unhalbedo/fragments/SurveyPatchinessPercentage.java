package chrisandbrendanappdev.unhalbedo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import chrisandbrendanappdev.unhalbedo.R;
import chrisandbrendanappdev.unhalbedo.data.DataEnums;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyPatchinessPercentage extends SurveyFragment {

    private SeekBar slider;
    private TextView valueDisplay;
    private Button butNext;

    public SurveyPatchinessPercentage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_patchiness_percentage_fragment, container, false);

        init(v);

        return v;
    }

    @Override
    void getViews(View v) {
        slider = (SeekBar) v.findViewById(R.id.survey_patchiness_percentage_slider);
        valueDisplay = (TextView) v.findViewById(R.id.survey_patchiness_percentage_value);
        butNext = (Button) v.findViewById(R.id.survey_patchiness_percentage_next);
    }

    @Override
    void addOnClickListeners() {
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valueDisplay.setText(progress + "% Covered");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                data.setPatchinessPercentage(seekBar.getProgress());
            }
        });
        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slider.getProgress() == 100) { data.setSnowState(DataEnums.SnowState.SNOW_COVERED); }
                else if (slider.getProgress() == 0) { data.setSnowState(DataEnums.SnowState.SNOW_FREE_GREEN); }
                saveDataAndContinue(new SurveyGroundCover());
            }
        });
    }

    @Override
    public void fillInEmptyValues() {
        if (data.getPatchinessPercentage() != -999) {
            slider.setProgress(data.getPatchinessPercentage());
        }
        valueDisplay.setText(slider.getProgress() + "% Covered");
    }
}
