package chrisandbrendanappdev.unhalbedo.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import chrisandbrendanappdev.unhalbedo.R;
import chrisandbrendanappdev.unhalbedo.httprequests.GetRequest;

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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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
        if (isLoggedIn(username)) {
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
    private boolean isLoggedIn(String username) {
        if (username.equals("")) {
            // No user last logged in
            return false;
        }
        // Previous username logged in
        return true;
    }

    /*
     *  On successfully logging in, show a list of entries by the
     *  user in a ListView
     */
    private void setViewTextToLoggedIn() {
        // Add the logged in layout to the activity
        View v = layoutInflater.inflate(R.layout.main_page_logged_in_layout, container);

        loadEntries(v);

    }

    private void loadEntries(View v) {
        ListView entries = v.findViewById(R.id.Entries);

        // Add items to HashMap
        HashMap<String, String> entryMap = getEntries();

        // Make list of HashMap
        List<HashMap<String, String>> listItems = new ArrayList<>();

        // Adapter to load items in to list
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_entry_layout,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.list_entry_title, R.id.list_entry_info});

        // Iterate through map
        Iterator it = entryMap.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        entries.setAdapter(adapter);
    }

    private HashMap<String, String> getEntries() {
        HashMap<String, String> entries = new HashMap<>();
        String token = sharedPreferences.getString(getString(R.string.token), "");
        String username = sharedPreferences.getString(getString(R.string.username), "");

        int ID = GetRequest.UserID(token, username);
        // TODO: Change back to ID once server changes are made
        JSONObject results = GetRequest.Entries(token, 210);
        System.out.println(results);

        try {
            JSONArray listOfEntries = results.getJSONArray("entries");
            System.out.println(listOfEntries);
            for (int i = 0; i < listOfEntries.length(); i++) {
                JSONObject obj = listOfEntries.getJSONObject(i);
                System.out.println(obj);
                String title = obj.getString("station_Number");
                String date = obj.getString("observation_Date");
                String time = obj.getString("observation_Time");
                entries.put(title, date + "   " + time);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return entries;
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
