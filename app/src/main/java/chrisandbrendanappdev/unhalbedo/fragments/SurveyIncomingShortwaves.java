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
public class SurveyIncomingShortwaves extends SurveyFragment {

    private EditText in1, in2, in3;
    private double cur1, cur2, cur3;
    private Button butNext;

    public SurveyIncomingShortwaves() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_incoming_shortwaves_fragment, container, false);

        init(v);

        getActivity().setTitle("Incoming Shortwaves");

        return v;
    }

    @Override
    void getViews(View v) {
        in1 = (EditText) v.findViewById(R.id.survey_incoming_shortwave_1);
        in2 = (EditText) v.findViewById(R.id.survey_incoming_shortwave_2);
        in3 = (EditText) v.findViewById(R.id.survey_incoming_shortwave_3);
        butNext = (Button) v.findViewById(R.id.survey_incoming_shortwave_next);
    }

    @Override
    void addOnClickListeners() {
        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entriesAreValid()) {
                    data.setIncoming1(Double.parseDouble(in1.getText().toString()));
                    data.setIncoming2(Double.parseDouble(in2.getText().toString()));
                    data.setIncoming3(Double.parseDouble(in3.getText().toString()));
                    saveDataAndContinue(new SurveyOutgoingShortwaves());
                }
            }
        });
    }

    @Override
    void fillInEmptyValues() {
        cur1 = data.getIncoming1();
        if (cur1 != -999) {
            in1.setText(cur1 + "");
        }

        cur2 = data.getIncoming2();
        if (cur2 != -999) {
            in2.setText(cur2 + "");
        }

        cur3 = data.getIncoming3();
        if (cur3 != -999) {
            in3.setText(cur3 + "");
        }
    }

    private boolean entriesAreValid() {
        boolean allValid = true;

        try {
            double i1 = Double.parseDouble(in1.getText().toString());
            if (i1 <= 0) throw new ArithmeticException();
        } catch (NumberFormatException e) {
            allValid = false;
            in1.setError("Must enter a number");
        } catch (ArithmeticException e) {
            allValid = false;
            in1.setError("Number doesn't make sense");
        }

        try {
            double i2 = Double.parseDouble(in2.getText().toString());
            if (i2 <= 0) throw new ArithmeticException();
        } catch (NumberFormatException e) {
            allValid = false;
            in2.setError("Must enter a number");
        } catch (ArithmeticException e) {
            allValid = false;
            in2.setError("Number doesn't make sense");
        }

        try {
            double i3 = Double.parseDouble(in3.getText().toString());
            if (i3 <= 0) throw new ArithmeticException();
        } catch (NumberFormatException e) {
            allValid = false;
            in3.setError("Must enter a number");
        } catch (ArithmeticException e) {
            allValid = false;
            in3.setError("Number doesn't make sense");
        }

        return allValid;
    }

    private View.OnFocusChangeListener dataCheck = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus)
                entriesAreValid();
        }
    };
}
