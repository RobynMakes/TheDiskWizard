module goodwill.robyn.thediskwizard {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires eu.hansolo.tilesfx;

    opens goodwill.robyn.thediskwizard to javafx.fxml;
    exports goodwill.robyn.thediskwizard;
}