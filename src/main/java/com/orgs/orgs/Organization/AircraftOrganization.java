package com.orgs.orgs.Organization;

// авиазавод
public class AircraftOrganization extends Organization {
    public AircraftOrganization(String name, Type type) {
        super(name, type);
        super.category = Category.Aircraft;
    }


    @Override
    public double getNextFinancialReport() {
        int averagePlaneRequests = 5;
        double averageCostsPerPlane = 5461345.91;
        double averageSalaryPerWorker = 426985386.58;
        double averagePlanePrice = 411698789;
        int workers = 32;
        return (double) Math.round(
                ((Math.random() + 0.5) * ((averagePlanePrice - averageCostsPerPlane) *
                        averagePlaneRequests - averageSalaryPerWorker * workers)) * 100) / 100;
    }
}
