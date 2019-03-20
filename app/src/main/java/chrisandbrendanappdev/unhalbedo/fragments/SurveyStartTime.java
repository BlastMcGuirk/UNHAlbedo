package chrisandbrendanappdev.unhalbedo.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import chrisandbrendanappdev.unhalbedo.R;

/**
 *  Start time question. Here, the user will select the start time, as well as the start date
 *  individually. After the user enters these values, they are brought to the Sky Analysis
 *  question.
 */
public class SurveyStartTime extends SurveyFragment {

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    private TextView day, time;
    private Button setDay, setTime, next;

    public SurveyStartTime() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.survey_start_time_fragment, container, false);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.US);
        timeFormat = new SimpleDateFormat("h:mm aa", Locale.US);

        init(v, getString(R.string.title_start_time));

        return v;
    }

    @Override
    void getViews(View v) {
        day = v.findViewById(R.id.survey_start_time_day);
        time = v.findViewById(R.id.survey_start_time_time);

        setDay = v.findViewById(R.id.survey_start_time_set_day);
        setTime = v.findViewById(R.id.survey_start_time_set_time);
        next = v.findViewById(R.id.survey_start_time_next);
    }

    @Override
    void addOnClickListeners() {
        setDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDayPickerDialog();
            }
        });
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean daySet = !day.getText().toString().equals("");
                boolean timeSet = !time.getText().toString().equals("");
                if (daySet && timeSet) {
                    data.setStartCalendar(calendar);
                    saveDataAndContinue(new SurveySkyAnalysis());
                }
                if (!daySet) {
                    day.setError("Please enter a date");
                }
                if (!timeSet) {
                    time.setError("Please enter a time");
                }
            }
        });
    }

    @Override
    void fillInEmptyValues() {
        if (data.getStartCalendar() != null) {
            calendar = data.getStartCalendar();

            String dateText = dateFormat.format(calendar.getTime());
            day.setText(dateText);

            String timeText = timeFormat.format(calendar.getTime());
            time.setText(timeText);
        }
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // Change the day in the calendar
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Change the text view to show the day
            String dateText = dateFormat.format(calendar.getTime());
            day.setText(dateText);
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Change the time in the calendar
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            // Change the text view to show the time
            String timeText = timeFormat.format(calendar.getTime());
            time.setText(timeText);
        }
    };

    public void showTimePickerDialog() {
        new TimePickerDialog(getActivity(), t,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true).show();
    }

    public void showDayPickerDialog() {
        DatePickerDialog dpd = new DatePickerDialog(Objects.requireNonNull(getActivity()), d,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
        dpd.show();
    }
}
