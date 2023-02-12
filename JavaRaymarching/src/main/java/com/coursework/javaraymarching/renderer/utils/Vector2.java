package com.coursework.javaraymarching.renderer.utils;

public class Vector2 {
    public float x, y;

    public Vector2() {
        x = 0; y = 0;
    }
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Vector2(float d) {
        this.x = d;
        this.y = d;
    }
    public Vector2(double x, double y){
        this.x=(float)x; this.y=(float)y;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }
    public static float length(Vector2 p) {
        return (float) Math.sqrt(p.x * p.x + p.y * p.y);
    }

    public void normalize() {
        float l = (float) Math.sqrt(x * x + y * y);
        if (l == 0)
            return;
        this.x = this.x / l;
        this.y = this.y / l;
    }

    public static Vector2 normalize(Vector2 p) {
        float l = (float) Math.sqrt(p.x * p.x + p.y * p.y);
        if (l == 0)
            return new Vector2(0);
        return new Vector2(p.x / l, p.y / l);
    }

    public void add(Vector2 p) {
        this.x += p.x;
        this.y += p.y;
    }

    public void sub(Vector2 p) {
        this.x -= p.x;
        this.y -= p.y;
    }
    public void sub(float d) {
        this.x -= d;
        this.y -= d;
    }

    public void add(float d) {
        this.x += d;
        this.y += d;
    }

    public void mult(float d) {
        this.x *= d;
        this.y *= d;
    }

    public void abs() {
        this.x = Math.abs(this.x);
        this.y = Math.abs(this.y);
    }
    public static Vector2 abs(Vector2 p) {
        return new Vector2(Math.abs(p.x), Math.abs(p.y));
    }

    public Vector2 negativeVector(){
        return new Vector2(-x, -y);
    }

    public static Vector2 add(Vector2 a, Vector2 b) {
        return new Vector2(a.x + b.x, a.y + b.y);
    }
    public static Vector2 add(Vector2 a, float d) {
        return new Vector2(a.x + d, a.y + d);
    }

    public static Vector2 sub(Vector2 a, Vector2 b) {
        return new Vector2(a.x - b.x, a.y - b.y);
    }
    public static Vector2 sub(Vector2 a, float d) {
        return new Vector2(a.x - d, a.y - d);
    }

    public static Vector2 mult(Vector2 p, float d) {
        return new Vector2(p.x * d, p.y * d);
    }
    public static Vector2 mult(Vector2 a, Vector2 b) {
        return new Vector2(a.x * b.x, a.y * b.y);
    }

    public static Vector2 div(Vector2 p, float d) {
        return new Vector2(p.x/d, p.y/d);
    }

    public static float dot(Vector2 a, Vector2 b) {
        return a.x * b.x + a.y * b.y;
    }

    public Vector2 copy() {
        return new Vector2(this.x, this.y);
    }
    public static Vector2 copy(Vector2 p) {
        return new Vector2(p.x, p.y);
    }
}