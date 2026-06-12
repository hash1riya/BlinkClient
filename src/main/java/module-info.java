module com.blink.blinkclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires java.management;
    requires com.fasterxml.jackson.datatype.jsr310;


    opens com.blink.blinkclient to javafx.fxml;
    opens com.blink.blinkclient.ui to javafx.fxml;
    opens com.blink.blinkclient.model to com.fasterxml.jackson.databind;
    exports com.blink.blinkclient;
}