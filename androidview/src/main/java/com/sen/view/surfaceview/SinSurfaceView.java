package com.sen.view.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by wangshengxing on 16/6/13.
 */
public class SinSurfaceView extends SurfaceView implements SurfaceHolder.Callback ,Runnable{

    private Canvas mCanvas;
    private boolean mIsDrawing;

    private SurfaceHolder mHolder;
    public SinSurfaceView(Context context) {
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing){
            sinDraw();
        }
    }

    public void sinDraw(){

    }
}
