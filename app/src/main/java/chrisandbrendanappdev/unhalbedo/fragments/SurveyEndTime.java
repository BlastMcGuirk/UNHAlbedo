package chrisandbrendanappdev.unhalbedo.fragments;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import chrisandbrendanappdev.unhalbedo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyEndTime extends SurveyFragment {

    private Calendar calendar;
    private SimpleDateFormat timeFormat;

    private TextView time;
    private Button setTime, next;

    public SurveyEndTime() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_end_time_fragment, container, false);

        calendar = Calendar.getInstance();
        timeFormat = new SimpleDateFormat("h:mm aa", Locale.US);

        init(v, getString(R.string.title_end_time));

        return v;
    }

    @Override
    void getViews(View v) {
        time = (TextView) v.findViewById(R.id.survey_end_time_time);
        setTime = (Button) v.findViewById(R.id.survey_end_time_set_time);
        next = (Button) v.findViewById(R.id.survey_end_time_next);
    }

    @Override
    void addOnClickListeners() {
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setEndCalendar(calendar);
                saveDataAndContinue(new SurveySummary());
            }
        });
    }

    @Override
    public void fillInEmptyValues() {
        if (data.getEndCalendar() != null) {
            calendar = data.getEndCalendar();

            String timeText = timeFormat.format(calendar.getTime());
            time.setText(timeText);
        }
    }

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Change the time in the calendar
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            // Change the text view to show the time
            String timeText = timeFormat.format(calendar.getTime());
            time.setText(timeText);

            data.setEndCalendar(calendar);
        }
    };

    public void showTimePickerDialog(View v) {
        new TimePickerDialog(getActivity(), t,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true).show();
    }
}
