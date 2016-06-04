package com.sen.view.mappath;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangshengxing on 16/5/27.
 */
public class PointUtils {

    /**
     * 找到在矩形中的点
     */

    public static List<Point> insideRect(Rect rect, List<Point> points){

        if (points == null || points.size()<=0) {
            return null;
        }
        List<Point> result = new ArrayList<>();
        for (Point point:points){
            if(rInsideRect(rect,point)){
                result.add(new Point(point));
            }
        }
        return result;
    }

    /**
     * 判断点在矩形中
     */
    public static boolean rInsideRect(Rect rect, Point point){
        return  ( (point.x>=rect.left && point.x<=rect.right)&& (point.y>=rect.top && point.y<=rect.bottom));
    }
    public static boolean insideRect(Rect rect, Point point){
        if (rect == null || point == null) {
            return false;
        }
        return  ( (point.x>=rect.left && point.x<=rect.right)&& (point.y>=rect.top && point.y<=rect.bottom));
    }
    /**
     * 过滤相临点小于一定距离的点
     */
    public static List<Point> filterPoint(List<Point> srcPoints,int radius){
        if (srcPoints == null || srcPoints.size()<=0) {
            return null;
        }
        List<Point> filterPoints  = new LinkedList<>();
        Point lastPoint = srcPoints.get(0);
        filterPoints.add(srcPoints.get(0));
        final int critical = radius*radius;
        for (int i = 0; i <srcPoints.size() ; i++) {
            Point point = srcPoints.get(i);
            if(powDistance(lastPoint,point)>critical){
                filterPoints.add(point);
                lastPoint = point;
            }
        }
        return filterPoints;
    }
    /**
     * 两点距离平方和
     */
    public static int powDistance(Point a,Point b){
        int diffX = Math.abs(a.x-b.x);
        int diffY = Math.abs(a.y-b.y);
        return diffX*diffX+diffY*diffY;
    }
    /**
     * 得到点之间的path
     */
    public static Path points2Path(List<Point> points){
        if (points == null || points.size()<=0) {
            return null;
        }
        Path pointsPath = new Path();
        Point moveTo = points.get(0);
        pointsPath.moveTo(moveTo.x,moveTo.y);
        for (int j = 0; j <points.size() ; j=j+1) {
            Point toPoint = points.get(j);
            pointsPath.lineTo(toPoint.x,toPoint.y);
        }
        return pointsPath;
    }
    /**
     * 得到点之间的path
     */
    public static Path points2MovePath(List<Point> points,float moveH,float moveV){
        if (points == null || points.size()<=0) {
            return null;
        }
        Path pointsPath = new Path();
        Point moveTo = points.get(0);
        pointsPath.moveTo(moveTo.x,moveTo.y);
        for (int j = 0; j <points.size() ; j=j+1) {
            Point toPoint = points.get(j);
            pointsPath.lineTo(toPoint.x+moveH,toPoint.y+moveV);
        }
        return pointsPath;
    }


    /**
     * 得到点之间的path
     */
    public static void points2MovePath(Path pointsPath,List<Point> points, float moveH, float moveV){
        if (points == null || points.size()<=0) {
            return;
        }
        Point moveTo = points.get(0);
        pointsPath.moveTo(moveTo.x+moveH,moveTo.y+moveV);
        for (int j = 1; j <points.size() ; j=j+1) {
            Point toPoint = points.get(j);
            pointsPath.lineTo(toPoint.x+moveH,toPoint.y+moveV);
        }
    }


    /**
     * 得到点之间的闭区间的path
     */
    public static Path points2ClosePath(List<Point> points,float moveH,float moveV){
        if (points == null || points.size()<=0) {
            return null;
        }
        Path closePath = points2MovePath(points,-moveH,-moveV);
        for (int j = points.size()-1; j >=0 ; j--) {
            Point toPoint = points.get(j);
            closePath.lineTo(toPoint.x+moveH,toPoint.y+moveV);
        }
        return closePath;
    }

    /**
     * 得到两点之间的角度 0-360
     */
    public static double degree(Point p1,Point p2){
        double angle = 0;
        int diffX,diffY;
        diffX = p2.x - p1.x;
        diffY = p2.y - p1.y;
        if (diffX==0){
            if (diffY>0){
                return 90;
            }else {
                return -90;
            }
        }else if(diffY==0){
            if (diffX>0){
                return 0;
            }else {
                return 180;
            }
        }else {
            float k = Math.abs(diffY/diffX);   //斜率
            angle  = Math.atan(k); //注意这个角度的范围是0 ----------pi/2, 不是0到90°
        }
        return  Math.toDegrees(angle);
    }
}
