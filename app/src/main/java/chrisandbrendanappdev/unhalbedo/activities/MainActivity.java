package chrisandbrendanappdev.unhalbedo.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import chrisandbrendanappdev.unhalbedo.R;

public class MainActivity extends AppCompatActivity {

    /*
     *  Injects information in to activity if the user is logged in, or a message if the user
     *  is logged out
     */
    private LayoutInflater layoutInflater;
    private ViewGroup container;

    /*
     *  Store the username so it can be used later for a call to the database, retrieving data
     *  entries by this user
     */
    private SharedPreferences sharedPreferences;
    private String username;

    /*
     *  Starting method for the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the variables
        initializeVariables();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check if there is a user logged in
        username = sharedPreferences.getString(getString(R.string.username), "");

        // Clear the container to replace with logged in/out layout
        container.removeAllViews();

        // Attempt a login
        if (attemptLogin(username)) {
            // User has successfully logged in
            setViewTextToLoggedIn();
        } else {
            // User hasn't logged in
            setViewTextToLoggedOut();
        }
    }

    /*
     *  Creates the buttons for the Action Bar across the top of the screen. Uses the menu
     *  "main_menu" that contains a "Profile" button and a "Start Submission" button
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /*
     *  Sets the onClick for each button in the menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                // Start profile activity
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
                break;
            case R.id.action_start_submission:
                // Start submission activity
                Intent submissionIntent = new Intent(this, SurveyActivity.class);
                startActivity(submissionIntent);
                break;
            default:
                break;
        }
        return true;
    }

    /*
     *  Initializes the private variables for the activity
     */
    private void initializeVariables() {
        // For injecting login results
        layoutInflater = getLayoutInflater();
        container = (ViewGroup) findViewById(R.id.main_activity_container);

        sharedPreferences = getSharedPreferences(getString(R.string.username), MODE_PRIVATE);
    }

    /*
     *  Attempts to login to the server
     *
     *  @return success of the login (true if logged in, false if failed)
     */
    private boolean attemptLogin(String username) {
        if (username.equals("")) {
            // No user last logged in
            return false;
        }
        // Previous username logged in
        // TODO: Log in
        return true;
    }

    /*
     *  On successfully logging in, show a list of entries by the
     *  user in a ListView
     */
    private void setViewTextToLoggedIn() {
        // Add the logged in layout to the activity
        View v = layoutInflater.inflate(R.layout.main_page_logged_in_layout, container);

        // TODO: Fill a ListView with data entries
        // for now just fill in a Logged in as username
        String message = getString(R.string.main_logged_in) + username;
        TextView tv = (TextView) v.findViewById(R.id.main_page_logged_in_view);
        tv.setText(message);
    }

    /*
     *  On failing to log in, show a message that the user is
     *  not logged in
     */
    private void setViewTextToLoggedOut() {
        // Add the logged out layout to the activity
        View v = layoutInflater.inflate(R.layout.main_page_logged_out_layout, container);

        // Set message for user to warn them they aren't logged in
        String message = getString(R.string.main_not_logged_in);
        TextView tv = (TextView) v.findViewById(R.id.main_page_logged_out_view);
        tv.setText(message);
    }

}
