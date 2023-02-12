package com.coursework.javaraymarching.renderer.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.paint.Color;

public class Vector3 {
    public float x, y, z;

    public Vector3() {
        x = 0; y = 0; z = 0;
    }
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3(float x, float y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }
    public Vector3(float d) {
        this.x = d;
        this.y = d;
        this.z = d;
    }
    public Vector3(double x, double y, double z){
        this((float)x, (float)y, (float)z);
    }

    public Vector3(Color color){
        x = (float) color.getRed();
        y = (float) color.getGreen();
        z = (float) color.getBlue();
    }

    public Vector3 xyy() {
        return new Vector3(x, y, y);
    }
    public Vector3 xxy() {
        return new Vector3(x, x, y);
    }
    public Vector3 xyx() {
        return new Vector3(x, y, x);
    }
    public Vector3 yxx() {
        return new Vector3(y, x, x);
    }
    public Vector3 yxy() {
        return new Vector3(y, x, y);
    }
    public Vector3 yyx() {
        return new Vector3(y, y, x);
    }
    public Vector3 xxx() {
        return new Vector3(x, x, x);
    }
    public Vector2 xz() {
        return new Vector2(x, z);
    }
    public Vector2 xy() {
        return new Vector2(x, y);
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }
    public static float length(Vector3 p) {
        return (float) Math.sqrt(p.x * p.x + p.y * p.y + p.z * p.z);
    }

    public void normalize() {
        float l = this.length();
        if (l == 0)
            return;
        x = x / l;
        y = y / l;
        z = z / l;
    }

    public static Vector3 normalize(Vector3 p) {
        float l = p.length();
        if (l == 0)
            return new Vector3();
        return new Vector3(p.x / l, p.y / l, p.z / l);
    }

    public void add(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;
    }
    public void add(Vector3 p) {
        x += p.x;
        y += p.y;
        z += p.z;
    }

    public void sub(Vector3 p) {
        this.x -= p.x;
        this.y -= p.y;
        this.z -= p.z;
    }
    public void sub(float d) {
        this.x -= d;
        this.y -= d;
        this.z -= d;
    }

    public void add(float d) {
        this.x += d;
        this.y += d;
        this.z += d;
    }

    public void mult(float d) {
        this.x *= d;
        this.y *= d;
        this.z *= d;
    }

    public void abs() {
        this.x = Math.abs(this.x);
        this.y = Math.abs(this.y);
        this.z = Math.abs(this.z);
    }
    public static Vector3 abs(Vector3 p) {
        return new Vector3(Math.abs(p.x), Math.abs(p.y), Math.abs(p.z));
    }

    public Vector3 negativeVector(){
        return new Vector3(-x, -y, -z);
    }

    @JsonIgnore
    public void setAdd(Vector3 a, Vector3 b){
        x = a.x + b.x;
        y = a.y + b.y;
        z = a.z + b.z;
    }
    public static Vector3 add(Vector3 a, Vector3 b) {
        return new Vector3(a.x + b.x, a.y + b.y, a.z + b.z);
    }
    public static Vector3 add(Vector3 a, Vector3 b, Vector3 c) {
        return new Vector3(a.x + b.x + c.x, a.y + b.y + c.y, a.z + b.z + c.z);
    }
    public static Vector3 add(Vector3 a, float d) {
        return new Vector3(a.x + d, a.y + d, a.z + d);
    }

    public void setSub(Vector3 a, Vector3 b){
        x = a.x - b.x;
        y = a.y - b.y;
        z = a.z - b.z;
    }
    public static Vector3 sub(Vector3 a, Vector3 b) {
        return new Vector3(a.x - b.x, a.y - b.y, a.z - b.z);
    }
    public static Vector3 sub(Vector3 a, float d) {
        return new Vector3(a.x - d, a.y - d, a.z - d);
    }

    @JsonIgnore
    public void setMult(Vector3 p, float d){
        x = p.x * d;
        y = p.y * d;
        z = p.z * d;
    }
    public static Vector3 mult(Vector3 p, float d) {
        return new Vector3(p.x * d, p.y * d, p.z * d);
    }
    public static Vector3 mult(Vector3 p, float d1, float d2) {
        return new Vector3(p.x * d1 * d2, p.y * d1 * d2, p.z * d1 * d2);
    }
    public static Vector3 mult(Vector3 a, Vector3 b) {
        return new Vector3(a.x * b.x, a.y * b.y, a.z * b.z);
    }

    public static Vector3 div(Vector3 p, float d) {
        return new Vector3(p.x/d, p.y/d, p.z/d);
    }

    public static float dot(Vector3 a, Vector3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public Vector3 copy() {
        return new Vector3(this.x, this.y, this.z);
    }
    public static Vector3 copy(Vector3 p) {
        return new Vector3(p.x, p.y);
    }

    public static Vector3 cross(Vector3 a, Vector3 b) {
        float i = a.y * b.z - b.y * a.z;
        float j = -(a.x * b.z - b.x * a.z);
        float k = a.x * b.y - b.x * a.y;
        return new Vector3(i, j, k);
    }

    public static float angleBetween(Vector3 a, Vector3 b) {
        return (float) Math.acos(Vector3.dot(a, b) / (a.length() * b.length()));
    }
    public Color toColor() {
        return Color.color(x, y, z);
    }
    @JsonIgnore
    public int getRed(){
        return (int) (x*255);
    }
    @JsonIgnore
    public int getGreen(){
        return (int) (y*255);
    }
    @JsonIgnore
    public int getBlue(){
        return (int) (z*255);
    }
}