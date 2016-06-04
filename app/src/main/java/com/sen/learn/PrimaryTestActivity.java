package com.sen.learn;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sen.internetlib.fragment.TestOkhttpFragment;
import com.sen.learn.fragment.AmapWebRoadConditionFragment;
import com.sen.learn.fragment.CardLayoutFragment;
import com.sen.view.fragment.CanvasFragment;
import com.sen.view.fragment.CanvasLineFragment;
import com.sen.view.fragment.CanvasPathFragment;
import com.sen.view.fragment.LayoutAnimationFragment;
import com.sen.view.fragment.LinearGradientFragment;
import com.sen.view.fragment.PathPointsFragment;
import com.sen.view.fragment.RectBitmapFragment;


public class PrimaryTestActivity extends AppCompatActivity {

    PowerManager powerManager = null;
    PowerManager.WakeLock wakeLock = null;

    private View mMainView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater();
        setContentView(R.layout.activity_primary_test);
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
            fragment = new LinearGradientFragment();
            manager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
