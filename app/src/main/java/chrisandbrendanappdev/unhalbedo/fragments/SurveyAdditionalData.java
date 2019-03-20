package chrisandbrendanappdev.unhalbedo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Objects;

import chrisandbrendanappdev.unhalbedo.R;

/**
 *  Fragment for the survey. Asks the user for additional data, including snow depth, temp,
 *  and weight. The user may specify if they would like to use imperial or metric units.
 *  Once the user hits next, the next question will be allowing them to enter notes about the
 *  observation.
 */
public class SurveyAdditionalData extends SurveyFragment {

    private EditText depth, temp, weight1, weight2;
    // Selects the units
    private Spinner depthSpin, tempSpin, weightSpin1, weightSpin2;
    private boolean depthMetric, tempMetric, weightMetric;
    private Button butNext;

    public SurveyAdditionalData() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_additional_data_fragment, container, false);

        init(v, getString(R.string.title_additional_data));

        return v;
    }

    @Override
    void getViews(View v) {
        depth = v.findViewById(R.id.survey_additional_data_snow_depth);
        temp = v.findViewById(R.id.survey_additional_data_snow_temperature);
        weight1 = v.findViewById(R.id.survey_additional_data_weight_no_snow);
        weight2 = v.findViewById(R.id.survey_additional_data_weight_snow);

        depthSpin = v.findViewById(R.id.survey_additional_data_spinner_depth);
        tempSpin = v.findViewById(R.id.survey_additional_data_spinner_temp);
        weightSpin1 = v.findViewById(R.id.survey_additional_data_spinner_weight1);
        weightSpin2 = v.findViewById(R.id.survey_additional_data_spinner_weight2);

        butNext = v.findViewById(R.id.survey_additional_data_next);

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
        ArrayAdapter<String> depthAdapter = new ArrayAdapter<>(
                Objects.requireNonNull(getActivity()),
                android.R.layout.simple_list_item_1,
                depths
        );
        ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                temps
        );
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(
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
                weightMetric = position == 1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        weightSpin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                weightSpin1.setSelection(position, true);
                weightMetric = position == 1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        tempSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tempMetric = position == 1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        depthSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                depthMetric = position == 1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
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
                        data.setMetricDepth(depthMetric);
                    }
                    if (!temp.getText().toString().equals("")) {
                        data.setTemperature(Double.parseDouble(temp.getText().toString()));
                        data.setMetricTemp(tempMetric);
                    }
                    if (!weight1.getText().toString().equals("")) {
                        data.setSnowTubeWeight(Double.parseDouble(weight1.getText().toString()));
                        data.setSnowWeightWithTube(Double.parseDouble(weight2.getText().toString()));
                        data.setMetricWeight(weightMetric);
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
