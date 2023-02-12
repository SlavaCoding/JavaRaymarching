package com.coursework.javaraymarching.renderer;

import com.coursework.javaraymarching.renderer.shapes.*;
import com.coursework.javaraymarching.renderer.utils.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShapeManager {
    private ArrayList<Shape> shapes;
    private ObservableList<String> names;
    private int currentIndex;

    public ShapeManager(){
        shapes = new ArrayList<>();
        names = FXCollections.observableArrayList();
        currentIndex = 0;
        Material green = new Material(new Vector3(0, 1, 0), new Vector3(0, 1, 0));
        green.setReflectivity(0.1f);
        Floor floor = new Floor(0f);
        floor.setMaterial(green);
        add(floor);
//        Material red = new Material(new Vector3(1f, 0f, 0f), new Vector3(0f, 0.25f, 1f));
//        Material reflect = new Material(0f, 1f, 0f);
//        reflect.setReflectivity(0.8f);
//        Material yellow = new Material(1f, 1f, 0f);
//        Material blue = new Material(0, 0, 1);
//        blue.setReflectivity(0.1f);
//        Material inRed = new Material(1f, 0f, 0f);
//
//        Sphere redSphere = new Sphere(0.5f, 0.1f, 0.0f, 0.5f);
//        redSphere.setMaterial(red);
//        Sphere refSphere = new Sphere(-2.5f, 0.5f, 0.0f,0.5f);
//        refSphere.setMaterial(reflect);
//        Box box = new Box(-2.5f, 0.4f, -1.0f,0.5f, 0.5f, 0.5f);
//        box.setMaterial(yellow);
//        box.setRotate(new Vector3(45, 45, 45));
//        Floor floor = new Floor(0f);
//        floor.setMaterial(blue);
//        floor.setSmoothRadius(0.2f);
//        Sphere notSphere = new Sphere(0.6f, 0.4f, 0.0f, 0.3f);
//        notSphere.setMaterial(inRed);
//        notSphere.setMode(OperationMode.Subtract);
//        notSphere.setSmoothRadius(0.1f);
//        BoxFrame boxFrame = new BoxFrame(-2.5f, 0.5f, 2.0f, 1, 1, 1, 0.1f);
//        Torus torus = new Torus(-1f, 0.5f, 0f, 0.5f, 0.1f);
//        CappedTorus cappedTorus = new CappedTorus(-1f, 0.5f, 2f, 0.5f, 0.1f, 180);
//        Cone cone = new Cone(0.5f, 1, 2, 1, 45);
//        Capsule capsule = new Capsule(0.5f, 0.1f, -2, 0.5f, 0.2f);
//        Cylinder cylinder = new Cylinder(-1f, 0.5f, -2, 0.5f, 0.2f);
//
//        add(redSphere);
//        add(notSphere);
//        add(refSphere);
//        add(box);
//        add(boxFrame);
//        add(torus);
//        add(cappedTorus);
//        add(cone);
//        add(capsule);
//        add(cylinder);
    }

    public void add(Shape s){
        shapes.add(s);
        names.add(s.getClass().getSimpleName());
    }

    public void deleteShape(int index){
        shapes.remove(index);
        names.remove(index);
    }

    public void swapShape(int a, int b){
        Collections.swap(shapes, a, b);
        Collections.swap(names, a, b);
    }

    @JsonIgnore
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }
    public void setShapes(ArrayList<Shape> shapes){
        this.shapes = shapes;
    }

    public List<String> getNames() {
        return names;
    }
    public void setNames(List<String> names){
        this.names = FXCollections.observableArrayList(names);
    }

    @JsonIgnore
    public Shape getCurrentShape(){
        return shapes.get(currentIndex);
    }

    @JsonIgnore
    public String getCurrentName(){
        return names.get(currentIndex);
    }

    @JsonIgnore
    public void setCurrentName(String name){
        names.set(currentIndex, name);
    }
}