module com.proj3.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.proj3.demo to javafx.fxml;
    exports com.proj3.demo;
}