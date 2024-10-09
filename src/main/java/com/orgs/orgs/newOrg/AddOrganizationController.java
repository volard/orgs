package com.orgs.orgs.newOrg;

import com.orgs.orgs.genericUI.Notification;
import com.orgs.orgs.genericUI.NotificationType;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class AddOrganizationController {
    private final AddOrganizationView view;
    private final ResourceBundle bundle;
    private final OrganizationDataHolder organizationDataHolder;
    private final Stage stage;

    public AddOrganizationController(Stage stage, ResourceBundle bundle) {
        this.stage = stage;
        this.bundle = bundle;
        this.organizationDataHolder = new OrganizationDataHolder();
        this.view = new AddOrganizationView(bundle);

        bindModel();
        setupEventHandlers();
    }

    private void bindModel() {
        organizationDataHolder.nameProperty().bindBidirectional(view.getNameField().textProperty());
        organizationDataHolder.typeProperty().bindBidirectional(view.getTypeComboBox().valueProperty());
        organizationDataHolder.categoryProperty().bindBidirectional(view.getCategoryComboBox().valueProperty());
    }

    private void setupEventHandlers() {
        view.getSaveButton().setOnAction(e -> save());
        view.getCancelButton().setOnAction(e -> cancel());
    }

    private void save() {
        if (validateInput()) {
            stage.close();
        }
    }

    private void cancel() {
        stage.close();
    }

    private boolean validateInput() {
        String name = organizationDataHolder.getName().trim();
        if (name.isEmpty()) {
            Notification.showNotification(this.bundle, NotificationType.ERROR, bundle.getString("AddOrganizationView_emptyName_error"));
            return false;
        }
        // Add more validation as needed
        return true;
    }

    public AddOrganizationView getView() {
        return view;
    }

    public OrganizationDataHolder getOrganizationDataHolder() {
        return organizationDataHolder;
    }
}
