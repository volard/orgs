package com.orgs.orgs.newOrg;

import com.orgs.orgs.Organization.Category;
import com.orgs.orgs.Organization.Type;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrganizationDataHolder {
    private StringProperty name;
    private ObjectProperty<Type> type;
    private ObjectProperty<Category> category;

    public OrganizationDataHolder() {
        this.name = new SimpleStringProperty();
        this.type = new SimpleObjectProperty<>(Type.IP);
        this.category = new SimpleObjectProperty<>(Category.Aircraft);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public Type getType() {
        return type.get();
    }

    public void setType(Type value) {
        type.set(value);
    }

    public ObjectProperty<Type> typeProperty() {
        return type;
    }

    public ObjectProperty<Category> categoryProperty() {
        return category;
    }

    public Category getCategory() {
        return category.get();
    }
}

