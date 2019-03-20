package chrisandbrendanappdev.unhalbedo.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import chrisandbrendanappdev.unhalbedo.R;
import chrisandbrendanappdev.unhalbedo.fragments.ProfileLoggedIn;
import chrisandbrendanappdev.unhalbedo.fragments.ProfileLoggedOut;

/**
 *  Profile page. Here, the user can see their account information, log in, or log out.
 *  The user can also reset their password whether loggged in or logged out.
 */
public class ProfileActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initializeVariables();

        if (username.equals("")) {
            // Logged out
            startLoggedOutFragment();
        } else {
            // Logged in
            startLoggedInFragment();
        }
    }

    // Fetch username to check if logged in or not
    private void initializeVariables() {
        sharedPreferences = getSharedPreferences(getString(R.string.username), MODE_PRIVATE);
        username = sharedPreferences.getString(getString(R.string.username), "");
    }

    // Load the logged out fragment if the user is NOT logged in
    private void startLoggedOutFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.profile_container, new ProfileLoggedOut())
                .commit();
    }

    // Load the logged in fragment if the user IS logged in
    private void startLoggedInFragment() {
        // TODO: FETCH USER PROFILE INFORMATION AND DISPLAY IF LOGGED IN
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.profile_container, new ProfileLoggedIn())
                .commit();
    }
}
