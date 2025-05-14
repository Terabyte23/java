package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IncomeReportPageLoader extends AbstractFormLoader {

    public IncomeReportPageLoader(SpringFXMLLoader springFXMLLoader) {
        super(springFXMLLoader);
    }

    @Override
    public Parent load() {
        FXMLLoader fxmlLoader = getSpringFXMLLoader().load("/income/incomeReportPage.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке страницы отчета о доходах", e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Отчет о доходах");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
        return root;
    }
}
