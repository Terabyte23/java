package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.controller.IncomeReportController;
import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IncomeReportPageLoader {

    private final SpringFXMLLoader springFXMLLoader;

    public IncomeReportPageLoader(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

    public void loadIncomeReportPage(Stage primaryStage) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/income/incomeReportPage.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке страницы отчета о доходах", e);
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Отчет о доходах");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
