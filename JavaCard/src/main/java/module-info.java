module cda {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens cda to javafx.fxml;
    exports cda;
}
