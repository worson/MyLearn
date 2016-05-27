package com.sen.view.fragment;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sen.view.R;

import java.util.ArrayList;
import java.util.List;

import hud.haliai.com.share.utils.HaloLogger;

/**
 * A simple {@link Fragment} subclass.
 */
public class CanvasPathFragment extends Fragment {

    private static final String TAG = "wangshengxing";
    private ViewGroup mMainView;

    public CanvasPathFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = (ViewGroup)inflater.inflate(R.layout.fragment_canvas_path, container, false);
        View view = new LinePathView(getContext());
        mMainView.addView(view);
        return mMainView;
    }

    class LinePathView extends View{
        public LinePathView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
//            testPath(canvas);
            testLineRect(canvas);
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        private void testPath(Canvas canvas){
            Paint paint = new Paint();
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
//            Path firstPath = getRectPath(new Point(0,0),100,100);
            Path firstPath = getRectPath(new Point(0,0),40,40);
            Path secondPath = getRectPath(new Point(60,60),100,100);
//            Path firstPath = getLinePath(new Point(10,0),new Point(10,100));
//            Path secondPath = getLinePath(new Point(200,0),new Point(200,100));
            canvas.drawPath(firstPath,paint);
            paint.setColor(Color.BLUE);
            canvas.drawPath(secondPath,paint);
            if(firstPath.op(secondPath,Path.Op.INTERSECT)){
                paint.setColor(Color.RED);
                canvas.drawPath(firstPath,paint);
                if(firstPath.isEmpty()){
                    HaloLogger.logI(TAG,"绘制文本："+"PATH不相交");
                }else {
                    HaloLogger.logI(TAG,"绘制文本："+"PATH相交");
                }
            }else{

            }

        }
        /**
         * 测试线与矩形的相交
         *
         * **/
        private void testLineRect(Canvas canvas){
            Paint paint = new Paint();
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
//            Path firstPath = getRectPath(new Point(0,0),100,100);
            Path firstPath = new Path();
            firstPath.moveTo(80,0);
            firstPath.lineTo(80,200);
            Path secondPath = getRectPath(new Point(60,60),100,100);
//            Path firstPath = getLinePath(new Point(10,0),new Point(10,100));
//            Path secondPath = getLinePath(new Point(200,0),new Point(200,100));
            canvas.drawPath(firstPath,paint);
            paint.setColor(Color.BLUE);
            canvas.drawPath(secondPath,paint);
            if(firstPath.op(secondPath,Path.Op.INTERSECT)){
                paint.setColor(Color.RED);
                canvas.drawPath(firstPath,paint);
                if(firstPath.isEmpty()){
                    HaloLogger.logI(TAG,"绘制文本："+"PATH不相交");
                }else {
                    HaloLogger.logI(TAG,"绘制文本："+"PATH相交");
                }
            }else{

            }

        }
        private Path getLinePath(Point firstPoint, Point secondPoint) {
            Path path = new Path();
            path.moveTo(firstPoint.x, firstPoint.y);
            path.lineTo(secondPoint.x, secondPoint.y);
            return path;
        }
        // TODO: 16/5/23 未实现
        private Path getRectPath(Point refPoint, int width, int height) {
            List<Point> points = new ArrayList<>();
            points.add(new Point(refPoint.x, refPoint.y));
            points.add(new Point(refPoint.x + width, refPoint.y));
            points.add(new Point(refPoint.x + width, refPoint.y + height));
            points.add(new Point(refPoint.x, refPoint.y + height));
//        points.add(new Point(refPoint.x,refPoint.y));
            Path path = new Path();

            Point moveTo = points.get(0);
            path.moveTo(moveTo.x, moveTo.y);
            for (int i = 1; i < points.size(); i = i + 1) {
                Point toPoint = points.get(i);
                path.lineTo(toPoint.x, toPoint.y);
            }
            path.close();
//        HaloLogger.logI(TAG,"getRectPath ：path is "+path);
            return path;
        }
    }
}


