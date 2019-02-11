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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import chrisandbrendanappdev.unhalbedo.R;
import chrisandbrendanappdev.unhalbedo.httprequests.LoginRequest;

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
            @SuppressLint("ApplySharedPref")
            @Override
            public void onClick(View v) {
                String username = usernameText.getText().toString(); //"CoCoRAHS5";
                String urlToken = "http://albedo.gsscdev.com/api/auth/get_token/";
                String password = passwordText.getText().toString(); //"A1b3d02011!"; //passwordText.getText().toString() == "1" ?  : "A1b3d02011";
                String token;

                // Check for admin login
                if (username.equals("admin")) {
                    username = "CoCoRAHS5";
                    password = "A1b3d02011!";
                }

                // get token
                token = LoginRequest.getAuthToken(urlToken, username, password);

                if (!token.equals("")) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.username), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(getString(R.string.username), username);
                    editor.putString(getString(R.string.password), password);
                    editor.putString(getString(R.string.token), token);
                    editor.commit();

                    getActivity().finish();
                } else {
                    passwordText.setError("Invalid username/password");
                }
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
