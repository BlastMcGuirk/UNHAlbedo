package chrisandbrendanappdev.unhalbedo.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import chrisandbrendanappdev.unhalbedo.R;
import chrisandbrendanappdev.unhalbedo.data.DataEnums;

/**
 *  Ground cover question. Multiple buttons with images. Once the user clicks on the button
 *  that matches their ground cover, the survey will continue with IncomingShortwaves.
 */
public class SurveyGroundCover extends SurveyFragment {

    private Button grassLiving, grassDead, wetSoil, drySoil, pavement, wood, other;

    public SurveyGroundCover() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_ground_cover_fragment, container, false);

        init(v, getString(R.string.title_ground_cover));

        return v;
    }

    @Override
    void getViews(View v) {
        grassLiving = v.findViewById(R.id.survey_ground_cover_grass_living);
        grassDead = v.findViewById(R.id.survey_ground_cover_grass_dead);
        wetSoil = v.findViewById(R.id.survey_ground_cover_wet_soil);
        drySoil = v.findViewById(R.id.survey_ground_cover_dry_soil);
        pavement = v.findViewById(R.id.survey_ground_cover_pavement);
        wood = v.findViewById(R.id.survey_ground_cover_wooden_deck);
        other = v.findViewById(R.id.survey_ground_cover_other);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Enter Other Ground Type");

                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DataEnums.GroundCover ground = DataEnums.GroundCover.OTHER;
                        ground.setOtherType(input.getText().toString());
                        data.setGroundCover(ground);
                        saveDataAndContinue(new SurveyIncomingShortwaves());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    private void continueSurvey(DataEnums.GroundCover ground) {
        data.setGroundCover(ground);
        saveDataAndContinue(new SurveyIncomingShortwaves());
    }
}
