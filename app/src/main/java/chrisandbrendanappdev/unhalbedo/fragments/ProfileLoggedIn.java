package chrisandbrendanappdev.unhalbedo.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import chrisandbrendanappdev.unhalbedo.R;

/**
 *  Fragment displayed when logged in. Shows profile information, allows the user to log out,
 *  and allows the user to reset their password.
 */
public class ProfileLoggedIn extends Fragment {

    // Profile Information
    private TextView username;//, staff, name, station, town, state;
    // Buttons
    private Button resetPassword, logOut;

    // Shared information through the app
    private SharedPreferences sharedPreferences;

    public ProfileLoggedIn() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.profile_logged_in_layout, container, false);

        sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(getString(R.string.username), Context.MODE_PRIVATE);

        findViews(v);
        addOnClickListeners();

        // Fill in profile data to TextViews
        fillData();

        return v;
    }

    private void findViews(View v) {
        // Account info
        username = v.findViewById(R.id.profile_in_username);
        /*staff = v.findViewById(R.id.profile_in_staff);
        name =  v.findViewById(R.id.profile_in_name);
        station = v.findViewById(R.id.profile_in_station);
        town = v.findViewById(R.id.profile_in_town);
        state = v.findViewById(R.id.profile_in_state);*/

        // Buttons
        resetPassword = v.findViewById(R.id.profile_in_reset_password);
        logOut = v.findViewById(R.id.profile_in_logout);
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
                // Reset SharedPreferences values to empty strings (meaning logged out)
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.username), "");
                editor.putString(getString(R.string.password), "");
                editor.putString(getString(R.string.token), "");
                editor.commit();

                Objects.requireNonNull(getActivity()).finish();
            }
        });
    }

    private void fillData() {
        String usernameString = sharedPreferences.getString(getString(R.string.username), "");
        username.setText(usernameString);

        //String token = sharedPreferences.getString(getString(R.string.token), "");

        // JSONObject res = GetRequest.Users(token);

        // Fill in data here
        // TODO: Fill in Profile information
    }

}
