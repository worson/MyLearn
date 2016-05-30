package com.sen.view.fragment;


import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sen.view.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LayoutAnimationFragment extends Fragment {

    private ViewGroup mMainView;
    private Context mContext;

    private int mViewCnt = 0;

    public LayoutAnimationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = (ViewGroup)inflater.inflate(R.layout.fragment_layout_animation, container, false);
//        setLayoutAnimation(mMainView);
        setLayoutTransition(mMainView);
        mContext = getContext();
        Button button = new Button(mContext);
        button.setText("增加View");
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                newView();
            }
        });
        mMainView.addView(button);

        return mMainView;
    }

    private void setLayoutAnimation(ViewGroup viewGroup){
        ScaleAnimation sa = new ScaleAnimation(0,1,0,1);
        AlphaAnimation aa = new AlphaAnimation(0,1);
        RotateAnimation ra = new RotateAnimation(0,360,100,100);
        sa.setDuration(2000);
        aa.setDuration(2000);
        ra.setDuration(2000);
        LayoutAnimationController lac = new LayoutAnimationController(ra,0.5F);
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        viewGroup.setLayoutAnimation(lac);
    }

    private void newView(){
        mViewCnt++;
        TextView textView = new TextView(mContext);
        textView.setText("您好，魔方: "+mViewCnt);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        mMainView.addView(textView,1);
    }

    private void setLayoutTransition(ViewGroup viewGroup){
//        LayoutTransition transitioner = new LayoutTransition();
//        ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "rotation", 0f, 90f, 0f);
//        transitioner.setAnimator(LayoutTransition.DISAPPEARING, animOut);
//        viewGroup.setLayoutTransition(transitioner);

        LayoutTransition mTransitioner = new LayoutTransition();
        //入场动画:view在这个容器中消失时触发的动画
        ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "rotationY", 0f, 360f,0f);
        mTransitioner.setAnimator(LayoutTransition.APPEARING, animIn);

        //出场动画:view显示时的动画
        ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "rotation", 0f, 90f, 0f);
        mTransitioner.setAnimator(LayoutTransition.DISAPPEARING, animOut);

        viewGroup.setLayoutTransition(mTransitioner);
    }

}
