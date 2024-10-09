module com.orgs.orgs {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires jdk.compiler;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens com.orgs.orgs to javafx.fxml;
    exports com.orgs.orgs;
    exports com.orgs.orgs.Organization;
}