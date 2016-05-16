package hud.haliai.com.share;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class BaseToolbarActivity extends AppCompatActivity {


    public String mToolbarTitle = "";
    //VIEW
    private Toolbar mToolbar;


    public void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.common_tool_bar);
        TextView toolTitle = (TextView)findViewById(R.id.toolbar_title);
        toolTitle.setText(mToolbarTitle);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
