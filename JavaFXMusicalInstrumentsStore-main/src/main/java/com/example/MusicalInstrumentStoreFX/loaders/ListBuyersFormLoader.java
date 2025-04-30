package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ListBuyersFormLoader {
    private final SpringFXMLLoader springFXMLLoader;

    public ListBuyersFormLoader(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

    public void loadListBuyersForm(Stage primaryStage) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/buyers/listBuyersForm.fxml");
        Parent root = null;

        try {
            root = fxmlLoader.load();  // Загружаем новый root
        } catch (IOException e) {
            throw new RuntimeException(e);  // Обработка исключения
        }

        // Создаем новую сцену с новым корнем
        Scene scene = new Scene(root);

        // Получаем primaryStage и устанавливаем сцену
        primaryStage.setScene(scene);  // Устанавливаем новую сцену
        primaryStage.setTitle("Список покупателей");  // Устанавливаем заголовок
        primaryStage.centerOnScreen();  // Центрируем на экране
        primaryStage.show();  // Показываем сцену
    }
}
