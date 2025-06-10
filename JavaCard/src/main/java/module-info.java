module cda {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens cda to javafx.fxml;
    opens cda.model to javafx.base;
    exports cda;
}
