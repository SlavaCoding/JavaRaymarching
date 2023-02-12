package com.coursework.javaraymarching.renderer.utils;

public class MathF {
    public static float mix(float a, float b, float h){
        return a*(1-h) + b*h;
    }
    public static Vector3 mix(Vector3 a, Vector3 b){
        return Vector3.div(Vector3.add(a, b), 2);
    }
    public static Vector3 mix(Vector3 a, Vector3 b, float h){
        return Vector3.add(Vector3.mult(a, 1-h), Vector3.mult(b, h));
    }

    public static Vector3 clamp(Vector3 v, float min, float max) {
        Vector3 p = v.copy();
        p.x = clamp(p.x, min, max);
        p.y = clamp(p.y, min, max);
        p.z = clamp(p.z, min, max);
        return p;
    }
    public static float clamp(float x, float min, float max) {
        if(x < min) return min;
        else if(x > max) return max;
        return x;
    }
    public static Vector3 saturate(Vector3 v){
        Vector3 p = v.copy();
        p.x = saturate(p.x);
        p.y = saturate(p.y);
        p.z = saturate(p.z);
        return p;
    }
    public static float saturate(float x){
        if(x<0.0f) return 0.0f;
        else if(x>1.0f) return 1.0f;
        return x;
    }

    public static Vector3 max(Vector3 a, Vector3 b) {
        if(a.length()<b.length()) return b;
        return a;
    }
    public static Vector3 max(Vector3 a, float b){
        return new Vector3(Math.max(a.x, b), Math.max(a.y, b), Math.max(a.z, b));
    }
    public static Vector2 max(Vector2 a, float b) {
        return new Vector2(Math.max(a.x, b), Math.max(a.y, b));
    }

    public static Vector3 reflect(Vector3 a, Vector3 n){
        return Vector3.sub(a, Vector3.mult(n, 2 * Vector3.dot(a, n)));
    }

}