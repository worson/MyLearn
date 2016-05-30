package com.sen.learn;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sen.internetlib.fragment.TestOkhttpFragment;
import com.sen.learn.fragment.CardLayoutFragment;
import com.sen.view.fragment.CanvasFragment;
import com.sen.view.fragment.CanvasLineFragment;
import com.sen.view.fragment.CanvasPathFragment;
import com.sen.view.fragment.LayoutAnimationFragment;
import com.sen.view.fragment.PathPointsFragment;
import com.sen.view.fragment.RectBitmapFragment;


public class PrimaryTestActivity extends AppCompatActivity {

    private View mMainView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater();
        setContentView(R.layout.activity_primary_test);
        mMainView = findViewById(R.id.fragment_container);
        startFragment();
    }

    private void startFragment(){
//        FragmentManager manager = getSupportFragmentManager();
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new TestOkhttpFragment();
            manager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
