package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.controller.MenuFormController;
import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MenuFormLoader extends AbstractFormLoader {
    private MenuFormController menuFormController;

    public MenuFormLoader(SpringFXMLLoader springFXMLLoader) {
        super(springFXMLLoader);
    }

    @Override
    public Parent load() {
        FXMLLoader fxmlLoader = getSpringFXMLLoader().load("/menu/menuForm.fxml");
        try {
            Parent root = fxmlLoader.load();
            menuFormController = fxmlLoader.getController();
            return root;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки формы меню", e);
        }
    }

    public MenuFormController getMenuFormController() {
        return menuFormController;
    }
}
