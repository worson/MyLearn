package com.sen.view.fragment;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.sen.view.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import hud.haliai.com.share.utils.HaloLogger;

/**
 * A simple {@link Fragment} subclass.
 */
public class CanvasLineFragment extends Fragment {

    private static final String TAG = CanvasLineFragment.class.getName();

    private Button mRedrawButton;
    private ViewGroup mMainView;
    private ImageView mStrategyRouteView;

    public CanvasLineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = (ViewGroup)inflater.inflate(R.layout.fragment_canvas_line, container, false);
        mStrategyRouteView = (ImageView)mMainView
                .findViewById(R.id.amap_strategy_route_image);
        mRedrawButton = (Button)mMainView.findViewById(R.id.redraw_button);
        mRedrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePath();
            }
        });
        //updatePath();
        return mMainView;
    }

    private void updatePath(){
        mPathPoints.clear();
        mPathPoints.add(new Point(0,0));
        mPathPoints.add(new Point(100,0));
        mPathPoints.add(new Point(0,100));
        mPathPoints.add(new Point(50,-50));
        mPathPoints.add(new Point(100,100));
        mPathPoints.add(new Point(0,0));
        mPathPoints.add(new Point(200,0));
        mPathPoints.add(new Point(200,200));


        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mStrategyRouteView.measure(w, h);
        int height = mStrategyRouteView.getMeasuredHeight();
        int width = mStrategyRouteView.getMeasuredWidth();

        List<Point> newPointList = scalePonitPath(500,500,mPathPoints);
        Bitmap srcBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Bitmap bitmap = updatePathBitmap(width,height,newPointList,srcBitmap);
        mStrategyRouteView.setImageBitmap(bitmap);


    }

    private List<Point> mPathPoints = new ArrayList<Point>();
    private Paint mPathPaint = new Paint(Color.YELLOW);

    private List<Point> scalePonitPath(int width, int height, List<Point> points){

        if (points == null || width ==0 || height ==0) {
            return null;
        }
        List<Point> newPointList = new LinkedList<Point>();
        final int alignMode = 0;
        int maxLeft,maxTop,maxRight,maxBottom;//取名风格以左下为参照
        int maxLeftIndex,maxTopIndex,maxRightIndex,maxBottomIndex;
        int pointWidth,pointHeight,widthMove,heightMove;
        final int paddingWidth=10,paddingHeight=10;
        int index = 0;
        boolean isHeightScale = true;//缩放后较小的进行居中处理，true时表示高度
        double widthScalefactor = 1, heightScalefactor =1,scalefactor =1;

        maxLeft = points.get(0).x;
        maxRight = maxLeft;
        maxTop = points.get(0).y;
        maxBottom = maxTop;
        for (Point point: points) {
//            maxLeft = Math.min(point.x,maxLeft);
//            maxRight = Math.max(point.x,maxRight);
//            maxTop = Math.max(point.y,maxTop);
//            maxBottom = Math.min(point.y,maxBottom);
            if (point.x < maxLeft){
                maxLeft = point.x;
                maxLeftIndex = index;
            }
            if (point.x>maxRight){
                maxRight=point.x;
                maxRightIndex = index;
            }
            if (point.y>maxTop){
                maxTop = point.y;
                maxTopIndex = index;
            }
            if (point.y<maxBottom){
                maxBottom = point.y;
                maxBottomIndex = index;
            }
            index++;//放到最后

        }
        Point refPoint = new Point(maxLeft,maxBottom);//
        pointWidth = maxRight-maxLeft;
        pointHeight = maxTop - maxBottom;
        widthScalefactor = (pointWidth*1.0)/(width-2*paddingWidth);
        heightScalefactor = (pointHeight*1.0)/(height-2*paddingHeight);
        if(widthScalefactor==0 ||widthScalefactor==0){
            return null;
        }
        //缩放比例要大的一个，说明缩放还几乎满屏，另一个则要居中处理
        if(widthScalefactor>heightScalefactor){
            scalefactor = widthScalefactor;
            widthMove = 0;
            heightMove = ((height-2*paddingHeight)-((int)(pointHeight/scalefactor)))/2;
        }else{
            scalefactor = heightScalefactor;
            widthMove = ((width-2*paddingWidth)-((int)(pointWidth/scalefactor)))/2;
            heightMove = 0;
        }

        widthMove = widthMove+paddingWidth;
        heightMove = heightMove+paddingHeight;
        for(Point point: points){
            int newX = (int)((point.x-refPoint.x)/scalefactor)+widthMove;
            int newY = (int)((point.y-refPoint.y)/scalefactor)+heightMove;
            Point newPoint = new Point(newX,newY);
            newPointList.add(newPoint);
        }
        HaloLogger.logI(TAG,"源数据点为："+points.get(0)+points.get(points.size()-1));
        HaloLogger.logI(TAG,"源数据左右上下的宽度分别为："+maxLeft+", "+maxRight+", "+maxTop+", "+maxBottom);
        HaloLogger.logI(TAG,"源数据左上参考点为："+refPoint.x+", "+refPoint.y);
        HaloLogger.logI(TAG,"转换后参考点："+refPoint.x+", "+refPoint.y);


        return newPointList;
    }

    private Bitmap updatePathBitmap(int width, int height,List<Point> points,Bitmap bitmap){
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        canvas.drawColor(Color.BLACK);
        Path path = new Path();
        Point startPoint = points.get(0);
        Point endPoint = points.get(points.size()-1);
        path.moveTo(startPoint.x,startPoint.y);
        int step = 1;//points.size()/20
        for (int i = 1; i <points.size() ; i=i+step) {
            Point toPoint = points.get(i);
            path.lineTo(toPoint.x,toPoint.y);

        }


        Paint textPaint = new TextPaint();
        textPaint.setStrokeWidth(10);
        textPaint.setTextSize(20);
        textPaint.setColor(Color.WHITE);
        canvas.drawText("无名路",20,20,textPaint);


        canvas.drawTextOnPath("深南大道",path,20,50,textPaint);
        canvas.drawPath(path,updatePaint());

        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(startPoint.x,startPoint.y,6,paint);
        canvas.drawCircle(endPoint.x,endPoint.y,6,paint);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(startPoint.x,startPoint.y,10,paint);
        canvas.drawCircle(endPoint.x,endPoint.y,10,paint);
        return bitmap;
    }

    private Paint updatePaint(){
        mPathPaint.setColor(Color.BLUE);
        mPathPaint.setStrokeWidth(4);
        mPathPaint.setStyle(Paint.Style.STROKE);
        return mPathPaint;
    }

}
