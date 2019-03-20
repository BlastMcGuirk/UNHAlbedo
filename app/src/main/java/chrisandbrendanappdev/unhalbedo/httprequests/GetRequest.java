package chrisandbrendanappdev.unhalbedo.httprequests;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *  Possible GET request calls to server
 */

public class GetRequest {

    private static final String usersURL = "http://albedo.gsscdev.com/api/users/?page=";
    private static final String entriesURL = "http://albedo.gsscdev.com/api/user/";
    private static final String jsonQuery = "?format=json";
    private static final String addJSONQuery = "&format=json";

    public static JSONObject Users(String token) {
        return basicGet(token, usersURL + 1 + addJSONQuery);
    }

    private static JSONObject Users(String token, int page) {
        return basicGet(token, usersURL + page + addJSONQuery);
    }

    public static JSONObject Entries(String token, int ID) {
        return basicGet(token, entriesURL + ID + "/posts/" + jsonQuery);
    }

    public static int UserID(String token, String username) {
        try {
            JSONObject users;
            int page = 1;
            do {
                users = GetRequest.Users(token, page);
                JSONArray results = users.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject user = results.getJSONObject(i);
                    if (user.getString("username").equals(username)) {
                        return user.getInt("id");
                    }
                }
                page++;
            } while (!users.getString("next").equals("null"));
        } catch (Exception e) {
            System.out.println("Exception, cannot get username");
            e.printStackTrace();
            return 0;
        }
        System.out.println("Username not found");
        return 0;
    }

    // Performs a basic get request to the server using a specified URL
    private static JSONObject basicGet(String token, String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization", "Token " + token);
            http.setDoInput(true);

            int status = http.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                InputStream inStream = http.getInputStream();
                System.out.println("Success! content type: " + http.getHeaderField("Content-Type"));
                BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                in.close();

                return new JSONObject(sb.toString());
            } else {
                System.out.println("Error " + status);
                System.out.println(http.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
