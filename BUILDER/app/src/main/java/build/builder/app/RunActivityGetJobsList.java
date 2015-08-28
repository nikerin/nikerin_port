package build.builder.app;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivan Nikerin [mail:fenderbas666@gmail.com]
 */
public class RunActivityGetJobsList extends main{
    LinearLayout llt;
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onCreate(Bundle savedInstanceState){

            final String blue = "blue";
            final String red = "red";
            final String notbuilt = "notbuilt";
            final String disabled = "disabled" ;
            final String CLASS = "RunActivityGetJobsList";

            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_list_jobs);

            llt = (LinearLayout) findViewById(R.id.container);

            final String urlView = getIntent().getExtras().getString("url_view");

            ConfigDataParam configParam = ConfigDataParam.getInstance();

            HashMap param = configParam.getParamConfig();
            String userToken = (String) param.get("user.token");



            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            try {

                HashMap<String, ArrayList> mapListJobs = GetData.getMapListJobs(urlView, userToken);

                int sizeMap = mapListJobs.size();

                if(sizeMap!=0) {

                    Log.d(CLASS, "Create map list job success" + mapListJobs.toString());

                    for (Map.Entry<String, ArrayList> e : mapListJobs.entrySet()) {
                        final String content = e.getKey();
                        ArrayList jobListParams = mapListJobs.get(content);
                        final String urlJob = (String) jobListParams.get(0);
                        String idElement = (String) jobListParams.get(1);
                        String statusJob = (String) jobListParams.get(2);
                        Button btn = new Button(this);

                        Drawable imageStatusJob;

                        if(android.os.Build.VERSION.SDK_INT >= 21){
                            imageStatusJob = getResources().getDrawable(R.drawable.def, getTheme());
                        } else {
                            imageStatusJob = getResources().getDrawable(R.drawable.def);
                        }

                        if (statusJob.equals(blue)) {
                            if(android.os.Build.VERSION.SDK_INT >= 21){
                                imageStatusJob = getResources().getDrawable(R.drawable.ok, getTheme());

                            } else {
                                imageStatusJob = getResources().getDrawable(R.drawable.ok);;
                            }
                        }
                        else if (statusJob.equals(red)){
                            if(android.os.Build.VERSION.SDK_INT >= 21){
                                imageStatusJob = getResources().getDrawable(R.drawable.bad, getTheme());
                            } else {
                                imageStatusJob = getResources().getDrawable(R.drawable.bad);
                            }
                        }
                        else if (statusJob.equals(notbuilt)){
                            if(android.os.Build.VERSION.SDK_INT >= 21){
                                imageStatusJob = getResources().getDrawable(R.drawable.non, getTheme());
                            } else {
                                imageStatusJob = getResources().getDrawable(R.drawable.non);
                            }
                        }
                        else if (statusJob.equals(disabled)){
                            if(android.os.Build.VERSION.SDK_INT >= 21){
                                imageStatusJob = getResources().getDrawable(R.drawable.disable, getTheme());
                            } else {
                                imageStatusJob = getResources().getDrawable(R.drawable.disable);
                            }
                        }

                        btn.setCompoundDrawablesWithIntrinsicBounds(imageStatusJob, null, null, null);


                        btn.setText(content);
                        int id = Integer.parseInt(idElement);
                        btn.setId(id);
                        LinearLayout.LayoutParams lButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        btn.setLayoutParams(lButtonParams);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intObj = new Intent(getApplicationContext(), RunActivityGetJobInfo.class);

                                intObj.putExtra("name_job", content);
                                intObj.putExtra("url_job", urlJob);

                                startActivity(intObj);
                            }
                        });


                        llt.addView(btn);

                    }
                }
                else{
                    Log.e(CLASS, "ERROR CREATE LIST JOB FOR VIEW");
                    TextView contentError = new TextView(this);
                    contentError.setText("ERROR SHOW LIST JOBS FOR VIEWS");
                    LinearLayout.LayoutParams TViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    contentError.setLayoutParams(TViewParams);
                    llt.addView(contentError);
                }

            }catch (Exception e){
                Log.e(CLASS, "ERROR CREATE LIST JOB FOR VIEW");
                TextView contentError = new TextView(this);
                contentError.setText("ERROR SHOW LIST JOBS FOR VIEWS");
                LinearLayout.LayoutParams TViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                contentError.setLayoutParams(TViewParams);
                llt.addView(contentError);
                e.printStackTrace();
            }


        }
    }
