package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MainFormLoader extends AbstractFormLoader {

    public MainFormLoader(SpringFXMLLoader springFXMLLoader) {
        super(springFXMLLoader);
    }
    @Override
    public Parent load() {
        try {
            FXMLLoader fxmlLoader = getSpringFXMLLoader().load("/main/mainForm.fxml");
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setTitle("Главное меню");
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().show();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки главной формы", e);
        }
        return null;
    }
}
