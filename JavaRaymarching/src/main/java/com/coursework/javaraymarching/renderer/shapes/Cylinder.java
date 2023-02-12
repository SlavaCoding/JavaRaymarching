package com.coursework.javaraymarching.renderer.shapes;

import com.coursework.javaraymarching.renderer.utils.MathF;
import com.coursework.javaraymarching.renderer.utils.Vector2;
import com.coursework.javaraymarching.renderer.utils.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Cylinder extends Shape{
    private float height, radius;

    public Cylinder(){
        this(0, 0, 0, 10, 2f);
    }
    public Cylinder(float x, float y, float z, float height, float radius){
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
        Vector2 d = new Vector2(Vector2.length(pos.xz())-radius,Math.abs(pos.y)-height/2);
        return Math.min(Math.max(d.x,d.y), 0.0f) + Vector2.length(MathF.max(d,0.0f));
    }
}