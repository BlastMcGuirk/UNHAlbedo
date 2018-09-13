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
            @Override
            public void onClick(View v) {
                // TODO: Log in to server and redirect to main page
                String username = "CoCoRAHS5"; //usernameText.getText().toString();
                String password = "A1b3d02011!"; //passwordText.getText().toString();
                String token = "";
                String urlToken = "http://albedo.gsscdev.com/api/auth/get_token";
                String urlLogin = "http://albedo.gsscdev.com/api/auth/login";

                // get token
                JSONObject tokenObj = LoginRequest.getAuthToken(urlToken, username, password);
                System.out.println(tokenObj);
                try {
                    token = tokenObj.getString("token");
                } catch (Exception ignored) {}

                if (!token.equals("")) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.username), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(getString(R.string.username), username);
                    editor.commit();

                    getActivity().finish();
                } else {
                    passwordText.setError("Invalid username/password");
                }

                /*try {
                    // make a get request
                    URL url = new URL(urlString);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("GET");

                    // read the get request
                    BufferedReader buff = new BufferedReader(new InputStreamReader(http.getInputStream()));
                    String line;

                    // get the csrfmiddlewaretoken
                    while ((line = buff.readLine()) != null) {
                        if (line.contains("csrf")) {
                            System.out.println("--------------------------> > > > > > " + line);
                            int startIndex = line.indexOf("value='") + 7;
                            csrfmiddlewaretoken = line.substring(startIndex, startIndex + 32);
                        }
                    }
                    buff.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                // POST


                /*try {
                    String query = String.format("username=%s&password=%s",
                            URLEncoder.encode(username, charset),
                            URLEncoder.encode(password, charset));
                    URLConnection connection = new URL(url).openConnection();

                    connection.setDoOutput(true);
                    connection.setRequestProperty("Accept-Charset", charset);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

                    OutputStream output = connection.getOutputStream();
                    output.write(query.getBytes(charset));

                    System.out.println(((HttpURLConnection) connection).getResponseCode());
                    InputStream response = connection.getInputStream();

                    System.out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/


                /*// GET REQUEST
                try {
                    System.out.println("Now opening connection to: " + url);
                    InputStream response = new URL(url + "?" + "username=" + username + "&password=" + password).openStream();
                    Scanner scanner = new Scanner(response);
                    String responseBody = scanner.useDelimiter("\\A").next();
                    System.out.println(responseBody);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                //Toast.makeText(getContext(), "Log in", Toast.LENGTH_SHORT).show();

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
