package com.coursework.javaraymarching.renderer.shapes;

import com.coursework.javaraymarching.renderer.utils.MathF;
import com.coursework.javaraymarching.renderer.utils.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BoxFrame extends Shape{
    private Vector3 size;
    private float thickness;

    public BoxFrame(){
        this(new Vector3(0), new Vector3(10), 1f);
    }
    public BoxFrame(float x, float y, float z, float width, float height, float depth, float thickness){
        this(new Vector3(x, y, z), new Vector3(width, height, depth), thickness);
    }
    public BoxFrame(Vector3 center, Vector3 size, float thickness){
        super(center);
        this.size = Vector3.div(size, 2);
        this.thickness = thickness;
    }
    public Vector3 getSize(){
        return Vector3.mult(size, 2);
    }
    public void setSize(Vector3 size){
        this.size = Vector3.div(size, 2);
    }
    public float getThickness() {
        return thickness;
    }
    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    @JsonIgnore
    @Override
    public float getDistance(Vector3 p){
        p.abs();
        p.sub(size);
        Vector3 q = Vector3.add(p, thickness);
        q.abs();
        q.sub(thickness);
        Vector3 mp = max(p);
        Vector3 mq = max(q);
        return Math.min(Math.min(
                length(mp.x, mq.y, mq.z)+Math.min(Math.max(p.x, Math.max(q.y, q.z)), 0.0f),
                length(mq.x, mp.y, mq.z)+Math.min(Math.max(q.x, Math.max(p.y, q.z)), 0.0f)),
                length(mq.x, mq.y, mp.z)+Math.min(Math.max(q.x, Math.max(q.y, p.z)), 0.0f));
    }

    private Vector3 max(Vector3 p){
        return new Vector3(Math.max(p.x, 0.0f), Math.max(p.y, 0.0f), Math.max(p.z, 0.0f));
    }
    private float length(float x, float y, float z) {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }
}