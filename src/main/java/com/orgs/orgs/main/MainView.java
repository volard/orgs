package com.orgs.orgs.main;

import com.orgs.orgs.Organization.Organization;
import com.orgs.orgs.genericUI.Notification;
import com.orgs.orgs.genericUI.NotificationType;
import com.orgs.orgs.newOrg.AddOrganizationController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;


public class MainView extends VBox {

    private final ResourceBundle bundle;

    private final TableView<Organization> tableView;
    private final TextField searchField;
    private final Button addButton;
    private final Button deleteButton;
    private final Button exportButton;
    private final Button importButton;
    private final MainController controller;
    private final Stage primaryStage;

    public MainView(MainController controller, ResourceBundle bundle) {
        this.controller = controller;
        this.bundle = bundle;
        this.primaryStage = new Stage();

        // Set window padding
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
        TableColumn<Organization, String> financialColumn = new TableColumn<>(bundle.getString("lastMoney") + ", " + bundle.getString("currencySymbol"));
        financialColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.2f", cellData.getValue().getFinancialReportMoney())));

        // Adds multiple columns to a JavaFX TableView instance.
        tableView.getColumns().addAll(idColumn, categoryColumn, nameColumn, typeColumn, financialColumn);


        // Bind the table items to the controller's organizations
        tableView.setItems(controller.getFilteredData());
        tableView.setEditable(true);

        // Search field
        searchField = new TextField();
        FontIcon searchIcon = new FontIcon(FontAwesomeSolid.SEARCH);
        searchField.setPromptText(bundle.getString("search"));
        searchField.textProperty().addListener(controller::filterData);

        // New button
        addButton = new Button(bundle.getString("add"));
        FontIcon addIcon = new FontIcon(FontAwesomeSolid.PLUS_SQUARE);
        addButton.setGraphic(addIcon);

        // Delete button
        deleteButton = new Button(bundle.getString("delete"));
        FontIcon trashIcon = new FontIcon(FontAwesomeSolid.TRASH);
        deleteButton.setGraphic(trashIcon);

        // Export button
        exportButton = new Button(bundle.getString("export"));
        FontIcon exportIcon = new FontIcon(FontAwesomeSolid.FILE_EXPORT);
        exportButton.setGraphic(exportIcon);

        // Import button
        importButton = new Button(bundle.getString("import"));
        FontIcon importIcon = new FontIcon(FontAwesomeSolid.FILE_IMPORT);
        importButton.setGraphic(importIcon);

        // Place buttons in UI
        HBox buttonBox = new HBox(10, searchIcon, searchField, addButton, deleteButton, exportButton, importButton);

        // Stretch search field horizontally
        HBox.setHgrow(searchField, Priority.ALWAYS);

        // Stretch table view vertically
        VBox.setVgrow(tableView, Priority.ALWAYS);

        // Align items horizontally to center in controls container
        buttonBox.setAlignment(Pos.CENTER);

        // Add components to the view
        getChildren().addAll(buttonBox, tableView);

        // Set up event handlers
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        addButton.setOnAction(e -> addOrganization());
        deleteButton.setOnAction(e -> deleteOrganization());
        exportButton.setOnAction(e -> exportOrganizations());
        importButton.setOnAction(e -> importOrganizations());
    }

    private void exportOrganizations() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Organizations");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
        );
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            controller.exportOrganizations(file);
            Notification.showNotification(bundle, NotificationType.SUCCESS, "");
        }
    }

    private void importOrganizations() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Organizations");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
        );
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                controller.importOrganizations(file);
            } catch (IOException ex) {
                // Handle exception (e.g., show an error dialog)
                ex.printStackTrace();
            }
        }
    }

    private void addOrganization() {
        Stage addStage = new Stage();
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.setTitle(bundle.getString("newOrgTitle"));

        AddOrganizationController addController = new AddOrganizationController(addStage, bundle);
        Scene scene = new Scene(addController.getView(), 480, 200);
        addStage.setScene(scene);

        addStage.showAndWait();

        if (addController.getOrganizationDataHolder().getName() != null && !addController.getOrganizationDataHolder().getName().isEmpty()) {
            controller.createOrganization(
                    addController.getOrganizationDataHolder().getName(),
                    addController.getOrganizationDataHolder().getType(),
                    addController.getOrganizationDataHolder().getCategory()
            );
        }
    }

    private void deleteOrganization() {
        Organization selectedOrganization = tableView.getSelectionModel().getSelectedItem();
        if (selectedOrganization != null) {
            controller.deleteOrganization(selectedOrganization);
        }
    }
}
