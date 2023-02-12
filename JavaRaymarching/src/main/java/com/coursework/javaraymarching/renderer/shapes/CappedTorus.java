package com.coursework.javaraymarching.renderer.shapes;

import com.coursework.javaraymarching.renderer.utils.Vector2;
import com.coursework.javaraymarching.renderer.utils.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CappedTorus extends Shape{
    private float radius, thickness, angle;

    public CappedTorus(){
        this(0, 0, 0, 10, 1f, 180);
    }
    public CappedTorus(float x, float y, float z, float radius, float thickness, float angle){
        super(new Vector3(x, y, z));
        setRadius(radius);
        this.thickness = thickness;
        setAngle(angle);
    }

    public float getThickness() {
        return thickness;
    }
    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    public float getAngle() {
        return angle;
    }
    public void setAngle(float angle) {
        this.angle = angle;
        sc = new Vector2(Math.sin(Math.toRadians(angle/2)), Math.cos(Math.toRadians(angle/2)));
    }
    public float getRadius() {
        return radius;
    }
    public void setRadius(float radius) {
        this.radius = radius;
        rr = radius*radius;
        r2 = radius*2.0f;
    }

    private Vector2 sc;
    private float rr;
    private float r2;

    @JsonIgnore
    @Override
    public float getDistance(Vector3 pos) {
        pos.x = Math.abs(pos.x);
        Vector2 posXY = pos.xy();
        float k = (sc.y*pos.x > sc.x*pos.y) ? Vector2.dot(posXY, sc) : Vector2.length(posXY);
        return (float) (Math.sqrt(Vector3.dot(pos, pos) + rr - r2*k)-thickness);
    }
}