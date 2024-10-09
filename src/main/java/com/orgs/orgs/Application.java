package com.orgs.orgs;

import atlantafx.base.theme.PrimerDark;
import com.orgs.orgs.main.MainController;
import com.orgs.orgs.main.MainView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) {

        Locale locale = Locale.forLanguageTag("ru");
        ResourceBundle bundle = ResourceBundle.getBundle("com.orgs.orgs.Strings", locale);

        // Custom stylesheet
        javafx.application.Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());

        BorderPane root = new BorderPane();

        // Create and set the main view
        MainView mainView = new MainView(new MainController(bundle), bundle);
        root.setCenter(mainView);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);

        stage.setTitle(bundle.getString("title"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}