package com.coursework.javaraymarching.renderer.lights;

import com.coursework.javaraymarching.renderer.utils.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DirectionalLight extends Light{
    private Vector3 direction;
    private Vector3 normalDirection;

    public DirectionalLight(){
        setDirection(new Vector3(0, 1, 0));
    }
    public DirectionalLight(Vector3 direction){
        setDirection(direction);
    }

    public DirectionalLight(Vector3 direction, Vector3 ambientIntensity, Vector3 directIntensity){
        super(ambientIntensity, directIntensity);
        setDirection(direction);
    }

    public Vector3 getDirection() {
        return direction;
    }
    public void setDirection(Vector3 direction) {
        this.direction = direction;
        normalDirection = Vector3.normalize(direction);
    }

    @JsonIgnore
    @Override
    public Vector3 getLightDirection(Vector3 pos){
        return normalDirection;
    }

    @JsonIgnore
    @Override
    public Vector3 getAmbientIntensity(Vector3 pos) {
        return ambientBase;
    }
    @JsonIgnore
    @Override
    public Vector3 getDirectIntensity(Vector3 pos) {
        return directBase;
    }
}