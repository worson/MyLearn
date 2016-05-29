package com.sen.view.fragment;


import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.sen.view.R;
import com.sen.view.mappath.PathRectManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import hud.haliai.com.share.utils.HaloLogger;

/**
 * A simple {@link Fragment} subclass.
 */
public class CanvasLineFragment extends Fragment {

    private static final String TAG = CanvasLineFragment.class.getName();
    private static final boolean PATH_DEBUG_MODE = true;
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


    private void initPath(int strategy,List<Point> pointList){
        pointList.clear();
        textPoints.clear();
        loadNames.clear();
        switch (strategy){
            case 0:
                mPathPoints.add(new Point(0,0));
                mPathPoints.add(new Point(50,0));
                mPathPoints.add(new Point(50,50));
                mPathPoints.add(new Point(0,50));
                mPathPoints.add(new Point(0,100));
                mPathPoints.add(new Point(0,150));
                mPathPoints.add(new Point(50,150));
                mPathPoints.add(new Point(50,200));

                textPoints.add(new Point(50,30));
                textPoints.add(new Point(30,105));
                textPoints.add(new Point(30,100));
                break;
            case 1:
                mPathPoints.add(new Point(0,0));
                mPathPoints.add(new Point(50,0));
                mPathPoints.add(new Point(50,50));
                mPathPoints.add(new Point(0,50));
                mPathPoints.add(new Point(0,100));
                mPathPoints.add(new Point(0,150));
                mPathPoints.add(new Point(50,150));
                mPathPoints.add(new Point(100,200));

                textPoints.add(new Point(20,100));
                textPoints.add(new Point(25,100));
                textPoints.add(new Point(35,100));
                textPoints.add(new Point(40,100));
                textPoints.add(new Point(40,50));
                break;
            case 2:
                break;
            default:
                break;
        }
        loadNames.addAll(Arrays.asList(new String[]{"深南大道立交桥","南海大道","学府路","滨海大道","无名路","107国道"}));

    }

    private void updatePath(){
        initPath(0,mPathPoints);
        int height = 500;
        int width = 500;

        RectMapPara mRectMapPara = measureRect(500,500,mPathPoints);
        List<Point> newPointList = rectRemap(mPathPoints,mRectMapPara);

        List<Path> undrawPaths= new ArrayList<>();
        Path path1 = new Path();
        path1.addRect(0,0,width,height, Path.Direction.CCW);
        path1.addRect(width-200,height-70,width,height, Path.Direction.CCW);
        if (newPointList != null) {
            Bitmap srcBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);;
            Bitmap bitmap = drawPath(newPointList,srcBitmap);
//            drawPathText(rectRemap(textPoints,mRectMapPara),loadNames,3,bitmap);
            drawPathText(rectRemap(textPoints,mRectMapPara),loadNames,bitmap);
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
    private List<Point> textPoints = new ArrayList<Point>();
    private List<String> loadNames = new ArrayList<>();

    private Paint mPathPaint = new Paint(Color.YELLOW);

    private class RectMapPara
    {

        public Point refPoint;
        public double scalefactor;
        public int widthMove;
        public int heightMove;
    }

    PathRectManager mPathRectManager = new PathRectManager();

    private List<Point> rectRemap(List<Point> points,RectMapPara rectMapPara){
        Point refPoint = rectMapPara.refPoint;
        double scalefactor = rectMapPara.scalefactor;
        int widthMove = rectMapPara.widthMove;
        int heightMove = rectMapPara.heightMove;
        List<Point> newPointList = new LinkedList<Point>();
        for(Point point: points){
            int newX = (int)((point.x-refPoint.x)/scalefactor)+widthMove;
            int newY = (int)((point.y-refPoint.y)/scalefactor)+heightMove;
            Point newPoint = new Point(newX,newY);
            newPointList.add(newPoint);
        }
        return newPointList;
    }

    private RectMapPara measureRect(int width, int height, List<Point> points){
        if (points == null || width ==0 || height ==0) {
            return null;
        }
        List<Point> newPointList = new LinkedList<Point>();
        final int alignMode = 0;
        int maxLeft,maxTop,maxRight,maxBottom;//取名风格以左下为参照
        int maxLeftIndex,maxTopIndex,maxRightIndex,maxBottomIndex;
        int pointWidth,pointHeight,widthMove,heightMove;
        final int paddingWidth=20,paddingHeight=20;
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

        RectMapPara rectMapPara = new RectMapPara();
        rectMapPara.refPoint = refPoint;
        rectMapPara.scalefactor = scalefactor;
        rectMapPara.heightMove = heightMove;
        rectMapPara.widthMove = widthMove;

        return  rectMapPara;
    }

    private void drawPathText(List<Point> points, List<String> loads, Bitmap bitmap){
        Canvas canvas = new Canvas(bitmap);
        Paint textPaint = new TextPaint();
        List<Path> drawPathList = new LinkedList<>();
        textPaint.setStrokeWidth(10);
        textPaint.setTextSize(24);
        textPaint.setColor(Color.WHITE);
        Point centerPoint = new Point(bitmap.getWidth()/2,bitmap.getHeight()/2);
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        int textHeight = (int) (Math.ceil(fm.descent - fm.ascent));
        Path intersectPath = new Path();

        Paint markPaint = new Paint();
        markPaint.setColor(Color.RED);
        markPaint.setStrokeWidth(3);
        markPaint.setStyle(Paint.Style.FILL);


        mPathRectManager.drawUndrawRegion(canvas,textPaint);
        List<PathRectManager.RectRequest> rectRequests = new ArrayList<>();

        mPathRectManager.drawUndrawRegion(canvas,textPaint);
        //最长的路优先显示，优先取路的中间坐标点，多条路名左右交叉
        final int Cnt = Math.min(loads.size(),points.size());
        for (int i = 0; i < Cnt; i++) {
            String content = loads.get(i);
            if (content != null) {
                if(content.length()>6){
                    content = content.substring(0,5)+"...";
                }
                Point point = points.get(i);
                PathRectManager.RectRequest rectRequest= new PathRectManager.RectRequest();
                rectRequest.setType(PathRectManager.RectType.TextRect);
                rectRequest.setPoint(point);
                rectRequest.setMinRect(PathRectManager.getTextRect(content,textPaint));
                rectRequests.add(rectRequest);

                canvas.drawCircle(point.x,point.y,2,markPaint);
            }

        }
        List<PathRectManager.RectResponse> textRectResponses = mPathRectManager.findRect(rectRequests);
        Path textPath = new Path();
        for (int i = 0; i < textRectResponses.size(); i++){
            PathRectManager.RectResponse rectResponse = textRectResponses.get(i);
            Rect textRect = rectResponse.getRect();
            String content = loads.get(rectResponse.getIndex());
            if(content.length()>6){
                content = content.substring(0,5)+"...";
            }
            if (textRect != null) {
                textPath.reset();
                textPath.moveTo(textRect.left,textRect.bottom);
                textPath.lineTo(textRect.right,textRect.bottom);
                canvas.drawTextOnPath(content,textPath,0,0,textPaint);
                if (PATH_DEBUG_MODE){
                    Paint paint = new Paint();
                    paint.setStrokeWidth(1);
                    paint.setColor(Color.RED);
                    paint.setStyle(Paint.Style.STROKE);
                    textPath.reset();
                    textPath.addRect(new RectF(textRect), Path.Direction.CCW);
                    canvas.drawPath(textPath,paint);
                }
            }

//            PathRectManager.drawRectText(content,textRect,textPaint);
        }
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private  Bitmap drawPathText(List<Point> points, List<String> loads, int counter, Bitmap bitmap){
        final boolean DEBUG_DRAW = false;
        final int TEXT_MARGIN_WIDTH = 15;
        final int TEXT_MARGIN_HEIGHT = 15;
        int moveH =0,moveV=0,baseX=0,baseY=0;
        Canvas canvas = new Canvas(bitmap);
        Paint textPaint = new TextPaint();
        List<Path> drawPathList = new LinkedList<>();
        textPaint.setStrokeWidth(10);
        textPaint.setTextSize(24);
        textPaint.setColor(Color.WHITE);
        Point centerPoint = new Point(bitmap.getWidth()/2,bitmap.getHeight()/2);
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        int textHeight = (int) (Math.ceil(fm.descent - fm.ascent));
        Path intersectPath = new Path();

        Paint markPaint = new Paint();
        markPaint.setColor(Color.RED);
        markPaint.setStrokeWidth(3);
        markPaint.setStyle(Paint.Style.FILL);


        //计算不能标注的位置
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        Path path1 = new Path();
        path1.addRect(0,0,width,height, Path.Direction.CCW);
        Path windowPath = new Path();
        windowPath.addRect(-10,-10,width+10,height+10, Path.Direction.CCW);
        windowPath.op(path1, Path.Op.DIFFERENCE);
        windowPath.addRect(width-200,height-70,width,height, Path.Direction.CCW);
        drawPathList.add(windowPath);

        //最长的路优先显示，优先取路的中间坐标点，多条路名左右交叉
        final int Cnt = Math.min(counter,points.size());
        for (int i = 0; i < Cnt; i++) {
            Point point = points.get(i);
            canvas.drawCircle(point.x,point.y,2,markPaint);
            String content = loads.get(i);
            if (content != null) {
                if(content.length()>6){
                    content = content.substring(0,5)+"...";
                }
                float tw = textPaint.measureText(content);
                float th = Math.abs(textPaint.descent() + textPaint.ascent());
                int pathtop = 0;
                HaloLogger.logI(TAG,"正在绘制文本："+i+" "+content+"..."+"长度为："+content.length()+" 长："+(int)tw+" 高:"+textHeight);
                for (int strategy = 0; strategy < 3; strategy++) {
                    //计算当前点与路径的大概关系
                    switch (strategy){
                        case 0://向右下生长
                            moveH = TEXT_MARGIN_WIDTH;
                            moveV = textHeight+TEXT_MARGIN_HEIGHT;
                            pathtop = point.y+TEXT_MARGIN_HEIGHT;
                            break;
                        case 1://向左下生长
                            moveH = -((int)textHeight+TEXT_MARGIN_WIDTH);
                            moveV = textHeight+TEXT_MARGIN_HEIGHT;
                            pathtop = point.y+TEXT_MARGIN_HEIGHT;
                            break;
                        case 2://右上生长
                            moveH = TEXT_MARGIN_WIDTH;
                            moveV = -(TEXT_MARGIN_HEIGHT);
                            pathtop = point.y-TEXT_MARGIN_HEIGHT-textHeight;
                            break;
                        default:
                            break;
                    }
                    //右上生成
                    baseX = point.x+moveH;
                    baseY = point.y+moveV;
                    //右上生长
                    Path currentPath = getTextPath(baseX,pathtop,baseX+(int)tw,pathtop+textHeight);
//                    Path currentPath = getTextPath(new Point(baseX,baseY),(int)tw,textHeight-moveH);
//                    Path currentPath = getRectPath(new Point(baseX,pathtop),(int)tw,-textHeight);
                    boolean calculateOk = false;
                    for (int pathIndext = 0; pathIndext < drawPathList.size(); pathIndext++){
                        Path path = drawPathList.get(pathIndext);
                        if (intersectPath.op(currentPath,path,Path.Op.INTERSECT)){//计算成功
                            if(intersectPath.isEmpty()) {//未相交  path.op(currentPath,Path.Op.INTERSECT)
                                if(pathIndext == (drawPathList.size()-1)){
                                    calculateOk = true;
                                }
                            }else {
                                HaloLogger.logI(TAG,"绘制文本："+content+"与PATH相交");
                                calculateOk = false;
                                break;
                            }

                        }else{
                            HaloLogger.logI(TAG,"相交计算不成功");
                        }
                    }
                    if(drawPathList.size()<=0){
                        calculateOk = true;
                    }
                    if (calculateOk){//测量成功
                        HaloLogger.logI(TAG,"完成绘制文本："+i+" "+content);
                        canvas.drawText(content,baseX,baseY,textPaint);
                        drawPathList.add(currentPath);

//                        canvas.drawTextOnPath(content,currentPath,0,0,textPaint);
                        canvas.drawCircle(point.x,point.y,2,markPaint);
                        if (DEBUG_DRAW){

                            Paint paint = new Paint();
                            paint.setStrokeWidth(2);
                            paint.setStyle(Paint.Style.STROKE);
                            paint.setColor(Color.RED);
                            canvas.drawPath(currentPath,paint);

                            Path line = new Path();
                            line.moveTo(point.x,point.y);
                            line.lineTo(point.x+tw,point.y);
                            canvas.drawPath(line,paint);
                        }
                        break;
                    }
                }

            }

        }
        return bitmap;
    }

    //右上生成
    private Path getTextPath(float left, float top, float right, float bottom){

        Path path = new Path();
        //float left, float top, float right, float bottom, Direction dir
        path.addRect(left,top,right,bottom, Path.Direction.CCW);
        return path;
    }

    //右上生成
    private Path getTextPath(Point refPoint,int width,int height){

        Path path = new Path();
        //float left, float top, float right, float bottom, Direction dir
        path.addRect(refPoint.x,refPoint.y,refPoint.x+width,refPoint.y-height, Path.Direction.CCW);
        return path;
    }

    // TODO: 16/5/23 未实现
    private Path getRectPath(Point refPoint,int width,int height){
        List<Point> points = new ArrayList<>();
        points.add(new Point(refPoint.x,refPoint.y));
        points.add(new Point(refPoint.x+width,refPoint.y));
        points.add(new Point(refPoint.x+width,refPoint.y+height));
        points.add(new Point(refPoint.x,refPoint.y+height));
//        points.add(new Point(refPoint.x,refPoint.y));
        Path path = new Path();

        Point moveTo = points.get(0);
        path.moveTo(moveTo.x,moveTo.y);
        for (int i = 1; i <points.size() ; i=i+1) {
            Point toPoint = points.get(i);
            path.lineTo(toPoint.x,toPoint.y);
        }
        path.close();
//        HaloLogger.logI(TAG,"getRectPath ：path is "+path);
        return path;
    }

    private Bitmap drawPath(List<Point> points,Bitmap bitmap){
        Canvas canvas = new Canvas(bitmap);

        Paint pathPaint  = new Paint();
        pathPaint.setColor(Color.BLUE);
        pathPaint.setStrokeWidth(5);
        pathPaint.setStyle(Paint.Style.STROKE);

        Paint paint = new Paint();
        canvas.drawColor(Color.BLACK);

        Point startPoint = points.get(0);
        Point endPoint = points.get(points.size()-1);

        Paint textPaint = new TextPaint();
        textPaint.setTextSize(20);
        textPaint.setColor(Color.WHITE);

        String[] LOADS = new String[]{"深南大道","南海大道","学府路","滨海大道","无名路","107车道"};
        int[] COLORS  = new int[]{Color.BLUE,Color.RED,Color.BLUE,Color.BLUE,Color.RED,Color.BLUE};

        int step = points.size()/1;//points.size()/20
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
            canvas.drawPath(path,pathPaint);
        }

//        paint.setColor(Color.GREEN);
//        paint.setStrokeWidth(3);
//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawCircle(startPoint.x,startPoint.y,10,paint);
//        canvas.drawCircle(endPoint.x,endPoint.y,10,paint);
        //外圆
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawCircle(startPoint.x,startPoint.y,10,paint);
//        canvas.drawCircle(endPoint.x,endPoint.y,10,paint);

        Point startMarkPoint = new Point(startPoint.x-10,startPoint.y-10);
        Point endMarkPoint = new Point(endPoint.x-12,endPoint.y-40);
        Bitmap startMark = BitmapFactory.decodeResource(getResources(), R.drawable.route_strategy_map_start);
        canvas.drawBitmap(startMark,startMarkPoint.x,startMarkPoint.y,paint);
        Bitmap endMark = BitmapFactory.decodeResource(getResources(), R.drawable.route_strategy_map_end);
        canvas.drawBitmap(endMark,endMarkPoint.x,endMarkPoint.y,paint);

        mPathRectManager.clearUndrawRegion();
        mPathRectManager.addUndrawRegion(bitmap.getWidth(),bitmap.getHeight());
        mPathRectManager.addUndrawRegion(startMarkPoint,startMark.getWidth(),startMark.getHeight());
        mPathRectManager.addUndrawRegion(endMarkPoint,endMark.getWidth(),endMark.getHeight());

        return bitmap;

    }

    private Paint updatePaint(){
        mPathPaint.setColor(Color.BLUE);
        mPathPaint.setStrokeWidth(4);
        mPathPaint.setStyle(Paint.Style.STROKE);
        return mPathPaint;
    }

}
