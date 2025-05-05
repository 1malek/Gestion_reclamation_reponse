module com.example.lastjavafx {
    //requires javafx.controls;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires kernel;
    requires layout;
    requires stripe.java;


    opens com.example.lastjavafx to javafx.fxml;
    exports com.example.lastjavafx;
    exports com.example.lastjavafx.Controller;
    opens com.example.lastjavafx.Controller to javafx.fxml;
}