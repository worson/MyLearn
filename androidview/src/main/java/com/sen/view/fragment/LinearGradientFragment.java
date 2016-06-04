package com.sen.view.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.ComposeShader;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.sen.view.R;
import com.sen.view.mappath.PathUtils;
import com.sen.view.mappath.PointUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinearGradientFragment extends Fragment {
    private static final String TAG = PathPointsFragment.class.getName();
    private static final String WSX = "wangshengxing";
    private static final boolean PATH_DEBUG_MODE = true;
    private Button mRedrawButton;
    private ViewGroup mMainView;
    private ImageView mDisplayImageView;
    private Bitmap mDisplayBitmap;

    private Canvas mCanvas;

    private Context mContext;

    public LinearGradientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getActivity();

        // Inflate the layout for this fragment
        mMainView = (ViewGroup) inflater.inflate(R.layout.fragment_linear_gradient, container, false);
        mDisplayImageView = (ImageView) mMainView
                .findViewById(R.id.display_imageview);
        mRedrawButton = (Button) mMainView.findViewById(R.id.redraw_button);
        mRedrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultInit();
//                updateDraw();
                drawLinearGradientLine();
                mDisplayImageView.setImageBitmap(mDisplayBitmap);
            }
        });

        return mMainView;
    }

    private void defaultInit(){
        int height = mDisplayImageView.getHeight();
        int width = mDisplayImageView.getWidth();
        if (mDisplayBitmap == null) {
            mDisplayBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mDisplayBitmap);
        }
    }

    private void drawLinearGradientLine(){

        mCanvas.drawColor(Color.WHITE);
        Paint pathPaint = new Paint();
        pathPaint.setColor(Color.BLUE);
        pathPaint.setStrokeWidth(5);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setAntiAlias(true);
        CornerPathEffect cornerPathEffect = new CornerPathEffect(80);
//        pathPaint.setPathEffect(cornerPathEffect);
//        Resources resources = mContext.getResources();
//        int colorStart = resources.getColor(R.color.strategy_path_start);
//        int colorEnd = resources.getColor(R.color.strategy_path_end);
//        int colorshadow = resources.getColor(R.color.color_strategy_path_shadow);

//        pathPaint.setShadowLayer(0.2f,5,5,colorshadow);





        Path path = new Path();
        List<Point> points = new ArrayList<>();

        Point startPoint = new Point(1000,500);
        Point endPoint = new Point(10,10);
        LinearGradient startEndGradient = new LinearGradient(startPoint.x,startPoint.y,endPoint.x,endPoint.y,new int[]{Color.rgb(0xff,0,0),Color.rgb(0,0,0xff)},new float[]{0f,0.8f}, Shader.TileMode.CLAMP);
        pathPaint.setShader(startEndGradient);
        points.clear();
        points.add(startPoint);
        points.add(endPoint);
        path.reset();
        PointUtils.points2MovePath(path,points,0,0);
        mCanvas.drawPath(path,pathPaint);


        LinearGradient linearGradient = new LinearGradient(10,40,500,40,new int[]{Color.rgb(0xff,0,0),Color.rgb(0,0,0xff)},new float[]{0f,1f}, Shader.TileMode.CLAMP);
        LinearGradient linearGradient2 = new LinearGradient(500,40,600,500,new int[]{Color.rgb(0xff,0,0),Color.rgb(0,0,0xff)},new float[]{0f,1f}, Shader.TileMode.CLAMP);
        LinearGradient linearGradient3 = new LinearGradient(600,500,300,800,new int[]{Color.rgb(0xff,0,0),Color.rgb(0,0,0xff)},new float[]{0f,1f}, Shader.TileMode.CLAMP);
        //LinearGradient linearGradient4 = new LinearGradient(500,0,0,0,new int[]{Color.rgb(0,0,0xff),Color.rgb(0xff,0,0)},new float[]{0f,0.5f}, Shader.TileMode.CLAMP);
        ComposeShader composeShader = new ComposeShader(linearGradient,linearGradient2, PorterDuff.Mode.DST_ATOP);
        ComposeShader composeShader2 = new ComposeShader(linearGradient3,composeShader, PorterDuff.Mode.DST_ATOP);
        //ComposeShader composeShader3 = new ComposeShader(composeShader,composeShader2, PorterDuff.Mode.DARKEN);
//        LinearGradient linearGradient = new LinearGradient(0,0,400,400,Color.rgb(0x00,0x7a,0xff),Color.rgb(0x00,0x7a,0xff),Shader.TileMode.REPEAT);
        pathPaint.setShader(composeShader2);

        /*points.clear();
        points.add(new Point(10,40));
        points.add(new Point(500,40));
        points.add(new Point(600,500));
        points.add(new Point(300,800));
        path.reset();
        PointUtils.points2MovePath(path,points,0,0);
        mCanvas.drawPath(path,pathPaint);*/

//        pathPaint.setStyle(Paint.Style.FILL);
//        mCanvas.drawRect(new RectF(0,0,1000,800),pathPaint);
    }

}
