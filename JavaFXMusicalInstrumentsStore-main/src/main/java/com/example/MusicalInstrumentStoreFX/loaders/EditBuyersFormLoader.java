package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.controller.EditBuyerFormController;
import com.example.MusicalInstrumentStoreFX.model.entity.AppUser;
import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EditBuyersFormLoader extends AbstractFormLoader{

    public EditBuyersFormLoader(SpringFXMLLoader springFXMLLoader) {
        super(springFXMLLoader);
    }

    public void load(AppUser appUser) {
        FXMLLoader fxmlLoader = getSpringFXMLLoader().load("/buyers/editBuyerForm.fxml");

        try {
            Parent editBuyerFormRoot = fxmlLoader.load();
            EditBuyerFormController editBuyerFormController = fxmlLoader.getController();
            editBuyerFormController.setEditBuyer(appUser);

            Scene scene = new Scene(editBuyerFormRoot);
            getPrimaryStage().setTitle("Редактирование покупателя");
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setResizable(false);
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().show();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Override
    public Parent load() {
        // Реализация метода без параметров (можно оставить пустым, если не нужно)
        throw new UnsupportedOperationException("Этот метод не должен вызываться");
    }
}
