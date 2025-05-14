package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.controller.EditInstrumentsFormController;
import com.example.MusicalInstrumentStoreFX.model.entity.Instruments;
import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EditInstrumentFormLoader extends AbstractFormLoader {

    public EditInstrumentFormLoader(SpringFXMLLoader springFXMLLoader) {
        super(springFXMLLoader);
    }

    public void load(Instruments selectedInstrument) {
        try {
            var fxmlLoader = getSpringFXMLLoader().load("/instrument/editInstrumentForm.fxml");
            Parent root = fxmlLoader.load();
            EditInstrumentsFormController controller = fxmlLoader.getController();
            controller.setEditInstruments(selectedInstrument);

            Scene scene = new Scene(root);
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setTitle("Редактирование инструмента");
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().setResizable(false);
            getPrimaryStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Parent load() {
        // Реализация метода без параметров (можно оставить пустым, если не нужно)
        throw new UnsupportedOperationException("Этот метод не должен вызываться");
    }
}
