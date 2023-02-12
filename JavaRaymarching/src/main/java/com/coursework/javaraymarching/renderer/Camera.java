package com.coursework.javaraymarching.renderer;

import com.coursework.javaraymarching.renderer.utils.Matrix3;
import com.coursework.javaraymarching.renderer.utils.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**Класс камеры используется для вычисления положения и направления лучей*/
public class Camera {
    private Vector3 position;
    private Vector3 target;
    private Matrix3 camMatrix;
    private float focal;

    public Camera(){
        position = new Vector3(30, 30, -30);
        target = new Vector3(0, 10, 0);
        focal = 2.5f;
    }
    public Camera(Vector3 pos, Vector3 target, float focal){
        position = pos;
        this.target = target;
        this.focal = focal;
        setCamera();
    }

    public Vector3 getPosition() {
        return position;
    }
    public void setPosition(Vector3 position) {
        this.position = position;
    }
    public void setPosition(float x, float y, float z){
        position = new Vector3(x, y, z);
    }
    public void setPosition(String x, String y, String z){
        position = new Vector3(Float.parseFloat(x), Float.parseFloat(y), Float.parseFloat(z));
    }

    @JsonIgnore
    public float getX(){
        return position.x;
    }
    @JsonIgnore
    public float getY(){
        return position.y;
    }
    @JsonIgnore
    public float getZ(){
        return position.z;
    }

    public Vector3 getTarget() {
        return target;
    }
    public void setTarget(Vector3 target) {
        this.target = target;
    }
    public void setTarget(String x, String y, String z){
        target = new Vector3(Float.parseFloat(x), Float.parseFloat(y), Float.parseFloat(z));
    }

    public float getFocal() {
        return focal;
    }
    public void setFocal(float focal) {
        this.focal = focal;
    }

    @JsonIgnore
    public void setCamera(){
        Vector3 cw = Vector3.sub(target, position);
        cw.normalize();
        Vector3 cu = new Vector3(-cw.z, 0, cw.x);
        cu.normalize();
        Vector3 cv = Vector3.cross(cu, cw);
        camMatrix = new Matrix3(cu, cv, cw);
    }
    public Vector3 calcRayDirection(float x, float y){
        Vector3 view = new Vector3(x, y, focal);
        view.normalize();
        camMatrix.transformVector(view);
        return view;
    }
}