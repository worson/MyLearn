package com.sen.view.mappath;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangshengxing on 16/5/24.
 */
public class PathUtils {


    public static class RectMapPara
    {

        private Point refPoint;
        private double scalefactor;
        private int widthMove;
        private int heightMove;

        public Point getRefPoint() {
            return refPoint;
        }

        public void setRefPoint(Point refPoint) {
            this.refPoint = refPoint;
        }

        public double getScalefactor() {
            return scalefactor;
        }

        public void setScalefactor(double scalefactor) {
            this.scalefactor = scalefactor;
        }

        public int getWidthMove() {
            return widthMove;
        }

        public void setWidthMove(int widthMove) {
            this.widthMove = widthMove;
        }

        public int getHeightMove() {
            return heightMove;
        }

        public void setHeightMove(int heightMove) {
            this.heightMove = heightMove;
        }
    }

    public static Rect keepRectInside(Rect rect,float x,float y,Rect size){
        Rect result = null;

        return result;
    }
    public static List<Point> rectRemap(List<Point> points, RectMapPara rectMapPara){
        Point refPoint = rectMapPara.getRefPoint();
        double scalefactor = rectMapPara.getScalefactor();
        int widthMove = rectMapPara.getWidthMove();
        int heightMove = rectMapPara.getHeightMove();
        List<Point> newPointList = new LinkedList<Point>();
        int newX ,newY;
        for(Point point: points){
            newX = (int)((point.x-refPoint.x)/scalefactor)+widthMove;
            newY = (int)((point.y-refPoint.y)/scalefactor)+heightMove;
            Point newPoint = new Point(newX,newY);
            newPointList.add(newPoint);
        }
        return newPointList;
    }

    public static RectMapPara measureRect(int width, int height, List<Point> points){
        if (points == null || width ==0 || height ==0) {
            return null;
        }
        int maxLeft,maxTop,maxRight,maxBottom;//取名风格以左下为参照
        int pointWidth,pointHeight,widthMove,heightMove;
        final int paddingWidth=20,paddingHeight=20;
        double widthScalefactor = 1, heightScalefactor =1,scalefactor =1;

        Point startPoint = points.get(0);
        Point endPoint = points.get(points.size()-1);

        maxLeft = points.get(0).x;
        maxRight = maxLeft;
        maxTop = points.get(0).y;
        maxBottom = maxTop;
        for (Point point: points) {
            maxLeft = Math.min(point.x,maxLeft);
            maxRight = Math.max(point.x,maxRight);
            maxTop = Math.max(point.y,maxTop);
            maxBottom = Math.min(point.y,maxBottom);
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
        rectMapPara.setRefPoint(refPoint);
        rectMapPara.setScalefactor(scalefactor);
        rectMapPara.setHeightMove(heightMove);
        rectMapPara.setWidthMove(widthMove);

        return  rectMapPara;
    }
}
