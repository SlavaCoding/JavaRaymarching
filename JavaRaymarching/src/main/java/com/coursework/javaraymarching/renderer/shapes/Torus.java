package com.coursework.javaraymarching.renderer.shapes;

import com.coursework.javaraymarching.renderer.utils.Vector2;
import com.coursework.javaraymarching.renderer.utils.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Torus extends Shape{
    private float thickness, radius;

    public Torus(){
        this(0, 0, 0, 10, 1f);
    }
    public Torus(float x, float y, float z, float radius, float thickness){
        super(new Vector3(x, y, z));
        this.thickness = thickness;
        this.radius = radius;
    }

    public float getThickness() {
        return thickness;
    }
    public void setThickness(float thickness) {
        this.thickness = thickness;
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
        Vector2 q = new Vector2(Vector2.length(pos.xz())-radius, pos.y);
        return Vector2.length(q)-thickness;
    }
}