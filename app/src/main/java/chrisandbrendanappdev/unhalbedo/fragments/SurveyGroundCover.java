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
public class SurveyGroundCover extends SurveyFragment {

    private Button grassLiving, grassDead, wetSoil, drySoil, pavement, wood, other;

    public SurveyGroundCover() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_ground_cover_fragment, container, false);

        init(v);

        getActivity().setTitle("Ground Cover");

        return v;
    }

    @Override
    void getViews(View v) {
        grassLiving = (Button) v.findViewById(R.id.survey_ground_cover_grass_living);
        grassDead = (Button) v.findViewById(R.id.survey_ground_cover_grass_dead);
        wetSoil = (Button) v.findViewById(R.id.survey_ground_cover_wet_soil);
        drySoil = (Button) v.findViewById(R.id.survey_ground_cover_dry_soil);
        pavement = (Button) v.findViewById(R.id.survey_ground_cover_pavement);
        wood = (Button) v.findViewById(R.id.survey_ground_cover_wooden_deck);
        other = (Button) v.findViewById(R.id.survey_ground_cover_other);
    }

    @Override
    void addOnClickListeners() {
        grassLiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueSurvey(DataEnums.GroundCover.GRASS_LIVING);
            }
        });
        grassDead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueSurvey(DataEnums.GroundCover.GRASS_DEAD);
            }
        });
        wetSoil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueSurvey(DataEnums.GroundCover.SOIL_WET);
            }
        });
        drySoil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueSurvey(DataEnums.GroundCover.SOIL_DRY);
            }
        });
        pavement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueSurvey(DataEnums.GroundCover.PAVEMENT);
            }
        });
        wood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueSurvey(DataEnums.GroundCover.WOODEN_DECK);
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Open up option to type the OTHER
                continueSurvey(DataEnums.GroundCover.OTHER);
            }
        });
    }

    private void continueSurvey(DataEnums.GroundCover ground) {
        data.setGroundCover(ground);
        saveDataAndContinue(new SurveyIncomingShortwaves());
    }
}
