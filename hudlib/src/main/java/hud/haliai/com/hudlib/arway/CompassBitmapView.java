package hud.haliai.com.hudlib.arway;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;

/**
 * Created by wangshengxing on 16/6/24.
 */
public class CompassBitmapView extends SuperBitmapView{
    //逆时针方向
    private int ANGLE_START = 0;
    private int ANGLE_END = 180;
    private int COMPASS_SCALE_NUMBER = 13;
    private float ANGLE_STEP = (float) ((ANGLE_END-ANGLE_START)*1.0/(COMPASS_SCALE_NUMBER-1));

    private float SCALE_LENGTH_FATOR = 0.022f;
    private float SCALE_LENGTH_START_FATOR = 0.459f;

    private int mScaleLength = 0;
    private float mAngle;
    private PointF mScaleStart = new PointF();
    private PointF mScaleEnd = new PointF();
    private PointF mCompassCenter = new PointF();

    public CompassBitmapView(int width, int height, Bitmap.Config config) throws Exception {
        super(width, height, config);
        mScaleLength = (int)(mWidth*SCALE_LENGTH_FATOR);
        mScaleStart.x = (int)(mWidth*SCALE_LENGTH_START_FATOR);
        mScaleStart.y=0;
        mScaleEnd.x = (int)(mWidth*(SCALE_LENGTH_START_FATOR+SCALE_LENGTH_FATOR));
        mScaleEnd.y=0;
        mCompassCenter.x = mWidth/2;
        mCompassCenter.y = mHeight;
    }

    public CompassBitmapView(Bitmap bitmap) {
        super(bitmap);
    }

    public void setAngle(float angle) {
        this.mAngle = angle;
    }

    @Override
    public void draw() {
        drawBasic();
        drawAngle();
    }
    /***
     * 绘制表盘
     * */
    private void drawBasic(){
        mPaint.reset();
        mPaint.setColor(Color.GREEN);
        for (int i = ANGLE_START; i <COMPASS_SCALE_NUMBER ; i++) {
            mCanvas.drawLine(mScaleStart.x,mScaleStart.y,mScaleEnd.x,mScaleEnd.y,mPaint);
            mCanvas.rotate(ANGLE_STEP,mCompassCenter.x,mCompassCenter.y);
        }
        mCanvas.rotate(ANGLE_START-ANGLE_END);
    }

    private void drawAngle(){

    }
}
