package com.orgs.orgs;

import com.orgs.orgs.Organization.Organization;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;


public class MainView extends VBox {

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
        setPadding(new Insets(10));
        setSpacing(10);

        // Create the table
        tableView = new TableView<>();
        tableView.setEditable(true);

        // Add columns to the table
        TableColumn<Organization, Integer> idColumn = new TableColumn<>(bundle.getString("id"));
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));

        TableColumn<Organization, String> nameColumn = new TableColumn<>(bundle.getString("name"));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Organization, String> typeColumn = new TableColumn<>(bundle.getString("type"));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().getShortName()));

        TableColumn<Organization, String> categoryColumn = new TableColumn<>(bundle.getString("category"));
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().getName()));


        tableView.getColumns().addAll(idColumn, categoryColumn, nameColumn, typeColumn);

        // Bind the table items to the controller's organizations
        tableView.setItems(controller.getOrganizations());

        // Create search field and buttons
        searchField = new TextField();
        searchField.setPromptText(bundle.getString("search"));

        // Create management buttons
        addButton = new Button(bundle.getString("add"));
        deleteButton = new Button(bundle.getString("delete"));
        modifyButton = new Button(bundle.getString("modify"));
        exportButton = new Button(bundle.getString("export"));
        importButton = new Button(bundle.getString("import"));

        // Place buttons in UI
        HBox buttonBox = new HBox(10, addButton, deleteButton, modifyButton, exportButton, importButton);

        // Add components to the view
        getChildren().addAll(searchField, tableView, buttonBox);

        // Set up event handlers
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        addButton.setOnAction(e -> addOrganization());
        deleteButton.setOnAction(e -> deleteOrganization());
        modifyButton.setOnAction(e -> modifyOrganization());
        exportButton.setOnAction(e -> controller.exportOrganizations());
        importButton.setOnAction(e -> controller.importOrganizations());
        searchField.setOnAction(e -> searchOrganizations());
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
