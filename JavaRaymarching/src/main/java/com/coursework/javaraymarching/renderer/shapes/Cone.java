package com.coursework.javaraymarching.renderer.shapes;

import com.coursework.javaraymarching.renderer.utils.MathF;
import com.coursework.javaraymarching.renderer.utils.Vector2;
import com.coursework.javaraymarching.renderer.utils.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Cone extends Shape{
    private float height, angle;

    public Cone(){
        this(0, 10, 0, 10, 45);
    }
    public Cone(float x, float y, float z, float height, float angle){
        super(new Vector3(x, y, z));
        this.height = height;
        this.angle = angle;
        sc = new Vector2(Math.sin(Math.toRadians(angle/2)), Math.cos(Math.toRadians(angle/2)));
        updateQ();
    }
    private void updateQ(){
        q = Vector2.mult(new Vector2(sc.x/sc.y, -1.0f), height);
        k = Math.signum(q.y);
        dotqq = Vector2.dot(q, q);
    }

    public float getHeight() {
        return height;
    }
    public void setHeight(float height) {
        this.height = height;
        updateQ();
    }

    public float getAngle() {
        return angle;
    }
    public void setAngle(float angle) {
        this.angle = angle;
        sc = new Vector2(Math.sin(Math.toRadians(angle/2)), Math.cos(Math.toRadians(angle/2)));
        updateQ();
    }

    private Vector2 q, sc;
    private float k, dotqq;

    @JsonIgnore
    @Override
    public float getDistance(Vector3 pos) {
        Vector2 w = new Vector2(Vector2.length(pos.xz()), pos.y);
        Vector2 a = Vector2.sub(w, Vector2.mult(q, MathF.saturate(Vector2.dot(w, q)/ dotqq)));
        Vector2 b = Vector2.sub(w, new Vector2(MathF.saturate(w.x/q.x)*q.x, q.y));
        float d = Math.min(Vector2.dot(a, a), Vector2.dot(b, b));
        float s = Math.max(k*(w.x*q.y-w.y*q.x), k*(w.y-q.y));
        return (float) (Math.sqrt(d)*Math.signum(s));
    }
}