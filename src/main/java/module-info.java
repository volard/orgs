module com.orgs.orgs {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires jdk.compiler;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    opens com.orgs.orgs to javafx.fxml;
    exports com.orgs.orgs;
    exports com.orgs.orgs.Organization;

}