package com.orgs.orgs.newOrg;

import com.orgs.orgs.Organization.Category;
import com.orgs.orgs.Organization.Type;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.ResourceBundle;

public class AddOrganizationView extends VBox {
    private final TextField nameField;
    private final ComboBox<Type> typeComboBox;
    private final Button saveButton;
    private final Button cancelButton;
    private final ComboBox<Category> categoryComboBox;
    private final ResourceBundle bundle;

    public AddOrganizationView(ResourceBundle bundle) {
        this.bundle = bundle;
        setPadding(new Insets(10));
        setSpacing(10);

        Label nameLabel = new Label(bundle.getString("name"));
        nameField = new TextField();

        Label typeLabel = new Label(bundle.getString("type"));
        typeComboBox = new ComboBox<>();

        // Set items to all Category values
        typeComboBox.setItems(FXCollections.observableArrayList(Type.values()));

        // Set a custom cell factory to display the name property
        typeComboBox.setCellFactory(listView -> new ListCell<Type>() {
            @Override
            protected void updateItem(Type item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getFullName());
                }
            }
        });

        // Set a custom string converter to display the selected item's name
        typeComboBox.setConverter(new StringConverter<Type>() {
            @Override
            public String toString(Type category) {
                return category == null ? null : category.getFullName();
            }

            @Override
            public Type fromString(String string) {
                return null; // Not needed for this use case
            }
        });
        typeComboBox.setValue(Type.IP);


        Label categoryLabel = new Label(bundle.getString("category"));
        categoryComboBox = new ComboBox<>();

        // Set items to all Category values
        categoryComboBox.setItems(FXCollections.observableArrayList(Category.values()));

        // Set a custom cell factory to display the name property
        categoryComboBox.setCellFactory(listView -> new ListCell<Category>() {
            @Override
            protected void updateItem(Category item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        // Set a custom string converter to display the selected item's name
        categoryComboBox.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category category) {
                return category == null ? null : category.getName();
            }

            @Override
            public Category fromString(String string) {
                return null; // Not needed for this use case
            }
        });
        categoryComboBox.setValue(Category.Aircraft);

        saveButton = new Button(bundle.getString("add"));
        cancelButton = new Button(bundle.getString("cancel"));

        HBox buttonBox = new HBox(10, saveButton, cancelButton);
        HBox nameBox = new HBox(10, nameLabel, nameField);
        HBox categoriesBox = new HBox(10, categoryLabel, categoryComboBox);
        HBox typeBox = new HBox(10, typeLabel, typeComboBox);

        HBox.setHgrow(nameField, Priority.ALWAYS);
        HBox.setHgrow(categoriesBox, Priority.ALWAYS);
        HBox.setHgrow(typeBox, Priority.ALWAYS);

        nameBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        categoriesBox.setAlignment(Pos.CENTER_LEFT);
        typeBox.setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(nameBox, categoriesBox, typeBox, buttonBox);
    }

    public TextField getNameField() {
        return nameField;
    }

    public ComboBox<Type> getTypeComboBox() {
        return typeComboBox;
    }

    public ComboBox<Category> getCategoryComboBox() {
        return categoryComboBox;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }
}
