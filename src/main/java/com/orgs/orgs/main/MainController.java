package com.orgs.orgs.main;

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

import java.io.File;
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
                "Зажиточный минимум",
                "Космические огурцы",
                "Бюрократия и сыновья",
                "Вечный двигатель",
                "Карманная вселенная",
                "Облачный подвал",
                "Гениальный тупик",
                "Светлое завтра",
                "Бумажный небоскрёб",
                "Прогресс наоборот",
                "Квантовая лопата",
                "Философский камень",
                "Вечный двигатель торговли",
                "Параллельные миры",
                "Созидательный хаос",
                "Пятое колесо",
                "Воздушные замки",
                "Цифровой борщ",
                "Галактический валенок",
                "Квантовая ложка",
                "Парадоксальные решения",
                "Вечнозелёный асфальт",
                "Умные бублики",
                "Космическая лапша",
                "Высокие технологии низин",
                "Подводный воздухоплаватель",
                "Гравитационный пух",
                "Мягкое железо",
                "Горячий снег",
                "Твёрдая вода",
                "Летающие корни",
                "Подземное солнце",
                "Невидимый прожектор",
                "Громкая тишина",
                "Квадратный круг",
                "Сухой дождь",
                "Медленный экспресс"
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

    public void filterData(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        filteredData.setPredicate(item -> {
            // If search field is empty, display all items
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // Search through all object fields
            return item.getSearchableString().contains(newValue.toLowerCase());
        });
    }

    public void createOrganization(String name, Type type, Category category) {
        organizationRegistry.create(name, type, category);
    }

    public void deleteOrganization(Organization organization) {
        organizationRegistry.delete(organization);
    }

    public void exportOrganizations(File file) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Organizations");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue(bundle.getString("id"));
            headerRow.createCell(1).setCellValue(bundle.getString("category"));
            headerRow.createCell(2).setCellValue(bundle.getString("name"));
            headerRow.createCell(3).setCellValue(bundle.getString("type"));
            headerRow.createCell(4).setCellValue(bundle.getString("lastMoney"));

            // Populate data rows
            int rowNum = 1;
            for (Organization org : organizationRegistry.getRegistry()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(org.getId());
                row.createCell(1).setCellValue(org.getCategory().toString());
                row.createCell(2).setCellValue(org.getName());
                row.createCell(3).setCellValue(org.getType().toString());
                row.createCell(4).setCellValue(org.getFinancialReportMoney());
            }

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Consider showing an error dialog to the user
        }
    }

    public void importOrganizations(File file) throws IOException {
        try (FileInputStream fileIn = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fileIn)) {

            Sheet sheet = workbook.getSheetAt(0);

            organizationRegistry.clear(); // Clear existing data

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                int id = (int) row.getCell(0).getNumericCellValue();
                Category category = Category.valueOf(row.getCell(1).getStringCellValue());
                String name = row.getCell(2).getStringCellValue();
                Type type = Type.valueOf(row.getCell(3).getStringCellValue());
                double lastMoney = row.getCell(4).getNumericCellValue();

                organizationRegistry.create(name, type, category, id, lastMoney);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Consider showing an error dialog to the user
        }
    }
}
