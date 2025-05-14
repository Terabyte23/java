package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BrandFormLoader extends AbstractFormLoader{

    public BrandFormLoader(SpringFXMLLoader springFXMLLoader) {
        super(springFXMLLoader);
    }

    @Override
    public Parent load() {
        try {
            FXMLLoader fxmlLoader = getSpringFXMLLoader().load("/brand/brandForm.fxml");
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setTitle("Создание нового бренда");
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
