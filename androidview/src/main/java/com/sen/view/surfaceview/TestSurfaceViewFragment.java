package com.sen.view.surfaceview;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sen.view.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestSurfaceViewFragment extends Fragment {

    private ViewGroup mMainView;
    public TestSurfaceViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = (ViewGroup) inflater.inflate(R.layout.fragment_test_surface_view, container, false);
        initData();
        initView();
        return mMainView;
    }
    public void initData(){

    }

    public void initView(){

    }

}
