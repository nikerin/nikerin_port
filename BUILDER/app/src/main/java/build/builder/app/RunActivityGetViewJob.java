package build.builder.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivan Nikerin [mail:fenderbas666@gmail.com]
 */
public class RunActivityGetViewJob extends main {
    LinearLayout llt;
    RelativeLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_views);

        content = (RelativeLayout) findViewById(R.id.list_view);

        llt = (LinearLayout) findViewById(R.id.container);

        ConfigDataParam configParam = ConfigDataParam.getInstance();

        HashMap param = configParam.getParamConfig();
        String hostJenkins = (String) param.get("jenkins.host");
        String userToken = (String) param.get("user.token");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Map<String, ArrayList> listViewJobs = GetData.getMapListViews(hostJenkins,userToken);
        LinearLayout.LayoutParams TViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView head = new TextView(this);
        head.setText("LIST VIEW");
        head.setLayoutParams(TViewParams);
        content.addView(head);

        for(Map.Entry<String, ArrayList> e : listViewJobs.entrySet()) {
            final String content = e.getKey();
            ArrayList listViewParams = listViewJobs.get(content);
            String idElenent = (String) listViewParams.get(1);
            final String urlView = (String) listViewParams.get(0);
            Button btn = new Button(this);
            btn.setText(content);
            int id = Integer.parseInt(idElenent);

            btn.setId(id);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intObj = new Intent(getApplicationContext(), RunActivityGetJobsList.class);
                    intObj.putExtra("url_view", urlView);

                    startActivity(intObj);
                }
            });

            LinearLayout.LayoutParams lButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT);
            btn.setLayoutParams(lButtonParams);
            llt.addView(btn);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_job, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
