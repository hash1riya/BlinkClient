module com.blink.blinkclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;


    opens com.blink.blinkclient to javafx.fxml;
    exports com.blink.blinkclient;
}