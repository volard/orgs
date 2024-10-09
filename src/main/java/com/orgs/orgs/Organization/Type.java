package com.orgs.orgs.Organization;
public enum Type{
    ANO("Автономная некоммерческая организация", "АНО"),
    OOO("Общество с ограниченной ответственностью", "ООО"),
    IP("Индивидуальный предприниматель", "ИП"),
    OAO("Открытое акционерное общество", "ОАО"),
    ZAO("Закрытое акционерное общество", "ЗАО");

    private final String fullName;
    private final String shortName;

    Type(String fullName, String shortName){
        this.fullName = fullName;
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return shortName;
    }
}