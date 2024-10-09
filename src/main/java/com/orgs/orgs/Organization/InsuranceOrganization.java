package com.orgs.orgs.Organization;

// страховая
public class InsuranceOrganization extends Organization {
    public InsuranceOrganization(String name, Type type) {
        super(name, type);
        super.category = Category.Insurance;
    }

    @Override
    public double getNextFinancialReport() {
        double insuranceAvailability = 0.2;
        return (double) Math.round(
                (Math.random() + Math.random() + Math.random() * 53156 + insuranceAvailability)
                        * 100
        ) / 100;
    }
}
