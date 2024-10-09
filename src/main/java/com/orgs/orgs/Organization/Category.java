package com.orgs.orgs.Organization;

public enum Category {
    ShipBuilding("Судостроительная"),
    Aircraft("Самолетная"),
    Insurance("Страховая");


    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
