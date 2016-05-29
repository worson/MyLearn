package com.sen.view.mappath;

import android.graphics.Point;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangshengxing on 16/5/27.
 */
public class PointUtils {
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
}
