package build.builder.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import java.io.*;

import android.widget.Toast;

/**
 * Created by Ivan Nikerin [mail:fenderbas666@gmail.com]
 */
public class WriterConfigParams extends main {

    final String CLASS = "WriterConfigParams";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String descHostJenkins = "jenkins.host";
        final String descUserJenkins = "jenkins.login";
        final String descTokenJenkins = "jenkins.token";

        String outfilename = "config.properties";

        File outfile = new File(getApplicationContext().getFilesDir().getPath(), outfilename);

        Boolean existFile = outfile.exists();

        if(existFile){
            outfile.delete();
        }
        else{
            Log.d(CLASS, "file config not Fount");
        }

        try
        {
            String jenkinsHost = getIntent().getExtras().getString("jenkinsHostParam");
            String jenkinsLogin = getIntent().getExtras().getString("jenkinsLoginParam");
            String jenkinsToken = getIntent().getExtras().getString("jenkinsTokenParam");

            String userToken = jenkinsLogin + ":" + jenkinsToken;

            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            AutorizationsJenkins auth = new AutorizationsJenkins();

            int respAuthCode = auth.getCodeAuth(jenkinsHost,userToken);

            if(respAuthCode == 200) {


                FileWriter fw = new FileWriter(outfile);

                BufferedWriter bw = new BufferedWriter(fw);

                PrintWriter pw = new PrintWriter(bw);

                pw.println(descHostJenkins + "=" + jenkinsHost);
                pw.println(descUserJenkins + "=" + jenkinsLogin);
                pw.println(descTokenJenkins + "=" + jenkinsToken);

                pw.close();

                Intent intObj = new Intent(getApplicationContext(), ExistConfigFileAuth.class);
                startActivity(intObj);
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(),"ERROR AUTH CODE RESP IS " +respAuthCode, Toast.LENGTH_SHORT);
                 toast.show();

            }
        }
        catch(IOException e)
        {
            Log.e("CLASS", "Error! Output file does already exist! You will overwrite"
                    + " this file!");
        }



    }

}
