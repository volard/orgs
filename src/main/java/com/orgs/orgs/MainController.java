package com.orgs.orgs;

import com.orgs.orgs.Organization.Category;
import com.orgs.orgs.Organization.Organization;
import com.orgs.orgs.Organization.OrganizationRegistry;
import com.orgs.orgs.Organization.Type;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController {
    private final FilteredList<Organization> filteredData;
    private final ResourceBundle bundle;
    private final OrganizationRegistry<ObservableList<Organization>> organizationRegistry;

    public MainController(ResourceBundle bundle) {
        this.bundle = bundle;
        this.organizationRegistry = new OrganizationRegistry<>(FXCollections.observableArrayList());
        generateRandomOrganizations();
        filteredData = new FilteredList<>(organizationRegistry.getRegistry(), p -> true);
    }

    private void generateRandomOrganizations() {
        List<String> generationList = List.of(
                "Перспектива",
                "Визави",
                "Сквозная",
                "Констракшн и так далее",
                "Разрывная",
                "Передовые дела",
                "Употребительная за углом",
                "Пилотируемый космос",
                "Упокойная душа",
                "Хорошо и ещё хуже",
                "Расточительная компания", "Вкусно и тупо",
                "Напитки пригорья",
                "Зажиточный минимум"
        );


        // generate random organizations but names is fixed anyway
        for (String name : generationList) {
            Category randCategory = Category.values()[new Random().nextInt(Category.values().length)];
            Type randType = Type.values()[new Random().nextInt(Type.values().length)];

            organizationRegistry.create(name, randType, randCategory);
        }
    }

    public FilteredList<Organization> getFilteredData() {
        return filteredData;
    }

    public ObservableList<Organization> getOrganizationRegistry() {
        return organizationRegistry.getRegistry();
    }

    public void filterData(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        filteredData.setPredicate(item -> {
            // If search field is empty, display all items
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            // Here you need to define your search criteria
            // This example assumes YourDataType has a getName() method
            if (item.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            // Add more conditions here if you want to search on multiple fields

            return false; // Does not match
        });
    }

    public ObservableList<Organization> getOrganizations() {
        return organizationRegistry.getRegistry();
    }

    public void addOrganization(Organization organization) {
        organizationRegistry.add(organization);
    }

    public void deleteOrganization(Organization organization) {
        organizationRegistry.delete(organization);
    }

    public void modifyOrganization(Organization oldOrganization, Organization newOrganization) {
        int index = organizationRegistry.getRegistry().indexOf(oldOrganization);
        if (index != -1) {
            newOrganization.setId(oldOrganization.getId());
        }
    }

    public boolean exportOrganizations() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Organizations");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue(bundle.getString("id"));
            headerRow.createCell(1).setCellValue(bundle.getString("category"));
            headerRow.createCell(2).setCellValue(bundle.getString("name"));
            headerRow.createCell(3).setCellValue(bundle.getString("type"));

            // Populate data rows
            int rowNum = 1;
            for (Organization org : organizationRegistry.getRegistry()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(org.getId());
                row.createCell(1).setCellValue(org.getCategory().toString());
                row.createCell(2).setCellValue(org.getName());
                row.createCell(3).setCellValue(org.getType().toString());
            }

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream("organizations.xlsx")) {
                workbook.write(fileOut);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            // Consider showing an error dialog to the user
            return false;
        }
    }

    public void importOrganizations() {
        try (FileInputStream fileIn = new FileInputStream("organizations.xlsx");
             Workbook workbook = new XSSFWorkbook(fileIn)) {

            Sheet sheet = workbook.getSheetAt(0);

            organizationRegistry.clear(); // Clear existing data

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                int id = (int) row.getCell(0).getNumericCellValue();
                Category category = Category.valueOf(row.getCell(1).getStringCellValue());
                String name = row.getCell(2).getStringCellValue();
                Type type = Type.valueOf(row.getCell(3).getStringCellValue());

                organizationRegistry.create(name, type, category, id);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Consider showing an error dialog to the user
        }
    }

    public ObservableList<Organization> searchOrganizations(String query) {
        return organizationRegistry.getRegistry().filtered(org ->
                String.valueOf(org.getId()).contains(query) ||
                        org.getName().contains(query) ||
                        org.getType().toString().contains(query)
        );
    }
}
