package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.controller.SelectedInstrumentFormController;
import com.example.MusicalInstrumentStoreFX.model.entity.Instruments;
import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SelectedInstrumentFormModalityLoader {
    private final SpringFXMLLoader springFXMLLoader;

    public SelectedInstrumentFormModalityLoader(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

    public void loadSelectedInstrumentFormModality(Instruments instruments, Stage primaryStage){
        FXMLLoader fxmlLoader = springFXMLLoader.load("/instrument/selectedInstrumentForm.fxml");
        Parent root = null;
        try {
            root = fxmlLoader.load();
            SelectedInstrumentFormController selectedInstrumentFormController = fxmlLoader.getController();
            selectedInstrumentFormController.setInstruments(instruments);

            primaryStage.setTitle("Информация об инструменте");
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setScene(new Scene(root));
            primaryStage.showAndWait();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
