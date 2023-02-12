package com.coursework.javaraymarching;

import com.coursework.javaraymarching.renderer.*;
import com.coursework.javaraymarching.renderer.lights.DirectionalLight;
import com.coursework.javaraymarching.renderer.lights.Light;
import com.coursework.javaraymarching.renderer.lights.PointLight;
import com.coursework.javaraymarching.renderer.shapes.*;
import com.coursework.javaraymarching.renderer.utils.ShapeHit;
import com.coursework.javaraymarching.renderer.utils.Vector3;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class MainController {
    //public WrappedImageView Display;
    public ImageView Display;

    public TextField WidthEdit;
    public TextField HeightEdit;
    public TextField MaxStepsEdit;
    public TextField MaxDistantEdit;
    public TextField EpsilonEdit;
    public TextField ReflectStepsEdit;
    public TextField SoftOfShadowEdit;
    public TextField EpsilonShadowEdit;

    public TextField CamXEdit;
    public TextField CamYEdit;
    public TextField CamZEdit;
    public TextField TargetXEdit;
    public TextField TargetYEdit;
    public TextField TargetZEdit;
    public TextField FocalEdit;
    public ColorPicker BackgroundColorPicker;

    public ListView<String> ShapeList;
    public TextField CenterXEdit;
    public TextField CenterYEdit;
    public TextField CenterZEdit;
    public Slider RotateXSlider;
    public Slider RotateYSlider;
    public Slider RotateZSlider;
    public Label RotateXLabel;
    public Label RotateYLabel;
    public Label RotateZLabel;
    public TextField SmoothRadiusEdit;
    public TextField RoundingRadiusEdit;
    public ComboBox<String> OperationModeEdit;

    public ComboBox<String> ShapeCreator;
    public ColorPicker AmbientColorPicker;
    public ColorPicker DiffuseColorPicker;
    public Slider ReflectivitySlider;
    public Label ReflectivityLabel;
    public CheckBox SpecularFlag;
    public TextField SpecularEdit;
    public Label LabelEdit1;
    public Label LabelEdit2;
    public Label LabelEdit3;
    public Label LabelEdit4;
    public Label LabelSlider;
    public TextField SpecificEdit1;
    public TextField SpecificEdit2;
    public TextField SpecificEdit3;
    public TextField SpecificEdit4;
    public Slider SpecificSlider;

    public ListView<String> LightList;
    public ColorPicker AmbientLightPicker;
    public ColorPicker DirectLightPicker;
    public TextField AmbientKEdit;
    public TextField DirectKEdit;
    public TitledPane LightParam;
    public TextField LightXEdit;
    public TextField LightYEdit;
    public TextField LightZEdit;
    public ComboBox<String> LightCreator;
    public Label XPos;
    public Label YPos;
    public Label ZPos;
    public Label ShapeInfoLabel;
    public TextField NameShapeEdit;

    private boolean autoUpdateFields;
    WritableImage cameraView;
    private RenderEngine render;
    private GlobalSettings settings;
    private Camera camera;
    private ShapeManager shapeManager;
    private LightManager lightManager;
    private ObjectMapper objectMapper;
    @FXML
    private void initialize() {
//        settings = new GlobalSettings();
//        camera = new Camera(new Vector3(3.0f, 4f, -4.0f), new Vector3(-1, 0, 0), 2.5f);
//        shapeManager = new ShapeManager();
//        lightManager = new LightManager();
//        render = new RenderEngine(settings, camera, shapeManager, lightManager);
        objectMapper = new ObjectMapper();
//        File file = new File("B://JavaRaymarching//saved", "default.json");
//        loadFromFile(file);
        newScene();

        ShapeList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if(autoUpdateFields){
                    setShapeFields();
                    if (newValue.intValue() != -1){
                        shapeManager.setCurrentIndex(newValue.intValue());
                    }
                    else shapeManager.setCurrentIndex(1);
                    setShapeEdits();
                }
            }
        });
        LightList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if(autoUpdateFields){
                    setLightFields();
                    lightManager.setCurrentIndex(newValue.intValue());
                    setLightEdits();
                }
            }
        });

        RotateXSlider.valueProperty().addListener((ch, oldVal, newVal)-> RotateXLabel.setText(newVal.intValue()+""));
        RotateYSlider.valueProperty().addListener((ch, oldVal, newVal)-> RotateYLabel.setText(newVal.intValue()+""));
        RotateZSlider.valueProperty().addListener((ch, oldVal, newVal)-> RotateZLabel.setText(newVal.intValue()+""));
        ReflectivitySlider.valueProperty().addListener((ch, oldVal, newVal)-> ReflectivityLabel.setText(newVal.intValue()+""));
        RotateXLabel.setText("0");
        RotateYLabel.setText("0");
        RotateZLabel.setText("0");
        ReflectivityLabel.setText("0");

        updateAllEdits();

//        try {
//            file.createNewFile();
//            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, render);
//        }
//        catch (Exception ignored){
//
//        }

//        String str = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(render);
//        System.out.println(str);
    }
    private void updateAllEdits(){
        WidthEdit.setText(settings.getWidth()+"");
        HeightEdit.setText(settings.getHeight()+"");
        MaxStepsEdit.setText(settings.getMaxSteps()+"");
        MaxDistantEdit.setText(settings.getMaxDistant()+"");
        EpsilonEdit.setText(settings.getEpsilon()+"");
        ReflectStepsEdit.setText(settings.getReflectSteps()+"");
        SoftOfShadowEdit.setText(settings.getSoftOfShadow()+"");
        EpsilonShadowEdit.setText(settings.getEpsilonShadow()+"");
        BackgroundColorPicker.setValue(settings.getBackgroundColor().toColor());

        CamXEdit.setText(camera.getX()+"");
        CamYEdit.setText(camera.getY()+"");
        CamZEdit.setText(camera.getZ()+"");
        TargetXEdit.setText(camera.getTarget().x+"");
        TargetYEdit.setText(camera.getTarget().y+"");
        TargetZEdit.setText(camera.getTarget().z+"");
        FocalEdit.setText(camera.getFocal()+"");

        autoUpdateFields = false;
        setShapeEdits();
        setLightEdits();
        ShapeList.setItems((ObservableList<String>) shapeManager.getNames());
        ShapeList.getSelectionModel().selectFirst();
        LightList.setItems((ObservableList<String>) lightManager.getNames());

        cameraView = new WritableImage(settings.getWidth(), settings.getHeight());
        Display.setImage(cameraView);
        autoUpdateFields = true;
    }
    private void resetEdits(){
        LabelEdit1.setManaged(false);
        LabelEdit2.setManaged(false);
        LabelEdit3.setManaged(false);
        LabelEdit4.setManaged(false);
        LabelSlider.setManaged(false);
        SpecificEdit1.setManaged(false);
        SpecificEdit2.setManaged(false);
        SpecificEdit3.setManaged(false);
        SpecificEdit4.setManaged(false);
        SpecificSlider.setManaged(false);
        LabelEdit1.setVisible(false);
        LabelEdit2.setVisible(false);
        LabelEdit3.setVisible(false);
        LabelEdit4.setVisible(false);
        LabelSlider.setVisible(false);
        SpecificEdit1.setVisible(false);
        SpecificEdit2.setVisible(false);
        SpecificEdit3.setVisible(false);
        SpecificEdit4.setVisible(false);
        SpecificSlider.setVisible(false);
    }
    private void setSpecificSlider(String text, int min, int max){
        SpecificSlider.setMin(min);
        SpecificSlider.setMax(max);
        SpecificSlider.setManaged(true);
        SpecificSlider.setVisible(true);
        LabelSlider.setText(text);
        LabelSlider.setManaged(true);
        LabelSlider.setVisible(true);
    }
    private void setEditFields(String e1, String e2, String e3, String e4){
        LabelEdit1.setManaged(true);
        LabelEdit1.setVisible(true);
        LabelEdit1.setText(e1);
        SpecificEdit1.setManaged(true);
        SpecificEdit1.setVisible(true);
        if(e2 != null){
            LabelEdit2.setManaged(true);
            LabelEdit2.setVisible(true);
            LabelEdit2.setText(e2);
            SpecificEdit2.setManaged(true);
            SpecificEdit2.setVisible(true);
        }
        else return;
        if(e3 != null){
            LabelEdit3.setManaged(true);
            LabelEdit3.setVisible(true);
            LabelEdit3.setText(e3);
            SpecificEdit3.setManaged(true);
            SpecificEdit3.setVisible(true);
        }
        else return;
        if(e4 != null){
            LabelEdit4.setManaged(true);
            LabelEdit4.setVisible(true);
            LabelEdit4.setText(e4);
            SpecificEdit4.setManaged(true);
            SpecificEdit4.setVisible(true);
        }
    }

    private void setShapeEdits(){
        NameShapeEdit.setText(shapeManager.getCurrentName());
        Shape curr = shapeManager.getCurrentShape();
        CenterXEdit.setText(curr.getCenter().x+"");
        CenterYEdit.setText(curr.getCenter().y+"");
        CenterZEdit.setText(curr.getCenter().z+"");
        RotateXSlider.setValue(curr.getRotate().x);
        RotateYSlider.setValue(curr.getRotate().y);
        RotateZSlider.setValue(curr.getRotate().z);
        SmoothRadiusEdit.setText(curr.getSmoothRadius()+"");
        RoundingRadiusEdit.setText(curr.getRoundingRadius()+"");
        OperationModeEdit.getSelectionModel().select(curr.getMode());
        Material material = curr.getMaterial();
        AmbientColorPicker.setValue(material.getAmbientColor().toColor());
        DiffuseColorPicker.setValue(material.getDiffuseColor().toColor());
        ReflectivitySlider.setValue(material.getReflectivity()*100);
        SpecularFlag.setSelected(material.isSpecular());
        SpecularEdit.setText(material.getSpecularity()+"");

        resetEdits();
        String type = curr.getClass().getSimpleName();
        switch (type) {
            case "Box" -> {
                setEditFields("Ширина", "Высота", "Глубина", null);
                Vector3 size = ((Box)curr).getSize();
                SpecificEdit1.setText(size.x+"");
                SpecificEdit2.setText(size.y+"");
                SpecificEdit3.setText(size.z+"");
            }
            case "BoxFrame" -> {
                setEditFields("Ширина", "Высота", "Глубина", "Толщина");
                BoxFrame boxFrame = (BoxFrame) curr;
                Vector3 size = boxFrame.getSize();
                SpecificEdit1.setText(size.x+"");
                SpecificEdit2.setText(size.y+"");
                SpecificEdit3.setText(size.z+"");
                SpecificEdit4.setText(boxFrame.getThickness()+"");
            }
            case "CappedTorus" -> {
                setEditFields("Радиус", "Толщина", null, null);
                setSpecificSlider("Угол", 1, 360);
                CappedTorus cappedTorus = (CappedTorus) curr;
                SpecificEdit1.setText(cappedTorus.getRadius()+"");
                SpecificEdit2.setText(cappedTorus.getThickness()+"");
                SpecificSlider.setValue(cappedTorus.getAngle());
            }
            case "Capsule" -> {
                setEditFields("Высота", "Радиус", null, null);
                Capsule capsule = (Capsule) curr;
                SpecificEdit1.setText(capsule.getHeight()+"");
                SpecificEdit2.setText(capsule.getRadius()+"");
            }
            case "Cone" -> {
                setEditFields("Высота", null, null, null);
                setSpecificSlider("Угол", 1, 180);
                Cone cone = (Cone) curr;
                SpecificEdit1.setText(cone.getHeight()+"");
                SpecificSlider.setValue(cone.getAngle());
            }
            case "Cylinder" -> {
                setEditFields("Высота", "Радиус", null, null);
                Cylinder cylinder = (Cylinder) curr;
                SpecificEdit1.setText(cylinder.getHeight()+"");
                SpecificEdit2.setText(cylinder.getRadius()+"");
            }
            case "Floor" -> {
                setEditFields("Высота", null, null, null);
                SpecificEdit1.setText(((Floor)curr).getH()+"");
            }
            case "Sphere" -> {
                setEditFields("Радиус", null, null, null);
                SpecificEdit1.setText(((Sphere)curr).getRadius()+"");
            }
            case "Torus" -> {
                setEditFields("Радиус", "Толщина", null, null);
                Torus torus = (Torus) curr;
                SpecificEdit1.setText(torus.getRadius()+"");
                SpecificEdit2.setText(torus.getThickness()+"");
            }
        }
    }

    private void setShapeFields(){
        shapeManager.setCurrentName(NameShapeEdit.getText());
        Shape curr = shapeManager.getCurrentShape();
        Vector3 center = new Vector3(Float.parseFloat(CenterXEdit.getText()),
                                    Float.parseFloat(CenterYEdit.getText()),
                                    Float.parseFloat(CenterZEdit.getText()));
        curr.setCenter(center);
        curr.setRotate(new Vector3(RotateXSlider.getValue(), RotateYSlider.getValue(), RotateZSlider.getValue()));
        curr.setSmoothRadius(Float.parseFloat(SmoothRadiusEdit.getText()));
        curr.setRoundingRadius(Float.parseFloat(RoundingRadiusEdit.getText()));
        curr.setMode(OperationModeEdit.getSelectionModel().getSelectedIndex());
        Material material = curr.getMaterial();
        Color ambient = AmbientColorPicker.getValue();
        Color diffuse = DiffuseColorPicker.getValue();
        material.setAmbientColor(new Vector3(ambient));
        material.setDiffuseColor(new Vector3(diffuse));
        material.setReflectivity((float) (ReflectivitySlider.getValue()/100.0f));
        material.setSpecular(SpecularFlag.isSelected());
        material.setSpecularity(Float.parseFloat(SpecularEdit.getText()));

        String type = curr.getClass().getSimpleName();
        switch (type) {
            case "Box" -> {
                Vector3 size = new Vector3(Float.parseFloat(SpecificEdit1.getText()),
                                           Float.parseFloat(SpecificEdit2.getText()),
                                           Float.parseFloat(SpecificEdit3.getText()));
                ((Box)curr).setSize(size);
            }
            case "BoxFrame" -> {
                Vector3 size = new Vector3(Float.parseFloat(SpecificEdit1.getText()),
                        Float.parseFloat(SpecificEdit2.getText()),
                        Float.parseFloat(SpecificEdit3.getText()));
                BoxFrame boxFrame = (BoxFrame) curr;
                boxFrame.setSize(size);
                boxFrame.setThickness(Float.parseFloat(SpecificEdit4.getText()));
            }
            case "CappedTorus" -> {
                CappedTorus cappedTorus = (CappedTorus) curr;
                cappedTorus.setRadius(Float.parseFloat(SpecificEdit1.getText()));
                cappedTorus.setThickness(Float.parseFloat(SpecificEdit2.getText()));
                cappedTorus.setAngle((float) SpecificSlider.getValue());
            }
            case "Capsule" -> {
                Capsule capsule = (Capsule) curr;
                capsule.setHeight(Float.parseFloat(SpecificEdit1.getText()));
                capsule.setRadius(Float.parseFloat(SpecificEdit2.getText()));
            }
            case "Cone" -> {
                Cone cone = (Cone) curr;
                cone.setHeight(Float.parseFloat(SpecificEdit1.getText()));
                cone.setAngle((float) SpecificSlider.getValue());
            }
            case "Cylinder" -> {
                Cylinder cylinder = (Cylinder) curr;
                cylinder.setHeight(Float.parseFloat(SpecificEdit1.getText()));
                cylinder.setRadius(Float.parseFloat(SpecificEdit2.getText()));
            }
            case "Floor" -> {
                ((Floor)curr).setH(Float.parseFloat(SpecificEdit1.getText()));
            }
            case "Sphere" -> {
                ((Sphere)curr).setRadius(Float.parseFloat(SpecificEdit1.getText()));
            }
            case "Torus" -> {
                Torus torus = (Torus) curr;
                torus.setRadius(Float.parseFloat(SpecificEdit1.getText()));
                torus.setThickness(Float.parseFloat(SpecificEdit2.getText()));
            }
        }
    }

    private void setLightEdits(){
        Light curr = lightManager.getCurrentLight();
        AmbientLightPicker.setValue(curr.getAmbientBase().toColor());
        DirectLightPicker.setValue(curr.getDirectBase().toColor());
        if (curr instanceof PointLight pointLight){
            LightParam.setText("Положение источника света");
            AmbientKEdit.setVisible(true);
            AmbientKEdit.setText(pointLight.getAmbientK()+"");
            DirectKEdit.setVisible(true);
            DirectKEdit.setText(pointLight.getDirectK()+"");
            LightXEdit.setText(pointLight.getPosition().x+"");
            LightYEdit.setText(pointLight.getPosition().y+"");
            LightZEdit.setText(pointLight.getPosition().z+"");
        }
        else{
            LightParam.setText("Направление света");
            AmbientKEdit.setVisible(false);
            DirectKEdit.setVisible(false);
            DirectionalLight directionalLight = (DirectionalLight) curr;
            LightXEdit.setText(directionalLight.getDirection().x+"");
            LightYEdit.setText(directionalLight.getDirection().y+"");
            LightZEdit.setText(directionalLight.getDirection().z+"");
        }
    }

    private void setLightFields(){
        Light curr = lightManager.getCurrentLight();
        curr.setAmbientBase(new Vector3(AmbientLightPicker.getValue()));
        curr.setDirectBase(new Vector3(DirectLightPicker.getValue()));
        Vector3 param = new Vector3(Float.parseFloat(LightXEdit.getText()),
                                    Float.parseFloat(LightYEdit.getText()),
                                    Float.parseFloat(LightZEdit.getText()));
        if (curr instanceof PointLight pointLight){
            pointLight.setAmbientK(Float.parseFloat(AmbientKEdit.getText()));
            pointLight.setDirectK(Float.parseFloat(DirectKEdit.getText()));
            pointLight.setPosition(param);
        }
        else {
            ((DirectionalLight)curr).setDirection(param);
        }
    }

    private void updateSettings(){
        settings.setWidth(Integer.parseInt(WidthEdit.getText()));
        settings.setHeight(Integer.parseInt(HeightEdit.getText()));
        settings.setMaxSteps(Integer.parseInt(MaxStepsEdit.getText()));
        settings.setMaxDistant(Integer.parseInt(MaxDistantEdit.getText()));
        settings.setEpsilon(Float.parseFloat(EpsilonEdit.getText()));
        settings.setReflectSteps(Integer.parseInt(ReflectStepsEdit.getText()));
        settings.setSoftOfShadow(Float.parseFloat(SoftOfShadowEdit.getText()));
        settings.setEpsilonShadow(Float.parseFloat(EpsilonShadowEdit.getText()));
        settings.setBackgroundColor(new Vector3(BackgroundColorPicker.getValue()));

        camera.setPosition(CamXEdit.getText(), CamYEdit.getText(), CamZEdit.getText());
        camera.setTarget(TargetXEdit.getText(), TargetYEdit.getText(), TargetZEdit.getText());
        camera.setFocal(Float.parseFloat(FocalEdit.getText()));
        camera.setCamera();

        setShapeFields();
        setLightFields();
    }

    private void prepareCanvas(){
        IntBuffer buffer = IntBuffer.allocate(settings.getWidth() * settings.getHeight());
        pixels = buffer.array();
        pixelBuffer = new PixelBuffer<>(settings.getWidth(), settings.getHeight(), buffer, PixelFormat.getIntArgbPreInstance());
        cameraView = new WritableImage(pixelBuffer);
    }
    int[] pixels;
    PixelBuffer<IntBuffer> pixelBuffer;

    public void update() {
        updateSettings();
        prepareCanvas();
        render.Render(pixels);
        pixelBuffer.updateBuffer(b->null);
        Display.setImage(cameraView);
    }

//    public void displayMap(ActionEvent event){
//        updateSettings();
//        prepareCanvas();
//        render.PerformanceMap(pixels);
//        pixelBuffer.updateBuffer(b->null);
//        Display.setImage(cameraView);
//    }

    public void addShape() {
        String shapeType = ShapeCreator.getValue();
        switch (shapeType) {
            case "Куб" -> shapeManager.add(new Box());
            case "Кубическая рамка" -> shapeManager.add(new BoxFrame());
            case "Фрагмент тора" -> shapeManager.add(new CappedTorus());
            case "Капсула" -> shapeManager.add(new Capsule());
            case "Конус" -> shapeManager.add(new Cone());
            case "Цилиндр" -> shapeManager.add(new Cylinder());
            case "Пол" -> shapeManager.add(new Floor());
            case "Сфера" -> shapeManager.add(new Sphere());
            case "Тор" -> shapeManager.add(new Torus());
        }
        ShapeCreator.setValue("Добавить фигуру");
    }

    public void addLight() {
        String lightType = LightCreator.getValue();
        switch (lightType){
            case "Направленный свет" -> lightManager.add(new DirectionalLight());
            case "Точечный свет" -> lightManager.add(new PointLight());
        }
    }
    public void deleteShape() {
        int del = ShapeList.getSelectionModel().getSelectedIndex();
        if (del > 0) ShapeList.getSelectionModel().select(del - 1);
        shapeManager.deleteShape(del);
        setShapeEdits();
    }

    public void deleteLight() {
        int del = LightList.getSelectionModel().getSelectedIndex();
        if (del > 0) LightList.getSelectionModel().select(del - 1);
        lightManager.deleteLight(del);
        setLightEdits();
    }

    public void shapeUp() {
        int moved = ShapeList.getSelectionModel().getSelectedIndex();
        if(moved > 0) {
            shapeManager.swapShape(moved, moved-1);
            setShapeEdits();
            ShapeList.getSelectionModel().select(moved-1);
        }
    }

    public void shapeDown() {
        int moved = ShapeList.getSelectionModel().getSelectedIndex();
        if(moved < ShapeList.getItems().size()-1){
            shapeManager.swapShape(moved, moved+1);
            setShapeEdits();
            ShapeList.getSelectionModel().select(moved+1);
        }
    }

    public void errorMsg(String msg, String info){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("JavaRaymarching");
        alert.setHeaderText(msg);
        alert.setContentText(info);
        alert.showAndWait();
    }

    public void saveScene() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить сцену");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
        File file = fileChooser.showSaveDialog(Display.getScene().getWindow());
        if (file != null){
            updateSettings();
            try{
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, render);
            }
            catch (IOException e){
                errorMsg("Ошибка сохранения файла", e.getMessage());
            }
        }
    }

    public void saveImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить изображение");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
        File file = fileChooser.showSaveDialog(Display.getScene().getWindow());
        if (file != null){
            BufferedImage img = SwingFXUtils.fromFXImage(Display.getImage(), null);
            try {
                ImageIO.write(img, "png", file);
            }
            catch (IOException e){
                errorMsg("Ошибка сохранения файла", e.getMessage());
            }
        }
    }

    public void openScene() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть сцену");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
        File file = fileChooser.showOpenDialog(Display.getScene().getWindow());
        if (file != null){
            loadFromFile(file);
            updateAllEdits();
        }
    }
    private void loadFromFile(File file) {
        try{
            render = objectMapper.readValue(file, RenderEngine.class);
        }
        catch (IOException e){
            errorMsg("Ошибка открытия файла", e.getMessage());
            return;
        }
        settings = render.getSettings();
        camera = render.getCamera();
        shapeManager = render.getShapeManager();
        lightManager = render.getLightManager();
    }

    public void newScene() {
        settings = new GlobalSettings();
        camera = new Camera(new Vector3(30f, 40f, -40f), new Vector3(-10, 9, 0), 2.5f);
        shapeManager = new ShapeManager();
        lightManager = new LightManager();
        render = new RenderEngine(settings, camera, shapeManager, lightManager);
        updateAllEdits();
    }

    public void holstMouseMove(MouseEvent mouseEvent) {
        Vector3 pos = render.getPosition((int) mouseEvent.getX(), (int) mouseEvent.getY());
        XPos.setText(String.format("%.2f", pos.x));
        YPos.setText(String.format("%.2f", pos.y));
        ZPos.setText(String.format("%.2f", pos.z));
        ArrayList<Shape> shapes = shapeManager.getShapes();
        ShapeHit target = new ShapeHit(1e10f, shapes.get(0));
        for(Shape s : shapes){
            target = s.mappingShape(pos, target);
        }
        if(target.dist()<0.1f){
            int index = shapes.indexOf(target.shape());
            ShapeInfoLabel.setText(shapeManager.getNames().get(index));
        }
        else {
            ShapeInfoLabel.setText("Не выбран ");
        }
    }

    public void holstMouseClick(MouseEvent mouseEvent) {
        Vector3 pos = render.getPosition((int) mouseEvent.getX(), (int) mouseEvent.getY());
        ArrayList<Shape> shapes = shapeManager.getShapes();
        ShapeHit target = new ShapeHit(1e10f, shapes.get(0));
        for(Shape s : shapes){
            target = s.mappingShape(pos, target);
        }
        if(target.dist()<0.1f){
            int index = shapes.indexOf(target.shape());

            ShapeList.getSelectionModel().select(index);
        }
    }
}