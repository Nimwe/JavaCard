module cda {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;

    opens cda to javafx.fxml;

    //indique à la JVM d'autoriser la réflexion sur le package cda.model spécifiquement pour le module Jackson.
    opens cda.model to com.fasterxml.jackson.databind, javafx.base;
    exports cda;
}
