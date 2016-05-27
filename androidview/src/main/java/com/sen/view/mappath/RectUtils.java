package com.sen.view.mappath;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.List;

/**
 * Created by wangshengxing on 16/5/27.
 */
public class RectUtils {
    /**
     * 在一堆点里面与某个不包括最近点最大的矩形
     * */
    public static Rect getMaxRect(List<Point> pointList,Point refPoint){
        Rect result = null;
        int baseX = refPoint.x,baseY = refPoint.y;
        int left=baseX,right=baseX;
        int top=baseY,bottom=baseY;
        int minToRight= Integer.MAX_VALUE,minToBottom=Integer.MAX_VALUE;
        final int cnt = pointList.size();
        int diffX = 0,diffY = 0;
        //比较出区域值
        for (int i = 0; i <cnt ; i++) {
            Point point = pointList.get(i);
            if (baseX>point.x){
                left = Math.max(left,point.x);
            }else {
                diffX = point.x-baseX;
                if (minToRight<diffX){
                    right = point.x;
                }

            }
            if (baseY>point.y){
                top = Math.max(top,point.y);
            }else {
                diffY = point.y-baseY;
                if (minToBottom<diffY){
                    bottom = point.y;
                }

            }
        }
        result = new Rect(left,top,right,bottom);
        return result;
    }

    /**
     * 判断点是否在一个矩形内
     */

    public static boolean isIncludePoint(Rect rect,Point point){
        boolean result = false;

        return result;
    }
    /**
     * 在当前第一矩形中取出与第二个相交的部分
     */
    public static Rect intersect(Rect firstRect,Rect secondRect){
        Rect result = null;
        int left,top,right,bottom;
        left = Math.max(firstRect.left,secondRect.left);
        top = Math.max(firstRect.top,secondRect.top);
        right = Math.min(firstRect.right,secondRect.right);
        bottom = Math.min(firstRect.bottom,secondRect.bottom);
        result = new Rect(left,top,right,bottom);
        return result;
    }

}
