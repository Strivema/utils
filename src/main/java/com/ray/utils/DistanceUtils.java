package com.ray.utils;

/**
 * @author Ray.Ma
 * @date 2019/7/9 10:37
 */

/**
 * 计算地图上两个点的直线距离
 */
public class DistanceUtils {


    //    地球半径
    public static final int EART_RADIUS = 6371000;

    /**
     * 计算地图上两个点的距离
     *
     * @param lat_a
     * @param lng_a
     * @param lat_b
     * @param lng_b
     * @return
     */
    public static double getDistance(double lat_a, double lng_a, double lat_b, double lng_b) {
        double pk = 180 / Math.PI;
        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;
        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);
        return EART_RADIUS * tt;
    }

}
