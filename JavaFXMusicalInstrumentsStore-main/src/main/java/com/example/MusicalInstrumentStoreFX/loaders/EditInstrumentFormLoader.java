package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.controller.EditInstrumentsFormController;
import com.example.MusicalInstrumentStoreFX.model.entity.Instruments;
import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EditInstrumentFormLoader {
    private final SpringFXMLLoader springFXMLLoader;

    public EditInstrumentFormLoader(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

    public void loadEditInstrumentForm(Stage primaryStage, Instruments selectedInstrument) {
        try {
            var fxmlLoader = springFXMLLoader.load("/instrument/editInstrumentForm.fxml");
            Parent root = fxmlLoader.load();
            EditInstrumentsFormController controller = fxmlLoader.getController();
            controller.setEditInstruments(selectedInstrument);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Редактирование инструмента");
            primaryStage.centerOnScreen();
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
