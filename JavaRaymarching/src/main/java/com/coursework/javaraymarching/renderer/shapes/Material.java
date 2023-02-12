package com.coursework.javaraymarching.renderer.shapes;

import com.coursework.javaraymarching.renderer.utils.MathF;
import com.coursework.javaraymarching.renderer.utils.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Material {
    private Vector3 ambientColor;
    private Vector3 diffuseColor;
    private float reflectivity;
    private boolean isSpecular;
    private float specularity;

    public Material(){
        this(new Vector3(0.5f), new Vector3(0.5f));
    }
    public Material(float R, float G, float B){
        this(new Vector3(R, G, B), new Vector3(R, G, B));
    }
    public Material(Vector3 ambientColor, Vector3 diffuseColor){
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        reflectivity = 0;
        isSpecular = false;
        specularity = 100;
    }

    @JsonIgnore
    public void setColor(float R, float G, float B){
        setAmbientColor(R, G, B);
        setDiffuseColor(R, G, B);
    }

    public Vector3 getAmbientColor(){
        return ambientColor;
    }
    public void setAmbientColor(float R, float G, float B){
        ambientColor = new Vector3(R, G, B);
    }
    public void setAmbientColor(Vector3 color){
        ambientColor = color;
    }

    public Vector3 getDiffuseColor(){
        return diffuseColor;
    }
    public void setDiffuseColor(float R, float G, float B){
        diffuseColor = new Vector3(R, G, B);
    }
    public void setDiffuseColor(Vector3 color){
        diffuseColor = color;
    }

    public float getReflectivity() {
        return reflectivity;
    }
    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public boolean isSpecular() {
        return isSpecular;
    }
    public void setSpecular(boolean specular) {
        isSpecular = specular;
    }

    public float getSpecularity() {
        return specularity;
    }
    public void setSpecularity(float specularity) {
        this.specularity = specularity;
    }

    public static Material blend(Material a, Material b, float h){
        if(h == 0)
            return a;
        else if (h == 1)
            return b;
        Material res = new Material(MathF.mix(a.ambientColor, b.ambientColor, h), MathF.mix(a.diffuseColor, b.diffuseColor, h));
        res.setReflectivity(MathF.mix(a.reflectivity, b.reflectivity, h));
        return res;
    }
}