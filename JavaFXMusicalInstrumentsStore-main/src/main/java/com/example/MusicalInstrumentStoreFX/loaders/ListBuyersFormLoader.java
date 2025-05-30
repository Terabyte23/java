package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ListBuyersFormLoader extends AbstractFormLoader {

    public ListBuyersFormLoader(SpringFXMLLoader springFXMLLoader) {
        super(springFXMLLoader);
    }
    @Override
    public Parent load() {
        FXMLLoader fxmlLoader = getSpringFXMLLoader().load("/buyers/listBuyersForm.fxml");
        Parent root = null;

        try {
            root = fxmlLoader.load();  // Загружаем новый root
        } catch (IOException e) {
            throw new RuntimeException(e);  // Обработка исключения
        }

        // Создаем новую сцену с новым корнем
        Scene scene = new Scene(root);

        // Получаем primaryStage и устанавливаем сцену
        getPrimaryStage().setScene(scene);  // Устанавливаем новую сцену
        getPrimaryStage().setTitle("Список покупателей");  // Устанавливаем заголовок
        getPrimaryStage().centerOnScreen();  // Центрируем на экране
        getPrimaryStage().show();  // Показываем сцену
        return root;
    }
}
