package build.builder.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import android.view.View;
import android.widget.*;

/**
 * Created by Ivan Nikerin [mail:fenderbas666@gmail.com]
 */
public class main extends ActionBarActivity {
    LinearLayout llt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_autorization_jen);

        llt = (LinearLayout) findViewById(R.id.container);

        LinearLayout.LayoutParams TViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView hostJenkins = new TextView(this);
        hostJenkins.setText("HOST JENKINS");
        hostJenkins.setLayoutParams(TViewParams);
        llt.addView(hostJenkins);

        final EditText hostJenkinsIn = new EditText(this);
        hostJenkinsIn.setLayoutParams(TViewParams);
        llt.addView(hostJenkinsIn);

        final TextView loginJenkins = new TextView(this);
        loginJenkins.setText("LOGIN JENKINS");
        loginJenkins.setLayoutParams(TViewParams);
        llt.addView(loginJenkins);

        final EditText loginJenkinsIn = new EditText(this);
        loginJenkinsIn.setLayoutParams(TViewParams);
        llt.addView(loginJenkinsIn);

        TextView tokenJenkins = new TextView(this);
        tokenJenkins.setText("TOKEN JENKINS");
        tokenJenkins.setLayoutParams(TViewParams);
        llt.addView(tokenJenkins);

        final EditText tokenJenkinsIn = new EditText(this);
        tokenJenkinsIn.setLayoutParams(TViewParams);
        llt.addView(tokenJenkinsIn);

        Button btn = new Button(this);
        btn.setText("LOG IN");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intObj = new Intent(getApplicationContext(), WriterConfigParams.class);
                String jenkinsHostParam = hostJenkinsIn.getText().toString();
                String jenkinsLoginParam = loginJenkinsIn.getText().toString();
                String jenkinsTokenParam = tokenJenkinsIn.getText().toString();
                intObj.putExtra("jenkinsHostParam", jenkinsHostParam);
                intObj.putExtra("jenkinsLoginParam", jenkinsLoginParam);
                intObj.putExtra("jenkinsTokenParam", jenkinsTokenParam);

                startActivity(intObj);
            }
        });

        LinearLayout.LayoutParams lButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT);
        btn.setLayoutParams(lButtonParams);
        llt.addView(btn);


        }
    }
