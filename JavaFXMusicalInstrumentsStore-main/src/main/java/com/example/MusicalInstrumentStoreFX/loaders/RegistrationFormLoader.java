package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RegistrationFormLoader extends AbstractFormLoader{

    public RegistrationFormLoader(SpringFXMLLoader springFXMLLoader) {
        super(springFXMLLoader);
    }

        public void load(String title){ {
            FXMLLoader fxmlLoader = getSpringFXMLLoader().load("/user/registrationForm.fxml");
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setTitle(title); // Меняем заголовок окна
        }
    }
    @Override
    public Parent load() {
        // Реализация метода без параметров (можно оставить пустым, если не нужно)
        throw new UnsupportedOperationException("Этот метод не должен вызываться");
    }
}
