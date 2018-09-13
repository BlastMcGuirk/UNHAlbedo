package chrisandbrendanappdev.unhalbedo.httprequests;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Brendan on 9/6/2018.
 */

public class LoginRequest {

    public static JSONObject getAuthToken(String url, String username, String password) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlObj.openConnection();

            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.setRequestProperty("Accept", "application/json");
            http.setDoInput(true);
            http.setDoOutput(true);

            JSONObject loginInfo = new JSONObject();
            loginInfo.put("username", username);
            loginInfo.put("password", password);

            //System.out.println("------------------> Starting request with: " + loginInfo.toString());

            OutputStream os = http.getOutputStream();
            os.write(loginInfo.toString().getBytes("UTF-8"));
            os.close();

            //System.out.println("Status: " + http.getResponseCode());
            // get the status
            int HttpResult = http.getResponseCode();
            if (HttpResult != HttpURLConnection.HTTP_OK) {
                System.out.println("STATUS: ----------------------------VVV " + HttpResult);
                System.out.println("INFO: " + http.getResponseMessage());
                return null;
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"));
                String line = null;
                while (!(line = br.readLine()).contains("csrfmiddlewaretoken"));
                br.close();

                int startIndex = line.indexOf("value='") + 7;
                JSONObject retObj = new JSONObject();
                retObj.put("token", line.substring(startIndex, startIndex + 32));
                System.out.println("RetObj: " + retObj.toString());
                return retObj;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
