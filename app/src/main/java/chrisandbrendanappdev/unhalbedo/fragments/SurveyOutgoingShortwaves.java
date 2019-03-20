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
 *  Outgoing shortwave question. This question asks the user to enter the outgoing shortwave
 *  values recorded from the light sensor. Currently, due to the fact that the camera isn't
 *  implemented, the user must enter the values they got from the light sensor they would
 *  normally bring with them to the field. After the values are entered, they are checked for
 *  validity, including comparing them to the incoming shortwave values, and users are brought
 *  to the next question, Additional Data.
 */
public class SurveyOutgoingShortwaves extends SurveyFragment {

    private EditText out1, out2, out3;
    private Button butNext;

    public SurveyOutgoingShortwaves() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_outgoing_shortwaves_fragment, container, false);

        init(v, getString(R.string.title_outgoing_shortwaves));

        return v;
    }

    @Override
    void getViews(View v) {
        out1 = v.findViewById(R.id.survey_outgoing_shortwave_1);
        out2 = v.findViewById(R.id.survey_outgoing_shortwave_2);
        out3 = v.findViewById(R.id.survey_outgoing_shortwave_3);
        butNext = v.findViewById(R.id.survey_outgoing_shortwave_next);
    }

    @Override
    void addOnClickListeners() {
        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entriesAreValid()) {
                    data.setOutgoing1(Double.parseDouble(out1.getText().toString()));
                    data.setOutgoing2(Double.parseDouble(out2.getText().toString()));
                    data.setOutgoing3(Double.parseDouble(out3.getText().toString()));
                    saveDataAndContinue(new SurveyAdditionalData());
                }
            }
        });
    }

    @Override
    void fillInEmptyValues() {
        double o1, o2, o3;

        o1 = data.getOutgoing1();
        if (o1 != -999) {
            String o1Text = o1 + "";
            out1.setText(o1Text);
        }

        o2 = data.getOutgoing2();
        if (o2 != -999) {
            String o2Text = o2 + "";
            out2.setText(o2Text);
        }

        o3 = data.getOutgoing3();
        if (o3 != -999) {
            String o3Text = o3 + "";
            out3.setText(o3Text);
        }
    }

    /**
     * Checks each value for validity. First, it checks that the value is filled in, then it
     * checks if the number makes sense (> 0), then that it is less than it's paired incoming
     * value (meaning the the first incoming value is compared to the first outgoing value).
     *
     * @return boolean  true if all entries are valid
     */
    private boolean entriesAreValid() {
        boolean allValid = true;

        // Check first value
        try {
            double o1 = Double.parseDouble(out1.getText().toString());
            double i1 = data.getIncoming1();
            if (o1 <= 0) throw new ArithmeticException();
            else if (o1 >= i1) throw new IllegalArgumentException();
        } catch (NumberFormatException e) {
            allValid = false;
            out1.setError("Must enter a number");
        } catch (ArithmeticException e) {
            allValid = false;
            out1.setError("Number doesn't make sense");
        } catch (IllegalArgumentException e) {
            allValid = false;
            out1.setError("Incoming must be greater than outgoing");
        }

        // Check second value
        try {
            double o2 = Double.parseDouble(out2.getText().toString());
            double i2 = data.getIncoming2();
            if (o2 <= 0) throw new ArithmeticException();
            else if (o2 >= i2) throw new IllegalArgumentException();
        } catch (NumberFormatException e) {
            allValid = false;
            out2.setError("Must enter a number");
        } catch (ArithmeticException e) {
            allValid = false;
            out2.setError("Number doesn't make sense");
        } catch (IllegalArgumentException e) {
            allValid = false;
            out2.setError("Incoming must be greater than outgoing");
        }

        // Check third value
        try {
            double o3 = Double.parseDouble(out3.getText().toString());
            double i3 = data.getIncoming3();
            if (o3 <= 0) throw new ArithmeticException();
            else if (o3 >= i3) throw new IllegalArgumentException();
        } catch (NumberFormatException e) {
            allValid = false;
            out3.setError("Must enter a number");
        } catch (ArithmeticException e) {
            allValid = false;
            out3.setError("Number doesn't make sense");
        } catch (IllegalArgumentException e) {
            allValid = false;
            out3.setError("Incoming must be greater than outgoing");
        }

        return allValid;
    }
}
