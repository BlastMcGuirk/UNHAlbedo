package chrisandbrendanappdev.unhalbedo.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import chrisandbrendanappdev.unhalbedo.R;
import chrisandbrendanappdev.unhalbedo.data.DataSubmission;
import chrisandbrendanappdev.unhalbedo.fragments.SurveyLocation;

public class SurveyActivity extends AppCompatActivity implements DataProvider {

    private DataSubmission data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        data = new DataSubmission();

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
}