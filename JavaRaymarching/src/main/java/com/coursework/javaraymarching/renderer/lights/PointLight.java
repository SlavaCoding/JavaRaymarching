package com.coursework.javaraymarching.renderer.lights;

import com.coursework.javaraymarching.renderer.utils.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PointLight extends Light{
    private Vector3 position;
    private float ambientK, directK;

    public PointLight(){
        this(new Vector3(0, 1, 0));
    }

    public PointLight(Vector3 position) {
        this.position = position;
        ambientK = 0;
        directK = 0;
    }

    public PointLight(Vector3 position, Vector3 ambientIntensity, Vector3 directIntensity, float ambientK, float directK){
        super(ambientIntensity, directIntensity);
        this.position = position;
        this.ambientK = ambientK;
        this.directK = directK;
    }

    public Vector3 getPosition() {
        return position;
    }
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public float getAmbientK() {
        return ambientK;
    }
    public void setAmbientK(float ambientK) {
        this.ambientK = ambientK;
    }

    public float getDirectK() {
        return directK;
    }
    public void setDirectK(float directK) {
        this.directK = directK;
    }

    @JsonIgnore
    @Override
    public Vector3 getLightDirection(Vector3 pos){
        Vector3 direction = Vector3.sub(position, pos);
        direction.normalize();
        return direction;
    }

    @Override
    public Vector3 getAmbientIntensity(Vector3 pos) {
        return Vector3.div(ambientBase, 1+ambientK*Vector3.length(Vector3.sub(position, pos)));
    }

    @Override
    public Vector3 getDirectIntensity(Vector3 pos) {
        return Vector3.div(directBase, 1+directK*Vector3.length(Vector3.sub(position, pos)));
    }

}