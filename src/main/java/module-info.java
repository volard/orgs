module com.orgs.orgs {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;


    opens com.orgs.orgs to javafx.fxml;
    exports com.orgs.orgs;
}