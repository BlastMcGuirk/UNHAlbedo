package chrisandbrendanappdev.unhalbedo.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import chrisandbrendanappdev.unhalbedo.R;
import chrisandbrendanappdev.unhalbedo.fragments.ProfileLoggedIn;
import chrisandbrendanappdev.unhalbedo.fragments.ProfileLoggedOut;

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

    private void initializeVariables() {
        sharedPreferences = getSharedPreferences(getString(R.string.username), MODE_PRIVATE);
        username = sharedPreferences.getString(getString(R.string.username), "");
    }

    private void startLoggedOutFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.profile_container, new ProfileLoggedOut())
                .commit();
    }

    private void startLoggedInFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.profile_container, new ProfileLoggedIn())
                .commit();
    }
}
