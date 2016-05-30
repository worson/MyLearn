package com.sen.view.fragment;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sen.view.R;
import com.sen.view.mappath.RectBitmap;

import hud.haliai.com.share.utils.HaloLogger;

/**
 * A simple {@link Fragment} subclass.
 */
public class RectBitmapFragment extends Fragment {

    private static final String TAG = RectBitmapFragment.class.getName();
    private static final String WSX = "wangshengxing";
    private ViewGroup mMainView;

    public RectBitmapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = (ViewGroup)inflater.inflate(R.layout.fragment_rect_bitmap, container, false);
        final RectBitmapView rectBitmapView = new RectBitmapView(getContext());
        Button button = new Button(getContext());
        button.setText("测试RectBitmap");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rectBitmapView.testAddRect();
                HaloLogger.logI(WSX,"按键按下");
            }
        });
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mMainView.addView(button);
        rectBitmapView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mMainView.addView(rectBitmapView);

        return mMainView;
    }
    class RectBitmapView extends View{
        RectBitmap mRectBitmap;
        public RectBitmapView(Context context) {
            super(context);


        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (mRectBitmap == null) {
                mRectBitmap = new RectBitmap(canvas,500,500);
                HaloLogger.logI(WSX,"初始化RectBitmap");
            }


        }

        private void testAddRect(){
            HaloLogger.logI(WSX,"更新RectBitmap");
            mRectBitmap.pushRect(new Rect(-100,10,100,100));
//            mRectBitmap.refreshView();
        }
    }

}
