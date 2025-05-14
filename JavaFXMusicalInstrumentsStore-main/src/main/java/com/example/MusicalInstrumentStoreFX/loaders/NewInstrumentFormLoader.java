package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NewInstrumentFormLoader extends AbstractFormLoader{

    public NewInstrumentFormLoader(SpringFXMLLoader springFXMLLoader) {
        super(springFXMLLoader);
    }
    @Override
    public Parent load(){
        FXMLLoader fxmlLoader = getSpringFXMLLoader().load("/instrument/newInstrumentForm.fxml");
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Добавление нового инструмента");
        return root;
    }
}
