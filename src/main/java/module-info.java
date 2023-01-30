module com.example.stargo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.stargo to javafx.fxml;
    exports com.example.stargo;
}