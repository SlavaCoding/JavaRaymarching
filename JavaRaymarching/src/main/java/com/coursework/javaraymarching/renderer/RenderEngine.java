package com.coursework.javaraymarching.renderer;

import com.coursework.javaraymarching.renderer.lights.Light;
import com.coursework.javaraymarching.renderer.shapes.Material;
import com.coursework.javaraymarching.renderer.shapes.Shape;
import com.coursework.javaraymarching.renderer.utils.MathF;
import com.coursework.javaraymarching.renderer.utils.RayHit;
import com.coursework.javaraymarching.renderer.utils.Vector3;

import java.util.ArrayList;

/**Главный класс, который отвечает за отрисовку сцены*/
public class RenderEngine {
    private Camera camera;
    private GlobalSettings settings;
    private ShapeManager shapeManager;
    private LightManager lightManager;

    public RenderEngine(){}
    public RenderEngine(GlobalSettings settings, Camera camera, ShapeManager shapeManager, LightManager lightManager){
        this.settings = settings;
        this.camera = camera;
        this.shapeManager = shapeManager;
        this.lightManager = lightManager;
    }

    public Camera getCamera() {
        return camera;
    }
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public GlobalSettings getSettings() {
        return settings;
    }
    public void setSettings(GlobalSettings settings) {
        this.settings = settings;
    }

    public ShapeManager getShapeManager() {
        return shapeManager;
    }
    public void setShapeManager(ShapeManager shapeManager) {
        this.shapeManager = shapeManager;
    }

    public LightManager getLightManager() {
        return lightManager;
    }
    public void setLightManager(LightManager lightManager) {
        this.lightManager = lightManager;
    }

    /**Вычисление нормали поверхности в заданной точке*/
    private Vector3 calcNormal(Vector3 p){
        float h = 0.0001f;
        Vector3 offset = Vector3.add(p, new Vector3(h, -h, -h) );
        float m = map(offset).dist();
        Vector3 normal = new Vector3(m, -m, -m);
        offset.setAdd(p, new Vector3(-h, h, -h));
        m = map(offset).dist();
        normal.add(-m, m, -m);
        offset.setAdd(p, new Vector3(-h, -h, h));
        m = map(offset).dist();
        normal.add(-m, -m, m);
        offset.setAdd(p, new Vector3(h));
        m = map(offset).dist();
        normal.add(m);
        normal.normalize();
        return normal;
    }

    /**Вычисляет расстояние от точки до ближайшего объекта*/
    private RayHit map(Vector3 pos){
        RayHit res = new RayHit(1e10f, new Material(0, 0, 0));
        ArrayList<Shape> shapes = shapeManager.getShapes();
        for(Shape s : shapes){
            res = s.mapping(pos, res);
        }
        return res;
    }

    /**
     * Вычисляет тень от конкретного источника освещения
     * @param ro точка, из которой выходит луч
     * @param rd направление луча
     * @param maxt максимальное расстояние, на котором ищется о
     * @return возвращает уровень затененности от 0 - полная тень, до 1 - тени нет
     */
    private float softShadow(Vector3 ro, Vector3 rd, float maxt )
    {
        float res = 1.0f;
        float ph = 1e20f;
        float epsilon = settings.getEpsilonShadow();
        float mint = 10*epsilon;
        float k = settings.getSoftOfShadow();
        Vector3 pos = new Vector3();
        Vector3 direct = new Vector3();
        for( float t=mint; t<maxt; )
        {
            direct.setMult(rd, t);
            pos.setAdd(ro, direct);
            float h = map(pos).dist();
            if( h < epsilon )
                return 0.0f;
            float y = h*h/(2.0f*ph);
            float d = (float) Math.sqrt(h*h-y*y);
            res = (float) Math.min( res, k*d/Math.max(0.0,t-y) );
            ph = h;
            t += h;
        }
        return res;
    }

    /**
     * Вычисление освещения от всех источников света в заданной точке
     * @param pos координаты точки, в которой производится расчет
     * @param normal вектор нормали поверхности
     * @param view направление падающего луча
     * @param material материал фигуры
     * @return возвращает полученный цвет
     */
    private Vector3 calcColor(Vector3 pos, Vector3 normal, Vector3 view, Material material){
        Vector3 ambient = new Vector3();
        Vector3 diffuse = new Vector3();
        Vector3 specular = new Vector3();
        for(Light light : lightManager.getLights()){
            ambient.add(light.getAmbientIntensity(pos));
            Vector3 lightDirection = light.getLightDirection(pos);
            float dotLN = Vector3.dot(lightDirection, normal);
            if(dotLN > 0.0f){
                float shadow = softShadow(pos, lightDirection, 10.0f);//TODO Реализовать динамическое ограничение для тени
                diffuse.add(Vector3.mult(light.getDirectIntensity(pos), dotLN, shadow));
                Vector3 reflect = MathF.reflect(lightDirection, normal);
                reflect.normalize();
                float dotRV = Vector3.dot(reflect, view);
                if(material.isSpecular() && dotRV > 0.0f){
                    specular.add(Vector3.mult(light.getDirectIntensity(pos), (float)Math.pow(dotRV, material.getSpecularity()), shadow));
                }
            }
        }
        Vector3 color = Vector3.add(Vector3.mult(material.getAmbientColor(), ambient),
                                    Vector3.mult(material.getDiffuseColor(), diffuse),
                                    specular);
        return MathF.saturate(color);
    }

    /**
     *Вычисление луча света
     * @param ro точка, из которой выходит луч
     * @param rd направление луча
     * @param n количество возможных отражений
     * @return возвращает цвет выпущенношо луча
     */
    private Vector3 raycast(Vector3 ro, Vector3 rd, int n){
        float epsilon = settings.getEpsilon();
        float t = 10*epsilon;
        Vector3 pos = new Vector3();
        Vector3 direct = new Vector3();
        for (int i = 0; i < settings.getMaxSteps(); ++i){
            direct.setMult(rd, t);
            pos.setAdd(ro, direct);
            RayHit res = map(pos);
            if (res.dist()<epsilon){
                Vector3 normal = calcNormal(pos);
                Vector3 color = calcColor(pos, normal, rd, res.m());
                if (n!=0 && res.m().getReflectivity()!=0){
                    Vector3 reflect = MathF.reflect(rd, normal);
                    reflect.normalize();
                    color = MathF.mix(color, raycast(pos, reflect, n-1), res.m().getReflectivity());
                }
                return color;
            }
            t += res.dist();
            if(t>settings.getMaxDistant()) break;
        }
        return settings.getBackgroundColor();
    }

    /**Вычисляет координаты поверхности, на которую указывает курсор*/
    public Vector3 getPosition(int x, int y){
        int width = settings.getWidth();
        int height = settings.getHeight();
        Vector3 ro = camera.getPosition();
        Vector3 rd = camera.calcRayDirection((2.0f*x-width)/height, -(2.0f*y-height)/height);
        rd.normalize();
        float t = 0.1f;
        Vector3 pos = new Vector3();
        Vector3 direct = new Vector3();
        for (int i = 0; i < settings.getMaxSteps(); ++i){
            direct.setMult(rd, t);
            pos.setAdd(ro, direct);
            RayHit res = map(pos);
            if (res.dist()<0.01f){
                return pos;
            }
            t += res.dist();
            if(t>settings.getMaxDistant()) break;
        }
        return pos;
    }

    private int width, height, threadCount;
    private int[] pixels;
    /**Отдельный поток прорисовки*/
    private void renderThread(int n){
        for(int x=n; x<width; x+=threadCount){
            for (int y=0; y<height; y++){
                Vector3 rd = camera.calcRayDirection((2.0f*x-width)/height, -(2.0f*y-height)/height);
                rd.normalize();
                Vector3 col = raycast(camera.getPosition(), rd, settings.getReflectSteps());
                pixels[(x%width)+(y*width)]=255<<24|col.getRed()<<16|col.getGreen()<<8|col.getBlue();
            }
        }
    }
    /**
     * Запускает отрисовку, распределяя вычисления между потоками
     * @param emptyImg ссылка на массив, из которого будет создано изображение
    */
    public void Render(int[] emptyImg){
        width = settings.getWidth();
        height = settings.getHeight();
        pixels = emptyImg;
        threadCount = 4;
        Thread pr = new Thread(() -> {
            renderThread(0);
        });
        pr.start();
        Thread pr2 = new Thread(() -> {
            renderThread(1);
        });
        pr2.start();
        Thread pr3 = new Thread(() -> {
            renderThread(2);
        });
        pr3.start();
        renderThread(3);
        try{
            pr.join();
            pr2.join();
            pr3.join();
        }
        catch (InterruptedException ignored){}
    }
}