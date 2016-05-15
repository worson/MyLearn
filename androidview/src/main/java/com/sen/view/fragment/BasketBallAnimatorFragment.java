package com.sen.view.fragment;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.sen.view.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BasketBallAnimatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasketBallAnimatorFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = BasketBallAnimatorFragment.class.getSimpleName();

    private static final int[] ONCLICK_IDS = new int[]{R.id.paowuxian_button, R.id.verticalRun_button, R.id.simple_together_button, R.id.order_together_button};

    public BasketBallAnimatorFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private View mMainView;
    private int mScreenHeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_basket_ball_animator, container, false);
        mScreenHeight = mMainView.getHeight();
        // Inflate the layout for this fragment
        intview();
        return mMainView;
    }

    private ImageView mBasketBall;

    private void intview() {
        mBasketBall = (ImageView) mMainView.findViewById(R.id.basket_ball_imageview);
        for (int i = 0; i < ONCLICK_IDS.length; i++) {
            View view = mMainView.findViewById(ONCLICK_IDS[i]);
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
//        if ()v.getId()) {
//            case R.id.paowuxian_button:
//                paowuxian(null);
//                break;
//            case R.id.verticalRun_button:
//                verticalRun(null);
//                break;
//            default:
//                break;
//        }
        int id = v.getId();
        if (id == R.id.paowuxian_button) {
            paowuxian(null);
        } else if (id == R.id.verticalRun_button) {
            verticalRun(null);
        } else if (id == R.id.simple_together_button) {
            togetherRun(null);
        } else if (id == R.id.order_together_button) {
            playWithAfter(null);
        }
    }

    /**
     * 自由落体
     *
     * @param view
     */
    public void verticalRun(View view) {
        mScreenHeight = mMainView.getHeight();
        ValueAnimator animator = ValueAnimator.ofFloat(0, mScreenHeight
                - mBasketBall.getHeight());
        animator.setTarget(mBasketBall);
        animator.setDuration(1000).start();
//      animator.setInterpolator(value)
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBasketBall.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
    }

    /**
     * 抛物线
     *
     * @param view
     */
    public void paowuxian(View view) {

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            // fraction = t / duration
            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue) {
                Log.e(TAG, fraction * 3 + "");
                // x方向200px/s ，则y方向0.5 * 10 * t
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                mBasketBall.setX(point.x);
                mBasketBall.setY(point.y);

            }
        });
    }

    public void togetherRun(View view) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(mBasketBall, "scaleX",
                1.0f, 2f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(mBasketBall, "scaleY",
                1.0f, 2f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(2000);
        animSet.setInterpolator(new LinearInterpolator());
        //两个动画同时执行
        animSet.playTogether(anim1, anim2);
        animSet.start();
    }

    public void playWithAfter(View view) {
        float cx = mBasketBall.getX()<100?600: mBasketBall.getX();

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(mBasketBall, "scaleX",
                1.0f, 2f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(mBasketBall, "scaleY",
                1.0f, 2f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(mBasketBall,
                "x", cx, 0f);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(mBasketBall,
                "x", cx);

        /**
         * anim1，anim2,anim3同时执行
         * anim4接着执行
         */
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim1).with(anim2);
        animSet.play(anim2).with(anim3);
        animSet.play(anim4).after(anim3);
        animSet.setDuration(1000);
        animSet.start();
    }
}


