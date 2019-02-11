package chrisandbrendanappdev.unhalbedo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import chrisandbrendanappdev.unhalbedo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyAdditionalData extends SurveyFragment {

    private EditText depth, temp, weight1, weight2;
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
        depth = (EditText) v.findViewById(R.id.survey_additional_data_snow_depth);
        temp = (EditText) v.findViewById(R.id.survey_additional_data_snow_temperature);
        weight1 = (EditText) v.findViewById(R.id.survey_additional_data_weight_no_snow);
        weight2 = (EditText) v.findViewById(R.id.survey_additional_data_weight_snow);

        depthSpin = (Spinner) v.findViewById(R.id.survey_additional_data_spinner_depth);
        tempSpin = (Spinner) v.findViewById(R.id.survey_additional_data_spinner_temp);
        weightSpin1 = (Spinner) v.findViewById(R.id.survey_additional_data_spinner_weight1);
        weightSpin2 = (Spinner) v.findViewById(R.id.survey_additional_data_spinner_weight2);

        butNext = (Button) v.findViewById(R.id.survey_additional_data_next);

        setupAll();
    }

    @Override
    void fillInEmptyValues() {
        if (data.getSnowDepth() != -999) {
            depth.setText(String.valueOf(data.getSnowDepth()));
        }
        if (data.getTemperature() != -999) {
            temp.setText(String.valueOf(data.getTemperature()));
        }
        if (data.getSnowTubeWeight() != -999) {
            weight1.setText(String.valueOf(data.getSnowTubeWeight()));
            weight2.setText(String.valueOf(data.getSnowWeightWithTube()));
        }
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

        weightSpin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                weightSpin2.setSelection(position, true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        weightSpin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                weightSpin1.setSelection(position, true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    void addOnClickListeners() {
        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allEntriesValid()) {
                    if (!depth.getText().toString().equals("")) {
                        data.setSnowDepth(Double.parseDouble(depth.getText().toString()));
                    }
                    if (!temp.getText().toString().equals("")) {
                        data.setTemperature(Double.parseDouble(temp.getText().toString()));
                    }
                    if (!weight1.getText().toString().equals("")) {
                        data.setSnowTubeWeight(Double.parseDouble(weight1.getText().toString()));
                        data.setSnowWeightWithTube(Double.parseDouble(weight2.getText().toString()));
                    }
                    saveDataAndContinue(new SurveyNotes());
                }
            }
        });
    }

    private boolean allEntriesValid() {
        weight1.setError(null);
        weight2.setError(null);
        boolean valid = true;
        if (!weight1.getText().toString().equals("")) {
            if (weight2.getText().toString().equals("")) {
                valid = false;
                weight2.setError("Must include weight");
            } else {
                if (Double.parseDouble(weight1.getText().toString()) > Double.parseDouble(weight2.getText().toString())) {
                    valid = false;
                    weight1.setError("Snow weight cannot be negative");
                    weight2.setError("Snow weight cannot be negative");
                }
            }
        } else {
            if (!weight2.getText().toString().equals("")) {
                valid = false;
                weight1.setError("Must include weight");
            }
        }

        return valid;
    }
}
