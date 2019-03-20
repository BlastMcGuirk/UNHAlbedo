package chrisandbrendanappdev.unhalbedo.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

import chrisandbrendanappdev.unhalbedo.R;
import chrisandbrendanappdev.unhalbedo.activities.SurveyActivity;
import chrisandbrendanappdev.unhalbedo.data.DataEnums;

/**
 *  Location question. This asks the user to enter their duty station, or enter their own
 *  location. To enter their own location, they can either type in the latitude and longitude,
 *  or they can press the Current Location button to have the device read the phone's current
 *  location, and automatically enter the coordinates. Clicking on the next button will bring
 *  the user to the Start Time question.
 */
public class SurveyLocation extends SurveyFragment {

    private Spinner dutyStations;
    private ArrayAdapter<DataEnums.StationID> spinnerAdapter;

    private LocationManager locMan;

    private EditText latitude, longitude;
    private double curLat, curLon;
    private Button useCurrentLocation, next;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_location_fragment, container, false);

        // Initialize the fragment according to the SurveyFragment abstract class
        init(v, getString(R.string.title_location));

        locMan = ((SurveyActivity) Objects.requireNonNull(getActivity())).getLocationManager();
        requestPermission();

        return v;
    }

    private void requestPermission() {
        // Permissions - this is a big mess, but it makes the current location work...
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()).getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        getActivity().getApplicationContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]
                            {android.Manifest.permission.ACCESS_FINE_LOCATION,
                                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    @Override
    void getViews(View v) {
        dutyStations = v.findViewById(R.id.survey_location_duty_station);

        latitude = v.findViewById(R.id.survey_location_lat);
        longitude = v.findViewById(R.id.survey_location_lon);

        useCurrentLocation = v.findViewById(R.id.survey_location_current_loc);
        next = v.findViewById(R.id.survey_location_next);

        setupSpinner();
    }

    @Override
    void addOnClickListeners() {
        dutyStations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataEnums.StationID station = spinnerAdapter.getItem(position);
                if (position != 0) {
                    String latText = Objects.requireNonNull(station).getLatitude() + "";
                    String lonText = station.getLongitude() + "";
                    latitude.setText(latText);
                    longitude.setText(lonText);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        useCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Feature not enabled yet", Toast.LENGTH_SHORT).show();
                // Check for permissions... yikes again
                if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location loc = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                String latText = loc.getLatitude() + "";
                String lonText = loc.getLongitude() + "";
                latitude.setText(latText);
                longitude.setText(lonText);
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
            String latText = data.getLatitude() + "";
            String lonText = data.getLongitude() + "";
            latitude.setText(latText);
            longitude.setText(lonText);
            dutyStations.setSelection(spinnerAdapter.getPosition(data.getStationID()));
        }
    }

    private void setupSpinner() {
        spinnerAdapter = new ArrayAdapter<>(
                Objects.requireNonNull(getActivity()),
                android.R.layout.simple_list_item_1,
                DataEnums.StationID.values()
        );
        dutyStations.setAdapter(spinnerAdapter);

        // Set default to NH-ST-99
        DataEnums.StationID defaultStation = DataEnums.StationID.NHST99;
        dutyStations.setSelection(spinnerAdapter.getPosition(defaultStation));
        curLat = defaultStation.getLatitude();
        curLon = defaultStation.getLongitude();
        String latText = curLat + "";
        String lonText = curLon + "";
        latitude.setText(latText);
        longitude.setText(lonText);
    }
}
