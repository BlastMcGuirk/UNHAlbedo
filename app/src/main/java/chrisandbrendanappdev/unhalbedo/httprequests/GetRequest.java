package chrisandbrendanappdev.unhalbedo.httprequests;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Possible get request calls to server
 */

public class GetRequest {

    private static String usersURL = "http://albedo.gsscdev.com/api/users/";

    public static JSONObject Users(String token) {
        return basicGet(token, usersURL);
    }

    public static JSONObject Users(String token, String id) {
        return basicGet(token, usersURL + id + "/");
    }

    private static JSONObject basicGet(String token, String urlString) {
        try {
            URL url = new URL(urlString + "?format=json");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");
            //http.setRequestProperty("Authorization", "Token " + token);
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream os = http.getOutputStream();
            os.write(0);
            os.close();

            InputStream inStream = http.getInputStream();
            System.out.println("Sending GET to " + urlString + " with token " + token);
            System.out.println(http.getHeaderField("Content-Type"));

            int status = http.getResponseCode();
            System.out.println("Response: " + status);
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                    sb.append(line);
                }
                in.close();

                System.out.println("---------- basicGet:\n" + sb.toString());

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
