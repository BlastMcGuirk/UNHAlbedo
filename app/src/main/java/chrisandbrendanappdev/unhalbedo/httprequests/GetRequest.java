package chrisandbrendanappdev.unhalbedo.httprequests;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Possible get request calls to server
 */

public class GetRequest {

    private static String usersURL = "http://albedo.gsscdev.com/api/users/?page=";
    private static final String jsonQuery = "?format=json";
    private static final String addJSONQuery = "&format=json";

    public static JSONObject Users(String token) {
        return basicGet(token, usersURL + 1 + addJSONQuery);
    }

    public static JSONObject Users(String token, int page) {
        return basicGet(token, usersURL + page + addJSONQuery);
    }

    private static JSONObject basicGet(String token, String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization", "Token " + token);
            //http.setDoOutput(true);
            http.setDoInput(true);

            int status = http.getResponseCode();
            System.out.println("Response: " + status);
            if (status == HttpURLConnection.HTTP_OK) {
                InputStream inStream = http.getInputStream();
                System.out.println("Success! content type: " + http.getHeaderField("Content-Type"));
                BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                    sb.append(line);
                }
                in.close();

                //System.out.println("---------- basicGet:\n" + sb.toString());

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
