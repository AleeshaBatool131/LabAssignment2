module com.example.dataentryform {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dataentryform to javafx.fxml;
    exports com.example.dataentryform;
}