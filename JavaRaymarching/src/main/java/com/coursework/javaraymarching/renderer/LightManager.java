package com.coursework.javaraymarching.renderer;

import com.coursework.javaraymarching.renderer.lights.DirectionalLight;
import com.coursework.javaraymarching.renderer.lights.Light;
import com.coursework.javaraymarching.renderer.lights.PointLight;
import com.coursework.javaraymarching.renderer.utils.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class LightManager {
    private ArrayList<Light> lights;
    private ObservableList<String> names;
    private int currentIndex;

    public LightManager(){
        lights = new ArrayList<>();
        names = FXCollections.observableArrayList();
        currentIndex = 0;

        DirectionalLight dL = new DirectionalLight(new Vector3(0.0f, 3.0f, 1.0f), new Vector3(0.35f), new Vector3(0.5f));
        add(dL);
//        PointLight pointLight = new PointLight(new Vector3(10f, 30f, 10f),
//                                                new Vector3(0.35f), new Vector3(0.5f), 0f, 0f);
//        add(pointLight);
    }

    @JsonIgnore
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    @JsonIgnore
    public Light getCurrentLight(){
        return lights.get(currentIndex);
    }

    public void add(Light l){
        lights.add(l);
        names.add(l.getClass().getSimpleName());
    }

    public void deleteLight(int index){
        lights.remove(index);
        names.remove(index);
    }

    public ArrayList<Light> getLights() {
        return lights;
    }
    public void setLights(ArrayList<Light> lights) {
        this.lights = lights;
    }

    public List<String> getNames() {
        return names;
    }
    public void setNames(List<String> names) {
        this.names = FXCollections.observableArrayList(names);
    }
}