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

    Bitmap mStrategyRouteBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);

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



        int height = 500;
        int width = 500;

        List<Point> newPointList = scalePonitPath(500,500,mPathPoints);
        if (newPointList != null) {
            Bitmap bitmap = updatePathBitmap(width,height,newPointList, mStrategyRouteBitmap);
            drawPathText(bitmap);
            mStrategyRouteView.setImageBitmap(bitmap);
        }


    }
    private  Bitmap drawPathText(Bitmap bitmap){

        Canvas canvas = new Canvas(bitmap);
        Paint textPaint = new TextPaint();
        textPaint.setStrokeWidth(10);
        textPaint.setTextSize(20);
        textPaint.setColor(Color.WHITE);
        canvas.drawText("师哥",0,500,textPaint);
        HaloLogger.logI(TAG,"bitmap的宽高"+bitmap.getWidth()+" "+bitmap.getHeight());
        return bitmap;
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
        HaloLogger.logI(TAG,"源数据点为："+points);
        HaloLogger.logI(TAG,"源数据左右上下的宽度分别为："+maxLeft+", "+maxRight+", "+maxTop+", "+maxBottom);
        HaloLogger.logI(TAG,"源数据左上参考点为："+refPoint.x+", "+refPoint.y);
        HaloLogger.logI(TAG,"转换后点："+newPointList);


        return newPointList;
    }

    private Bitmap updatePathBitmap(int loads, int height,List<Point> points,Bitmap bitmap){

        mPathPaint.setColor(Color.BLUE);
        mPathPaint.setStrokeWidth(10);
        mPathPaint.setStyle(Paint.Style.STROKE);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        canvas.drawColor(Color.BLACK);

        Point startPoint = points.get(0);
        Point endPoint = points.get(points.size()-1);

        Paint textPaint = new TextPaint();
        textPaint.setStrokeWidth(10);
        textPaint.setTextSize(20);
        textPaint.setColor(Color.WHITE);
//        canvas.drawText("无名路",20,20,textPaint);

        int colorCnt = 0;
        final String[] LOADS = new String[]{"深南大道","南海大道","学府路","滨海大道"};

        int step = points.size()/5;//points.size()/20
        int index = 0;
        for (int j = 0; j <points.size()/step ; j=j+1) {
            Path path = new Path();
            Point moveTo = points.get(index);
            path.moveTo(moveTo.x,moveTo.y);
            for (int i = 0; i <step ; i=i+1) {

                if(++index >=points.size()){
                    break;
                }
                Point toPoint = points.get(index);
                path.lineTo(toPoint.x,toPoint.y);
            }
            if(Math.random()>0.7){
                canvas.drawTextOnPath(LOADS[(int)((LOADS.length-1)*Math.random())],path,0,20,textPaint);
                if(++colorCnt>2){
                    colorCnt=0;
                    mPathPaint.setColor(Color.RED);

                }
            }
            canvas.drawPath(path,mPathPaint);
            mPathPaint.setColor(Color.BLUE);
        }

        canvas.drawText("起点",startPoint.x,startPoint.x,textPaint);
        canvas.drawText("终点",endPoint.x,endPoint.x,textPaint);

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
