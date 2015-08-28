package build.builder.app;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Ivan Nikerin [mail:fenderbas666@gmail.com]
 */
public class RunActivityJobAction extends main {
    LinearLayout llt;
    RelativeLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_job_action);

        content = (RelativeLayout) findViewById(R.id.job_action);

        llt = (LinearLayout) findViewById(R.id.container);





    }
}
