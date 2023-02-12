package com.coursework.javaraymarching.renderer;

import com.coursework.javaraymarching.renderer.utils.Vector3;

public class GlobalSettings {
    private int width;
    private int height;
    private int maxSteps;
    private int maxDistant;
    private float epsilon;
    private int reflectSteps;
    private float softOfShadow;
    private float epsilonShadow;
    private Vector3 backgroundColor;

    public GlobalSettings(){
        width = 1180;
        height = 760;
        maxSteps = 800;
        maxDistant = 100;
        epsilon = 0.001f;
        reflectSteps = 4;
        softOfShadow = 7.0f;
        epsilonShadow = 0.01f;
        backgroundColor = new Vector3(135f/255f, 206f/255f, 235f/255f);
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public int getMaxSteps() {
        return maxSteps;
    }
    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    public int getMaxDistant() {
        return maxDistant;
    }
    public void setMaxDistant(int maxDistant) {
        this.maxDistant = maxDistant;
    }

    public int getReflectSteps() {
        return reflectSteps;
    }
    public void setReflectSteps(int reflectSteps) {
        this.reflectSteps = reflectSteps;
    }

    public float getEpsilon() {
        return epsilon;
    }
    public void setEpsilon(float epsilon) {
        this.epsilon = epsilon;
    }

    public float getSoftOfShadow() {
        return softOfShadow;
    }
    public void setSoftOfShadow(float softOfShadow) {
        this.softOfShadow = softOfShadow;
    }

    public float getEpsilonShadow() {
        return epsilonShadow;
    }
    public void setEpsilonShadow(float epsilonShadow) {
        this.epsilonShadow = epsilonShadow;
    }

    public Vector3 getBackgroundColor() {
        return backgroundColor;
    }
    public void setBackgroundColor(Vector3 backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}