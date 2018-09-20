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
public class SurveyOutgoingShortwaves extends SurveyFragment {

    private EditText out1, out2, out3;
    private Button butNext;

    public SurveyOutgoingShortwaves() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_outgoing_shortwaves_fragment, container, false);

        init(v, getString(R.string.title_outgoing_shortwaves));

        return v;
    }

    @Override
    void getViews(View v) {
        out1 = (EditText) v.findViewById(R.id.survey_outgoing_shortwave_1);
        out2 = (EditText) v.findViewById(R.id.survey_outgoing_shortwave_2);
        out3 = (EditText) v.findViewById(R.id.survey_outgoing_shortwave_3);
        butNext = (Button) v.findViewById(R.id.survey_outgoing_shortwave_next);
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
            out1.setText(o1 + "");
        }

        o2 = data.getOutgoing2();
        if (o2 != -999) {
            out2.setText(o2 + "");
        }

        o3 = data.getOutgoing3();
        if (o3 != -999) {
            out3.setText(o3 + "");
        }
    }

    private boolean entriesAreValid() {
        boolean allValid = true;

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
