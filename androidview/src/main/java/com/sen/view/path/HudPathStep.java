package com.sen.view.path;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshengxing on 16/5/24.
 */
public class HudPathStep {
    public class RoadStatus{
        private int distance;
        private String status;

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
    private List<Point> points;
    private String road;
    private Point roadPoint;
    private List<RoadStatus> roadStatuses;

    public HudPathStep() {
//        points = new LinkedList<>();
        roadStatuses = new ArrayList<>();
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public Point getRoadPoint() {
        return roadPoint;
    }

    public void setRoadPoint(Point roadPoint) {
        this.roadPoint = roadPoint;
    }

    public List<RoadStatus> getRoadStatuses() {
        return roadStatuses;
    }

    public void setRoadStatuses(List<RoadStatus> roadStatuses) {
        this.roadStatuses = roadStatuses;
    }

}
