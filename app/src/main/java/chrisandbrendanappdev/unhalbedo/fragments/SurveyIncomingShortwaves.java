package chrisandbrendanappdev.unhalbedo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import chrisandbrendanappdev.unhalbedo.R;

/**
 *  Incoming shortwave question. This question asks the user to enter the incoming shortwave
 *  values recorded from the light sensor. Currently, due to the fact that the camera isn't
 *  implemented, the user must enter the values they got from the light sensor they would
 *  normally bring with them to the field. After the values are entered, they are checked for
 *  validity, and users are brought to the next question, outgoing shortwaves.
 */
public class SurveyIncomingShortwaves extends SurveyFragment {

    private EditText in1, in2, in3;
    private Button butNext;

    public SurveyIncomingShortwaves() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_incoming_shortwaves_fragment, container, false);

        init(v, getString(R.string.title_incoming_shortwaves));

        return v;
    }

    @Override
    void getViews(View v) {
        in1 = v.findViewById(R.id.survey_incoming_shortwave_1);
        in2 = v.findViewById(R.id.survey_incoming_shortwave_2);
        in3 = v.findViewById(R.id.survey_incoming_shortwave_3);
        butNext = v.findViewById(R.id.survey_incoming_shortwave_next);
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
        double cur1 = data.getIncoming1();
        if (cur1 != -999) {
            String text = "" + cur1;
            in1.setText(text);
        }

        double cur2 = data.getIncoming2();
        if (cur2 != -999) {
            String text = "" + cur2;
            in2.setText(text);
        }

        double cur3 = data.getIncoming3();
        if (cur3 != -999) {
            String text = "" + cur3;
            in3.setText(text);
        }
    }

    /**
     * Checks each value for validity. First, it checks that the value is filled in, then it
     * checks if the number makes sense (> 0).
     *
     * @return boolean  true if all entries are valid
     */
    private boolean entriesAreValid() {
        boolean allValid = true;

        // Check first value
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

        // Check second value
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

        // Check third value
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
}
