module com.example.stargo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires javafx.media;

    opens com.example.stargo to javafx.fxml;
    exports com.example.stargo;
}