package com.orgs.orgs.Organization;

public abstract class Organization {

    public Organization(String name, Type type) {
        this.name = name;
        this.type = type;
        this.financialReportMoney = getNextFinancialReport();
    }

    private double financialReportMoney;
    private int id = 0;
    private final String name;
    private final Type type;
    protected Category category;

    public void setId(int id) {
        if (this.id == 0) {
            this.id = id;
        }
    }

    public String getName() {
        return this.name;
    }

    public Category getCategory() {
        return this.category;
    }

    public Type getType() {
        return this.type;
    }

    public double getFinancialReportMoney() {
        return this.financialReportMoney;
    }

    public void setFinancialReportMoney(double financialReportMoney) {
        this.financialReportMoney = financialReportMoney;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "\n" +
                "Организация" + "\n" +
                "Код:" + id + "\n" +
                "Название:" + name + "\n" +
                "Вид:" + type.getFullName() +
                "\n";
    }

    public String getSearchableString() {
        return (this.id +
                this.name +
                this.type.getFullName() +
                this.type.getShortName() +
                this.category.getName() +
                this.financialReportMoney).toLowerCase();
    }


    public abstract double getNextFinancialReport();
}
