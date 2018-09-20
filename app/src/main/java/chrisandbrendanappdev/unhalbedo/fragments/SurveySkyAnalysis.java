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
public class SurveySkyAnalysis extends SurveyFragment {

    private Button butACLR, butCLR, butPCL, butOVC;

    public SurveySkyAnalysis() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_sky_analysis_fragment, container, false);

        init(v, getString(R.string.title_sky_analysis));

        return v;
    }

    @Override
    void getViews(View v) {
        butACLR = (Button) v.findViewById(R.id.survey_sky_analysis_ACLR);
        butCLR = (Button) v.findViewById(R.id.survey_sky_analysis_CLR);
        butPCL = (Button) v.findViewById(R.id.survey_sky_analysis_PCL);
        butOVC = (Button) v.findViewById(R.id.survey_sky_analysis_OVC);
    }

    @Override
    void addOnClickListeners() {
        butACLR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueSurvey(DataEnums.CloudCover.ACLR);
            }
        });
        butCLR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueSurvey(DataEnums.CloudCover.CLR);
            }
        });
        butPCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueSurvey(DataEnums.CloudCover.PCL);
            }
        });
        butOVC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueSurvey(DataEnums.CloudCover.OVC);
            }
        });
    }

    private void continueSurvey(DataEnums.CloudCover cover) {
        data.setCloudCoverage(cover);
        saveDataAndContinue(new SurveySnowState());
    }
}
