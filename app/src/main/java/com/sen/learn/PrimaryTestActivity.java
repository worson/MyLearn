package com.sen.learn;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sen.learn.fragment.CardLayoutFragment;
import com.sen.view.fragment.CanvasFragment;
import com.sen.view.fragment.CanvasLineFragment;
import com.sen.view.fragment.CanvasPathFragment;
import com.sen.view.fragment.LayoutAnimationFragment;


public class PrimaryTestActivity extends AppCompatActivity {

    private View mMainView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_test);
        mMainView = findViewById(R.id.fragment_container);
        startFragment();
    }

    private void startFragment(){
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new CanvasLineFragment();
            manager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
