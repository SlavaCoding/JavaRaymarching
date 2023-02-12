package com.coursework.javaraymarching.renderer.shapes;

import com.coursework.javaraymarching.renderer.utils.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Floor extends Shape{
    float h;

    public Floor(){
        h = 0;
    }
    public Floor(float h){
        this.h = h;
    }

    public float getH() {
        return h;
    }
    public void setH(float h) {
        this.h = h;
    }

    @JsonIgnore
    @Override
    public float getDistance(Vector3 pos){
        return pos.y + h;
    }
}