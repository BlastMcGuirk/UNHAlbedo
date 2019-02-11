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
import android.widget.Toast;

import chrisandbrendanappdev.unhalbedo.R;
import chrisandbrendanappdev.unhalbedo.data.DataEnums;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyLocation extends SurveyFragment {

    private Spinner dutyStations;
    private ArrayAdapter<DataEnums.StationID> spinnerAdapter;

    private EditText latitude, longitude;
    private double curLat, curLon;
    private Button useCurrentLocation, next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_location_fragment, container, false);

        // Initialize the fragment according to the SurveyFragment abstract class
        init(v, getString(R.string.title_location));

        return v;
    }

    @Override
    void getViews(View v) {
        dutyStations = (Spinner) v.findViewById(R.id.survey_location_duty_station);

        latitude = (EditText) v.findViewById(R.id.survey_location_lat);
        longitude = (EditText) v.findViewById(R.id.survey_location_lon);

        useCurrentLocation = (Button) v.findViewById(R.id.survey_location_current_loc);
        next = (Button) v.findViewById(R.id.survey_location_next);

        setupSpinner();
    }

    @Override
    void addOnClickListeners() {
        dutyStations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataEnums.StationID station = spinnerAdapter.getItem(position);
                if (position != 0) {
                    latitude.setText(station.getLatitude() + "");
                    longitude.setText(station.getLongitude() + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        useCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add location services to use the person's current location
                Toast.makeText(getActivity(), "Feature not enabled yet", Toast.LENGTH_SHORT).show();
            }
        });
        latitude.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!latitude.getText().toString().equals(String.valueOf(curLat))) {
                    dutyStations.setSelection(spinnerAdapter.getPosition(DataEnums.StationID.NONE));
                }
            }
        });
        longitude.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!longitude.getText().toString().equals(String.valueOf(curLon))) {
                    dutyStations.setSelection(spinnerAdapter.getPosition(DataEnums.StationID.NONE));
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setStationID((DataEnums.StationID) dutyStations.getSelectedItem());
                data.setLatitude(Double.parseDouble(latitude.getText().toString()));
                data.setLongitude(Double.parseDouble(longitude.getText().toString()));
                saveDataAndContinue(new SurveyStartTime());
            }
        });
    }

    @Override
    void fillInEmptyValues() {
        if (data.getLatitude() != 0 || data.getLongitude() != 0) {
            latitude.setText(data.getLatitude() + "");
            longitude.setText(data.getLongitude() + "");
            dutyStations.setSelection(spinnerAdapter.getPosition(data.getStationID()));
        }
    }

    private void setupSpinner() {
        spinnerAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                DataEnums.StationID.values()
        );
        dutyStations.setAdapter(spinnerAdapter);

        // TODO: Check profile for default duty station
        // Temporarily set default to NH-ST-99
        DataEnums.StationID defaultStation = DataEnums.StationID.NHST99;
        dutyStations.setSelection(spinnerAdapter.getPosition(defaultStation));
        curLat = defaultStation.getLatitude();
        curLon = defaultStation.getLongitude();
        latitude.setText(curLat + "");
        longitude.setText(curLon + "");
    }
}
