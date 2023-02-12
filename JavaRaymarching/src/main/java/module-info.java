module com.coursework.javaraymarching {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.datatransfer;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires javafx.swing;

    opens com.coursework.javaraymarching to javafx.fxml;
    exports com.coursework.javaraymarching;
    exports com.coursework.javaraymarching.renderer;
    exports com.coursework.javaraymarching.renderer.shapes;
    exports com.coursework.javaraymarching.renderer.utils;
    exports com.coursework.javaraymarching.renderer.lights;
}