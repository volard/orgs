package com.orgs.orgs;

import com.orgs.orgs.Organization.Organization;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ResourceBundle;


public class MainView extends VBox {

    private final ResourceBundle bundle;

    private final TableView<Organization> tableView;
    private final TextField searchField;
    private final Button addButton;
    private final Button deleteButton;
    private final Button modifyButton;
    private final Button exportButton;
    private final Button importButton;
    private final MainController controller;

    public MainView(MainController controller, ResourceBundle bundle) {
        this.controller = controller;
        this.bundle = bundle;
        setPadding(new Insets(10));
        setSpacing(10);

        // Create the table
        tableView = new TableView<>();
        tableView.setEditable(true);

        // ID column
        TableColumn<Organization, Integer> idColumn = new TableColumn<>(bundle.getString("id"));
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));

        // Name column
        TableColumn<Organization, String> nameColumn = new TableColumn<>(bundle.getString("name"));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        // Type column
        TableColumn<Organization, String> typeColumn = new TableColumn<>(bundle.getString("type"));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().getShortName()));

        // Category column
        TableColumn<Organization, String> categoryColumn = new TableColumn<>(bundle.getString("category"));
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().getName()));

        // Money column
        TableColumn<Organization, Double> financialColumn = new TableColumn<>(bundle.getString("lastMoney") + " " + bundle.getString("currencySymbol"));
        categoryColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().financialReportMoney));

        tableView.getColumns().addAll(idColumn, categoryColumn, nameColumn, typeColumn);


        // Bind the table items to the controller's organizations
        tableView.setItems(controller.getOrganizations());
        tableView.setEditable(true);


        // Search field
        searchField = new TextField();
        FontIcon searchIcon = new FontIcon(FontAwesomeSolid.SEARCH);
        searchField.setPromptText(bundle.getString("search"));


        // New button
        addButton = new Button(bundle.getString("add"));
        FontIcon addIcon = new FontIcon(FontAwesomeSolid.PLUS_SQUARE);
        addButton.setGraphic(addIcon);

        // Delete button
        deleteButton = new Button(bundle.getString("delete"));
        FontIcon trashIcon = new FontIcon(FontAwesomeSolid.TRASH);
        deleteButton.setGraphic(trashIcon);

        // Modify button
        modifyButton = new Button(bundle.getString("modify"));
        FontIcon editIcon = new FontIcon(FontAwesomeSolid.EDIT);
        modifyButton.setGraphic(editIcon);

        // Export button
        exportButton = new Button(bundle.getString("export"));
        FontIcon exportIcon = new FontIcon(FontAwesomeSolid.FILE_EXPORT);
        exportButton.setGraphic(exportIcon);

        // Import button
        importButton = new Button(bundle.getString("import"));
        FontIcon importIcon = new FontIcon(FontAwesomeSolid.FILE_IMPORT);
        importButton.setGraphic(importIcon);

        // Place buttons in UI
        HBox buttonBox = new HBox(10, searchIcon, searchField, addButton, deleteButton, modifyButton, exportButton, importButton);
        HBox.setHgrow(searchField, Priority.ALWAYS);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        buttonBox.setAlignment(Pos.CENTER);


        // Add components to the view
        getChildren().addAll(buttonBox, tableView);

        // Set up event handlers
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        addButton.setOnAction(e -> addOrganization());
        deleteButton.setOnAction(e -> deleteOrganization());
        modifyButton.setOnAction(e -> modifyOrganization());
        exportButton.setOnAction(e -> exportOrganizations());
        importButton.setOnAction(e -> controller.importOrganizations());
        searchField.setOnAction(e -> searchOrganizations());
    }

    private void showNotification(NotificationType type, String message) {
        switch (type) {
            case ERROR -> Notifications.create()
                    .title(bundle.getString("notificationErrorTitle"))
                    .text(message)
                    .hideAfter(Duration.seconds(5))
                    .showError();
            case INFO -> Notifications.create()
                    .title(bundle.getString("notificationInfoTitle"))
                    .text(message)
                    .hideAfter(Duration.seconds(5))
                    .showInformation();
            case SUCCESS -> Notifications.create()
                    .title(bundle.getString("notificationSuccessTitle"))
                    .text(message)
                    .hideAfter(Duration.seconds(5))
                    .showConfirm();
        }
    }


    private void exportOrganizations() {
        if (controller.exportOrganizations()) {
            showNotification(NotificationType.SUCCESS, "");
        } else {
            showNotification(NotificationType.ERROR, "");
        }
    }

    private void addOrganization() {
        // Show a dialog to get new organization details
        // For simplicity, we're just adding a dummy organization here

    }

    private void deleteOrganization() {
        Organization selectedOrganization = tableView.getSelectionModel().getSelectedItem();
        if (selectedOrganization != null) {
            controller.deleteOrganization(selectedOrganization);
        }
    }

    private void modifyOrganization() {
        Organization selectedOrganization = tableView.getSelectionModel().getSelectedItem();
        if (selectedOrganization != null) {
            // Show a dialog to get modified organization details
            // For simplicity, we're just modifying the name here
//            Organization modifiedOrganization = new ConcreteOrganization("Modified Org", selectedOrganization.getType());
//            controller.modifyOrganization(selectedOrganization, modifiedOrganization);
        }
    }

    private void searchOrganizations() {
        String query = searchField.getText();
//        ObservableList<Organization> searchResults = controller.searchOrganizations(query);
//        tableView.setItems(searchResults);
    }
}
