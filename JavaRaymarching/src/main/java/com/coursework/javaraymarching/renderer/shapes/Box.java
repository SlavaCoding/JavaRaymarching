package com.coursework.javaraymarching.renderer.shapes;

import com.coursework.javaraymarching.renderer.utils.MathF;
import com.coursework.javaraymarching.renderer.utils.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Box extends Shape{
    private Vector3 size;

    public Box(){
        this(new Vector3(0), new Vector3(10));
    }
    public Box(float x, float y, float z, float width, float height, float depth){
        this(new Vector3(x, y, z), new Vector3(width, height, depth));
    }
    public Box(Vector3 pos, Vector3 size){
        super(pos);
        this.size = Vector3.div(size, 2);
    }

    public Vector3 getSize(){
        return Vector3.mult(size, 2);
    }
    public void setSize(Vector3 size){
        this.size = Vector3.div(size, 2);
    }

    @JsonIgnore
    @Override
    public float getDistance(Vector3 pos){
        pos.abs();
        pos.sub(size);
        return Math.min(Math.max(pos.x, Math.max(pos.y, pos.z)), 0.0f)+(MathF.max(pos, 0.0f)).length();
    }
}