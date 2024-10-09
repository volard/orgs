package com.orgs.orgs.Organization;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class OrganizationRegistry<T extends List<Organization>> {

    public OrganizationRegistry(T initialSet) {
        registry = initialSet;
    }

    private final T registry;

    public T getRegistry() {
        return registry;
    }

    public void clear() {
        registry.clear();
    }


    public Stream<Organization> findMatchingByPartOfName(String searchRequest) {
        return registry.stream().filter(org -> org.getName().toLowerCase().contains(searchRequest));
    }

    public Organization getById(int id) {
        return registry.stream().filter(org -> org.getId() == id).findAny().orElse(null);
    }

    private int generateUID() {
        int randomNum;
        do {
            randomNum = ThreadLocalRandom.current().nextInt(10000000, 99999999 + 1);
        } while (getById(randomNum) != null);
        return randomNum;
    }

    public boolean add(Organization organization) {
        organization.setId(generateUID());
        return registry.add(organization);
    }

    public void create(String name, Type type, Category category, int id) {
        Organization org = switch (category) {
            case Aircraft -> new AircraftOrganization(name, type);
            case Insurance -> new InsuranceOrganization(name, type);
            case ShipBuilding -> new ShipBuildingOrganization(name, type);
        };

        org.setId(id);
        registry.add(org);
    }

    public void create(String name, Type type, Category category) {
        create(name, type, category, generateUID());
    }

    public boolean delete(int id) {
        return registry.remove(getById(id));
    }

    public boolean delete(Organization organizationToDelete) {
        return registry.remove(organizationToDelete);
    }
}
