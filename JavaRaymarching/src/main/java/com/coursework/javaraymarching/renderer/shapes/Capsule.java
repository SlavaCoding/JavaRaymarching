package com.coursework.javaraymarching.renderer.shapes;

import com.coursework.javaraymarching.renderer.utils.MathF;
import com.coursework.javaraymarching.renderer.utils.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Capsule extends Shape {
    private float height, radius;

    public Capsule(){
        this(0, 0, 0, 10, 2.5f);
    }
    public Capsule(float x, float y, float z, float height, float radius){
        super(new Vector3(x, y, z));
        this.height = height;
        this.radius = radius;
    }

    public float getHeight() {
        return height;
    }
    public void setHeight(float height) {
        this.height = height;
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
        pos.y -= MathF.clamp(pos.y, 0.0f, height);
        return Vector3.length(pos) - radius;
    }
}