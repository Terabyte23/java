package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.controller.EditBuyerFormController;
import com.example.MusicalInstrumentStoreFX.model.entity.AppUser;
import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EditBuyersFormLoader {
    private final SpringFXMLLoader springFXMLLoader;

    public EditBuyersFormLoader(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

    public void loadEditBuyersForm(AppUser appUser, Stage primaryStage) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/buyers/editBuyerForm.fxml");

        try {
            Parent editBuyerFormRoot = fxmlLoader.load();
            EditBuyerFormController editBuyerFormController = fxmlLoader.getController();
            editBuyerFormController.setEditBuyer(appUser);

            Scene scene = new Scene(editBuyerFormRoot);
            primaryStage.setTitle("Редактирование покупателя");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
