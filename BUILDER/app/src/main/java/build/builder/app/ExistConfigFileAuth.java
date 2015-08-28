package build.builder.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Ivan Nikerin [mail:fenderbas666@gmail.com]
 */
public class ExistConfigFileAuth extends main {

    private static int SPLASH_SCREEN_TIMEOUT = 3000;
    final String CLASS = "ExistConfigFileAuth";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_show);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    Properties properties = new Properties();
                    String outfilename = "config.properties";

                    InputStream inputStream;
                    inputStream = new FileInputStream(getApplicationContext().getFilesDir().getPath() + "/" +outfilename);

                    properties.load(inputStream);
                    String jenkinsHostPre = properties.getProperty("jenkins.host");

                    String jenkinsHost;
                    boolean isContain = jenkinsHostPre.contains("http://");

                    if(isContain){
                        jenkinsHost = jenkinsHostPre;
                    }else {
                        jenkinsHost = "http://" + jenkinsHostPre;
                    }

                    String jenkinsLogin = properties.getProperty("jenkins.login");
                    String jenkinsToken = properties.getProperty("jenkins.token");
                    String userToken = jenkinsLogin + ":" + jenkinsToken;

                    if (android.os.Build.VERSION.SDK_INT > 9) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                    }

                    AutorizationsJenkins auth = new AutorizationsJenkins();

                    int respAuthCode = auth.getCodeAuth(jenkinsHost,userToken);

                    if(respAuthCode == 403){
                        Log.e(CLASS, "Resp Code Auth is " + respAuthCode + " DESC '403 Forbidden'");
                        Intent intent = new Intent(ExistConfigFileAuth.this, main.class);
                        startActivity(intent);
                        finish();
                        Log.d(CLASS, "CONFIG NOT FOUNT RUN autorization");
                    }
                    else if(respAuthCode == 401)
                    {
                        Log.e(CLASS,"Resp Code Auth is " + respAuthCode + " DESC '401 Unauthorized'");
                        Intent intent = new Intent(ExistConfigFileAuth.this, main.class);
                        startActivity(intent);
                        finish();
                        Log.d(CLASS, "CONFIG NOT FOUNT RUN autorization");
                    }
                    else if(respAuthCode == 404)
                    {
                        Log.e(CLASS,"Resp Code Auth is " + respAuthCode + " DESC '404 URL NON FOUNT'");
                        Intent intent = new Intent(ExistConfigFileAuth.this, main.class);
                        startActivity(intent);
                        finish();
                        Log.d(CLASS, "CONFIG NOT FOUNT RUN autorization");

                    } else if (respAuthCode == 200){
                        Log.d(CLASS, "Resp Code Auth is " + respAuthCode + " DESC '200 OK'");

                        HashMap<String,String> configParam = new HashMap<String, String>();
                        configParam.put("jenkins.host",jenkinsHost);
                        configParam.put("jenkins.login", jenkinsHost);
                        configParam.put("user.token", userToken);
                        ConfigDataParam.getInstance().setParamConfig(configParam);

                        Log.d(CLASS, "Config set in Memory");

                        Intent intObj = new Intent(getApplicationContext(), RunActivityGetViewJob.class);
                        startActivity(intObj);
                    }


                } catch (IOException e) {
                    Intent intent = new Intent(ExistConfigFileAuth.this, main.class);
                    startActivity(intent);
                    finish();
                    Log.d(CLASS,"CONFIG NOT FOUNT RUN autorization");
                }

            }
        }, SPLASH_SCREEN_TIMEOUT);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
