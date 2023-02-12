package com.coursework.javaraymarching.renderer.shapes;

import com.coursework.javaraymarching.renderer.utils.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

public class Sphere extends Shape {
    private float radius;

    public Sphere(){
        this(new Vector3(0), 10);
    }
    public Sphere(float x, float y, float z, float r){
        this(new Vector3(x, y, z), r);
    }
    public Sphere(Vector3 pos, float r){
        super(pos);
        radius = r;
    }

    public float getRadius() {
        return radius;
    }
    public void setRadius(float radius) {
        this.radius = radius;
    }

    @JsonIgnore
    @Override
    public float getDistance(Vector3 pos) {
        return pos.length() - radius;
    }
}