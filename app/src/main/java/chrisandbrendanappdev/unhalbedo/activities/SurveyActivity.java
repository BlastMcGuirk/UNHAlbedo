package chrisandbrendanappdev.unhalbedo.activities;

import android.content.Context;
import android.location.LocationManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import chrisandbrendanappdev.unhalbedo.R;
import chrisandbrendanappdev.unhalbedo.data.DataSubmission;
import chrisandbrendanappdev.unhalbedo.fragments.SurveyLocation;

/**
 *  Survey activity is the activity that manages the survey questions and submission.
 *  Each survey question is placed in a Fragment loaded in to this activity. The activity
 *  keeps track of the submission using a DataSubmission object and follows the DataProvider
 *  interface for saving/exchanging the data. The LocationManager is for using current
 *  location in the location survey question.
 */

public class SurveyActivity extends AppCompatActivity implements DataProvider {

    // Tracks survey answers
    private DataSubmission data;

    // Used for current location
    private LocationManager locMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        // Initialize survey answers
        data = new DataSubmission();

        locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Location is the first survey question
        // Load the fragment in to the activity
        SurveyLocation surveyStart = new SurveyLocation();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frag_view, surveyStart);
        ft.commit();
    }

    public DataSubmission getData() {
        return data;
    }

    public void setData(DataSubmission savedData) {
        this.data = savedData;
    }

    public LocationManager getLocationManager() { return locMan; }
}