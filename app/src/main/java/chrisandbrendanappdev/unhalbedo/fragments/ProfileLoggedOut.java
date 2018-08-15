package chrisandbrendanappdev.unhalbedo.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import chrisandbrendanappdev.unhalbedo.R;

/**
 *
 */
public class ProfileLoggedOut extends Fragment {

    private EditText usernameText, passwordText;
    private Button logIn;
    private TextView forgotPassword;

    public ProfileLoggedOut() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.profile_logged_out_layout, container, false);

        findViews(v);
        addOnClickListeners();

        return v;
    }

    private void findViews(View v) {
        usernameText = (EditText) v.findViewById(R.id.profile_out_username);
        passwordText = (EditText) v.findViewById(R.id.profile_out_password);
        logIn = (Button) v.findViewById(R.id.profile_out_login);
        forgotPassword = (TextView) v.findViewById(R.id.profile_out_forgot_password);
    }

    private void addOnClickListeners() {
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Log in to server and redirect to main page
                //Toast.makeText(getContext(), "Log in", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.username), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.username), usernameText.getText().toString());
                editor.commit();

                getActivity().finish();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Reset password
                Toast.makeText(getContext(), "Forgot Password", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
