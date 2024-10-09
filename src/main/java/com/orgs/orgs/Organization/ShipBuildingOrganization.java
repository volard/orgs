package com.orgs.orgs.Organization;

// судостроительная
public class ShipBuildingOrganization extends Organization {
    public ShipBuildingOrganization(String name, Type type) {
        super(name, type);
        super.category = Category.ShipBuilding;
    }

    @Override
    public double getNextFinancialReport() {
        int amountOfPeopleLiving = 4569;
        double averageTax = 4652.85;
        double averageRepairsCost = 438630.45;
        double averageInvestmentsAmount = 546899.74;
        return (double) Math.round(
                ((Math.random() + 0.8) * averageTax * amountOfPeopleLiving +
                        (Math.random() + 0.3) * averageInvestmentsAmount + averageRepairsCost) * 100) / 100;
    }
}
