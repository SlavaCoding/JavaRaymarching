package com.coursework.javaraymarching.renderer.lights;

import com.coursework.javaraymarching.renderer.utils.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DirectionalLight.class, name = "DirectionalLight"),
        @JsonSubTypes.Type(value = PointLight.class,       name = "PointLight")
})
public abstract class Light {
    protected Vector3 ambientBase;
    protected Vector3 directBase;

    public Light(){
        directBase = new Vector3(1);
        ambientBase = new Vector3(0);
    }

    public Light(Vector3 ambientIntensity, Vector3 directIntensity){
        this.ambientBase = ambientIntensity;
        this.directBase = directIntensity;
    }

    @JsonIgnore
    public abstract Vector3 getLightDirection(Vector3 pos);
    @JsonIgnore
    public abstract Vector3 getAmbientIntensity(Vector3 pos);
    @JsonIgnore
    public abstract Vector3 getDirectIntensity(Vector3 pos);

    public Vector3 getAmbientBase() {
        return ambientBase;
    }
    public void setAmbientBase(Vector3 ambientIntensity) {
        this.ambientBase = ambientIntensity;
    }

    public Vector3 getDirectBase() {
        return directBase;
    }
    public void setDirectBase(Vector3 directIntensity) {
        this.directBase = directIntensity;
    }
}