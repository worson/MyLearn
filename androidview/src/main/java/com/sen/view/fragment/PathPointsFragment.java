package com.sen.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sen.view.R;
import com.sen.view.mappath.Orientation;
import com.sen.view.mappath.PathRectManager;
import com.sen.view.mappath.PathUtils;
import com.sen.view.mappath.PointUtils;
import com.sen.view.mappath.RectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import hud.haliai.com.share.utils.HaloLogger;

/**
 * A simple {@link Fragment} subclass.
 */
public class PathPointsFragment extends Fragment {

    private static final String TAG = PathPointsFragment.class.getName();
    private static final String WSX = "wangshengxing";
    private static final boolean PATH_DEBUG_MODE = true;
    private Button mRedrawButton;
    private ViewGroup mMainView;
    private ImageView mStrategyRouteView;
    private Bitmap mStrategyRouteViewBitmap;

    private Canvas mCanvas;

    private Context mContext;


    public PathPointsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();

        // Inflate the layout for this fragment
        mMainView = (ViewGroup) inflater.inflate(R.layout.fragment_path_points, container, false);
        mStrategyRouteView = (ImageView) mMainView
                .findViewById(R.id.amap_strategy_route_image);
        mRedrawButton = (Button) mMainView.findViewById(R.id.redraw_button);
        mRedrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultInit();
                updatePath();
//                updateDraw();
            }
        });
        //updatePath();
        return mMainView;
    }

    Bitmap mStrategyRouteBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);

    private void defaultInit(){
        int height = mStrategyRouteView.getHeight();
        int width = mStrategyRouteView.getWidth();
        if (mStrategyRouteViewBitmap == null) {
            mRegionRect = new Rect(0,0,width,height);
            mStrategyRouteViewBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mStrategyRouteViewBitmap);
            mPathRectManager.setCanvas(mCanvas);
        }
    }

    private void initPath(int strategy, List<Point> pointList) {
        pointList.clear();
        textPoints.clear();
        loadNames.clear();
        int witdh = 200,var=0;
        double sinLength;
        switch (strategy) {
            case 0:
                mPathPoints.add(new Point(0, 0));
                mPathPoints.add(new Point(50, 0));
                mPathPoints.add(new Point(50, 50));
                mPathPoints.add(new Point(0, 50));
                mPathPoints.add(new Point(0, 100));
                mPathPoints.add(new Point(0, 150));
                mPathPoints.add(new Point(50, 150));
                mPathPoints.add(new Point(50, 200));

                textPoints.add(new Point(50, 30));
                textPoints.add(new Point(30, 105));
                textPoints.add(new Point(30, 100));
                break;
            case 1:
                sinLength = witdh/((4*3.14));
                var = 0;
                for (int i = 0; i <witdh ; i++) {
                    var = i;
                    mPathPoints.add(new Point(var, (int)(50* Math.sin(var/sinLength))));
                }

                var = 25;
                textPoints.add(new Point(var, (int)(50* Math.sin(var/sinLength))));
                var = 27;
                textPoints.add(new Point(var, (int)(50* Math.sin(var/sinLength))));
                var = 35;
                textPoints.add(new Point(var, (int)(50* Math.sin(var/sinLength))));
                var = 40;
                textPoints.add(new Point(var, (int)(50* Math.sin(var/sinLength))));
                break;
            case 2:

                sinLength = witdh/((4*3.14));
                var = 0;
                for (int i = 0; i <witdh ; i++) {
                    var = i;
                    mPathPoints.add(new Point(var, (int)(50* Math.sin(var/sinLength))));
                }

                var = 25;
                textPoints.add(new Point(var, (int)(50* Math.sin(var/sinLength))));
                var = 50;
                textPoints.add(new Point(var, (int)(50* Math.sin(var/sinLength))));
                var = 70;
                textPoints.add(new Point(var, (int)(50* Math.sin(var/sinLength))));
                var = 175;
                textPoints.add(new Point(var, (int)(50* Math.sin(var/sinLength))));
                break;
            default:
                break;
        }
        loadNames.addAll(Arrays.asList(new String[]{"深南大道立交桥", "南海大道", "学府路", "滨海大道", "无名路", "107国道"}));

    }

    private void updateDraw(){
        int height = mStrategyRouteView.getHeight();
        int width = mStrategyRouteView.getWidth();
        if (mStrategyRouteViewBitmap == null) {
            mRegionRect = new Rect(0,0,width,height);
            mStrategyRouteViewBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mStrategyRouteViewBitmap);
            mPathRectManager.setCanvas(mCanvas);
        }

        drawLinearGradient(mCanvas,mStrategyRouteViewBitmap);
        mStrategyRouteView.setImageBitmap(mStrategyRouteViewBitmap);
    }
    private void updatePath() {
        initPath(1, mPathPoints);
        int height = mStrategyRouteView.getHeight();
        int width = mStrategyRouteView.getWidth();
//        Rect margin = new Rect(20,20,90,20);
        Rect margin = new Rect(20,30,30,30);
        RectUtils.RectMapPara mRectMapPara = RectUtils.measureRect(width, height, mPathPoints,margin);
        List<Point> newPointList = RectUtils.rectRemap(mPathPoints, mRectMapPara);

        List<Path> undrawPaths = new ArrayList<>();
        Path path1 = new Path();
        path1.addRect(0, 0, width, height, Path.Direction.CCW);
        path1.addRect(width - 200, height - 70, width, height, Path.Direction.CCW);
        if (newPointList != null) {
            if (mStrategyRouteViewBitmap == null) {
                mRegionRect = new Rect(0,0,width,height);
                mStrategyRouteViewBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                mCanvas = new Canvas(mStrategyRouteViewBitmap);
                mPathRectManager.setCanvas(mCanvas);
            }

            drawPath(mCanvas,newPointList, mStrategyRouteViewBitmap);
//            drawPathText(rectRemap(textPoints,mRectMapPara),loadNames,3,bitmap);
            List<Point> newTextPoint = RectUtils.rectRemap(textPoints, mRectMapPara);
            drawPathText(mCanvas,newTextPoint, loadNames, mStrategyRouteViewBitmap);
            Rect windowRect = RectUtils.points2Rect(newPointList);
            RectUtils.marginRect(windowRect,new Rect(1,1,1,1));
            drawDebug(mCanvas,windowRect,mStrategyRouteViewBitmap);
            mStrategyRouteView.setImageBitmap(mStrategyRouteViewBitmap);


        }


    }

    private List<Point> mPathPoints = new ArrayList<Point>();
    private List<Point> textPoints = new ArrayList<Point>();
    private List<String> loadNames = new ArrayList<>();
    private Rect mRegionRect;
    private Paint mPathPaint = new Paint(Color.YELLOW);

    PathRectManager mPathRectManager = new PathRectManager();


    private void drawDebug(Canvas canvas,Rect rect, Bitmap bitmap){
//        Canvas canvas = new Canvas(bitmap);
        Paint testPaint = null;
        Path testPath = null;
        testPath = new Path();
        testPaint = new Paint();
        testPaint.setStrokeWidth(2);
        testPaint.setColor(Color.RED);
        testPaint.setTextSize(20);
        testPaint.setStyle(Paint.Style.STROKE);
        List<Point> srcPoints = new ArrayList<>();

        testPath.reset();
        PathUtils.addRect(testPath,rect);
        canvas.drawPath(testPath,testPaint);

        for (int i = 0; i <20 ; i++) {
            srcPoints.add(new Point(i*i,i*i));
        }
        Rect bowl = new Rect(0,0,100,100);
        testPath.reset();
        PathUtils.addRect(testPath,bowl);
        canvas.drawPath(testPath,testPaint);


        List<Point> filterPoints = PointUtils.insideRect(bowl,srcPoints);
        int orientation = RectUtils.pointsOrientation(bowl,filterPoints);
        HaloLogger.logI(WSX,"点与矩形的方向为"+orientation);
        testPath.reset();
        PathUtils.addPoints(testPath,filterPoints);
        canvas.drawPath(testPath,testPaint);

        Point refPoint = new Point(500,250);
        canvas.drawCircle(refPoint.x,refPoint.y,4,testPaint);
        Rect srcRect = new Rect(refPoint.x,refPoint.y,refPoint.x+60,refPoint.y+20);

        RectUtils.moveRect(srcRect,100,100);
        testPath.reset();
        PathUtils.addRect(testPath,srcRect);
//        canvas.drawPath(testPath,testPaint);

        Point desPoint = new Point(refPoint.x+100,refPoint.y+100);


        for (int i = 0; i < 6 ; i++) {
//            RectF rotateRect = RectUtils.rotateRect(i*60,refPoint,new RectF(srcRect));
            Rect rotateRect = RectUtils.rotateHorizontalRect(i*60,refPoint,srcRect);
            testPath.reset();
            PathUtils.addRect(testPath,rotateRect);
            canvas.drawPath(testPath,testPaint);
            HaloLogger.logI(WSX,"旋转度数为："+i*50);
            testPath.reset();
            testPath.moveTo(rotateRect.left,rotateRect.bottom);
            testPath.lineTo(rotateRect.right,rotateRect.bottom);
            canvas.drawTextOnPath(""+i,testPath,0,0,testPaint);

//            Point point = RectUtils.rotatePoint(i*60,refPoint,desPoint);
//            canvas.drawCircle(point.x,point.y,10,testPaint);
//            canvas.drawText(""+i,point.x,point.y,testPaint);

//            Matrix matrix = new Matrix();
//            matrix.postRotate((i+1)*60,refPoint.x,refPoint.y);
//            float[] xy = new float[2];
//            matrix.mapPoints(xy,new float[]{550,250});
//            canvas.drawCircle(xy[0],xy[1],10,testPaint);
//            canvas.drawRect(xy[0]-50,xy[1]-50,xy[0]+50,xy[1]+50,testPaint);

        }



//        Rect nearMaxRect = RectUtils.getNearMaxRect(textPoints, textPoints.get(1), mRegionRect, Orientation.Basic.Vertical);
//        Path regionPath = new Path();
//        PathUtils.addRect(regionPath, nearMaxRect);
//        mCanvas.drawPath(regionPath, testPaint);


    }
    private void drawPathText(Canvas canvas,List<Point> points, List<String> loads, Bitmap bitmap) {
//        Canvas canvas = new Canvas(bitmap);
        Paint textPaint = new TextPaint();
        List<Path> drawPathList = new LinkedList<>();
        textPaint.setStrokeWidth(10);
        textPaint.setTextSize(24);
        textPaint.setColor(Color.WHITE);
        Point centerPoint = new Point(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        int textHeight = (int) (Math.ceil(fm.descent - fm.ascent));
        Path intersectPath = new Path();

        Paint markPaint = new Paint();
        markPaint.setColor(Color.RED);
        markPaint.setStrokeWidth(3);
        markPaint.setStyle(Paint.Style.FILL);
        Paint testPaint = null;
        Path testPath = null;
        if (PATH_DEBUG_MODE) {
            testPath = new Path();
            testPaint = new Paint();
            testPaint.setStrokeWidth(1);
            testPaint.setColor(Color.RED);
            testPaint.setStyle(Paint.Style.STROKE);
        }

        List<PathRectManager.RectRequest> rectRequests = new ArrayList<>();
        if (PATH_DEBUG_MODE) {
//            mPathRectManager.drawUndrawRegion(canvas, textPaint);
        }
        //最长的路优先显示，优先取路的中间坐标点，多条路名左右交叉
        final int Cnt = Math.min(loads.size(), points.size());
        for (int i = 0; i < Cnt; i++) {
            String content = loads.get(i);
            if (content != null) {
                if (content.length() > 6) {
                    content = content.substring(0, 5) + "...";
                }
                Point point = points.get(i);
                PathRectManager.RectRequest rectRequest = new PathRectManager.RectRequest();
                rectRequest.setType(PathRectManager.RectType.TextRect);
                rectRequest.setPoint(point);
                rectRequest.setMinRect(PathRectManager.getTextRect(content, textPaint));
                rectRequests.add(rectRequest);
                if (PATH_DEBUG_MODE){
                    canvas.drawCircle(point.x,point.y,2,markPaint);
                }
            }

        }
        mPathRectManager.setAwayPath(true);
        List<PathRectManager.RectResponse> textRectResponses = mPathRectManager.findRect(rectRequests);
        Path textPath = new Path();
        for (int i = 0; i < textRectResponses.size(); i++) {
            PathRectManager.RectResponse rectResponse = textRectResponses.get(i);
            Rect textRect = rectResponse.getRect();
            String content = loads.get(rectResponse.getIndex());
            if (content.length() > 6) {
                content = content.substring(0, 5) + "...";
            }
            if (textRect != null) {
                textPath.reset();
                textPath.moveTo(textRect.left, textRect.bottom);
                textPath.lineTo(textRect.right, textRect.bottom);
                canvas.drawTextOnPath(content, textPath, 0, 0, textPaint);
                if (PATH_DEBUG_MODE) {
                    testPath.reset();
                    testPath.addRect(new RectF(textRect), Path.Direction.CCW);
                    canvas.drawPath(testPath, testPaint);
                }
            }

//            PathRectManager.drawRectText(content,textRect,textPaint);
        }

//        Rect maxRect = RectUtils.getNearMaxRect(points, points.get(1), new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), Orientation.Basic.Horizontal);
//        testPath.reset();
//        testPath.addRect(new RectF(maxRect), Path.Direction.CCW);
//        canvas.drawPath(testPath, testPaint);

    }

    private Bitmap drawLinearGradient(Canvas canvas,Bitmap bitmap) {
//        Canvas canvas = new Canvas(bitmap);

        Paint pathPaint = new Paint();
        pathPaint.setColor(Color.BLUE);
        pathPaint.setStrokeWidth(5);
//        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setAntiAlias(true);
        CornerPathEffect cornerPathEffect = new CornerPathEffect(80);
        pathPaint.setPathEffect(cornerPathEffect);
        //            pathPaint.setShadowLayer(0.2f,0,0,Color.rgb(0x00,0x7a,0xff));
        LinearGradient linearGradient = new LinearGradient( 0, 0, 50, 0,new int[]{ Color.rgb(0x00, 0x7a, 0xff),Color.rgb(0x00, 0xe7, 0xff)}, new float[]{0f, 0.3f}, Shader.TileMode.REPEAT);
//        LinearGradient linearGradient = new LinearGradient(0,0,400,400,Color.rgb(0x00,0x7a,0xff),Color.rgb(0x00,0x7a,0xff),Shader.TileMode.REPEAT);
        pathPaint.setShader(linearGradient);
        canvas.drawRect(0,0,bitmap.getWidth(),bitmap.getHeight(),pathPaint);
        return bitmap;
    }
    private Bitmap drawPath(Canvas canvas,List<Point> points, Bitmap bitmap) {
//        Canvas canvas = new Canvas(bitmap);

        Paint pathPaint = new Paint();
        pathPaint.setColor(Color.BLUE);
        pathPaint.setStrokeWidth(5);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setAntiAlias(true);
        CornerPathEffect cornerPathEffect = new CornerPathEffect(80);
        pathPaint.setPathEffect(cornerPathEffect);
        Resources resources = mContext.getResources();
        int colorStart = resources.getColor(R.color.strategy_path_start);
        int colorEnd = resources.getColor(R.color.strategy_path_end);
        int colorshadow = resources.getColor(R.color.color_strategy_path_shadow);

//        pathPaint.setShadowLayer(0.2f,5,5,colorshadow);



        LinearGradient linearGradient = new LinearGradient(0,0,500,500,new int[]{colorStart,colorEnd},new float[]{0f,0.3f},Shader.TileMode.REPEAT);
//        LinearGradient linearGradient = new LinearGradient(0,0,400,400,Color.rgb(0x00,0x7a,0xff),Color.rgb(0x00,0x7a,0xff),Shader.TileMode.REPEAT);
        pathPaint.setShader(linearGradient);

//        canvas.drawRect(0,0,400,400,pathPaint);

        Paint paint = new Paint();
        canvas.drawColor(Color.BLACK);

        Point startPoint = points.get(0);
        Point endPoint = points.get(points.size() - 1);

        Paint textPaint = new TextPaint();
        textPaint.setTextSize(20);
        textPaint.setColor(Color.WHITE);

        String[] LOADS = new String[]{"深南大道", "南海大道", "学府路", "滨海大道", "无名路", "107车道"};
        int[] COLORS = new int[]{Color.BLUE, Color.RED, Color.BLUE, Color.BLUE, Color.RED, Color.BLUE};


        DiscretePathEffect discretePathEffect = new DiscretePathEffect(3.0f,2.0f);
        ComposePathEffect composePathEffect = new ComposePathEffect(discretePathEffect,cornerPathEffect);
        Paint shadowPaint = new Paint();
        shadowPaint.setStrokeWidth(8);
        shadowPaint.setStyle(Paint.Style.STROKE);
        shadowPaint.setAntiAlias(true);
        shadowPaint.setPathEffect(composePathEffect);
        shadowPaint.setColor(colorshadow);
        shadowPaint.setAlpha(45);

        Path shadowPath = new Path();
        PointUtils.points2MovePath(shadowPath,points,10,10);
        canvas.drawPath(shadowPath, shadowPaint);


        int step = points.size() / 1;//points.size()/20
        int index = 0;
        for (int j = 0; j < points.size() / step; j = j + 1) {
            Path path = new Path();
            Point moveTo = points.get(index);
            path.moveTo(moveTo.x, moveTo.y);
            for (int i = 0; i < step; i = i + 1) {

                if (++index >= points.size()) {
                    break;
                }
                Point toPoint = points.get(index);
                path.lineTo(toPoint.x, toPoint.y);
            }

            canvas.drawPath(path, pathPaint);
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

        Point startMarkPoint = new Point(startPoint.x - 10, startPoint.y - 10);
        Point endMarkPoint = new Point(endPoint.x - 12, endPoint.y - 40);
        float angle = (float) PointUtils.degree(new Point(0,0),new Point(1,1));
        HaloLogger.logI(WSX,"发出的方向为: "+angle);
        Bitmap startMark = BitmapFactory.decodeResource(getResources(), R.drawable.route_strategy_map_start);
        RectF srcRectF = new RectF(startPoint.x - 10, startPoint.y - 10,startPoint.x - 10+startMark.getWidth(), startPoint.y - 10+startMark.getHeight());
        RectF rotateRectF = new RectF();
        Matrix rotateMatrix = new Matrix();
        rotateMatrix.postRotate(-45,startPoint.x,startPoint.y);
        rotateMatrix.mapRect(rotateRectF,srcRectF);


        rotateRectF = RectUtils.rotateRect(45,startPoint,srcRectF);
        canvas.drawBitmap(startMark,rotateMatrix,paint);
//        canvas.drawBitmap(startMark,null,rotateRectF,paint);

//        canvas.drawRect(rotateRectF,textPaint);
        canvas.rotate(angle,startPoint.x,startPoint.y);

        canvas.drawBitmap(startMark, startMarkPoint.x, startMarkPoint.y, paint);
        canvas.rotate(-angle,startPoint.x,startPoint.y);
        Bitmap endMark = BitmapFactory.decodeResource(getResources(), R.drawable.route_strategy_map_end);
        canvas.drawBitmap(endMark, endMarkPoint.x, endMarkPoint.y, paint);

        List<Point> filterPoints = PointUtils.filterPoint(points, 2);
        Path filterPointsPath = PointUtils.points2ClosePath(filterPoints, 1, 1);

        mCanvas.drawCircle(startPoint.x,startPoint.y,3,textPaint);
        mPathRectManager.clearUndrawRegion();
        mPathRectManager.setRegion(bitmap.getWidth(), bitmap.getHeight());
        mPathRectManager.addUndrawRegion(filterPointsPath);
        mPathRectManager.addUndrawRegion(startMarkPoint, startMark.getWidth(), startMark.getHeight());
        mPathRectManager.addUndrawRegion(endMarkPoint, endMark.getWidth(), endMark.getHeight());

//        Point x1 = new Point(100,100);
//        Point x2 = new Point(300,100);
//        Point x3 = new Point(100,300);
//        Point x4 = new Point(300,300);
//        Matrix matrix = new Matrix();
//        matrix.postRotate(90,0,0);
//        float[] xy = new float[2];
//        matrix.mapPoints(xy,new float[]{0,100});
//        x1.x = (int) xy[0];
//        x1.y = (int) xy[1];
//        Toast.makeText(mContext," x1.x="+x1.x+",x1.y="+x1.y,Toast.LENGTH_LONG).show();
        return bitmap;

    }

    private Paint updatePaint() {
        mPathPaint.setColor(Color.BLUE);
        mPathPaint.setStrokeWidth(4);
        mPathPaint.setStyle(Paint.Style.STROKE);
        return mPathPaint;
    }

}

