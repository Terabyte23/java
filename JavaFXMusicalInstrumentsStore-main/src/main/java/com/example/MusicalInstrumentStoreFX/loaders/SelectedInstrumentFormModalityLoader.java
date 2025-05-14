package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.controller.SelectedInstrumentFormController;
import com.example.MusicalInstrumentStoreFX.model.entity.AppUser;
import com.example.MusicalInstrumentStoreFX.model.entity.Instruments;
import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SelectedInstrumentFormModalityLoader extends AbstractFormLoader{

    public SelectedInstrumentFormModalityLoader(SpringFXMLLoader springFXMLLoader) {
        super(springFXMLLoader);
    }

    public void load(Instruments instruments, AppUser appUser) {
        FXMLLoader fxmlLoader = getSpringFXMLLoader().load("/instrument/selectedInstrumentForm.fxml");
        Parent root = null;
        try {
            root = fxmlLoader.load();
            SelectedInstrumentFormController selectedInstrumentFormController = fxmlLoader.getController();
            selectedInstrumentFormController.setInstruments(instruments);
            selectedInstrumentFormController.setAppUser(appUser);

            getPrimaryStage().setTitle("Информация об инструменте");
            getPrimaryStage().initModality(Modality.APPLICATION_MODAL);
            getPrimaryStage().setScene(new Scene(root));
            getPrimaryStage().showAndWait();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public Parent load() {
        // Реализация метода без параметров (можно оставить пустым, если не нужно)
        throw new UnsupportedOperationException("Этот метод не должен вызываться");
    }
}
