module com.example.lastjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires kernel;
    requires layout;


    opens com.example.lastjavafx to javafx.fxml;
    exports com.example.lastjavafx;
}