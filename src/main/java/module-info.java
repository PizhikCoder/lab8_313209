module fxproject.lab8_313209 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires org.apache.commons.codec;
    requires org.postgresql.jdbc;
    exports shared.core.models;
    opens shared.core.models to javafx.base;
    opens fxproject to javafx.fxml;
    exports fxproject;
    exports fxproject.UI.Controllers;
    opens fxproject.UI.Controllers to javafx.fxml;
    exports fxproject.backend.core;
}