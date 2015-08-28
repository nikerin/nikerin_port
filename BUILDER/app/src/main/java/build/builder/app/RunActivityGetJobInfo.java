package build.builder.app;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.HashMap;


/**
 * Created by Ivan Nikerin [mail:fenderbas666@gmail.com]
 */
public class RunActivityGetJobInfo extends main {
    LinearLayout llt;
    final int colorBack = Color.parseColor("#0D2DE2");
    final int colorText = Color.parseColor("#FFFFFF");
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final String blue = "blue";
        final String red = "red";
        final String notbuilt = "notbuilt";
        final String disabled = "disabled";
        final String CLASS = "RunActivityGetJobsList";

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_job_info);

        llt = (LinearLayout) findViewById(R.id.container);


        final String urlJob = getIntent().getExtras().getString("url_job");

        ConfigDataParam configParam = ConfigDataParam.getInstance();

        HashMap param = configParam.getParamConfig();
        String userToken = (String) param.get("user.token");


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        LinearLayout.LayoutParams TViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        try {

            HashMap<String, String> mapJobInfo = GetData.getMapJobInfo(urlJob, userToken);

            int sizeMap = mapJobInfo.size();

            if(sizeMap!=0) {

                Log.d(CLASS, "Create map param job success" + mapJobInfo.toString());

                //String description = mapJobInfo.get("description");


                String displayName = mapJobInfo.get("displayName");
                if(!displayName.equals("")) {
                    TextView nameJobHead = new TextView(this);
                    nameJobHead.setText("NAME JOB:");
                    nameJobHead.setTextColor(colorText);
                    nameJobHead.setBackgroundColor(colorBack);
                    nameJobHead.setLayoutParams(TViewParams);
                    llt.addView(nameJobHead);

                    TextView nameJobCont = new TextView(this);
                    nameJobCont.setText(displayName);
                    nameJobCont.setLayoutParams(TViewParams);
                    llt.addView(nameJobCont);
                }

                String statusJob = mapJobInfo.get("color");
                if(!statusJob.equals("")) {
                    TextView StatusJobHead = new TextView(this);
                    StatusJobHead.setText("JOB STATUS:");
                    StatusJobHead.setTextColor(colorText);
                    StatusJobHead.setBackgroundColor(colorBack);
                    StatusJobHead.setLayoutParams(TViewParams);
                    llt.addView(StatusJobHead);

                    Drawable imageStatusJob;

                    if (android.os.Build.VERSION.SDK_INT >= 21) {
                        imageStatusJob = getResources().getDrawable(R.drawable.def, getTheme());
                    } else {
                        imageStatusJob = getResources().getDrawable(R.drawable.def);
                    }

                    if (statusJob.equals(blue)) {
                        if (android.os.Build.VERSION.SDK_INT >= 21) {
                            imageStatusJob = getResources().getDrawable(R.drawable.ok, getTheme());

                        } else {
                            imageStatusJob = getResources().getDrawable(R.drawable.ok);
                            ;
                        }
                    } else if (statusJob.equals(red)) {
                        if (android.os.Build.VERSION.SDK_INT >= 21) {
                            imageStatusJob = getResources().getDrawable(R.drawable.bad, getTheme());
                        } else {
                            imageStatusJob = getResources().getDrawable(R.drawable.bad);
                        }
                    } else if (statusJob.equals(notbuilt)) {
                        if (android.os.Build.VERSION.SDK_INT >= 21) {
                            imageStatusJob = getResources().getDrawable(R.drawable.non, getTheme());
                        } else {
                            imageStatusJob = getResources().getDrawable(R.drawable.non);
                        }
                    } else if (statusJob.equals(disabled)) {
                        if (android.os.Build.VERSION.SDK_INT >= 21) {
                            imageStatusJob = getResources().getDrawable(R.drawable.disable, getTheme());
                        } else {
                            imageStatusJob = getResources().getDrawable(R.drawable.disable);
                        }
                    }


                    ImageView imgView = new ImageView(this);
                    imgView.setImageDrawable(imageStatusJob);
                    imgView.setScaleType(ImageView.ScaleType.MATRIX);
                    llt.addView(imgView);
                }

                String inQueue = mapJobInfo.get("inQueue");
                if(!inQueue.equals("")){
                    if(inQueue.equals("true")) {
                        TextView queue = new TextView(this);
                        queue.setText("IS INQUEUE");
                        queue.setTextColor(colorText);
                        queue.setBackgroundColor(colorBack);
                        queue.setLayoutParams(TViewParams);
                        llt.addView(queue);
                    }
                }

                String lastCompletedBuildNumber = mapJobInfo.get("lastCompletedBuildNumber");
                if(!lastCompletedBuildNumber.equals("")){

                    TextView lastCompletedBuildNumberHead = new TextView(this);
                    lastCompletedBuildNumberHead.setText("LAST COMPLETED BUILD NUMBER");
                    lastCompletedBuildNumberHead.setTextColor(colorText);
                    lastCompletedBuildNumberHead.setBackgroundColor(colorBack);
                    lastCompletedBuildNumberHead.setLayoutParams(TViewParams);
                    llt.addView(lastCompletedBuildNumberHead);

                    TextView lastCompletedBuildNumberCont = new TextView(this);
                    lastCompletedBuildNumberCont.setText(lastCompletedBuildNumber);
                    lastCompletedBuildNumberCont.setLayoutParams(TViewParams);
                    llt.addView(lastCompletedBuildNumberCont);
                    }


                String lastFailedBuildNumber = mapJobInfo.get("lastFailedBuildNumber");
                if(!lastFailedBuildNumber.equals("")){

                    TextView lastFailedBuildNumberHead = new TextView(this);
                    lastFailedBuildNumberHead.setText("LAST FAILED BUILD NUMBER");
                    lastFailedBuildNumberHead.setTextColor(colorText);
                    lastFailedBuildNumberHead.setBackgroundColor(colorBack);
                    lastFailedBuildNumberHead.setLayoutParams(TViewParams);
                    llt.addView(lastFailedBuildNumberHead);

                    TextView lastCompletedBuildNumberCont = new TextView(this);
                    lastCompletedBuildNumberCont.setText(lastFailedBuildNumber);
                    lastCompletedBuildNumberCont.setLayoutParams(TViewParams);
                    llt.addView(lastCompletedBuildNumberCont);
                }

                String lastStableBuildNumber = mapJobInfo.get("lastStableBuildNumber");
                if(!lastStableBuildNumber.equals("")){

                    TextView lastStableBuildNumberHead = new TextView(this);
                    lastStableBuildNumberHead.setText("LAST STABLE BUILD NUMBER");
                    lastStableBuildNumberHead.setTextColor(colorText);
                    lastStableBuildNumberHead.setBackgroundColor(colorBack);
                    lastStableBuildNumberHead.setLayoutParams(TViewParams);
                    llt.addView(lastStableBuildNumberHead);

                    TextView lastCompletedBuildNumberCont = new TextView(this);
                    lastCompletedBuildNumberCont.setText(lastStableBuildNumber);
                    lastCompletedBuildNumberCont.setLayoutParams(TViewParams);
                    llt.addView(lastCompletedBuildNumberCont);
                }

                String lastSuccessfulBuildNumber = mapJobInfo.get("lastSuccessfulBuildNumber");
                if(!lastSuccessfulBuildNumber.equals("")){

                    TextView lastSuccessfulBuildNumberHead = new TextView(this);
                    lastSuccessfulBuildNumberHead.setText("LAST SUCCESSFUL BUILD NUMBER");
                    lastSuccessfulBuildNumberHead.setTextColor(colorText);
                    lastSuccessfulBuildNumberHead.setBackgroundColor(colorBack);
                    lastSuccessfulBuildNumberHead.setLayoutParams(TViewParams);
                    llt.addView(lastSuccessfulBuildNumberHead);

                    TextView lastSuccessfulBuildNumberCont = new TextView(this);
                    lastSuccessfulBuildNumberCont.setText(lastSuccessfulBuildNumber);
                    lastSuccessfulBuildNumberCont.setLayoutParams(TViewParams);
                    llt.addView(lastSuccessfulBuildNumberCont);
                }

                final String nextBuildNumber = mapJobInfo.get("nextBuildNumber");
                //String lastCompletedBuildUrl = mapJobInfo.get("lastCompletedBuildUrl");
                //String lastFailedBuildUrl = mapJobInfo.get("lastFailedBuildUrl");
                //String lastStableBuildUrl = mapJobInfo.get("lastStableBuildUrl");
                //String lastSuccessfulBuildUrl = mapJobInfo.get("lastSuccessfulBuildUrl");

                Button btn = new Button(this);
                btn.setText("BUILD NOW");
                String idElement = "666";
                int id = Integer.parseInt(idElement);
                btn.setId(id);
                LinearLayout.LayoutParams lButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                btn.setLayoutParams(lButtonParams);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int resRunningBuils = GetData.buildNowRespcode(urlJob);
                        if(resRunningBuils == 201){
                            Toast toast = Toast.makeText(getApplicationContext(),"BUILD NUMBER " + nextBuildNumber + "RUN", Toast.LENGTH_SHORT);
                            toast.show();
                        }else {
                            Toast toast = Toast.makeText(getApplicationContext(),"BUILD NUMBER " + nextBuildNumber + "RUN ERROR", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });


                llt.addView(btn);


            }
            else{
                Log.e(CLASS, "ERROR CREATE INFO JOB");
                TextView contentError = new TextView(this);
                contentError.setText("ERROR SHOW INFO JOB");
                contentError.setLayoutParams(TViewParams);
                llt.addView(contentError);
            }

        }catch (Exception e){
            Log.e(CLASS, "ERROR CREATE INFO JOB FOR VIEW");
            TextView contentError = new TextView(this);
            contentError.setText("ERROR SHOW INFO JOB");
            contentError.setLayoutParams(TViewParams);
            llt.addView(contentError);
            e.printStackTrace();
        }

    }
}
