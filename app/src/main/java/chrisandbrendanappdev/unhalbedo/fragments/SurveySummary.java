package chrisandbrendanappdev.unhalbedo.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Objects;

import chrisandbrendanappdev.unhalbedo.R;
import chrisandbrendanappdev.unhalbedo.data.DataEnums;
import chrisandbrendanappdev.unhalbedo.httprequests.PostRequest;

/**
 *  A summary of the Survey. Here, all the values the user selected or entered are displayed
 *  so they can review it and make necessary changes if needed. Once the user is satisfied
 *  with the data they entered, they can press submit to send the data to the server. On a
 *  successful submission, they will be returned to the main activity page, and their entry
 *  will appear on the main page. If the submission fails, a Toast will appear telling the
 *  user what is wrong.
 */
public class SurveySummary extends SurveyFragment {

    private TextView stationID, latitude, longitude;
    private TextView date, startTime, endTime;
    private TextView incoming, outgoing, albedo;
    private TextView sky, snowState, groundCover, surfaceAge, snowMelt;
    private TextView depth, temp, weight;
    private TextView notes;
    private Button subButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_summary_fragment, container, false);

        init(v, getString(R.string.title_summary));

        return v;
    }

    @Override
    void getViews(View v) {
        stationID = v.findViewById(R.id.summary_stationID);
        latitude = v.findViewById(R.id.summary_latitude);
        longitude = v.findViewById(R.id.summary_longitude);

        date = v.findViewById(R.id.summary_date);
        startTime = v.findViewById(R.id.summary_start_time);
        endTime = v.findViewById(R.id.summary_end_time);

        incoming = v.findViewById(R.id.summary_incoming);
        outgoing = v.findViewById(R.id.summary_outgoing);
        albedo = v.findViewById(R.id.summary_albedo);

        sky = v.findViewById(R.id.summary_sky);
        snowState = v.findViewById(R.id.summary_snow_state);
        groundCover = v.findViewById(R.id.summary_ground_cover);
        surfaceAge = v.findViewById(R.id.summary_surface_age);
        snowMelt = v.findViewById(R.id.summary_snow_melt);

        depth = v.findViewById(R.id.summary_depth);
        temp = v.findViewById(R.id.summary_temp);
        weight = v.findViewById(R.id.summary_weight);

        notes = v.findViewById(R.id.summary_notes);

        subButton = v.findViewById(R.id.survey_summary_submit);
    }

    @Override
    void fillInEmptyValues() {
        stationID.append(" " + data.getStationID().toString());
        latitude.append(" " + String.valueOf(data.getLatitude()));
        longitude.append(" " + String.valueOf(data.getLongitude()));

        date.append(" " + data.getDate());
        startTime.append(" " + data.getStartTime());
        endTime.append(" " + data.getEndTime());

        incoming.append(" " + data.getIncoming1() + " - " + data.getIncoming2() + " - " + data.getIncoming3());
        outgoing.append(" " + data.getOutgoing1() + " - " + data.getOutgoing2() + " - " + data.getOutgoing3());
        albedo.append(" " + String.valueOf(data.getAlbedo()));

        sky.append(" " + data.getCloudCoverage().toString());
        snowState.append(" " + data.getSnowState().toString());
        if (data.getSnowState() == DataEnums.SnowState.PATCHY_SNOW) {
            snowState.append("\nPatchiness %: " + data.getPatchinessPercentage() + "%");
        }
        groundCover.append(" " + data.getGroundCover().toString());
        surfaceAge.append(" " + data.getSnowSurfaceAge().toString());
        snowMelt.append(" " + (data.isSnowMelt() ? "Y" : "N"));

        depth.append(String.valueOf(" " + (data.getSnowDepth() == -999 ? "N/A" : data.getSnowDepth())));
        temp.append(String.valueOf(" " + (data.getTemperature() == -999 ? "N/A" : data.getTemperature())));
        String weightstr = (data.getSnowWeightWithTube() == -999 || data.getSnowTubeWeight() == -999) ? "N/A" :
                        String.valueOf(data.getSnowWeightWithTube() - data.getSnowTubeWeight());
        weight.append(" " + weightstr);

        notes.append(data.getNotes());
    }

    @Override
    void addOnClickListeners() {
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences(getString(R.string.username), Context.MODE_PRIVATE);
                JSONObject sendData = data.getJSON(sp.getString(getString(R.string.username), ""), sp.getString(getString(R.string.token), ""));

                boolean res = PostRequest.submitData(sendData, sp.getString(getString(R.string.token), ""));
                if (!res) {
                    Toast.makeText(getActivity(), "Could not submit data", Toast.LENGTH_LONG).show();
                } else {
                    getActivity().finish();
                    Toast.makeText(getActivity(), "Submitted data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
