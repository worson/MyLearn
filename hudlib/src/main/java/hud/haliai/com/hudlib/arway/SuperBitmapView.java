package hud.haliai.com.hudlib.arway;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by wangshengxing on 16/6/24.
 */
abstract public class SuperBitmapView {
    public int mWidth,mHeight;
    public Bitmap mBitmap;
    public Canvas mCanvas;
    public Paint mPaint;

    public SuperBitmapView(int width, int height , Bitmap.Config config) throws Exception{
        if (width>0 && height>0) {
            this.mWidth = width;
            this.mHeight = height;
            Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, config);
            rInit(bitmap);
        }else {
            throw new Exception("error view size !");
        }
    }

    public SuperBitmapView(Bitmap bitmap) {
        rInit(bitmap);
    }

    private void rInit(Bitmap bitmap){
        if (bitmap != null) {
            mBitmap = bitmap;
            mWidth = bitmap.getWidth();
            mHeight = bitmap.getHeight();
            mCanvas = new Canvas(mBitmap);
            mPaint = new Paint();
        }
    }
    public void setBitmap(Bitmap bitmap){
        rInit(bitmap);
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    abstract public void draw();
}
