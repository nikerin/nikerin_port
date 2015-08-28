package build.builder.app;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.parser.ParseException;

/**
 * Created by Ivan Nikerin [mail:fenderbas666@gmail.com]
 */
public class GetData {
    private final String USER_AGENT = "Mozilla/5.0";
    public final String CLASS = "GetData";

    public String sendPost(String url, String userPassword) {


        Log.d(CLASS, "call " + CLASS + " params is " + url + " " + userPassword);

        URL obj = null;
        try {
            obj = new URL(url);
        } catch (Exception e) {
            e.printStackTrace();
        }


        HttpURLConnection con = null;
        try {
            assert obj != null;
            con = (HttpURLConnection) obj.openConnection();
        } catch (IOException e) {
            Log.e(CLASS, "EROOR OPEN CONNECTION");
            e.printStackTrace();
            return "";
        }


        byte[] encodinge = org.apache.commons.codec.binary.Base64.encodeBase64(userPassword.getBytes());

        String authStringEnc = new String(encodinge);

        try {
            con.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
            return "";
        }


        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        con.setRequestProperty("Authorization", "Basic " + authStringEnc);

        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return "";

        }

        String inputLine;
        StringBuffer response = new StringBuffer();

        try {
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        int responseCode = 0;
        try {
            responseCode = con.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        Log.d(CLASS, "response code for call " + CLASS + " params is " + url + " " + userPassword + " is " + Integer.toString(responseCode));

        return response.toString();
    }

    public static HashMap<String, ArrayList> getMapListJobs(String urlViews, String userToken) {

        GetData http = new GetData();
        HashMap<String, ArrayList> mapJobs = null;

        mapJobs = new HashMap<String, ArrayList>();

        String prefixApiJson = "/api/json";

        String urlReqest = urlViews + prefixApiJson;

        String respList = http.sendPost(urlReqest, userToken);

        if (!respList.equals("")) {

            JSONParser parser = new JSONParser();

            Object obj = null;
            try {
                obj = parser.parse(respList);
            } catch (ParseException e) {
                e.printStackTrace();
                return mapJobs;
            }
            JSONObject jsonObj = (JSONObject) obj;
            JSONArray listJob = (JSONArray) jsonObj.get("jobs");

            for (int i = 0; i < listJob.size(); i++) {

                ArrayList<String> paramList = new ArrayList<String>();

                JSONObject jsonParam = (JSONObject) listJob.get(i);


                JSONParser parsers = new JSONParser();

                Object objet = null;
                try {
                    objet = parsers.parse(jsonParam.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                    return mapJobs;
                }
                JSONObject jsonObjet = (JSONObject) objet;

                Object nameJob = jsonObjet.get("name");
                Object urlJob = jsonObjet.get("url");
                Object statusJob = jsonObjet.get("color");

                String idElmement = Integer.toString(i);

                String statusJobString = statusJob.toString();

                paramList.add(urlJob.toString());
                paramList.add(idElmement);
                paramList.add(statusJobString);

                mapJobs.put(nameJob.toString(), paramList);


            }
        }

        return mapJobs;

    }

    public static HashMap<String, ArrayList> getMapListViews(String host, String userToken) {

        GetData http = new GetData();
        HashMap<String, ArrayList> mapViews = null;
        String prefix_api_json = "/api/json";

        String urlReqest = host + prefix_api_json;

        mapViews = new HashMap<String, ArrayList>();

        String respList = http.sendPost(urlReqest, userToken);

        if (!respList.equals("")) {

            JSONParser parser = new JSONParser();

            Object obj = null;
            try {
                obj = parser.parse(respList);
            } catch (ParseException e) {
                e.printStackTrace();
                return mapViews;
            }
            JSONObject jsonObj = (JSONObject) obj;
            JSONArray listJob = (JSONArray) jsonObj.get("views");

            for (int i = 0; i < listJob.size(); i++) {

                ArrayList<String> paramList = new ArrayList<String>();

                JSONObject jsonParam = (JSONObject) listJob.get(i);


                JSONParser parsers = new JSONParser();

                Object objet = null;
                try {
                    objet = parsers.parse(jsonParam.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                    return mapViews;
                }
                JSONObject jsonObjet = (JSONObject) objet;

                Object nameJob = jsonObjet.get("name");
                Object urlJob = jsonObjet.get("url");
                String id_elmement = Integer.toString(i);

                paramList.add(urlJob.toString());
                paramList.add(id_elmement);

                mapViews.put(nameJob.toString(), paramList);


            }
        }

        return mapViews;

    }

    public static HashMap<String, String> getMapJobInfo(String host, String userToken) {

        GetData http = new GetData();
        HashMap<String, String> mapJobInfo = null;

        String prefix_api_json = "api/json";

        String urlReqest = host + prefix_api_json;

        mapJobInfo = new HashMap<String, String>();

        String respList = http.sendPost(urlReqest, userToken);

        if (!respList.equals("")) {

            JSONParser parser = new JSONParser();

            Object obj = null;
            try {
                obj = parser.parse(respList);
            } catch (ParseException e) {
                e.printStackTrace();
                return mapJobInfo;
            }

            JSONObject jsonObj = (JSONObject) obj;
            String description = "";
            String displayName = "";
            String color = "";
            String inQueue = "";
            String nextBuildNumber = "";
            try {
                description = jsonObj.get("description").toString();
                displayName = jsonObj.get("displayName").toString();
                color = jsonObj.get("color").toString();
                inQueue = jsonObj.get("inQueue").toString();
                nextBuildNumber = jsonObj.get("nextBuildNumber").toString();
            }catch (Exception e){
                Log.d("Get Info job", "Is NONE WRITE VALUE EMPTY");
            }


            String lastCompletedBuildNumber = "";
            String lastCompletedBuildUrl = "";
            try {
                JSONObject lastCompletedBuild = (JSONObject) jsonObj.get("lastCompletedBuild");
                lastCompletedBuildNumber = lastCompletedBuild.get("number").toString();
                lastCompletedBuildUrl = lastCompletedBuild.get("url").toString();
            }catch (Exception e){
                Log.d("Get lastCompletedBuild", "Is NONE WRITE VALUE EMPTY");
            }

            String lastFailedBuildNumber = "";
            String lastFailedBuildUrl = "";
            try {
                JSONObject lastFailedBuild = (JSONObject) jsonObj.get("lastFailedBuild");
                lastFailedBuildNumber = lastFailedBuild.get("number").toString();
                lastFailedBuildUrl = lastFailedBuild.get("url").toString();
            }catch (Exception e){
                Log.d("Get lastFailedBuild", "Is NONE WRITE VALUE EMPTY");
            }

            String lastStableBuildNumber = "";
            String lastStableBuildUrl = "";
            try {
            JSONObject lastStableBuild = (JSONObject) jsonObj.get("lastStableBuild");
            lastStableBuildNumber = lastStableBuild.get("number").toString();
            lastStableBuildUrl = lastStableBuild.get("url").toString();
            }catch (Exception e){
                Log.d("Get lastStableBuild", "Is NONE WRITE VALUE EMPTY");
            }

            String lastSuccessfulBuildNumber = "";
            String lastSuccessfulBuildUrl = "";
            try {
            JSONObject lastSuccessfulBuild = (JSONObject) jsonObj.get("lastSuccessfulBuild");
            lastSuccessfulBuildNumber = lastSuccessfulBuild.get("number").toString();
            lastSuccessfulBuildUrl = lastSuccessfulBuild.get("url").toString();
            }catch (Exception e){
                Log.d("Get lastSuccessfulBuild", "Is NONE WRITE VALUE EMPTY");
            }

            mapJobInfo.put("description", description);
            mapJobInfo.put("displayName", displayName);
            mapJobInfo.put("color", color);
            mapJobInfo.put("inQueue", inQueue);
            mapJobInfo.put("nextBuildNumber", nextBuildNumber);
            mapJobInfo.put("lastCompletedBuildNumber", lastCompletedBuildNumber);
            mapJobInfo.put("lastCompletedBuildUrl", lastCompletedBuildUrl);
            mapJobInfo.put("lastFailedBuildNumber", lastFailedBuildNumber);
            mapJobInfo.put("lastFailedBuildUrl", lastFailedBuildUrl);
            mapJobInfo.put("lastStableBuildNumber", lastStableBuildNumber);
            mapJobInfo.put("lastStableBuildUrl", lastStableBuildUrl);
            mapJobInfo.put("lastSuccessfulBuildNumber", lastSuccessfulBuildNumber);
            mapJobInfo.put("lastSuccessfulBuildUrl", lastSuccessfulBuildUrl);


        }
        return mapJobInfo;
    }

    public static int buildNowRespcode(String urlJob) {
        final String USER_AGENT = "Mozilla/5.0";
        ConfigDataParam configParam = ConfigDataParam.getInstance();

        HashMap param = configParam.getParamConfig();
        String userToken = (String) param.get("user.token");

        String urlBuildJenkins;

        Log.d("BUILD", "call " + "BUILD" + " params is " + urlJob + " " + userToken);

        urlBuildJenkins = urlJob + "build/api/json";

        ;

        try {

            URL obj = null;
            obj = new URL(urlBuildJenkins);


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
            Log.d("BUILD", "response code for call BUILD params is " + urlBuildJenkins + " " + userToken + " is " + Integer.toString(responseCode));
            return responseCode;

        }catch (Exception e){
            e.printStackTrace();
        }

        return 404;
    }
}

