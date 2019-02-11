package chrisandbrendanappdev.unhalbedo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import chrisandbrendanappdev.unhalbedo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyNotes extends SurveyFragment {

    private EditText notes;
    private Button butNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_notes_fragment, container, false);

        init(v, getString(R.string.title_notes));

        return v;
    }

    @Override
    void getViews(View v) {
        notes = (EditText) v.findViewById(R.id.survey_notes_notes);
        butNext = (Button) v.findViewById(R.id.survey_notes_next);
    }

    @Override
    void addOnClickListeners() {
        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setNotes(notes.getText().toString());
                saveDataAndContinue(new SurveyEndTime());
            }
        });
    }

    @Override
    void fillInEmptyValues() {
        if (data.getNotes() != "") {
            notes.setText(data.getNotes());
        }
    }
}
