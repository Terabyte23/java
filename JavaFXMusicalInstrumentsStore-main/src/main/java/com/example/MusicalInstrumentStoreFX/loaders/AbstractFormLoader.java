package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class AbstractFormLoader {
    private final SpringFXMLLoader springFXMLLoader;

    public AbstractFormLoader(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

    public Stage getPrimaryStage() {
        return this.springFXMLLoader.getPrimaryStage();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.springFXMLLoader.setPrimaryStage(primaryStage);
    }

    protected SpringFXMLLoader getSpringFXMLLoader() {
        return springFXMLLoader;
    }

    protected void closeWindow(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Вы уверены, что хотите закрыть приложение?");
        alert.setContentText("Нажмите ОК для закрытия или Отмена для продолжения.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            Platform.exit();
        } else {
            event.consume();
        }
    }

    // Абстрактный метод без параметров
    abstract public Parent load();
}
