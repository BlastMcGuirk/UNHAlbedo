package chrisandbrendanappdev.unhalbedo.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import chrisandbrendanappdev.unhalbedo.R;
import chrisandbrendanappdev.unhalbedo.httprequests.GetRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileLoggedIn extends Fragment {

    private TextView username, staff, name, station, town, state;
    private Button resetPassword, logOut;

    private SharedPreferences sharedPreferences;

    public ProfileLoggedIn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.profile_logged_in_layout, container, false);

        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.username), Context.MODE_PRIVATE);

        findViews(v);
        addOnClickListeners();

        fillData();

        return v;
    }

    private void findViews(View v) {
        // Account info
        username = (TextView) v.findViewById(R.id.profile_in_username);
        staff = (TextView) v.findViewById(R.id.profile_in_staff);
        name = (TextView) v.findViewById(R.id.profile_in_name);
        station = (TextView) v.findViewById(R.id.profile_in_station);
        town = (TextView) v.findViewById(R.id.profile_in_town);
        state = (TextView) v.findViewById(R.id.profile_in_state);

        // Buttons
        resetPassword = (Button) v.findViewById(R.id.profile_in_reset_password);
        logOut = (Button) v.findViewById(R.id.profile_in_logout);
    }

    private void addOnClickListeners() {
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Reset password
                Toast.makeText(getContext(), "Reset Password", Toast.LENGTH_SHORT).show();
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.username), "");
                editor.putString(getString(R.string.password), "");
                editor.putString(getString(R.string.token), "");
                editor.commit();

                getActivity().finish();
            }
        });
    }

    private void fillData() {
        String usernameString = sharedPreferences.getString("username", "");
        username.setText(username.getText() + " " + usernameString);

        String token = sharedPreferences.getString(getString(R.string.token), "");

        JSONObject res = GetRequest.Users(token);
    }

}
