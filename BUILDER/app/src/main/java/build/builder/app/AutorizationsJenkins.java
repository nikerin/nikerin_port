package build.builder.app;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ivan Nikerin [mail:fenderbas666@gmail.com]
 */
public class AutorizationsJenkins {
    final String CLASS = "AutorizationsJenkins";
    private final String USER_AGENT = "Mozilla/5.0";

    int getCodeAuth(String urlJenkins, String userToken) {


        boolean isContain = urlJenkins.contains("http://");

        if(isContain){
            urlJenkins = urlJenkins;
        }else {
            urlJenkins = "http://" + urlJenkins;
        }

        Log.d(CLASS, "call " + CLASS + " params is " + urlJenkins + " " + userToken);

        try {

            URL obj = null;
            obj = new URL(urlJenkins);


            HttpURLConnection con = null;

            assert obj != null;
            con = (HttpURLConnection) obj.openConnection();

            byte[] encodinge = org.apache.commons.codec.binary.Base64.encodeBase64(userToken.getBytes());

            String authStringEnc = new String(encodinge);

            con.setRequestMethod("POST");


            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            con.setRequestProperty("Authorization", "Basic " + authStringEnc);


            int responseCode = con.getResponseCode();
            Log.d(CLASS, "response code for call " + CLASS + " params is " + urlJenkins + " " + userToken + " is " + Integer.toString(responseCode));
            return responseCode;

        }catch (Exception e){
            e.printStackTrace();
        }

        return 404;
    }
}
