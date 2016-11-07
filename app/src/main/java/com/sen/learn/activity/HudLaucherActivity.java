package com.sen.learn.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sen.learn.R;


import hud.haliai.com.hudlib.fragment.HudSpeechPanelFragment;

public class HudLaucherActivity extends AppCompatActivity {

    PowerManager powerManager = null;
    PowerManager.WakeLock wakeLock = null;

    private View mMainView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater();
        setContentView(R.layout.activity_hud_laucher);
        mMainView = findViewById(R.id.fragment_container);
        startFragment();
        powerManager = (PowerManager)this.getSystemService(this.POWER_SERVICE);
        wakeLock = this.powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");
    }
    @Override
    protected void onResume() {
        super.onResume();
        wakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        wakeLock.release();
    }

    private void startFragment(){
//        FragmentManager manager = getSupportFragmentManager();
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new HudSpeechPanelFragment();
            manager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

}
