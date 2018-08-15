package chrisandbrendanappdev.unhalbedo.fragments;

import android.support.v4.app.Fragment;
import android.view.View;

import chrisandbrendanappdev.unhalbedo.R;
import chrisandbrendanappdev.unhalbedo.activities.DataProvider;
import chrisandbrendanappdev.unhalbedo.data.DataSubmission;

/**
 *  Abstract class for each question in the survey
 */

abstract class SurveyFragment extends Fragment {

    DataProvider dataProvider;
    DataSubmission data;

    public void init(View v) {
        getData();
        getViews(v);
        fillInEmptyValues();
        addOnClickListeners();
    }

    private void getData() {
        dataProvider = (DataProvider) getActivity();
        data = dataProvider.getData();
    }

    abstract void getViews(View v);
    abstract void addOnClickListeners();

    void fillInEmptyValues() {
        // Do nothing unless needed
    }

    void saveDataAndContinue(SurveyFragment nextFragment) {
        dataProvider.setData(data);
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                                     R.anim.enter_from_left,  R.anim.exit_to_right)
                .replace(R.id.frag_view, nextFragment)
                .addToBackStack(null)
                .commit();
    }

}
