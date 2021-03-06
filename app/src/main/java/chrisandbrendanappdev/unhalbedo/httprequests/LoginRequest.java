package chrisandbrendanappdev.unhalbedo.httprequests;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 *  Post request to login to the server. This doesn't actually "log in", but rather gets
 *  a token from the server granting the user access to server requests.
 */
public class LoginRequest {

    public static String getAuthToken(String url, String username, String password) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlObj.openConnection();

            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");
            http.setDoOutput(true);

            JSONObject loginInfo = new JSONObject();
            loginInfo.put("username", username);
            loginInfo.put("password", password);

            OutputStream os = http.getOutputStream();
            os.write(loginInfo.toString().getBytes(StandardCharsets.UTF_8));
            os.close();

            // get the status
            int HttpResult = http.getResponseCode();
            if (HttpResult != HttpURLConnection.HTTP_OK) {
                System.out.println("Error " + HttpResult);
                System.out.println("INFO: " + http.getResponseMessage());
                return "";
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) sb.append(line);
                br.close();

                JSONObject returner = new JSONObject(sb.toString());
                return returner.getString("token");
            }
        } catch (Exception e) {
            System.out.println("Failed to get token");
            e.printStackTrace();
            return "";
        }
    }

}
