module com.blink.blinkclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.blink.blinkclient to javafx.fxml;
    exports com.blink.blinkclient;
}