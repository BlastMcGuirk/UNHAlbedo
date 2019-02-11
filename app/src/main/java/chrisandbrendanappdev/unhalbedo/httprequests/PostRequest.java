package chrisandbrendanappdev.unhalbedo.httprequests;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostRequest {

    private final static String SUBMIT_DATA_URL = "http://albedo.gsscdev.com/api/data-entries/";

    public static boolean submitData(JSONObject data, String token) {
        try {
            URL urlObj = new URL(SUBMIT_DATA_URL);
            HttpURLConnection http = (HttpURLConnection) urlObj.openConnection();

            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization", "Token " + token);
            http.setDoOutput(true);
            http.setDoInput(true);

            System.out.println("token: " + token);

            OutputStream os = http.getOutputStream();
            os.write(data.toString().getBytes("UTF-8"));
            os.close();

            // get the status
            int HttpResult = http.getResponseCode();
            if (HttpResult != HttpURLConnection.HTTP_CREATED) {
                System.out.println("Error " + HttpResult);
                System.out.println("INFO: " + http.getResponseMessage());

                InputStream inStream = http.getErrorStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                    sb.append(line);
                }
                in.close();
                System.out.println(sb.toString());

                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Failed to get token");
            e.printStackTrace();
            return false;
        }
    }

}
