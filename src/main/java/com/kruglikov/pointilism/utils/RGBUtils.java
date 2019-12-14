package com.kruglikov.pointilism.utils;

public class RGBUtils {
    private RGBUtils(){}
    public static int rComponent(int argb) {
        return (argb & 0x00ff0000) >> 16;
    }
    public static int gComponent(int argb) {
        return (argb & 0x0000ff00) >> 8;
    }
    public static int bComponent(int argb) {
        return (argb & 0x000000ff);
    }

}