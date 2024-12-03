module edu.au.cpsc.checkpoint4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens edu.au.cpsc.checkpoint4 to javafx.fxml;
    exports edu.au.cpsc.checkpoint4;
}